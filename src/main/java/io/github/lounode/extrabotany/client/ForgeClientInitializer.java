package io.github.lounode.extrabotany.client;

import io.github.lounode.extrabotany.ExtraBotany;
import io.github.lounode.extrabotany.client.gui.CameraUI;
import io.github.lounode.extrabotany.client.gui.HUDHandler;
import io.github.lounode.extrabotany.client.gui.MasterBandOfManaTooltipComponent;
import io.github.lounode.extrabotany.client.renderer.ColorHandler;
import io.github.lounode.extrabotany.common.item.relic.CameraItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import io.github.lounode.extrabotany.client.renderer.entity.EntityRenderers;

@Mod.EventBusSubscriber(modid = ExtraBotany.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientInitializer {
    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent evt) {
        var bus = MinecraftForge.EVENT_BUS;
        bus.addListener(EventPriority.NORMAL, false, net.minecraftforge.client.event.RenderGuiEvent.Post.class, event -> {
            CameraUI.renderIfNeeded(event.getGuiGraphics(), event.getPartialTick());
        });
        //MasterRingToolTip TODO
        /*
        bus.addListener(EventPriority.LOWEST, (RenderTooltipEvent.Color e) -> {
            var manaItem = XplatAbstractions.INSTANCE.findManaItem(e.getItemStack());
            if (manaItem == null) {
                return;
            }
            // Forge does not pass the tooltip width to any tooltip event.
            // To avoid a mixin here, we just duplicate the width checking part.
            int width = 0;
            MasterBandOfManaTooltipComponent manaBar = null;
            for (ClientTooltipComponent component : e.getComponents()) {
                width = Math.max(width, component.getWidth(e.getFont()));
                if (component instanceof MasterBandOfManaTooltipComponent c) {
                    manaBar = c;
                }
            }
            if (manaBar != null) {
                manaBar.setContext(e.getX(), e.getY(), width);
            }
        });

         */
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent e) {
        e.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "hud",
                (gui, poseStack, partialTick, width, height) -> HUDHandler.onDrawScreenPost(poseStack, partialTick));
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        EntityRenderers.registerBlockEntityRenderers(evt::registerBlockEntityRenderer);
        //EntityRenderers.registerEntityRenderers(evt::registerEntityRenderer);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block evt) {
        ColorHandler.submitBlocks(evt::register);
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item evt) {
        ColorHandler.submitItems(evt::register);
    }

    @SubscribeEvent
    public static void onModelRegister(ModelEvent.RegisterAdditional evt) {
        var resourceManager = Minecraft.getInstance().getResourceManager();
        ExtraBotanyItemProperties.init((item, id, prop) -> ItemProperties.register(item.asItem(), id, prop));
    }
}
