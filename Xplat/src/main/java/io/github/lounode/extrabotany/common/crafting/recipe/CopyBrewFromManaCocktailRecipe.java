package io.github.lounode.extrabotany.common.crafting.recipe;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
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
	public @NotNull ItemStack assemble(@NotNull CraftingInput inv, @NotNull HolderLookup.Provider registries) {
		ItemStack result = getResultItem(registries).copy();
		ItemStack brewSource = ItemStack.EMPTY;
		ItemStack input = ItemStack.EMPTY;

		List<ItemStack> _inputs = new ArrayList<>();

		for (int i = 0; i < inv.size(); i++) {
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
			if (wine.getSwigsLeft(input) < wine.getSwigs(input)) {
				return ItemStack.EMPTY;
			}
		}

		BrewUtil.setBrew(result, BrewUtil.getBrew(brewSource));

		return result;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInput container) {
		NonNullList<ItemStack> nonnulllist = NonNullList.withSize(container.size(), ItemStack.EMPTY);

		for (int i = 0; i < nonnulllist.size(); ++i) {
			Item item = container.getItem(i).getItem();
			if (item.hasCraftingRemainingItem()) {
				nonnulllist.set(i, ItemStack.EMPTY);
			}
		}

		return nonnulllist;
	}

	private static class Serializer implements RecipeSerializer<CopyBrewFromManaCocktailRecipe> {
		private static final MapCodec<CopyBrewFromManaCocktailRecipe> CODEC = RecipeSerializer.SHAPELESS_RECIPE.codec()
				.xmap(CopyBrewFromManaCocktailRecipe::new, recipe -> recipe);
		private static final StreamCodec<RegistryFriendlyByteBuf, CopyBrewFromManaCocktailRecipe> STREAM_CODEC = RecipeSerializer.SHAPELESS_RECIPE.streamCodec()
				.map(CopyBrewFromManaCocktailRecipe::new, recipe -> recipe);

		private Serializer() {}

		@Override
		public MapCodec<CopyBrewFromManaCocktailRecipe> codec() {
			return CODEC;
		}

		@Override
		public StreamCodec<RegistryFriendlyByteBuf, CopyBrewFromManaCocktailRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
}
