package io.github.lounode.datagenlib.patchouli;

import net.minecraft.resources.ResourceLocation;

public class EnchantingPage extends AbstractPage {

    public EnchantingPage(String recipe) {
        object.addProperty("recipe", recipe);
    }

    @Override
    public ResourceLocation getType() {
        return null;
    }
    /*
    @Override
    public ResourceLocation getType() {
        return ArsNouveau.prefix( "enchanting_recipe");
    }

     */
}
