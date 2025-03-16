package io.github.lounode.datagenlib.patchouli;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

public interface IPatchouliPage {
    ResourceLocation getType();

    JsonObject build();
}
