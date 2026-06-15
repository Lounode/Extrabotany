package io.github.lounode.extrabotany.forge.event;

import net.minecraft.world.InteractionResult;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.PlayLevelSoundEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import io.github.lounode.extrabotany.common.block.flower.generating.ResoncundBlockEntity;
import io.github.lounode.extrabotany.common.brew.effect.HealReverseMobEffect;
import io.github.lounode.extrabotany.common.brew.effect.LinkMobEffect;
import io.github.lounode.extrabotany.common.brew.effect.ThirrorMobEffect;
import io.github.lounode.extrabotany.common.brew.effect.WarmMobEffect;
import io.github.lounode.extrabotany.common.event.EventSubscriptions;
import io.github.lounode.extrabotany.common.event.PlayLevelSoundEventWrapper;
import io.github.lounode.extrabotany.common.event.entity.living.LivingAttackEventWrapper;
import io.github.lounode.extrabotany.common.event.entity.living.LivingDamageEventWrapper;
import io.github.lounode.extrabotany.common.event.entity.living.LivingDeathEventWrapper;
import io.github.lounode.extrabotany.common.event.entity.living.LivingHealEventWrapper;
import io.github.lounode.extrabotany.common.event.entity.living.LivingHurtEventWrapper;
import io.github.lounode.extrabotany.common.event.entity.living.MobEffectEventWrapper;
import io.github.lounode.extrabotany.common.event.entity.living.ShieldBlockEventWrapper;
import io.github.lounode.extrabotany.common.event.entity.player.AttackEntityEventWrapper;
import io.github.lounode.extrabotany.common.event.entity.player.PlayerEventWrapper;
import io.github.lounode.extrabotany.common.event.entity.player.PlayerInteractEventWrapper;
import io.github.lounode.extrabotany.common.event.furnace.FurnaceFuelBurnTimeEventWrapper;
import io.github.lounode.extrabotany.common.event.level.LevelEventWrapper;
import io.github.lounode.extrabotany.common.event.server.ServerStartedEventWrapper;
import io.github.lounode.extrabotany.common.event.server.ServerStoppingEventWrapper;
import io.github.lounode.extrabotany.common.impl.WindImpl;
import io.github.lounode.extrabotany.common.item.NightmareFuelItem;
import io.github.lounode.extrabotany.common.item.SpiritFuelItem;
import io.github.lounode.extrabotany.common.item.equipment.armor.goblin_slayer.GoblinSlayerHelmetItem;
import io.github.lounode.extrabotany.common.item.equipment.armor.pleiades_combat_maid.PleiadesCombatMaidSuitItem;
import io.github.lounode.extrabotany.common.item.equipment.armor.pleiades_combat_maid.SanguinePleiadesCombatMaidSuitItem;
import io.github.lounode.extrabotany.common.item.equipment.armor.shadow_warrior.ShadowWarriorHelmetItem;
import io.github.lounode.extrabotany.common.item.equipment.bauble.FeatherOfJingweiItem;
import io.github.lounode.extrabotany.common.item.equipment.bauble.NatureOrbItem;
import io.github.lounode.extrabotany.common.item.equipment.bauble.PureDaisyPendantItem;
import io.github.lounode.extrabotany.common.item.equipment.shield.ManasteelShieldItem;
import io.github.lounode.extrabotany.common.item.equipment.tool.hammer.RheinHammerItem;
import io.github.lounode.extrabotany.common.item.relic.ExcaliburItem;
import io.github.lounode.extrabotany.common.item.relic.voidcore.CoreOfTheVoidItem;
import io.github.lounode.extrabotany.common.telemetry.ExtraBotanyTelemetry;

public final class NeoForgeEventBridge {
	private NeoForgeEventBridge() {}

	public static void register(IEventBus bus) {
		bus.addListener(NeoForgeEventBridge::onLevelLoad);
		bus.addListener(NeoForgeEventBridge::onLevelUnload);
		bus.addListener(NeoForgeEventBridge::onServerStarted);
		bus.addListener(NeoForgeEventBridge::onServerStopping);
		bus.addListener(NeoForgeEventBridge::onFurnaceFuel);
		bus.addListener(NeoForgeEventBridge::onIncomingDamage);
		bus.addListener(NeoForgeEventBridge::onDamagePost);
		bus.addListener(NeoForgeEventBridge::onLivingHeal);
		bus.addListener(NeoForgeEventBridge::onLivingDeath);
		bus.addListener(NeoForgeEventBridge::onEffectApplicable);
		bus.addListener(NeoForgeEventBridge::onEffectAdded);
		bus.addListener(NeoForgeEventBridge::onEffectRemoved);
		bus.addListener(NeoForgeEventBridge::onEffectExpired);
		bus.addListener(NeoForgeEventBridge::onShieldBlock);
		bus.addListener(NeoForgeEventBridge::onAttackEntity);
		bus.addListener(NeoForgeEventBridge::onLeftClickEmpty);
		bus.addListener(NeoForgeEventBridge::onRightClickBlock);
		bus.addListener(NeoForgeEventBridge::onBreakSpeed);
		bus.addListener(NeoForgeEventBridge::onPlayerLoggedOut);
		bus.addListener(NeoForgeEventBridge::onPlayerTickPost);
		bus.addListener(NeoForgeEventBridge::onSoundAtPosition);
		bus.addListener(NeoForgeEventBridge::onSoundAtEntity);
	}

	private static void onLevelLoad(LevelEvent.Load event) {
		WindImpl.EventHandler.onLevelLoad(new LevelEventWrapper.Load(event.getLevel()));
	}

	private static void onLevelUnload(LevelEvent.Unload event) {
		WindImpl.EventHandler.onLevelUnLoad(new LevelEventWrapper.Unload(event.getLevel()));
	}

	private static void onServerStarted(net.neoforged.neoforge.event.server.ServerStartedEvent event) {
		ExtraBotanyTelemetry.onServerStarted(new ServerStartedEventWrapper(event.getServer()));
	}

	private static void onServerStopping(net.neoforged.neoforge.event.server.ServerStoppingEvent event) {
		ExtraBotanyTelemetry.onServerStopping(new ServerStoppingEventWrapper(event.getServer()));
	}

	private static void onFurnaceFuel(FurnaceFuelBurnTimeEvent event) {
		FurnaceFuelBurnTimeEventWrapper wrapper = new FurnaceFuelBurnTimeEventWrapper(event.getItemStack(), event.getBurnTime());
		SpiritFuelItem.makeFuel(wrapper);
		NightmareFuelItem.makeFuel(wrapper);
		if (wrapper.isModified()) {
			event.setBurnTime(wrapper.getBurnTime());
		}
	}

	private static void onIncomingDamage(LivingIncomingDamageEvent event) {
		LivingAttackEventWrapper attack = new LivingAttackEventWrapper(event.getEntity(), event.getSource(), event.getAmount());
		WarmMobEffect.EventHandler.onEntityHurt(attack);
		ThirrorMobEffect.EventHandler.onLivingAttack(attack);
		CoreOfTheVoidItem.onLivingAttack(attack);
		applyIncoming(event, attack);
		if (event.isCanceled()) {
			return;
		}

		LivingHurtEventWrapper hurt = new LivingHurtEventWrapper(event.getEntity(), event.getSource(), event.getAmount());
		LinkMobEffect.onEntityDamaged(hurt);
		ShadowWarriorHelmetItem.EventHandler.onPlayerAttacked(hurt);
		GoblinSlayerHelmetItem.EventHandler.onPlayerAttack(hurt);
		PleiadesCombatMaidSuitItem.EventHandler.onEntityAttacked(hurt);
		PleiadesCombatMaidSuitItem.EventHandler.onPlayerAttacked(hurt);
		CoreOfTheVoidItem.onLivingHurt(hurt);
		applyIncoming(event, hurt);
		if (event.isCanceled()) {
			return;
		}

		LivingDamageEventWrapper damage = new LivingDamageEventWrapper(event.getEntity(), event.getSource(), event.getAmount());
		CoreOfTheVoidItem.onLivingDamage(damage);
		applyIncoming(event, damage);
	}

	private static void applyIncoming(LivingIncomingDamageEvent target, LivingAttackEventWrapper source) {
		target.setAmount(source.getAmount());
		if (source.isCanceled()) {
			target.setCanceled(true);
		}
	}

	private static void onDamagePost(LivingDamageEvent.Post event) {
		SanguinePleiadesCombatMaidSuitItem.EventHandler.onAttackLiving(
				new LivingDamageEventWrapper(event.getEntity(), event.getSource(), event.getNewDamage()));
	}

	private static void onLivingHeal(LivingHealEvent event) {
		LivingHealEventWrapper wrapper = new LivingHealEventWrapper(event.getEntity(), event.getAmount());
		HealReverseMobEffect.onLivingHeal(wrapper);
		event.setAmount(wrapper.getAmount());
	}

	private static void onLivingDeath(LivingDeathEvent event) {
		SanguinePleiadesCombatMaidSuitItem.EventHandler.onKilled(
				new LivingDeathEventWrapper(event.getEntity(), event.getSource()));
	}

	private static void onEffectApplicable(MobEffectEvent.Applicable event) {
		MobEffectEventWrapper.Applicable wrapper = new MobEffectEventWrapper.Applicable(event.getEntity(), event.getEffectInstance());
		CoreOfTheVoidItem.onEffectAdd(wrapper);
		if (wrapper.getResult() == MobEffectEventWrapper.Applicable.Result.APPLY) {
			event.setResult(MobEffectEvent.Applicable.Result.APPLY);
		} else if (wrapper.getResult() == MobEffectEventWrapper.Applicable.Result.DO_NOT_APPLY) {
			event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
		}
	}

	private static void onEffectAdded(MobEffectEvent.Added event) {
		SanguinePleiadesCombatMaidSuitItem.EventHandler.onEffectAdded(
				new MobEffectEventWrapper.Added(event.getEntity(), event.getEffectInstance()));
	}

	private static void onEffectRemoved(MobEffectEvent.Remove event) {
		if (event.getEffectInstance() != null) {
			SanguinePleiadesCombatMaidSuitItem.EventHandler.onEffectRemove(
					new MobEffectEventWrapper.Remove(event.getEntity(), event.getEffectInstance()));
		}
	}

	private static void onEffectExpired(MobEffectEvent.Expired event) {
		SanguinePleiadesCombatMaidSuitItem.EventHandler.onEffectExpired(
				new MobEffectEventWrapper.Expired(event.getEntity(), event.getEffectInstance()));
	}

	private static void onShieldBlock(LivingShieldBlockEvent event) {
		ManasteelShieldItem.EventHandler.onShieldBlockDamage(
				new ShieldBlockEventWrapper(event.getEntity(), event.getDamageSource(), event.getBlockedDamage()));
	}

	private static void onAttackEntity(AttackEntityEvent event) {
		AttackEntityEventWrapper wrapper = new AttackEntityEventWrapper(event.getEntity(), event.getTarget());
		ExcaliburItem.attackEntity(wrapper);
		FeatherOfJingweiItem.attackEntity(wrapper);
		io.github.lounode.extrabotany.common.item.relic.void_archives.variants.Excalibur.attackEntity(wrapper);
		if (wrapper.isCanceled()) {
			event.setCanceled(true);
		}
	}

	private static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
		PlayerInteractEventWrapper.LeftClickEmpty wrapper = new PlayerInteractEventWrapper.LeftClickEmpty(event.getEntity());
		ExcaliburItem.leftClick(wrapper);
		FeatherOfJingweiItem.leftClick(wrapper);
		io.github.lounode.extrabotany.common.item.relic.void_archives.variants.Excalibur.leftClick(wrapper);
	}

	private static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		PlayerInteractEventWrapper.RightClickBlock wrapper =
				new PlayerInteractEventWrapper.RightClickBlock(event.getEntity(), event.getHand(), event.getPos());
		NatureOrbItem.onPlayerInteract(wrapper);
		PureDaisyPendantItem.EventHandler.onPlayerInteract(wrapper);
		if (wrapper.isCanceled()) {
			event.setCancellationResult(InteractionResult.SUCCESS);
			event.setCanceled(true);
		}
	}

	private static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
		PlayerEventWrapper.BreakSpeed wrapper = new PlayerEventWrapper.BreakSpeed(
				event.getEntity(), event.getState(), event.getPosition().orElse(null), event.getNewSpeed());
		RheinHammerItem.onDig(wrapper);
		event.setNewSpeed(wrapper.getNewSpeed());
		if (wrapper.isCanceled()) {
			event.setCanceled(true);
		}
	}

	private static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
		CoreOfTheVoidItem.playerLoggedOut(new PlayerEventWrapper.PlayerLoggedOutEvent(event.getEntity()));
	}

	private static void onPlayerTickPost(PlayerTickEvent.Post event) {
		CoreOfTheVoidItem.updatePlayerFlyStatus(event.getEntity());
	}

	private static void onSoundAtPosition(PlayLevelSoundEvent.AtPosition event) {
		PlayLevelSoundEventWrapper.AtPosition wrapper =
				new PlayLevelSoundEventWrapper.AtPosition(event.getPosition(), event.getSound());
		for (ResoncundBlockEntity listener : EventSubscriptions.listeners(ResoncundBlockEntity.class)) {
			listener.onPlayLevelSound(wrapper);
		}
	}

	private static void onSoundAtEntity(PlayLevelSoundEvent.AtEntity event) {
		PlayLevelSoundEventWrapper.AtEntity wrapper =
				new PlayLevelSoundEventWrapper.AtEntity(event.getEntity(), event.getSound());
		for (ResoncundBlockEntity listener : EventSubscriptions.listeners(ResoncundBlockEntity.class)) {
			listener.onPlayLevelSound(wrapper);
		}
	}
}
