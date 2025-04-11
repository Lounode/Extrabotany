package io.github.lounode.extrabotany.fabric.mixin.client;

import io.github.lounode.extrabotany.fabric.event.PlayerInteractEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Minecraft.class)
public abstract class PlayerLeftClientEventPoster {
    @Shadow
    @Nullable
    public LocalPlayer player;

    @Inject(method = "startAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;resetAttackStrengthTicker()V"))
    private void leftClickEmpty(CallbackInfoReturnable<Boolean> cir) {
        PlayerInteractEvents.LEFT_CLICK.invoker().leftClickEmpty(player);
    }
}
