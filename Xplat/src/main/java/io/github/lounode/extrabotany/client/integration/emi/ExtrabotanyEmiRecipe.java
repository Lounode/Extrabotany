package io.github.lounode.extrabotany.client.integration.emi;

import dev.emi.emi.api.recipe.EmiRecipeCategory;

import net.minecraft.world.item.crafting.Recipe;

import vazkii.botania.client.integration.emi.BotaniaEmiRecipe;

public abstract class ExtrabotanyEmiRecipe extends BotaniaEmiRecipe {
	public ExtrabotanyEmiRecipe(EmiRecipeCategory category, Recipe<?> recipe) {
		super(category, recipe);
	}
}
