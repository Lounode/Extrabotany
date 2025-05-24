package io.github.lounode.extrabotany.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ExtrabotanyModelLayers {
	public static final ModelLayerLocation STARRY_IDOL_ARMOR_NORMAL = make("starry_idol_armor_normal");
	public static final ModelLayerLocation STARRY_IDOL_ARMOR_DRESS = make("starry_idol_armor_dress");

	private static ModelLayerLocation make(String name) {
		return make(name, "main");
	}

	private static ModelLayerLocation make(String name, String layer) {
		// Don't add to vanilla's ModelLayers. It seems to only be used for error checking
		// And would be annoying to do under Forge's parallel mod loading
		return new ModelLayerLocation(prefix(name), layer);
	}

	public static void init() {}
}
