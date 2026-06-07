package io.github.lounode.extrabotany.data.recipes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import vazkii.botania.common.block.mana.ManaPoolBlock;
import vazkii.botania.common.crafting.TerrestrialAgglomerationRecipe;
import vazkii.botania.common.item.BotaniaItems;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class TerrestrialAgglomerationProvider extends ExtraBotanyRecipeProvider {
	public TerrestrialAgglomerationProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public String getName() {
		return "ExtraBotany Terra Plate recipes";
	}

	@Override
	public void buildRecipes(RecipeOutput recipeOutput) {
		save(recipeOutput, idFor("aerialite_ingot"), ManaPoolBlock.MAX_MANA / 2,
				new ItemStack(ExtraBotanyItems.aerialite),
				Ingredient.of(BotaniaItems.enderAirBottle),
				Ingredient.of(BotaniaItems.dragonstone),
				Ingredient.of(Items.PHANTOM_MEMBRANE));
		save(recipeOutput, idFor("the_universe"), ManaPoolBlock.MAX_MANA,
				new ItemStack(ExtraBotanyItems.theUniverse),
				Ingredient.of(ExtraBotanyItems.theChaos),
				Ingredient.of(ExtraBotanyItems.theOrigin),
				Ingredient.of(ExtraBotanyItems.theEnd)
		);
		save(recipeOutput, idFor(ExtraBotanyItems.rheinHammer), ManaPoolBlock.MAX_MANA * 4,
				new ItemStack(ExtraBotanyItems.rheinHammer),
				Ingredient.of(ExtraBotanyItems.manasteelHammer),
				Ingredient.of(ExtraBotanyItems.elementiumHammer),
				Ingredient.of(ExtraBotanyItems.terrasteelHammer),
				Ingredient.of(ExtraBotanyItems.gaiaHammer),
				Ingredient.of(ExtraBotanyItems.photoniumHammer),
				Ingredient.of(ExtraBotanyItems.shadowiumHammer),
				Ingredient.of(ExtraBotanyItems.aerialiteHammer),
				Ingredient.of(ExtraBotanyItems.orichalcosHammer),
				Ingredient.of(ExtraBotanyItems.dasRheingold),
				Ingredient.of(ExtraBotanyItems.theUniverse)
		);

	}

	private static void save(RecipeOutput recipeOutput, ResourceLocation id, int mana, ItemStack output, Ingredient... inputs) {
		recipeOutput.accept(id, new TerrestrialAgglomerationRecipe(mana, output, inputs), null);
	}

	private static ResourceLocation idFor(String s) {
		return prefix("terra_plate/" + s);
	}

	private static ResourceLocation idFor(ItemLike itemLike) {
		return prefix("terra_plate/" + BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath());
	}
}
