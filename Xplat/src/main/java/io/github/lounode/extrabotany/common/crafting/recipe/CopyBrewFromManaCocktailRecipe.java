package io.github.lounode.extrabotany.common.crafting.recipe;

import com.google.gson.JsonObject;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;


public class CopyBrewFromManaCocktailRecipe extends CopyBrewRecipe {

    public static final RecipeSerializer<CopyBrewFromManaCocktailRecipe> SERIALIZER = new CopyBrewFromManaCocktailRecipe.Serializer();

    public CopyBrewFromManaCocktailRecipe(ShapelessRecipe compose) {
        super(compose);
    }

    @Override
    public Item getBrewSource() {
        return ExtraBotanyItems.manaCocktail;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    private static class Serializer implements RecipeSerializer<CopyBrewFromManaCocktailRecipe> {
        private Serializer() {
        }

        @Override
        public CopyBrewFromManaCocktailRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
            return new CopyBrewFromManaCocktailRecipe(SHAPELESS_RECIPE.fromJson(recipeId, json));
        }

        @Override
        public CopyBrewFromManaCocktailRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
            return new CopyBrewFromManaCocktailRecipe(SHAPELESS_RECIPE.fromNetwork(recipeId, buffer));
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull CopyBrewFromManaCocktailRecipe recipe) {
            SHAPELESS_RECIPE.toNetwork(buffer, recipe);
        }
    }
}
