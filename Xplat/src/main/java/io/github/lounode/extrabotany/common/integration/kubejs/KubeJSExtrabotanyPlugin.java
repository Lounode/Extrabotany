package io.github.lounode.extrabotany.common.integration.kubejs;

import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;

import io.github.lounode.extrabotany.common.integration.kubejs.schema.EdelweissSchema;
import io.github.lounode.extrabotany.common.integration.kubejs.schema.NaturePedestalSchema;
import io.github.lounode.extrabotany.common.integration.kubejs.schema.OmnivioletSchema;
import io.github.lounode.extrabotany.common.integration.kubejs.schema.StonesiaSchema;
import io.github.lounode.extrabotany.common.lib.LibMisc;

public class KubeJSExtrabotanyPlugin extends KubeJSPlugin {

	@Override
	public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
		event.namespace(LibMisc.MOD_ID)
				.register("pedestal_smash", NaturePedestalSchema.SCHEMA)
				.register("stonesia", StonesiaSchema.SCHEMA)
				.register("omniviolet", OmnivioletSchema.SCHEMA)
				.register("edelweiss", EdelweissSchema.SCHEMA);

	}
}
