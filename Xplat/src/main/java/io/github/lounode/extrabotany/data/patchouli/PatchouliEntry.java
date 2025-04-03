package io.github.lounode.extrabotany.data.patchouli;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import static io.github.lounode.extrabotany.common.lib.RegistryHelper.getRegistryName;


public class PatchouliEntry {

    JsonObject object = new JsonObject();
    JsonArray pages = new JsonArray();
    int textCounter = -1;
    private ResourceLocation name;
    private ResourceLocation category;


    public PatchouliEntry(ResourceLocation category, ResourceLocation name) {
        this.withCategory(category);
        this.withName(name);
    }

    public PatchouliEntry(ResourceLocation category, ItemLike itemLike) {
        this(category, getRegistryName(itemLike.asItem()));
        withIcon(itemLike);

    }
    public ResourceLocation getName() {
        return name;
    }
    public String getNameTranslationKey() {
        return name.getPath().contains(".") ? name.getPath() : name.getNamespace() + ".entry." + name.getPath();
    }

    public ResourceLocation getCategory() {
        return category;
    }

    public PatchouliEntry withName(ResourceLocation name) {
        this.name = name;
        object.addProperty("name", getNameTranslationKey());
        return this;
    }


    public PatchouliEntry withSortNum(int num) {
        object.addProperty("sortnum", num);
        return this;
    }

    public PatchouliEntry withPage(IPatchouliPage page) {
        pages.add(page.build());
        return this;
    }

    public PatchouliEntry withIcon(String path) {
        object.addProperty("icon", path);
        return this;
    }

    public PatchouliEntry withIcon(ItemLike item) {
        object.addProperty("icon", getRegistryName(item.asItem()).toString());
        return this;
    }

    public PatchouliEntry withCategory(ResourceLocation path) {
        category = path;
        object.addProperty("category", path.toString());
        return this;
    }

    public PatchouliEntry withProperty(String key, String string) {
        object.addProperty(key, string);
        return this;
    }

    public PatchouliEntry withProperty(String key, Number number) {
        object.addProperty(key, number);
        return this;
    }

    public PatchouliEntry withProperty(String key, Boolean bool) {
        object.addProperty(key, bool);
        return this;
    }


    public JsonObject build() {
        this.object.add("pages", pages);
        return this.object;
    }

    public PatchouliEntry withLocalizedText(String id) {
        textCounter++;
        return withTextPage("extrabotany.page."+ id + textCounter);
    }
    public PatchouliEntry withLocalizedText() {
        return withLocalizedText(this.name.getPath());
    }
    public PatchouliEntry withTextPage(String contents) {
        pages.add(new TextPage(contents).build());
        return this;
    }
}
