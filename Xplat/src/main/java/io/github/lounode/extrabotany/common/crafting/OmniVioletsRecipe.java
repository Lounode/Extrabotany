package io.github.lounode.extrabotany.common.crafting;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import org.jetbrains.annotations.NotNull;

import io.github.lounode.extrabotany.api.recipe.OmnivioletRecipe;

public class OmniVioletsRecipe implements OmnivioletRecipe {

	private final ResourceLocation id;
	private final Ingredient input;
	private final int burnTime;

	public OmniVioletsRecipe(ResourceLocation id, Ingredient input, int burnTime) {
		this.id = id;
		this.input = input;
		this.burnTime = burnTime;
	}

	@Override
	public Ingredient getInput() {
		return input;
	}

	@Override
	public int getBurnTime() {
		return burnTime;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ExtraBotanyRecipeTypes.OMNIVIOLET_SERIALIZER;
	}

	@Override
	public RecipeType<? extends OmnivioletRecipe> getType() {
		return ExtraBotanyRecipeTypes.OMNIVIOLET_RECIPE_TYPE;
	}

	public static class Serializer implements RecipeSerializer<OmniVioletsRecipe> {
		@Override
		public OmniVioletsRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
			var input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"), false);
			var burnTime = GsonHelper.getAsInt(json, "burnTime");

			return new OmniVioletsRecipe(recipeId, input, burnTime);
		}

		@Override
		public OmniVioletsRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
			var input = Ingredient.fromNetwork(buffer);
			var burnTime = buffer.readInt();
			return new OmniVioletsRecipe(recipeId, input, burnTime);
		}

		@Override
		public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull OmniVioletsRecipe recipe) {
			recipe.getInput().toNetwork(buffer);
			buffer.writeInt(recipe.getBurnTime());
		}
	}
}
