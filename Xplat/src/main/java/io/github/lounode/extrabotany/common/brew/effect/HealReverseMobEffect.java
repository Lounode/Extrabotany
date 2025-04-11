package io.github.lounode.extrabotany.common.brew.effect;

import io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes;
import io.github.lounode.extrabotany.common.event.entity.living.LivingHealEventWrapper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class HealReverseMobEffect extends MobEffect {
    protected HealReverseMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public static void onLivingHeal(LivingHealEventWrapper event) {
        LivingEntity entity = event.getEntity();
        float amount = event.getAmount();

        if (entity.hasEffect(ExtraBotanyMobEffects.HEAL_REVERSE)) {
            entity.hurt(ExtraBotanyDamageTypes.Sources.reverseHealDamage(entity.level().registryAccess()), amount);
            event.setAmount(0);
        }
    }
}
