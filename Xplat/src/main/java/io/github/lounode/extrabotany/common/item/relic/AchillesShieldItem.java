package io.github.lounode.extrabotany.common.item.relic;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;

import vazkii.botania.api.item.Relic;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.handler.PixieHandler;
import io.github.lounode.extrabotany.common.util.ItemStackDataHelper;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.relic.RelicImpl;

import io.github.lounode.extrabotany.common.item.equipment.shield.ManasteelShieldItem;
import io.github.lounode.extrabotany.common.item.material.ItemTiers;

import java.util.List;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class AchillesShieldItem extends ManasteelShieldItem {

	public static final String TAG_RELEASED = "released";

	private static final float RELEASE_ABSORPTION_REQUIRE = 10.0F;
	private static final float MAX_ABSORPTION = 20.0F;

	public AchillesShieldItem(Properties properties) {
		super(properties.durability(ItemTiers.ACHILLES_SHIELD.getUses()), ItemTiers.ACHILLES_SHIELD);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!isReleased(stack)) {
			if (hand == InteractionHand.MAIN_HAND &&
					player.isSecondaryUseActive() &&
					player.getAbsorptionAmount() >= RELEASE_ABSORPTION_REQUIRE) {
				setReleased(stack, true);
				return InteractionResultHolder.success(stack);
			}
			return super.use(level, player, hand);
		} else {
			if (hand == InteractionHand.MAIN_HAND &&
					player.isSecondaryUseActive()) {
				setReleased(stack, false);
				return InteractionResultHolder.success(stack);
			}
			return InteractionResultHolder.pass(stack);
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		if (!world.isClientSide && entity instanceof Player player) {
			var relic = EXplatAbstractions.INSTANCE.findRelic(stack);
			if (relic != null) {
				relic.tickBinding(player);
			}

			if (isReleased(stack) && player.tickCount % 10 == 0) {
				if (player.getAbsorptionAmount() <= 0F) {
					setReleased(stack, false);
				}
				player.setAbsorptionAmount(player.getAbsorptionAmount() - 1);
			}
		}
	}

	@Override
	public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
		super.onUseTick(level, entity, stack, remainingUseDuration);
		if (isReleased(stack)) {
			entity.stopUsingItem();
		}
	}

	@Override
	public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (isReleased(stack)) {
			BotaniaItems.thunderSword.hurtEnemy(stack, target, attacker);
		} else {
			attacker.setAbsorptionAmount(Math.min(MAX_ABSORPTION, attacker.getAbsorptionAmount() + 2F));
		}
		return super.hurtEnemy(stack, target, attacker);
	}

	@Override
	public void onShieldBlock(ItemStack stack, LivingEntity blocker, DamageSource source, float damage) {
		super.onShieldBlock(stack, blocker, source, damage);
		Entity attacker = source.getEntity();
		if (attacker == null) {
			return;
		}
		/* 不好调，蒜鸟
		if (source.is(DamageTypeTags.IS_PROJECTILE) && source.getDirectEntity() instanceof Projectile projectile) {
			Entity target = projectile.getOwner();
			if (target != null) {
				Vec3 vec3 = target.getDeltaMovement();
				double d0 = target.getX() + vec3.x - blocker.getX();
				double d1 = target.getEyeY() - (double)1.1F - blocker.getY();
				double d2 = target.getZ() + vec3.z - blocker.getZ();
				double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		
				projectile.setOwner(blocker);
				projectile.setDeltaMovement(0, 0 ,0);
				projectile.setXRot(0);
				projectile.setYRot(0);
				projectile.setYRot(projectile.getXRot() - -20.0F);
				projectile.shoot(d0, d1 + d3 * 0.2, d2, 0.75F, 8.0F);
			}
		
			return;
		}
		*/

		if (blocker instanceof Player player && ManaItemHandler.INSTANCE.requestManaExactForTool(stack, player, 500, true)) {
			attacker.hurt(player.damageSources().thorns(player), damage);
		}
	}

	public static boolean isReleased(ItemStack stack) {
		return ItemStackDataHelper.getBoolean(stack, TAG_RELEASED, false);
	}

	public static void setReleased(ItemStack stack, boolean mode) {
		ItemStackDataHelper.setBoolean(stack, TAG_RELEASED, mode);
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers() {
		return super.getDefaultAttributeModifiers()
				.withModifierAdded(Attributes.ATTACK_SPEED,
						new AttributeModifier(prefix("achilles_shield_attack_speed"), -2.6, AttributeModifier.Operation.ADD_VALUE),
						EquipmentSlotGroup.bySlot(EquipmentSlot.MAINHAND))
				.withModifierAdded(Attributes.ATTACK_DAMAGE,
						new AttributeModifier(prefix("achilles_shield_attack_damage"), 6, AttributeModifier.Operation.ADD_VALUE),
						EquipmentSlotGroup.bySlot(EquipmentSlot.MAINHAND))
				.withModifierAdded(PixieHandler.PIXIE_SPAWN_CHANCE,
						PixieHandler.makeModifier(prefix("achilles_shield_pixie"), 0.5F),
						EquipmentSlotGroup.bySlot(EquipmentSlot.OFFHAND));
	}

	public static Relic makeRelic(ItemStack stack) {
		return new RelicImpl(stack, null);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
		super.appendHoverText(stack, context, tooltip, flags);
		RelicImpl.addDefaultTooltip(stack, tooltip);
	}
}
