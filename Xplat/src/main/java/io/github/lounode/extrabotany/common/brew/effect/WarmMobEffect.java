package io.github.lounode.extrabotany.common.brew.effect;

import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import io.github.lounode.eventwrapper.event.entity.living.LivingAttackEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.extrabotany.common.brew.ExtraBotanyMobEffects;

public class WarmMobEffect extends MobEffect {
	public WarmMobEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@EventBusSubscriberWrapper
	public static class EventHandler {

		@SubscribeEventWrapper
		public static void onEntityHurt(LivingAttackEventWrapper event) {
			if (!event.getSource().is(DamageTypes.FREEZE)) {
				return;
			}
			if (event.getEntity().hasEffect(ExtraBotanyMobEffects.WARM)) {
				event.setCanceled(true);
			}
		}
	}
}
