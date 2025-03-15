package io.github.lounode.extrabotany.common.lib;

import io.github.lounode.extrabotany.ExtraBotany;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

public class ResourceLocationHelper {
    public static ResourceLocation prefix(String path) {
        return ResourceLocation.fromNamespaceAndPath(ExtraBotany.MODID, path);
    }

    public static ModelResourceLocation modelResourceLocation(String path, String variant) {
        return new ModelResourceLocation(prefix(path), variant);
    }
}
