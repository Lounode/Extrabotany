package io.github.lounode.datagenlib.patchouli;

import net.minecraft.resources.ResourceLocation;


public class SpotlightPage extends AbstractPage {

    public SpotlightPage(String itemString) {
        object.addProperty("item", itemString);
    }
    /*
    public SpotlightPage(ItemLike itemLike) {
        this(getRegistryName(itemLike.asItem()).toString());
    }
    */
    public SpotlightPage withTitle(String title) {
        object.addProperty("title", title);
        return this;
    }

    public SpotlightPage linkRecipe(boolean link) {
        object.addProperty("link_recipe", link);
        return this;
    }

    public SpotlightPage withText(String text) {
        object.addProperty("text", text);
        return this;
    }

    @Override
    public ResourceLocation getType() {
        return ResourceLocation.tryParse("patchouli:spotlight");
    }
}
