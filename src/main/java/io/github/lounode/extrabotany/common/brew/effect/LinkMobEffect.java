package io.github.lounode.extrabotany.common.brew.effect;

import io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LinkMobEffect extends MobEffect {
    private static final double LINK_RADIUS = 8.0D;
    private static final float BASE_RATIO = 0.2F;
    private static final float PER_LEVEL_BONUS = 0.2F;
    //private static final int MAX_LINK_ENTITY = 10;
    protected LinkMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @SubscribeEvent
    public static void onEntityDamaged(LivingDamageEvent event) {
        if (event.getSource().is(ExtraBotanyDamageTypes.LINK_DAMAGE)) {
            return;
        }
        LivingEntity victim = event.getEntity();
        float damage = event.getAmount();

        victim.level().getEntitiesOfClass(LivingEntity.class, victim.getBoundingBox().inflate(LINK_RADIUS))
                .stream()
                .filter(e -> e != victim)
                .filter(e -> e.hasEffect(ExtrabotanyMobEffects.LINK))
                .forEach(e -> {
                    int level = e.getEffect(ExtrabotanyMobEffects.LINK).getAmplifier();
                    float ratio = BASE_RATIO + (PER_LEVEL_BONUS * level);
                    e.hurt(damageSource(e.level().registryAccess()), damage * ratio);
                });
    }

    private static DamageSource damageSource(RegistryAccess registryAccess) {
        return ExtraBotanyDamageTypes.Sources.linkDamage(registryAccess);
    }
}
