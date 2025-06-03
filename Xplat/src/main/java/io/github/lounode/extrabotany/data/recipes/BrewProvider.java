package io.github.lounode.extrabotany.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;

import io.github.lounode.extrabotany.common.brew.ExtraBotanyBrews;

import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class BrewProvider extends vazkii.botania.data.recipes.BrewProvider {
	public BrewProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public String getName() {
		return "Extrabotany Brew recipes";
	}

	@Override
	public void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
		consumer.accept(new FinishedRecipe(idFor("revolution"), ExtraBotanyBrews.revolution,
				Ingredient.of(Items.NETHER_WART),
				Ingredient.of(Items.IRON_PICKAXE),
				Ingredient.of(Items.SUGAR)));
		consumer.accept(new FinishedRecipe(idFor("deadpool"), ExtraBotanyBrews.deadpool,
				Ingredient.of(Items.NETHER_WART),
				Ingredient.of(Items.ROTTEN_FLESH),
				Ingredient.of(Items.BONE),
				Ingredient.of(Items.BLAZE_POWDER)));

		consumer.accept(new FinishedRecipe(idFor("shell"), ExtraBotanyBrews.shield,
				Ingredient.of(Items.NETHER_WART),
				Ingredient.of(Items.GOLDEN_APPLE),
				Ingredient.of(Items.BUCKET),
				Ingredient.of(Blocks.OBSIDIAN)));

		consumer.accept(new FinishedRecipe(idFor("floating"), ExtraBotanyBrews.floating,
				Ingredient.of(Items.NETHER_WART),
				Ingredient.of(Items.CHORUS_FRUIT),
				Ingredient.of(Items.SUGAR)));

		consumer.accept(new FinishedRecipe(idFor("all_in_one"), ExtraBotanyBrews.allInOne,
				Ingredient.of(Items.NETHER_WART),
				Ingredient.of(Items.GOLDEN_CARROT),
				Ingredient.of(Items.GHAST_TEAR),
				Ingredient.of(Items.GLOWSTONE_DUST)));
	}

	private static ResourceLocation idFor(String s) {
		return prefix("brew/" + s);
	}

	protected static class FinishedRecipe implements net.minecraft.data.recipes.FinishedRecipe {
		private final ResourceLocation id;
		private final Brew brew;
		private final Ingredient[] inputs;

		public FinishedRecipe(ResourceLocation id, Brew brew, Ingredient... inputs) {
			this.id = id;
			this.brew = brew;
			this.inputs = inputs;
		}

		@Override
		public void serializeRecipeData(JsonObject json) {
			json.addProperty("brew", BotaniaAPI.instance().getBrewRegistry().getKey(brew).toString());
			JsonArray ingredients = new JsonArray();
			for (Ingredient ingr : inputs) {
				ingredients.add(ingr.toJson());
			}
			json.add("ingredients", ingredients);
		}

		@Override
		public ResourceLocation getId() {
			return id;
		}

		@Override
		public RecipeSerializer<?> getType() {
			return BotaniaRecipeTypes.BREW_SERIALIZER;
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
