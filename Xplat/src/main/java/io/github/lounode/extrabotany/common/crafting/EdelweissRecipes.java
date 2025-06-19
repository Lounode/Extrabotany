package io.github.lounode.extrabotany.common.crafting;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import org.jetbrains.annotations.NotNull;

import io.github.lounode.extrabotany.api.recipe.EdelweissRecipe;

public class EdelweissRecipes implements EdelweissRecipe {

	private final ResourceLocation id;
	private final EntityTypePredicate input;
	private final int outputMana;

	public EdelweissRecipes(ResourceLocation id, EntityTypePredicate input, int outputMana) {
		this.id = id;
		this.input = input;
		this.outputMana = outputMana;
	}

	@Override
	public EntityTypePredicate getInput() {
		return input;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ExtraBotanyRecipeTypes.EDELWEISS_SERIALIZER;
	}

	@Override
	public RecipeType<? extends EdelweissRecipe> getType() {
		return ExtraBotanyRecipeTypes.EDELWEISS_RECIPE_TYPE;
	}

	@Override
	public int getManaOutput() {
		return outputMana;
	}

	public static class Serializer implements RecipeSerializer<EdelweissRecipes> {
		@Override
		public EdelweissRecipes fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
			var input = EntityTypePredicate.fromJson(json.getAsJsonPrimitive("input"));
			var outputMana = GsonHelper.getAsInt(json, "outputMana");
			return new EdelweissRecipes(recipeId, input, outputMana);
		}

		@Override
		public EdelweissRecipes fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
			var input = EntityTypePredicate.fromJson(new JsonPrimitive(buffer.readUtf()));
			var outputMana = buffer.readInt();
			return new EdelweissRecipes(recipeId, input, outputMana);
		}

		@Override
		public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull EdelweissRecipes recipe) {
			buffer.writeUtf(recipe.getInput().serializeToJson().getAsString());
			buffer.writeInt(recipe.getManaOutput());
		}
	}
}
