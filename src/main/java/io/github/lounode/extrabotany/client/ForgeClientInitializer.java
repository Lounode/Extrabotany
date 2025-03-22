package io.github.lounode.extrabotany.client;

import io.github.lounode.extrabotany.ExtraBotany;
import io.github.lounode.extrabotany.client.gui.MasterBandOfManaTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
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
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        EntityRenderers.registerBlockEntityRenderers(evt::registerBlockEntityRenderer);
        //EntityRenderers.registerEntityRenderers(evt::registerEntityRenderer);
    }
}
