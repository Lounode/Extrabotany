package io.github.lounode.extrabotany.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.lounode.extrabotany.common.item.relic.CameraItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import vazkii.botania.client.lib.ResourcesLib;

public final class HUDHandler {
    private HUDHandler() {
    }
    public static final ResourceLocation manaBar1 = ResourceLocation.parse(ResourcesLib.GUI_MANA_HUD);
    public static final ResourceLocation CAMERA_UI_LOCATION = ResourceLocation.parse("extrabotany:textures/gui/spyglass_scope.png");

    protected static float scopeScale;


    public static void onDrawScreenPost(GuiGraphics gui, float partialTicks) {
        PoseStack ms = gui.pose();
        Minecraft mc = Minecraft.getInstance();
        if (mc.options.hideGui) {
            return;
        }
        ProfilerFiller profiler = mc.getProfiler();
        ItemStack main = mc.player.getMainHandItem();
        ItemStack offhand = mc.player.getOffhandItem();

        profiler.push("extrabotany-hud");

        float f = mc.getDeltaFrameTime();
        scopeScale = Mth.lerp(0.5F * f, scopeScale, 1.125F);
        if (mc.options.getCameraType().isFirstPerson()) {
            if (mc.player.isUsingItem() && mc.player.getUseItem().getItem() instanceof CameraItem) {
                profiler.push("camera-hud");
                CameraItem.Hud.renderSpyglassOverlay(gui, scopeScale);
                profiler.pop();
            } else {
                scopeScale = 0.5F;
            }
        }

        profiler.pop();

        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
    }
}
