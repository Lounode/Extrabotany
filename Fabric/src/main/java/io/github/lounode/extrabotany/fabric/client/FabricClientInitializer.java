package io.github.lounode.extrabotany.fabric.client;

import io.github.lounode.extrabotany.client.ExtraBotanyItemProperties;
import io.github.lounode.extrabotany.client.gui.HUDHandler;
import io.github.lounode.extrabotany.client.renderer.ColorHandler;
import io.github.lounode.extrabotany.client.renderer.entity.EntityRenderers;
import io.github.lounode.extrabotany.common.item.equipment.bauble.FeatherOfJingweiItem;
import io.github.lounode.extrabotany.common.item.relic.ExcaliburItem;
import io.github.lounode.extrabotany.fabric.events.PlayerInteractEvents;
import io.github.lounode.extrabotany.fabric.network.FabricPacketHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.InteractionHand;

public class FabricClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricPacketHandler.initClient();

        //Block&Items
        ExtraBotanyItemProperties.init((item, id, prop) -> ItemProperties.register(item.asItem(), id, prop));

        //EntityRender
        EntityRenderers.registerBlockEntityRenderers(BlockEntityRenderers::register);
        EntityRenderers.registerEntityRenderers(EntityRendererRegistry::register);

        //Events
        ClientLifecycleEvents.CLIENT_STARTED.register(this::loadComplete);
        HudRenderCallback.EVENT.register(HUDHandler::onDrawScreenPost);

        //LeftClick
        PlayerInteractEvents.LEFT_CLICK.register((player) -> {
            ExcaliburItem.leftClick(player.getItemInHand(InteractionHand.MAIN_HAND));
            return true;
        });
        PlayerInteractEvents.LEFT_CLICK.register((player) -> {
            FeatherOfJingweiItem.leftClick(player);
            return true;
        });
    }

    private void loadComplete(Minecraft mc) {
        ColorHandler.submitBlocks(ColorProviderRegistry.BLOCK::register);
        ColorHandler.submitItems(ColorProviderRegistry.ITEM::register);
    }
}
