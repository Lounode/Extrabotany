package io.github.lounode.extrabotany.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ExtrabotanyLayerDefinitions {

	public static void init(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> consumer) {
		//consumer.accept(ExtrabotanyModelLayers.STARRY_IDOL_INNER_ARMOR, () -> LayerDefinition.create(ElementiumArmorModel.createInsideMesh(), 64, 128));
		//consumer.accept(ExtrabotanyModelLayers.STARRY_IDOL_OUTER_ARMOR, () -> LayerDefinition.create(ElementiumArmorModel.createOutsideMesh(), 64, 128));
		consumer.accept(ExtrabotanyModelLayers.STARRY_IDOL_ARMOR, () -> LayerDefinition.create(ModelMikuArmor.createBodyLayer(), 128, 128));
	}
}
