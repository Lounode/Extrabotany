package io.github.lounode.extrabotany.common.integration.kubejs.util;

import com.mojang.serialization.Codec;

import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.type.TypeInfo;

import net.minecraft.world.level.block.Blocks;

import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.crafting.BlockStateIngredient;
import vazkii.botania.common.crafting.StateIngredients;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public enum BlockStateComponent implements RecipeComponent<StateIngredient> {
	INSTANCE;

	public static final RecipeComponentType<StateIngredient> TYPE = RecipeComponentType.unit(prefix("block_state"), INSTANCE);

	@Override
	public RecipeComponentType<?> type() {
		return TYPE;
	}

	@Override
	public Codec<StateIngredient> codec() {
		return StateIngredients.TYPED_CODEC;
	}

	@Override
	public TypeInfo typeInfo() {
		return TypeInfo.of(StateIngredient.class);
	}

	@Override
	public boolean isEmpty(StateIngredient value) {
		return value == StateIngredients.NONE
				|| value instanceof BlockStateIngredient ingredient && ingredient.state().is(Blocks.AIR);
	}

	@Override
	public String toString() {
		return "extrabotany:block_state";
	}
}
