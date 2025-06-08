package io.github.lounode.extrabotany.common.integration.kubejs.schema;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface NaturePedestalSchema {

	RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("output");
	RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
	RecipeKey<InputItem> SMASH_TOOLS = ItemComponents.INPUT.key("smash_tools").optional(InputItem.of("#extrabotany:hammers")).alwaysWrite();
	RecipeKey<Integer> EXP = NumberComponent.INT.key("exp").optional(5).alwaysWrite();
	RecipeKey<Integer> STRIKE = NumberComponent.INT.key("strike").optional(5).alwaysWrite();

	RecipeSchema SCHEMA = new RecipeSchema(RESULT, INPUT, SMASH_TOOLS, EXP, STRIKE);
}
