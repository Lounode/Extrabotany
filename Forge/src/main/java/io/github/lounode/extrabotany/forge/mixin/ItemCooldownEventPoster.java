package io.github.lounode.extrabotany.forge.mixin;

import io.github.lounode.extrabotany.forge.event.ItemCooldownStartEvent;
import io.github.lounode.extrabotany.forge.event.ItemCooldownFinishEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ServerItemCooldowns;
import net.minecraftforge.common.MinecraftForge;
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
        MinecraftForge.EVENT_BUS.post(new ItemCooldownFinishEvent(player, pItem));
    }

    @Inject(method = "onCooldownStarted", at = @At("HEAD"), remap = false)
    private void sendItemStartCooldownEvent(Item pItem, int pTicks, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new ItemCooldownStartEvent(player, pItem, pTicks));
    }
}
