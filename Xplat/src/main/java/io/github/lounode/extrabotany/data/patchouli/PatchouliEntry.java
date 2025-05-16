package io.github.lounode.extrabotany.data.patchouli;

import com.demonwav.mcdev.annotations.Translatable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.lounode.extrabotany.data.patchouli.page.IPatchouliPage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Map;

import static io.github.lounode.extrabotany.common.lib.RegistryHelper.getRegistryName;


public class PatchouliEntry {
    JsonObject object = new JsonObject();
    private ResourceLocation id;

    public PatchouliEntry(ResourceLocation category,
                          @Translatable String name,
                          Item icon,
                          List<IPatchouliPage> pages,
                          ResourceLocation id,
                          int sortNum,
                          ResourceLocation advancement,
                          Map<ResourceLocation, Integer> extraRecipeMappings) {
        object.addProperty("category", category.toString());
        object.addProperty("name", name);
        object.addProperty("icon", getRegistryName(icon.asItem()).toString());

        if (sortNum != 0) {
            object.addProperty("sortnum", sortNum);
        }

        if (advancement != null) {
            object.addProperty("advancement", advancement.toString());
        }

        JsonArray pagesJArray = new JsonArray();
        for (var page : pages) {
            pagesJArray.add(page.build());
        }
        this.object.add("pages", pagesJArray);

        if (!extraRecipeMappings.isEmpty()) {
            JsonObject mappings = new JsonObject();

            for (var mapping : extraRecipeMappings.entrySet()) {
                mappings.addProperty(mapping.getKey().toString() , mapping.getValue());
            }

            this.object.add("extra_recipe_mappings", mappings);
        }

        this.id = id;
    }

    public ResourceLocation getID() {
        return this.id;
    }

    public JsonObject serializeEntry() {
        return object;
    }
}
