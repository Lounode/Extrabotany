package io.github.lounode.extrabotany.api.recipe;

import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public interface EdelweissRecipe extends Recipe<Container>, ManaOutputRecipe {
	ResourceLocation TYPE_ID = prefix("edelweiss");

	EntityTypePredicate getInput();

	@Override
	RecipeType<? extends EdelweissRecipe> getType();

	default int getManaOutput(@NotNull Level level, @NotNull BlockPos pos) {
		return getManaOutput();
	}

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
