package io.github.lounode.extrabotany.forge.client;

import io.github.lounode.extrabotany.client.ExtraBotanyItemProperties;
import io.github.lounode.extrabotany.client.core.ExtraBotanyModels;
import io.github.lounode.extrabotany.client.gui.HUD;
import io.github.lounode.extrabotany.client.renderer.ColorHandler;
import io.github.lounode.extrabotany.client.renderer.entity.EntityRenderers;
import io.github.lounode.extrabotany.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientInitializer {
    public static HUD hud;
    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent evt) {
        var bus = MinecraftForge.EVENT_BUS;
        hud = new HUD(Minecraft.getInstance());
        /*
        bus.addListener((CustomizeGuiOverlayEvent.BossEventProgress e) -> {
            var result = BossBarHandler.onBarRender(e.getGuiGraphics(), e.getX(), e.getY(),
                    e.getBossEvent(), true);
            result.ifPresent(increment -> {
                e.setCanceled(true);
                e.setIncrement(increment);
            });
        });

        //MasterRingToolTip TODO

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
                (gui, poseStack, partialTick, width, height) -> hud.onDrawScreenPost(poseStack, partialTick));
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        EntityRenderers.registerBlockEntityRenderers(evt::registerBlockEntityRenderer);
        EntityRenderers.registerEntityRenderers(evt::registerEntityRenderer);
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
        ExtraBotanyModels.INSTANCE.onModelRegister(resourceManager, evt::register);
        ExtraBotanyItemProperties.init((item, id, prop) -> ItemProperties.register(item.asItem(), id, prop));
    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult evt) {
        ExtraBotanyModels.INSTANCE.onModelBake(evt.getModelBakery(), evt.getModels());
    }
}
