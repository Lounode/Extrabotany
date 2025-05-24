package io.github.lounode.extrabotany.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import io.github.lounode.extrabotany.client.model.armor.StarryIdolArmorModel;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ExtrabotanyLayerDefinitions {

	public static void init(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> consumer) {
		consumer.accept(ExtrabotanyModelLayers.STARRY_IDOL_ARMOR_NORMAL, () -> LayerDefinition.create(StarryIdolArmorModel.createNormalMesh(), 128, 128));
		consumer.accept(ExtrabotanyModelLayers.STARRY_IDOL_ARMOR_DRESS, () -> LayerDefinition.create(StarryIdolArmorModel.createDressMesh(), 128, 128));
	}
}
