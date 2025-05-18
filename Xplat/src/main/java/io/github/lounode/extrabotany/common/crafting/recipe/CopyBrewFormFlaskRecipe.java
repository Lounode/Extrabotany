package io.github.lounode.extrabotany.common.crafting.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.common.item.BotaniaItems;

public class CopyBrewFormFlaskRecipe extends CopyBrewRecipe {
    
    public static final RecipeSerializer<CopyBrewFormFlaskRecipe> SERIALIZER = new CopyBrewFormFlaskRecipe.Serializer();

    public CopyBrewFormFlaskRecipe(ShapelessRecipe compose) {
        super(compose);
    }

    @Override
    public Item getBrewSource() {
        return BotaniaItems.brewFlask;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    private static class Serializer implements RecipeSerializer<CopyBrewFormFlaskRecipe> {
        private Serializer() {
        }

        @Override
        public CopyBrewFormFlaskRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
            return new CopyBrewFormFlaskRecipe(SHAPELESS_RECIPE.fromJson(recipeId, json));
        }

        @Override
        public CopyBrewFormFlaskRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
            return new CopyBrewFormFlaskRecipe(SHAPELESS_RECIPE.fromNetwork(recipeId, buffer));
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull CopyBrewFormFlaskRecipe recipe) {
            SHAPELESS_RECIPE.toNetwork(buffer, recipe);
        }
    }
}
