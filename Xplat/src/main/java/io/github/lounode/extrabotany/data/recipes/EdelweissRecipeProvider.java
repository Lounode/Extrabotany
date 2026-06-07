package io.github.lounode.extrabotany.data.recipes;

import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import io.github.lounode.extrabotany.common.crafting.EdelweissRecipes;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class EdelweissRecipeProvider extends ExtraBotanyRecipeProvider {

	public EdelweissRecipeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {
		ResourceLocation id = id("snow_golem");
		recipeOutput.accept(id, new EdelweissRecipes(id, EntityTypePredicate.of(EntityType.SNOW_GOLEM), 3200), null);
	}

	protected ResourceLocation id(String id) {
		return prefix("edelweiss/" + id);
	}

	@Override
	public String getName() {
		return "Extrabotany Edelweiss recipes";
	}
}
