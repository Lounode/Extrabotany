package io.github.lounode.extrabotany.api.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public interface OmnivioletRecipe extends Recipe<Container> {
	ResourceLocation TYPE_ID = prefix("omniviolet");

	Ingredient getInput();
	int getBurnTime();

	@Override
	RecipeType<? extends OmnivioletRecipe> getType();

	@Override
	default boolean matches(Container c, Level l) {
		return false;
	}

	@Override
	default ItemStack assemble(Container c, @NotNull RegistryAccess registries) {
		return ItemStack.EMPTY;
	}

	@Override
	default boolean canCraftInDimensions(int width, int height) {
		return false;
	}

	@Override
	default ItemStack getResultItem(@NotNull RegistryAccess registries) {
		return ItemStack.EMPTY;
	}

	@Override
	default boolean isSpecial() {
		return true;
	}
}
