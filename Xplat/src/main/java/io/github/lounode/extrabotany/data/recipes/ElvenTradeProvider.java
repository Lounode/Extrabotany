package io.github.lounode.extrabotany.data.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import vazkii.botania.common.crafting.ElvenTradeRecipe;
import vazkii.botania.common.item.BotaniaItems;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ElvenTradeProvider extends ExtraBotanyRecipeProvider {
	public ElvenTradeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void buildRecipes(RecipeOutput recipeOutput) {
		ResourceLocation id = id("elementium_quartz");
		recipeOutput.accept(id, new ElvenTradeRecipe(new ItemStack[] { new ItemStack(ExtraBotanyItems.elementiumQuartz) }, Ingredient.of(BotaniaItems.manaQuartz), Ingredient.of(BotaniaItems.manaQuartz)), null);
	}

	protected static Ingredient ingr(ItemLike i) {
		return Ingredient.of(i);
	}

	private static ResourceLocation id(String path) {
		return prefix("elven_trade/" + path);
	}

	@Override
	public String getName() {
		return "ExtraBotany elven trade recipes";
	}
}
