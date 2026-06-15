package io.github.lounode.extrabotany.data.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import io.github.lounode.extrabotany.common.crafting.OmniVioletsRecipe;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class OmnivioletProvider extends ExtraBotanyRecipeProvider {

	public OmnivioletProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {
		save(recipeOutput, id("book"), Ingredient.of(Items.BOOK), 50);
		save(recipeOutput, id("written_book"), Ingredient.of(Items.WRITTEN_BOOK), 65);
	}

	protected ResourceLocation id(String id) {
		return prefix("omniviolet/" + id);
	}

	@Override
	public String getName() {
		return "Extrabotany Omniviolet recipes";
	}

	private static void save(RecipeOutput recipeOutput, ResourceLocation id, Ingredient input, int burnTime) {
		recipeOutput.accept(id, new OmniVioletsRecipe(id, input, burnTime), null);
	}
}
