package io.github.lounode.extrabotany.fabric.mixin;

import io.github.lounode.extrabotany.common.event.entity.living.MobEffectEventWrapper;
import io.github.lounode.extrabotany.fabric.event.LivingEntityEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MobEffectEventPoster {
    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At(value = "HEAD")
    )
    private void onEffectAdd(MobEffectInstance effectInstance, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self =  (LivingEntity)(Object)this;
        if (!self.canBeAffected(effectInstance)) {
            return;
        }
        MobEffectInstance mobEffectInstance = self.getActiveEffectsMap().get(effectInstance.getEffect());

        LivingEntityEvents.MOB_EFFECT_ADD.invoker().onMobEffectAdd(new MobEffectEventWrapper.Added(
                self,
                mobEffectInstance,
                effectInstance,
                entity
        ));
    }
}
