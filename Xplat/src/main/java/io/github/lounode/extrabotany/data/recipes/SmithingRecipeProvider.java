package io.github.lounode.extrabotany.data.recipes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import vazkii.botania.common.item.BotaniaItems;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class SmithingRecipeProvider extends ExtraBotanyRecipeProvider {
	public SmithingRecipeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {
		Ingredient heroMedal = Ingredient.of(ExtraBotanyItems.heroMedal);

		SmithingTransformRecipeBuilder.smithing(
				Ingredient.of(BotaniaItems.dreamwoodTwig),
				Ingredient.of(BotaniaItems.terraSword),
				heroMedal,
				RecipeCategory.COMBAT,
				ExtraBotanyItems.excalibur
		)
				.unlocks("has_item", conditionsFromItem(ExtraBotanyItems.heroMedal))
				.save(recipeOutput, id(ExtraBotanyItems.excalibur));
		SmithingTransformRecipeBuilder.smithing(
				Ingredient.of(BotaniaItems.gaiaIngot),
				Ingredient.of(ExtraBotanyItems.terrasteelHammer),
				heroMedal,
				RecipeCategory.COMBAT,
				ExtraBotanyItems.gaiaHammer
		)
				.unlocks("has_item", conditionsFromItem(BotaniaItems.gaiaIngot))
				.save(recipeOutput, id(ExtraBotanyItems.gaiaHammer));
		SmithingTransformRecipeBuilder.smithing(
				Ingredient.of(ExtraBotanyTags.Items.INGOTS_ORICHALCOS),
				Ingredient.of(ExtraBotanyItems.terrasteelShield),
				heroMedal,
				RecipeCategory.COMBAT,
				ExtraBotanyItems.achillesShield
		)
				.unlocks("has_item", conditionsFromTag(ExtraBotanyTags.Items.INGOTS_ORICHALCOS))
				.save(recipeOutput, id(ExtraBotanyItems.achillesShield));
	}

	protected ResourceLocation id(ItemLike itemLike) {
		return prefix("smithing/" + getItemName(itemLike));
	}

	protected static String getItemName(ItemLike itemLike) {
		return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
	}

	@Override
	public String getName() {
		return "Extrabotany smithing recipes";
	}
}
