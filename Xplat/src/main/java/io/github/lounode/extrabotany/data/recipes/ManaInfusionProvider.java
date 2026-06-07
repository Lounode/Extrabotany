package io.github.lounode.extrabotany.data.recipes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.crafting.ManaInfusionRecipe;
import vazkii.botania.common.crafting.StateIngredients;

import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.block.flower.ExtrabotanyFlowerBlocks;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ManaInfusionProvider extends ExtraBotanyRecipeProvider {
	public ManaInfusionProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public String getName() {
		return "ExtraBotany mana pool recipes";
	}

	@Override
	public void buildRecipes(RecipeOutput recipeOutput) {
		save(recipeOutput, id("nightmare_fuel"), new ItemStack(ExtraBotanyItems.nightmareFuel), Ingredient.of(Items.COAL), 2000);
		save(recipeOutput, id("fried_chicken"), new ItemStack(ExtraBotanyItems.friedChicken), Ingredient.of(Items.COOKED_CHICKEN), 600);
		//Dimension
		dimension(recipeOutput, id("snowball_to_ender_pearl"), new ItemStack(Items.ENDER_PEARL), ingr(Items.SNOWBALL), 2000);
		dimension(recipeOutput, id("diamond_horse_armor_to_shulker_shell"), new ItemStack(Items.SHULKER_SHELL), ingr(Items.DIAMOND_HORSE_ARMOR), 20000);
		dimension(recipeOutput, id("apple_to_chorus_fruit"), new ItemStack(Items.CHORUS_FRUIT), ingr(Items.APPLE), 500);
		dimension(recipeOutput, id("stone_to_end_stone"), new ItemStack(Items.END_STONE), ingr(Items.STONE), 500);
		dimension(recipeOutput, id("cobblestone_to_nether_rack"), new ItemStack(Items.NETHERRACK), ingr(Items.COBBLESTONE), 500);
		dimension(recipeOutput, id("sand_to_soul_sand"), new ItemStack(Items.SOUL_SAND), ingr(Items.SAND), 500);
		dimension(recipeOutput, id("iron_ore_to_quartz_ore"), new ItemStack(Items.NETHER_QUARTZ_ORE), ingr(Items.IRON_ORE), 2000);
		dimension(recipeOutput, id("blaze_rod_dupe"), new ItemStack(Items.BLAZE_ROD, 2), ingr(Items.BLAZE_ROD), 2000);
		dimension(recipeOutput, id("nether_star_to_totem_of_undying"), new ItemStack(Items.TOTEM_OF_UNDYING), ingr(Items.NETHER_STAR), 50000);
		dimension(recipeOutput, id("the_origin_to_elytra"), new ItemStack(Items.ELYTRA), ingr(ExtraBotanyItems.theOrigin), 50000);
		//Mini
		mini(recipeOutput, ExtrabotanyFlowerBlocks.necrofleurChibi, ExtrabotanyFlowerBlocks.necrofleur);
	}

	protected void cycle(RecipeOutput recipeOutput, int cost, String group, ItemLike... items) {
		for (int i = 0; i < items.length; i++) {
			Ingredient in = ingr(items[i]);
			ItemStack out = new ItemStack(i == items.length - 1 ? items[0] : items[i + 1]);
			String id = String.format("%s_to_%s", BuiltInRegistries.ITEM.getKey(items[i].asItem()).getPath(), BuiltInRegistries.ITEM.getKey(out.getItem()).getPath());
			alchemy(recipeOutput, id(id), out, in, cost, group);
		}
	}

	protected void mini(RecipeOutput recipeOutput, ItemLike mini, ItemLike full) {
		alchemy(recipeOutput, id(BuiltInRegistries.ITEM.getKey(mini.asItem()).getPath()), new ItemStack(mini), ingr(full), 2500, "botania:flower_shrinking");
	}

	protected void deconstruct(RecipeOutput recipeOutput, String id, ItemLike items, ItemLike block) {
		alchemy(recipeOutput, id(id), new ItemStack(items, 4), ingr(block), 25, "botania:block_deconstruction");
	}

	protected ResourceLocation id(String s) {
		return prefix("mana_infusion/" + s);
	}

	protected static Ingredient ingr(ItemLike i) {
		return Ingredient.of(i);
	}

	private static final StateIngredient CONJURATION = StateIngredients.of(BotaniaBlocks.conjurationCatalyst);
	private static final StateIngredient ALCHEMY = StateIngredients.of(BotaniaBlocks.alchemyCatalyst);
	private static final StateIngredient DIMENSION = StateIngredients.of(ExtraBotanyBlocks.dimensionCatalyst);

	private static void dimension(RecipeOutput recipeOutput, ResourceLocation id, ItemStack output, Ingredient input, int mana) {
		save(recipeOutput, id, output, input, mana, "", DIMENSION);
	}

	private static void alchemy(RecipeOutput recipeOutput, ResourceLocation id, ItemStack output, Ingredient input, int mana, String group) {
		save(recipeOutput, id, output, input, mana, group, ALCHEMY);
	}

	private static void save(RecipeOutput recipeOutput, ResourceLocation id, ItemStack output, Ingredient input, int mana) {
		save(recipeOutput, id, output, input, mana, "", null);
	}

	private static void save(RecipeOutput recipeOutput, ResourceLocation id, ItemStack output, Ingredient input, int mana, String group, @Nullable StateIngredient catalyst) {
		recipeOutput.accept(id, new ManaInfusionRecipe(output, input, mana, group, catalyst), null);
	}
}
