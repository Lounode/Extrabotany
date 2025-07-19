package io.github.lounode.extrabotany.common.crafting.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.brew.BotaniaBrews;
import vazkii.botania.common.crafting.recipe.NoOpRecipeSerializer;
import vazkii.botania.common.item.lens.LensItem;

import io.github.lounode.extrabotany.common.brew.BrewUtil;
import io.github.lounode.extrabotany.common.item.brew.ManaCocktailItem;
import io.github.lounode.extrabotany.common.item.lens.PotionLens;

public final class PotionLensChangePotionRecipe extends CustomRecipe {

	public static final NoOpRecipeSerializer<PotionLensChangePotionRecipe> SERIALIZER = new NoOpRecipeSerializer<>(PotionLensChangePotionRecipe::new);

	public PotionLensChangePotionRecipe(ResourceLocation id) {
		super(id, CraftingBookCategory.MISC);
	}

	@Override
	public boolean matches(CraftingContainer inv, Level level) {
		boolean findPotionLens = false;
		boolean findCocktail = false;

		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (stack.isEmpty()) {
				continue;
			}

			if (stack.getItem() instanceof LensItem && LensItem.getLens(stack) instanceof PotionLens) {
				if (findPotionLens) {
					return false;
				}
				findPotionLens = true;
			} else if (stack.getItem() instanceof ManaCocktailItem cocktail && cocktail.getSwigsLeft(stack) >= cocktail.getSwigs()) {
				if (findCocktail) {
					return false;
				}
				findCocktail = true;
			} else {
				return false;
			}
		}

		return findPotionLens && findCocktail;
	}

	@Override
	public ItemStack assemble(CraftingContainer inv, RegistryAccess registryAccess) {
		ItemStack lensItem = ItemStack.EMPTY;
		ItemStack cocktailItem = ItemStack.EMPTY;

		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (stack.isEmpty()) {
				continue;
			}

			if (stack.getItem() instanceof LensItem && LensItem.getLens(stack) instanceof PotionLens) {
				lensItem = stack;
			} else {
				if (stack.getItem() instanceof ManaCocktailItem cocktail) {
					cocktailItem = stack;
				}
			}
		}

		if (lensItem == ItemStack.EMPTY || cocktailItem == ItemStack.EMPTY) {
			return ItemStack.EMPTY;
		}

		Brew brewFromCocktail = BrewUtil.getBrew(cocktailItem);

		if (brewFromCocktail == BotaniaBrews.fallbackBrew) {
			return ItemStack.EMPTY;
		}

		ItemStack result = lensItem.copy();
		result.setCount(1);
		BrewUtil.setBrew(result, brewFromCocktail);

		return result;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width > 1 || height > 1;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
}
