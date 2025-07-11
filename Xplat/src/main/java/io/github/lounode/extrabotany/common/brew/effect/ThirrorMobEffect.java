package io.github.lounode.extrabotany.common.brew.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import io.github.lounode.eventwrapper.event.entity.living.LivingAttackEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.extrabotany.common.brew.ExtraBotanyMobEffects;

import java.util.Objects;

public class ThirrorMobEffect extends MobEffect {
	public ThirrorMobEffect(MobEffectCategory category, int color) {
		super(category, color);
	}

	@EventBusSubscriberWrapper
	public static class EventHandler {

		@SubscribeEventWrapper
		public static void onLivingAttack(LivingAttackEventWrapper event) {
			LivingEntity defender = event.getEntity();
			if (!defender.hasEffect(ExtraBotanyMobEffects.THIRROR)) {
				return;
			}
			if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) {
				return;
			}

			int level = Objects.requireNonNull(defender.getEffect(ExtraBotanyMobEffects.THIRROR)).getAmplifier();
			float returnDamage = event.getAmount() / Math.max(1, 6 - level);

			attacker.hurt(defender.damageSources().thorns(defender), returnDamage);
		}
	}
}
