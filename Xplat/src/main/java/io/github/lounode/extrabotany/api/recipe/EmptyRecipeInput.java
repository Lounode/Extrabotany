package io.github.lounode.extrabotany.api.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public enum EmptyRecipeInput implements RecipeInput {
	INSTANCE;

	@Override
	public ItemStack getItem(int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public int size() {
		return 0;
	}
}
