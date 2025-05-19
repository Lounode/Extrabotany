package io.github.lounode.extrabotany.client.core;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import io.github.lounode.extrabotany.api.ExtraBotanyAPI;

public class ExtraBotanyModels {
	public static final ExtraBotanyModels INSTANCE = new ExtraBotanyModels();
	private final Map<ResourceLocation, Function<BakedModel, BakedModel>> afterBakeModifiers;
	private final Map<ResourceLocation, Consumer<BakedModel>> modelConsumers;

	public boolean registeredModels = false;

	private ExtraBotanyModels() {
		afterBakeModifiers = new HashMap<>();

		modelConsumers = new HashMap<>();

		for (var variant : ExtraBotanyAPI.instance().getCOVVariants().values()) {
			variant.onModelInit(modelConsumers);
		}

	}

	public void onModelRegister(ResourceManager rm, Consumer<ResourceLocation> consumer) {
		modelConsumers.keySet().forEach(consumer);

		if (!registeredModels) {
			registeredModels = true;
		}
	}

	public void onModelBake(ModelBakery loader, Map<ResourceLocation, BakedModel> map) {
		if (!registeredModels) {
			ExtraBotanyAPI.LOGGER.error("Additional models failed to register! Aborting baking models to avoid early crashing.");
			return;
		}
		afterBakeModifiers.forEach((resourceLocation, afterBakeModifier) -> map.computeIfPresent(resourceLocation, (resourceLoc, bakedModel) -> afterBakeModifier.apply(bakedModel)));
		modelConsumers.forEach((resourceLocation, bakedModelConsumer) -> bakedModelConsumer.accept(map.get(resourceLocation)));
	}

	public BakedModel modifyModelAfterbake(BakedModel bakedModel, ResourceLocation id) {
		modelConsumers.getOrDefault(id, model -> {}).accept(bakedModel);
		return afterBakeModifiers.getOrDefault(id, Function.identity()).apply(bakedModel);
	}
}
