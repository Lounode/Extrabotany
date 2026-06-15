package io.github.lounode.extrabotany.data.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import org.jetbrains.annotations.Nullable;

import io.github.lounode.extrabotany.common.crafting.PedestalsRecipe;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;

import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class PedestalRecipeProvider extends ExtraBotanyRecipeProvider {
	public PedestalRecipeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public String getName() {
		return "ExtraBotany pedestal recipes";
	}

	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {
		save(recipeOutput, id("gilded_potato_mashed"),
				new ItemStack(ExtraBotanyItems.gildedPotatoMashed),
				Ingredient.of(ExtraBotanyItems.gildedPotato)
		);
		save(recipeOutput, id("spirit_fragment"),
				new ItemStack(ExtraBotanyItems.spiritFragment),
				Ingredient.of(ExtraBotanyItems.spiritFuel),
				Ingredient.of(ExtraBotanyTags.Items.HAMMERS),
				10,
				5
		);
	}

	protected ResourceLocation id(String s) {
		return prefix("pedestal_smash/" + s);
	}

	private static void save(RecipeOutput recipeOutput, ResourceLocation id, ItemStack output, Ingredient input) {
		save(recipeOutput, id, output, input, null, 5, 5);
	}

	private static void save(RecipeOutput recipeOutput, ResourceLocation id, ItemStack output, Ingredient input, @Nullable Ingredient hammer, int strike, int exp) {
		recipeOutput.accept(id, new PedestalsRecipe(id, output, hammer == null ? Ingredient.of(ExtraBotanyTags.Items.HAMMERS) : hammer, input, strike, exp), null);
	}
}
