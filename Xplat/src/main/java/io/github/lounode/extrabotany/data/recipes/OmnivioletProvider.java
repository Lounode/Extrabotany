package io.github.lounode.extrabotany.data.recipes;

import com.google.gson.JsonObject;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import org.jetbrains.annotations.Nullable;

import io.github.lounode.extrabotany.common.crafting.ExtraBotanyRecipeTypes;

import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class OmnivioletProvider extends ExtraBotanyRecipeProvider {

	public OmnivioletProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	protected void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
		consumer.accept(new FinishedRecipe(id("book"), Ingredient.of(Items.BOOK), 50));
		consumer.accept(new FinishedRecipe(id("written_book"), Ingredient.of(Items.WRITTEN_BOOK), 65));
	}

	protected ResourceLocation id(String id) {
		return prefix("omniviolet/" + id);
	}

	@Override
	public String getName() {
		return "Extrabotany Omniviolet recipes";
	}

	protected static class FinishedRecipe implements net.minecraft.data.recipes.FinishedRecipe {
		private final ResourceLocation id;
		private final Ingredient input;
		private final int burnTime;

		public FinishedRecipe(ResourceLocation id, Ingredient input, int burnTime) {
			this.id = id;
			this.input = input;
			this.burnTime = burnTime;
		}

		@Override
		public void serializeRecipeData(JsonObject json) {
			json.add("input", input.toJson());
			json.addProperty("burnTime", burnTime);
		}

		@Override
		public ResourceLocation getId() {
			return id;
		}

		@Override
		public RecipeSerializer<?> getType() {
			return ExtraBotanyRecipeTypes.OMNIVIOLET_SERIALIZER;
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement() {
			return null;
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId() {
			return null;
		}
	}
}
