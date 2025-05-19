package io.github.lounode.extrabotany.common.item.relic.void_archives.variants;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import vazkii.botania.common.entity.ManaBurstEntity;

import io.github.lounode.eventwrapper.event.entity.player.AttackEntityEventWrapper;
import io.github.lounode.eventwrapper.event.entity.player.PlayerInteractEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.extrabotany.api.item.VoidArchivesVariant;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.item.relic.void_archives.VoidArchivesItem;
import io.github.lounode.extrabotany.common.sounds.ExtraBotanySounds;
import io.github.lounode.extrabotany.common.util.AttributeUtil;
import io.github.lounode.extrabotany.network.serverbound.LeftClickPacketVoidArchives;
import io.github.lounode.extrabotany.xplat.ExClientXplatAbstractions;

@EventBusSubscriberWrapper
public class Excalibur implements VoidArchivesVariant {

	public static Excalibur INSTANCE = new Excalibur();

	private static final String ID = "excalibur";

	private static final int MANA_PER_DAMAGE = 200;

	@Override
	public String getId() {
		return ID;
	}

	@SubscribeEventWrapper
	public static void leftClick(PlayerInteractEventWrapper.LeftClickEmpty event) {
		ItemStack stack = event.getItemStack();
		if (!stack.isEmpty() && stack.getItem() instanceof VoidArchivesItem) {
			ExClientXplatAbstractions.INSTANCE.sendToServer(LeftClickPacketVoidArchives.INSTANCE);
		}
	}

	@SubscribeEventWrapper
	public static void attackEntity(AttackEntityEventWrapper event) {
		Player player = event.getEntity();
		if (!player.level().isClientSide && player.getMainHandItem().getItem() instanceof VoidArchivesItem) {
			INSTANCE.trySpawnBurst(player, player.getAttackStrengthScale(0F));
		}
	}

	public void trySpawnBurst(Player player, float attackStrengthScale) {
		ItemStack stack = player.getMainHandItem();
		if (!(stack.getItem() instanceof VoidArchivesItem)) {
			return;
		}
		if (!isActive(stack)) {
			return;
		}
		if (!isMaster(stack, player)) {
			return;
		}
		if (player.isSpectator() ||
				attackStrengthScale != 1) {
			return;
		}

		ManaBurstEntity burst = getBurst(player, player.getMainHandItem());
		player.level().addFreshEntity(burst);

		player.getMainHandItem().hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
		player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ExtraBotanySounds.EXCALIBUR_ATTACK, SoundSource.PLAYERS, 1F, 1F);
	}

	public ManaBurstEntity getBurst(Player player, ItemStack stack) {
		ManaBurstEntity burst = new ManaBurstEntity(player) {
			@Override
			public boolean shouldBeSaved() {
				return false;
			}
		};

		float motionModifier = 9F;

		burst.setColor(0xFFFF20);
		burst.setMana(MANA_PER_DAMAGE);
		burst.setStartingMana(MANA_PER_DAMAGE);
		burst.setMinManaLoss(40);
		burst.setManaLossPerTick(4F);
		burst.setGravity(0F);
		burst.setDeltaMovement(burst.getDeltaMovement().scale(motionModifier));

		burst.setSourceLens(new ItemStack(ExtraBotanyItems.excalibur));
		return burst;
	}

	@Override
	public void onActive(ItemStack stack) {
		AttributeUtil.addAttributeModifier(stack, Attributes.MOVEMENT_SPEED,
				new AttributeModifier(
						"Excaliber modifier",
						0.3D,
						AttributeModifier.Operation.MULTIPLY_TOTAL
				), EquipmentSlot.MAINHAND);
		AttributeUtil.addAttributeModifier(stack, Attributes.ATTACK_DAMAGE,
				new AttributeModifier(
						"Excaliber modifier",
						15D,
						AttributeModifier.Operation.ADDITION
				), EquipmentSlot.MAINHAND);
	}

	@Override
	public void onInactive(ItemStack stack) {
		AttributeUtil.removeAttributeModifier(stack, "Excaliber modifier");
	}
}
