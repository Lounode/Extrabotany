package io.github.lounode.extrabotany.common.integration.kubejs.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.ReplacementMatch;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.util.UtilsJS;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.crafting.BlockStateIngredient;
import vazkii.botania.common.crafting.BlockStateStateIngredient;
import vazkii.botania.common.crafting.StateIngredientHelper;

import java.util.List;

public record BlockStateComponent(ComponentRole crole) implements RecipeComponent<StateIngredient> {
	public static final RecipeComponent<StateIngredient> INPUT = new BlockStateComponent(ComponentRole.INPUT);
	public static final RecipeComponent<StateIngredient> OUTPUT = new BlockStateComponent(ComponentRole.OUTPUT);
	public static final RecipeComponent<StateIngredient> BLOCK = new BlockStateComponent(ComponentRole.OTHER);

	public static final StateIngredient EMPTY_INGREDIENT = new StateIngredient() {
		@Override
		public boolean test(BlockState state) {
			return false;
		}

		@Override
		public BlockState pick(RandomSource random) {
			return Blocks.AIR.defaultBlockState();
		}

		@Override
		public JsonObject serialize() {
			return new JsonObject();
		}

		@Override
		public void write(FriendlyByteBuf buffer) {

		}

		@Override
		public List<ItemStack> getDisplayedStacks() {
			return List.of();
		}

		@Override
		public List<BlockState> getDisplayed() {
			return List.of();
		}
	};

	@Override
	public ComponentRole role() {
		return crole;
	}

	@Override
	public String componentType() {
		return "block_state";
	}

	@Override
	public Class<?> componentClass() {
		return StateIngredient.class;
	}

	@Override
	public JsonElement write(RecipeJS recipe, StateIngredient value) {
		return value.serialize();
	}

	@Override
	public StateIngredient read(RecipeJS recipe, Object from) {
		if (from instanceof StateIngredient in) {
			return in;
		} else if (from instanceof Block b) {
			return StateIngredientHelper.of(b);
		} else if (from instanceof BlockState s) {
			return StateIngredientHelper.of(s);
		} else if (from instanceof JsonObject json) {
			return StateIngredientHelper.deserialize(json);
		} else if (from instanceof CharSequence chars) {
			String s = chars.toString();
			if (s.startsWith("#")) {
				ResourceLocation tagLocation = ResourceLocation.tryParse(s.substring(1));
				return StateIngredientHelper.of(TagKey.create(Registries.BLOCK, tagLocation));
			}
			return StateIngredientHelper.of(UtilsJS.parseBlockState(chars.toString()));
		} else {
			return EMPTY_INGREDIENT;
		}
	}

	@Override
	public boolean isInput(RecipeJS recipe, StateIngredient value, ReplacementMatch match) {
		return crole.isInput();
	}

	@Override
	public boolean isOutput(RecipeJS recipe, StateIngredient value, ReplacementMatch match) {
		return crole.isOutput();
	}

	@Override
	public String checkEmpty(RecipeKey<StateIngredient> key, StateIngredient value) {
		if (value instanceof BlockStateIngredient ingr && ingr.getBlock() == Blocks.AIR) {
			return "Blocks '" + key.name + "' can't be empty!";
		} else if (value instanceof BlockStateStateIngredient ingr && ingr.getState().isAir()) {
			return "Blocks '" + key.name + "' can't be empty!";
		}

		return "";
	}

	@Override
	public String toString() {
		return componentType();
	}
}
