package io.github.lounode.extrabotany.api.recipe;

import io.github.lounode.extrabotany.api.ExtraBotanyAPI;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

public interface PedestalRecipe extends Recipe<Container> {
    ResourceLocation TYPE_ID = new ResourceLocation(ExtraBotanyAPI.MODID, "pedestal_smash");

    @NotNull
    @Override
    default RecipeType<?> getType() {
        return BuiltInRegistries.RECIPE_TYPE.get(TYPE_ID);
    }

    Ingredient getSmashTools();
    Ingredient getInput();
    ItemStack getOutput();
    int getStrike();
    int getExp();

}
