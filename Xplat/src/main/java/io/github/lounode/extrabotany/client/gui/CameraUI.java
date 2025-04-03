package io.github.lounode.extrabotany.client.gui;

import io.github.lounode.extrabotany.common.item.relic.CameraItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class CameraUI extends Gui {
    protected static final ResourceLocation SPYGLASS_SCOPE_LOCATION = prefix("textures/gui/spyglass_scope.png");
    public static final CameraUI INSTANCE = new CameraUI(Minecraft.getInstance(), Minecraft.getInstance().getItemRenderer());
    public CameraUI(Minecraft pMinecraft, ItemRenderer pItemRenderer) {
        super(pMinecraft, pItemRenderer);
    }

    public static void renderIfNeeded(GuiGraphics guiGraphics, float partialTick) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null && mc.player.isUsingItem() && mc.player.getUseItem().getItem() instanceof CameraItem) {
            INSTANCE.renderSpyglassOverlay(guiGraphics, 1.0F); // 1.0F 是默认缩放值
        }
    }

    public void renderSpyglassOverlay(GuiGraphics pGuiGraphics, float pScopeScale) {
        /*
        float f = (float)Math.min(this.screenWidth, this.screenHeight);
        float f1 = Math.min((float)this.screenWidth / f, (float)this.screenHeight / f) * pScopeScale;
        int i = Mth.floor(f * f1);
        int j = Mth.floor(f * f1);
        int k = (this.screenWidth - i) / 2;
        int l = (this.screenHeight - j) / 2;
        int i1 = k + i;
        int j1 = l + j;
        pGuiGraphics.blit(SPYGLASS_SCOPE_LOCATION, k, l, -90, 0.0F, 0.0F, i, j, i, j);
        pGuiGraphics.fill(RenderType.guiOverlay(), 0, j1, this.screenWidth, this.screenHeight, -90, -16777216);
        pGuiGraphics.fill(RenderType.guiOverlay(), 0, 0, this.screenWidth, l, -90, -16777216);
        pGuiGraphics.fill(RenderType.guiOverlay(), 0, l, k, j1, -90, -16777216);
        pGuiGraphics.fill(RenderType.guiOverlay(), i1, l, this.screenWidth, j1, -90, -16777216);

         */
    }
}
