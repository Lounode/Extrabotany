package io.github.lounode.extrabotany.fabric.mixin;

import io.github.lounode.extrabotany.fabric.event.LivingEntityEvents;
import io.github.lounode.extrabotany.common.event.entity.living.LivingHealEventWrapper;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class LivingEntityHealEventPoster {
    @ModifyVariable(method = "heal", at = @At("HEAD"), argsOnly = true)
    private float onLivingHeal(float healAmount) {
        LivingHealEventWrapper event = new LivingHealEventWrapper((LivingEntity) (Object) this, healAmount);
        LivingEntityEvents.HEAL.invoker().onHeal(event);

        if (event.isCanceled()) {
            return 0;
        }
        return event.getAmount();
    }
}
