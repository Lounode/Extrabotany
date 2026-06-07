package io.github.lounode.extrabotany.data.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.Ingredient;

import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class SmeltingProvider extends ExtraBotanyRecipeProvider {
	public SmeltingProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ExtraBotanyBlocks.gaiaQuartzBlock), RecipeCategory.BUILDING_BLOCKS,
				ExtraBotanyBlocks.smoothGaiaQuartz, 0.1f, 200)
				.unlockedBy("has_item", conditionsFromItem(ExtraBotanyBlocks.gaiaQuartzBlock))
				.save(recipeOutput, id("smooth_gaia_quartz"));
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ExtraBotanyBlocks.elementiumQuartzBlock), RecipeCategory.BUILDING_BLOCKS,
				ExtraBotanyBlocks.smoothElementiumQuartz, 0.1f, 200)
				.unlockedBy("has_item", conditionsFromItem(ExtraBotanyBlocks.elementiumQuartzBlock))
				.save(recipeOutput, id("smooth_elementium_quartz"));
	}

	protected static String id(String id) {
		return prefix("smelting/" + id).toString();
	}

	@Override
	public String getName() {
		return "Extrabotany smelting recipes";
	}
}
