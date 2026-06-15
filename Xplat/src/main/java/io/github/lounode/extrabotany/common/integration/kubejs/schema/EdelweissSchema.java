package io.github.lounode.extrabotany.common.integration.kubejs.schema;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface EdelweissSchema {

	RecipeKey<String> INPUT = StringComponent.ID.inputKey("input");
	RecipeKey<Integer> OUTPUT_MANA = NumberComponent.INT.otherKey("outputMana");

	RecipeSchema SCHEMA = new RecipeSchema(OUTPUT_MANA, INPUT);
}
