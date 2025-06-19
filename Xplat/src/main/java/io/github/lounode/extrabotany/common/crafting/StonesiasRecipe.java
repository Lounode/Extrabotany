package io.github.lounode.extrabotany.common.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.crafting.StateIngredientHelper;

import io.github.lounode.extrabotany.api.recipe.StonesiaRecipe;

public class StonesiasRecipe implements StonesiaRecipe {

	private final ResourceLocation id;
	private final StateIngredient input;
	private final int outputMana;

	public StonesiasRecipe(ResourceLocation id, StateIngredient input, int outputMana) {
		this.id = id;
		this.input = input;
		this.outputMana = outputMana;
	}

	@Override
	public StateIngredient getInput() {
		return input;
	}

	@Override
	public int getManaOutput() {
		return outputMana;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ExtraBotanyRecipeTypes.STONESIA_SERIALIZER;
	}

	@Override
	public RecipeType<? extends StonesiaRecipe> getType() {
		return ExtraBotanyRecipeTypes.STONESIA_RECIPE_TYPE;
	}

	public static class Serializer implements RecipeSerializer<StonesiasRecipe> {
		@Override
		public StonesiasRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
			var input = StateIngredientHelper.tryDeserialize(GsonHelper.getAsJsonObject(json, "input"));
			if (input == null) {
				throw new JsonSyntaxException("Unknown input: " + GsonHelper.getAsJsonObject(json, "input"));
			}
			var outputMana = GsonHelper.getAsInt(json, "outputMana");

			return new StonesiasRecipe(recipeId, input, outputMana);
		}

		@Override
		public StonesiasRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
			var input = StateIngredientHelper.read(buffer);
			var outputMana = buffer.readInt();
			return new StonesiasRecipe(recipeId, input, outputMana);
		}

		@Override
		public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull StonesiasRecipe recipe) {
			recipe.getInput().write(buffer);
			buffer.writeInt(recipe.getManaOutput());
		}
	}
}
