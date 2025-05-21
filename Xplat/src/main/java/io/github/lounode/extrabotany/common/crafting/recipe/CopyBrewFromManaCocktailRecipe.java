package io.github.lounode.extrabotany.common.crafting.recipe;

import com.google.gson.JsonObject;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import org.jetbrains.annotations.NotNull;

import io.github.lounode.extrabotany.common.brew.BrewUtil;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.item.brew.InfiniteWineItem;

import java.util.ArrayList;
import java.util.List;

public final class CopyBrewFromManaCocktailRecipe extends CopyBrewRecipe {

	public static final RecipeSerializer<CopyBrewFromManaCocktailRecipe> SERIALIZER = new CopyBrewFromManaCocktailRecipe.Serializer();

	public CopyBrewFromManaCocktailRecipe(ShapelessRecipe compose) {
		super(compose);
	}

	@Override
	public Item getBrewSource() {
		return ExtraBotanyItems.manaCocktail;
	}

	@Override
	public @NotNull RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public @NotNull ItemStack assemble(@NotNull CraftingContainer inv, @NotNull RegistryAccess registries) {
		ItemStack result = getResultItem(registries).copy();
		ItemStack brewSource = ItemStack.EMPTY;
		ItemStack input = ItemStack.EMPTY;

		List<ItemStack> _inputs = new ArrayList<>();

		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack _input = inv.getItem(i);
			if (_input.isEmpty()) {
				continue;
			}
			if (_input.is(getBrewSource())) {
				brewSource = _input;
			} else {
				input = _input;
			}

			_inputs.add(_input);
		}

		if (_inputs.size() != 2 || brewSource.isEmpty() || input.isEmpty()) {
			return ItemStack.EMPTY;
		}

		if (input.getItem() instanceof InfiniteWineItem wine) {
			if (wine.getSwigsLeft(input) < wine.getSwigs()) {
				return ItemStack.EMPTY;
			}
		}

		BrewUtil.setBrew(result, BrewUtil.getBrew(brewSource));

		return result;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
		NonNullList<ItemStack> nonnulllist = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);

		for (int i = 0; i < nonnulllist.size(); ++i) {
			Item item = container.getItem(i).getItem();
			if (item.hasCraftingRemainingItem()) {
				nonnulllist.set(i, ItemStack.EMPTY);
			}
		}

		return nonnulllist;
	}

	private static class Serializer implements RecipeSerializer<CopyBrewFromManaCocktailRecipe> {
		private Serializer() {}

		@Override
		public CopyBrewFromManaCocktailRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
			return new CopyBrewFromManaCocktailRecipe(SHAPELESS_RECIPE.fromJson(recipeId, json));
		}

		@Override
		public CopyBrewFromManaCocktailRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
			return new CopyBrewFromManaCocktailRecipe(SHAPELESS_RECIPE.fromNetwork(recipeId, buffer));
		}

		@Override
		public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull CopyBrewFromManaCocktailRecipe recipe) {
			SHAPELESS_RECIPE.toNetwork(buffer, recipe);
		}
	}
}
