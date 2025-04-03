package io.github.lounode.extrabotany.mixin.client;

import io.github.lounode.extrabotany.common.item.relic.CameraItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public  class CameraGuiMixin{
    @Shadow
    private Minecraft minecraft;

    @Shadow
    private void renderSpyglassOverlay(GuiGraphics pGuiGraphics, float pScopeScale) {}

    @Inject(method = "render", at = @At("HEAD"), remap = false)
    private void render(GuiGraphics pGuiGraphics, float pPartialTick, CallbackInfo ci){
        if (this.minecraft.options.getCameraType().isFirstPerson()) {
            if (this.minecraft.player.getUseItem().getItem() instanceof CameraItem) {
                this.renderSpyglassOverlay(pGuiGraphics, 1);
            }
        }
    }
}
