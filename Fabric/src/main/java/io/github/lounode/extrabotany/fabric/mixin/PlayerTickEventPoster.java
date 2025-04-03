package io.github.lounode.extrabotany.fabric.mixin;

import io.github.lounode.extrabotany.fabric.events.ItemCooldownEvents;
import io.github.lounode.extrabotany.fabric.events.PlayerTickEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerTickEventPoster {
    @Inject(method = "tick", at = @At("HEAD"), remap = false)
    private void playerTickStart(CallbackInfo ci) {
        PlayerTickEvents.START.invoker().tickStart((Player)(Object)this);
    }

    @Inject(method = "tick", at = @At("TAIL"), remap = false)
    private void playerTickEnd(CallbackInfo ci) {
        PlayerTickEvents.END.invoker().tickEnd((Player)(Object)this);
    }
}
