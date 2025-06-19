package io.github.lounode.extrabotany.common.integration.kubejs.schema;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface StonesiaSchema {

	RecipeKey<InputItem[]> INPUTS = ItemComponents.INPUT_ARRAY.key("input");
	RecipeKey<Integer> OUTPUT_MANA = NumberComponent.INT.key("outputMana");

	RecipeSchema SCHEMA = new RecipeSchema(OUTPUT_MANA, INPUTS);
}
