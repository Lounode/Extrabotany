package io.github.lounode.extrabotany.common.item.relic;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.internal.ManaBurst;
import vazkii.botania.api.item.Relic;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.LensEffectItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.entity.ManaBurstEntity;
import vazkii.botania.common.helper.PlayerHelper;
import vazkii.botania.common.item.equipment.tool.bow.LivingwoodBowItem;
import vazkii.botania.common.item.relic.RelicImpl;
import vazkii.botania.xplat.XplatAbstractions;

import io.github.lounode.extrabotany.api.entity.EntityNbtHelper;
import io.github.lounode.extrabotany.common.entity.MagicArrowEntity;
import io.github.lounode.extrabotany.common.item.enchantment.ICustomEnchantable;
import io.github.lounode.extrabotany.common.lib.LibAdvancementNames;
import io.github.lounode.extrabotany.common.sounds.ExtraBotanySounds;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.entity.MagicArrowEntity.TAG_DAMAGE;
import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class FailnaughtItem extends LivingwoodBowItem implements LensEffectItem, ICustomEnchantable {
	private static final float ADVANCEMENT_REQUIRE = 100.0F;
	private static final float ATTACK_BOX_RADIUS = 2.0F;
	private static final int HIT_ENTITY_COST = 50;
	public static final float MINIMUM_SHOOT_PROCESS = 0.1F;

	public static final float[] TIER_PROCESS = { 0, 0, 0.35F, 0.7F, 0.9F };
	public static final int[] MANA_PER_USE_MAX = { 0, 350, 500, 650, 800 };

	private static final List<Enchantment> SUPPORT_ENCHANTMENTS = Arrays.asList(
			Enchantments.POWER_ARROWS,
			Enchantments.PUNCH_ARROWS,
			Enchantments.FLAMING_ARROWS,
			Enchantments.MULTISHOT,
			Enchantments.QUICK_CHARGE,
			Enchantments.VANISHING_CURSE
	);
	private static final float DEFAULT_CHARGE_SPEED = 0.25F;
	private static final float QUICK_CHARGE_BONUS_PER_LEVEL = 0.1F;

	public FailnaughtItem(Properties props) {
		super(props);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack itemstack = player.getItemInHand(hand);
		int multiShoutLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, itemstack);
		float chargeProgress = getChargeProcess(itemstack, player);

		boolean flag = false;

		var relic = XplatAbstractions.INSTANCE.findRelic(itemstack);
		if (relic != null &&
				relic.isRightPlayer(player) &&
				ManaItemHandler.instance().requestManaExactForTool(itemstack, player,
						getManaForUse(chargeProgress) * (multiShoutLevel > 1 ? 3 : 1), false)

		) {
			flag = true;
		}

		if (!player.getAbilities().instabuild && !flag) {
			return InteractionResultHolder.fail(itemstack);
		} else {
			player.startUsingItem(hand);
			return InteractionResultHolder.consume(itemstack);
		}
	}

	/*
	* Normal: 80tick
	* QC1: 57tick
	* QC2: 44tick
	* QC3: 36tick
	* */
	@Override
	public void releaseUsing(ItemStack stack, Level world, LivingEntity livingEntity, int timeLeft) {
		if (world.isClientSide()) {
			return;
		}
		if (livingEntity instanceof Player player) {
			float chargeProgress = getChargeProcess(stack, player);

			int quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, stack);
			if (quickChargeLevel > 0) {
				ManaItemHandler.instance().requestManaExactForTool(stack, player, 100 * quickChargeLevel, true);
			}

			if (chargeProgress < MINIMUM_SHOOT_PROCESS) {
				return;
			}

			int multiShoutLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack);
			var relic = XplatAbstractions.INSTANCE.findRelic(stack);
			if (relic != null &&
					relic.isRightPlayer(player) &&
					(player.getAbilities().instabuild
							||
							ManaItemHandler.instance().requestManaExactForTool(stack, player,
									getManaForUse(chargeProgress) * (multiShoutLevel > 1 ? 3 : 1), true))) {
				int manaInBurst = getManaForUse(chargeProgress) * (multiShoutLevel > 1 ? 3 : 1);

				float spreadAngle = 10.0f;

				for (int i = 0; i < (multiShoutLevel > 0 ? 3 : 1); i++) {
					ManaBurstEntity burst = getBurst(player, player.getMainHandItem(), manaInBurst, getTier(chargeProgress));

					if (multiShoutLevel > 0 && i > 0) {
						float angle = (i == 1 ? -spreadAngle : spreadAngle) * Mth.DEG_TO_RAD;
						burst.setDeltaMovement(burst.getDeltaMovement().yRot(angle));
					}

					player.level().addFreshEntity(burst);
				}

				player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ExtraBotanySounds.FAILNAUGHT_SHOOT, SoundSource.PLAYERS, 1F, 1F);
			}
		}
	}

	public ManaBurstEntity getBurst(Player player, ItemStack stack, int mana, int tier) {
		MagicArrowEntity burst = new MagicArrowEntity(player);

		float motionModifier = 7F;

		burst.setColor(0x20FF20);
		burst.setMana(mana);
		burst.setStartingMana(mana);
		burst.setManaLossPerTick(MANA_PER_USE_MAX[tier] / 87.5f);
		burst.setGravity(0F);
		burst.setDeltaMovement(burst.getDeltaMovement().scale(motionModifier));

		burst.setSourceLens(stack.copy());

		float chargeProcess = getChargeProcess(stack, player);
		float tierProcess = getProcessInTier(chargeProcess);

		float baseDamage = 10;
		int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		if (powerLevel > 0 &&
				ManaItemHandler.instance().requestManaExactForTool(stack, player, 50 * powerLevel, true)) {
			baseDamage = baseDamage + 0.5f + 0.5f * powerLevel;
		}
		float damage = baseDamage * (tier * tierProcess);

		burst.setDamage(damage);
		return burst;
	}

	@Override
	public void apply(ItemStack stack, BurstProperties props, Level level) {}

	@Override
	public boolean collideBurst(ManaBurst burst, HitResult pos, boolean isManaBlock, boolean shouldKill, ItemStack stack) {
		return shouldKill;
	}

	@Override
	public void updateBurst(ManaBurst burst, ItemStack stack) {
		ThrowableProjectile entity = burst.entity();
		if (entity.level().isClientSide()) {
			return;
		}
		Entity thrower = entity.getOwner();

		if (!(thrower instanceof Player player) || !thrower.isAlive()) {
			entity.discard();
			return;
		}

		AABB axis = new AABB(entity.getX(), entity.getY(), entity.getZ(), entity.xOld, entity.yOld, entity.zOld).inflate(ATTACK_BOX_RADIUS);
		List<LivingEntity> entities = entity.level().getEntitiesOfClass(LivingEntity.class, axis).stream()
				.filter(e -> e != thrower)
				.filter(e -> !(e instanceof Player other && !player.canHarmPlayer(other)))
				.filter(e -> e.hurtTime == 0)
				.toList();
		float chargeProcess = getChargeProcess(stack, player);
		int tier = getTier(chargeProcess);
		float tierProcess = getProcessInTier(chargeProcess);

		for (LivingEntity living : entities) {
			int mana = burst.getMana();
			if (mana < HIT_ENTITY_COST) {
				return;
			}
			burst.setMana(mana - HIT_ENTITY_COST);

			float damage = EntityNbtHelper.getNBT(entity).getFloat(TAG_DAMAGE);
			DamageSource source = player.damageSources().playerAttack(player);
			living.hurt(source, damage);

			int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, stack);
			if (punchLevel > 0 &&
					ManaItemHandler.instance().requestManaExactForTool(stack, player, 20 * punchLevel, true)) {
				living.knockback(punchLevel * 0.5f,
						entity.getX() - living.getX(),
						entity.getZ() - living.getZ());
			}

			if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, stack) > 0 &&
					ManaItemHandler.instance().requestManaExactForTool(stack, player, 10, true)) {
				living.setSecondsOnFire(100);
			}

			if (living instanceof Skeleton skeleton && !skeleton.isAlive()) {
				float fx = (float) (player.getX() - living.getX());
				float fz = (float) (player.getZ() - living.getZ());
				float horizontalDistanceSq = Mth.sqrt(fx * fx + fz * fz);

				if (horizontalDistanceSq > ADVANCEMENT_REQUIRE) {
					PlayerHelper.grantCriterion((ServerPlayer) player, prefix("main/" + LibAdvancementNames.HUNDRED_BLOCK_PIERCE), "code_triggered");
				}
			}
		}
	}

	@Override
	public int getUseDuration(ItemStack stack) {
		return 72000;
	}

	public float chargeVelocityMultiplier(ItemStack itemStack, LivingEntity livingEntity) {
		if (livingEntity instanceof Player player) {
			int quickChargeLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, itemStack);
			if (ManaItemHandler.instance().requestManaExactForTool(itemStack, player, 100 * quickChargeLevel, false)) {
				return DEFAULT_CHARGE_SPEED + QUICK_CHARGE_BONUS_PER_LEVEL * quickChargeLevel;
			}
		}

		return DEFAULT_CHARGE_SPEED;
	}

	@Override
	public boolean doParticles(ManaBurst burst, ItemStack stack) {
		return true;
	}

	public float getChargeProcess(ItemStack stack, LivingEntity entity) {
		return Mth.clamp((getUseDuration(stack) - entity.getUseItemRemainingTicks()) * chargeVelocityMultiplier(stack, entity) / 20.0F, 0.0F, 1.0F);
	}

	public int getTier(float chargeProcess) {
		chargeProcess = Mth.clamp(chargeProcess, 0.0f, 1.0f);

		if (chargeProcess < TIER_PROCESS[2]) {
			return 1; // 0.0 - 0.35
		} else if (chargeProcess < TIER_PROCESS[3]) {
			return 2; // 0.35 - 0.7
		} else if (chargeProcess < TIER_PROCESS[4]) {
			return 3; // 0.7 - 0.9
		} else {
			return 4; // 0.9 - 1.0
		}
	}

	public float getProcessInTier(float chargeProcess) {
		return switch (getTier(chargeProcess)) {
			case 1 -> chargeProcess / TIER_PROCESS[2];
			case 2 -> (chargeProcess - TIER_PROCESS[2]) / (TIER_PROCESS[3] - TIER_PROCESS[2]);
			case 3 -> (chargeProcess - TIER_PROCESS[3]) / (TIER_PROCESS[4] - TIER_PROCESS[3]);
			case 4 -> (chargeProcess - TIER_PROCESS[4]) / (1.0f - TIER_PROCESS[4]);
			default -> 0;
		};
	}

	public int getManaForUse(float chargeProcess) {
		chargeProcess = Mth.clamp(chargeProcess, 0.0f, 1.0f);
		int tier = getTier(chargeProcess);
		float tierProcess = getProcessInTier(chargeProcess);

		return switch (tier) {
			case 1 -> (int) (MANA_PER_USE_MAX[1] * tierProcess);
			case 2 -> (int) (MANA_PER_USE_MAX[1] + (MANA_PER_USE_MAX[2] - MANA_PER_USE_MAX[1]) * tierProcess);
			case 3 -> (int) (MANA_PER_USE_MAX[2] + (MANA_PER_USE_MAX[3] - MANA_PER_USE_MAX[2]) * tierProcess);
			case 4 -> (int) (MANA_PER_USE_MAX[3] + (MANA_PER_USE_MAX[4] - MANA_PER_USE_MAX[3]) * tierProcess);
			default -> 0;
		};
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		return 0;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (!world.isClientSide && entity instanceof Player player) {
			var relic = XplatAbstractions.INSTANCE.findRelic(stack);
			if (relic != null) {
				relic.tickBinding(player);
			}
		}
	}

	@Override
	public boolean isValidRepairItem(ItemStack bow, ItemStack material) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag flags) {
		tooltip.add(Component.translatable("tooltip.extrabotany.failnaught").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.literal(""));
		RelicImpl.addDefaultTooltip(stack, tooltip);
	}

	public static Relic makeRelic(ItemStack stack) {
		return new RelicImpl(stack, null);
	}

	@Override
	public boolean isEnchantable(ItemStack pStack) {
		return true;
	}

	@Override
	public int getEnchantmentValue() {
		return 30;
	}

	@Override
	public boolean canEnchant(ItemStack stack, Enchantment enchantment) {
		return SUPPORT_ENCHANTMENTS.contains(enchantment);
	}

	@Override
	public boolean canEnchantOnTable(ItemStack stack, Enchantment enchantment) {
		return SUPPORT_ENCHANTMENTS.contains(enchantment);
	}
}
