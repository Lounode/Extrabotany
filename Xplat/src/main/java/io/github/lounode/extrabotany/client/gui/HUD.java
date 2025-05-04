package io.github.lounode.extrabotany.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.lounode.eventwrapper.event.entity.player.PlayerEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.extrabotany.common.item.relic.CameraItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;

@EventBusSubscriberWrapper
public final class HUD {
    public static HUD INSTANCE;

    private final Minecraft minecraft;
    private final ColorfulBossBarOverlay bossOverlay;
    private float scopeScale;
    public HUD(Minecraft minecraft) {
        INSTANCE = this;
        this.minecraft = minecraft;
        this.bossOverlay = new ColorfulBossBarOverlay(minecraft);
    }

    public void onDrawScreenPost(GuiGraphics gui, float partialTicks) {
        PoseStack ms = gui.pose();
        if (minecraft.options.hideGui) {
            return;
        }
        ProfilerFiller profiler = minecraft.getProfiler();
        profiler.push("extrabotany-hud");



        float f = minecraft.getDeltaFrameTime();
        scopeScale = Mth.lerp(0.5F * f, scopeScale, 1.125F);
        if (minecraft.options.getCameraType().isFirstPerson()) {
            if (minecraft.player.isUsingItem() && minecraft.player.getUseItem().getItem() instanceof CameraItem) {
                profiler.push("camera-hud");
                CameraItem.Hud.renderSpyglassOverlay(gui, scopeScale);
                profiler.pop();
            } else {
                scopeScale = 0.5F;
            }
        }

        profiler.push("boss-bar");
            bossOverlay.render(gui);
        profiler.pop();

        profiler.pop();
    }

    public static HUD getInstance() {
        return INSTANCE;
    }

    public ColorfulBossBarOverlay getBossOverlay() {
        return bossOverlay;
    }

    @SubscribeEventWrapper
    public static void onDisconnected(PlayerEventWrapper.PlayerLoggedOutEvent event) {
        HUD.getInstance().getBossOverlay().reset();
    }

}
