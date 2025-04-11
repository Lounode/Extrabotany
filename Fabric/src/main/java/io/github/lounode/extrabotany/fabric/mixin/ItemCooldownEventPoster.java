package io.github.lounode.extrabotany.fabric.mixin;

import io.github.lounode.extrabotany.fabric.event.ItemCooldownEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ServerItemCooldowns;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerItemCooldowns.class)
public class ItemCooldownEventPoster {
    @Final
    @Shadow(remap = false)
    private ServerPlayer player;
    @Inject(method = "onCooldownEnded", at = @At("HEAD"), remap = false)
    private void sendItemFinishCooldownEvent(Item pItem, CallbackInfo ci) {
        ItemCooldownEvents.FINISH.invoker().cooldownFinish(player, pItem);
    }

    @Inject(method = "onCooldownStarted", at = @At("HEAD"), remap = false)
    private void sendItemStartCooldownEvent(Item pItem, int pTicks, CallbackInfo ci) {
        ItemCooldownEvents.START.invoker().cooldownStart(player, pItem, pTicks);
    }
}
