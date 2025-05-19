package io.github.lounode.extrabotany.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;

import vazkii.botania.api.BotaniaFabricClientCapabilities;


import io.github.lounode.extrabotany.client.ExtraBotanyItemProperties;
import io.github.lounode.extrabotany.client.core.ExtraBotanyModels;
import io.github.lounode.extrabotany.client.gui.HUD;
import io.github.lounode.extrabotany.client.renderer.BlockRenderLayers;
import io.github.lounode.extrabotany.client.renderer.ColorHandler;
import io.github.lounode.extrabotany.client.renderer.entity.EntityRenderers;
import io.github.lounode.extrabotany.common.block.flower.ExtrabotanyFlowerBlocks;
import io.github.lounode.extrabotany.fabric.network.FabricPacketHandler;

public class FabricClientInitializer implements ClientModInitializer {
	private HUD hud;

	@Override
	public void onInitializeClient() {
		FabricPacketHandler.initClient();
		this.hud = new HUD(Minecraft.getInstance());

		//Block&Items
		ModelLoadingPlugin.register(pluginContext -> {
			ExtraBotanyModels.INSTANCE.onModelRegister(Minecraft.getInstance().getResourceManager(), pluginContext::addModels);
			pluginContext.modifyModelAfterBake().register((bakedModel, context) -> ExtraBotanyModels.INSTANCE.modifyModelAfterbake(bakedModel, context.id()));
		});
		BlockRenderLayers.init(BlockRenderLayerMap.INSTANCE::putBlock);
		ExtraBotanyItemProperties.init((item, id, prop) -> ItemProperties.register(item.asItem(), id, prop));

		//EntityRender
		EntityRenderers.registerBlockEntityRenderers(BlockEntityRenderers::register);
		EntityRenderers.registerEntityRenderers(EntityRendererRegistry::register);

		//Events
		ClientLifecycleEvents.CLIENT_STARTED.register(this::loadComplete);
		HudRenderCallback.EVENT.register((gui, partialTick) -> this.hud.onDrawScreenPost(gui, partialTick));

		registerCapabilities();
	}

	private static void registerCapabilities() {
		ExtrabotanyFlowerBlocks.registerWandHudCaps((factory, types) -> BotaniaFabricClientCapabilities.WAND_HUD.registerForBlockEntities((be, c) -> factory.apply(be), types));
	}

	private void loadComplete(Minecraft mc) {
		ColorHandler.submitBlocks(ColorProviderRegistry.BLOCK::register);
		ColorHandler.submitItems(ColorProviderRegistry.ITEM::register);
	}
}
