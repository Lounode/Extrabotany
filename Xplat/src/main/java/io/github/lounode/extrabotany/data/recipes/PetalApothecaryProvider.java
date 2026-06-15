package io.github.lounode.extrabotany.data.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import vazkii.botania.common.crafting.PetalApothecaryRecipe;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.lib.BotaniaTags;

import io.github.lounode.extrabotany.common.block.flower.ExtrabotanyFlowerBlocks;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class PetalApothecaryProvider extends ExtraBotanyRecipeProvider {
	public PetalApothecaryProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void buildRecipes(RecipeOutput recipeOutput) {
		Ingredient white = tagIngr("petals/white");
		Ingredient orange = tagIngr("petals/orange");
		Ingredient magenta = tagIngr("petals/magenta");
		Ingredient lightBlue = tagIngr("petals/light_blue");
		Ingredient yellow = tagIngr("petals/yellow");
		Ingredient lime = tagIngr("petals/lime");
		Ingredient pink = tagIngr("petals/pink");
		Ingredient gray = tagIngr("petals/gray");
		Ingredient lightGray = tagIngr("petals/light_gray");
		Ingredient cyan = tagIngr("petals/cyan");
		Ingredient purple = tagIngr("petals/purple");
		Ingredient blue = tagIngr("petals/blue");
		Ingredient brown = tagIngr("petals/brown");
		Ingredient green = tagIngr("petals/green");
		Ingredient red = tagIngr("petals/red");
		Ingredient black = tagIngr("petals/black");
		Ingredient runeWater = Ingredient.of(BotaniaItems.runeWater);
		Ingredient runeFire = Ingredient.of(BotaniaItems.runeFire);
		Ingredient runeEarth = Ingredient.of(BotaniaItems.runeEarth);
		Ingredient runeAir = Ingredient.of(BotaniaItems.runeAir);
		Ingredient runeSpring = Ingredient.of(BotaniaItems.runeSpring);
		Ingredient runeSummer = Ingredient.of(BotaniaItems.runeSummer);
		Ingredient runeAutumn = Ingredient.of(BotaniaItems.runeAutumn);
		Ingredient runeWinter = Ingredient.of(BotaniaItems.runeWinter);
		Ingredient runeMana = Ingredient.of(BotaniaItems.runeMana);
		Ingredient runeLust = Ingredient.of(BotaniaItems.runeLust);
		Ingredient runeGluttony = Ingredient.of(BotaniaItems.runeGluttony);
		Ingredient runeGreed = Ingredient.of(BotaniaItems.runeGreed);
		Ingredient runeSloth = Ingredient.of(BotaniaItems.runeSloth);
		Ingredient runeWrath = Ingredient.of(BotaniaItems.runeWrath);
		Ingredient runeEnvy = Ingredient.of(BotaniaItems.runeEnvy);
		Ingredient runePride = Ingredient.of(BotaniaItems.runePride);

		Ingredient redstoneRoot = Ingredient.of(BotaniaItems.redstoneRoot);
		Ingredient pixieDust = Ingredient.of(BotaniaItems.pixieDust);
		Ingredient gaiaSpirit = Ingredient.of(BotaniaItems.lifeEssence);
		Ingredient spritFragment = Ingredient.of(ExtraBotanyItems.spiritFragment);
		Ingredient manaDust = Ingredient.of(BotaniaItems.manaPowder);
		//Recipes

		save(recipeOutput, ExtrabotanyFlowerBlocks.tradeOrchid, lime, lime, green, brown, runeGreed, runeLust, redstoneRoot);
		save(recipeOutput, ExtrabotanyFlowerBlocks.woodienia, brown, brown, brown, gray, Ingredient.of(ExtraBotanyItems.elementiumQuartz), runeGluttony, redstoneRoot);
		save(recipeOutput, ExtrabotanyFlowerBlocks.reikarlily, lightBlue, lightBlue, cyan, cyan, blue, runePride, runeEnvy, runeSloth, gaiaSpirit);
		save(recipeOutput, ExtrabotanyFlowerBlocks.bellflower, yellow, yellow, lime, lime, spritFragment);
		save(recipeOutput, ExtrabotanyFlowerBlocks.annoyingflower, white, white, pink, pink, green, runeMana, spritFragment);
		save(recipeOutput, ExtrabotanyFlowerBlocks.stonesia, gray, gray, black, gaiaSpirit, runeAutumn, runeGluttony);
		save(recipeOutput, ExtrabotanyFlowerBlocks.edelweiss, white, white, white, lightBlue, lightBlue, manaDust, runeMana, runeWinter);
		save(recipeOutput, ExtrabotanyFlowerBlocks.resoncund, magenta, magenta, orange, orange, runeLust, runeGluttony);
		save(recipeOutput, ExtrabotanyFlowerBlocks.sunshineLily, yellow, yellow, yellow, orange);
		save(recipeOutput, ExtrabotanyFlowerBlocks.moonlightLily, black, black, purple, gray);
		save(recipeOutput, ExtrabotanyFlowerBlocks.serenitian, purple, purple, blue, blue, runeMana, runeSloth, runeGreed, gaiaSpirit, Ingredient.of(Items.WITHER_ROSE));
		save(recipeOutput, ExtrabotanyFlowerBlocks.twinstar, yellow, yellow, yellow, orange, orange, orange, manaDust, manaDust);
		save(recipeOutput, ExtrabotanyFlowerBlocks.omniviolet, purple, purple, blue, blue, runeSpring, runeMana, runeLust);
		save(recipeOutput, ExtrabotanyFlowerBlocks.tinkle, yellow, yellow, green, lime, runeWater, runeEarth, manaDust, spritFragment, spritFragment);
		save(recipeOutput, ExtrabotanyFlowerBlocks.bloodEnchantress, red, red, red, red, runeFire, runeSummer, runeWrath);
		save(recipeOutput, ExtrabotanyFlowerBlocks.mirrowtunia, cyan, cyan, lightBlue, blue, runeWrath, runePride, runeAir, manaDust);
		save(recipeOutput, ExtrabotanyFlowerBlocks.manalink, cyan, cyan, cyan, lightBlue, lightBlue, runeSloth, runeLust, gaiaSpirit);
		save(recipeOutput, ExtrabotanyFlowerBlocks.necrofleur, gray, gray, pink, pink, red, runeWrath, manaDust);
		save(recipeOutput, ExtrabotanyFlowerBlocks.enchanter, purple, purple, magenta, lime, lime, runePride, runeGreed, runeGluttony, gaiaSpirit);

	}

	private static Ingredient tagIngr(String path) {
		return switch (path) {
			case "petals/white" -> Ingredient.of(BotaniaTags.Items.PETALS_WHITE);
			case "petals/orange" -> Ingredient.of(BotaniaTags.Items.PETALS_ORANGE);
			case "petals/magenta" -> Ingredient.of(BotaniaTags.Items.PETALS_MAGENTA);
			case "petals/light_blue" -> Ingredient.of(BotaniaTags.Items.PETALS_LIGHT_BLUE);
			case "petals/yellow" -> Ingredient.of(BotaniaTags.Items.PETALS_YELLOW);
			case "petals/lime" -> Ingredient.of(BotaniaTags.Items.PETALS_LIME);
			case "petals/pink" -> Ingredient.of(BotaniaTags.Items.PETALS_PINK);
			case "petals/gray" -> Ingredient.of(BotaniaTags.Items.PETALS_GRAY);
			case "petals/light_gray" -> Ingredient.of(BotaniaTags.Items.PETALS_LIGHT_GRAY);
			case "petals/cyan" -> Ingredient.of(BotaniaTags.Items.PETALS_CYAN);
			case "petals/purple" -> Ingredient.of(BotaniaTags.Items.PETALS_PURPLE);
			case "petals/blue" -> Ingredient.of(BotaniaTags.Items.PETALS_BLUE);
			case "petals/brown" -> Ingredient.of(BotaniaTags.Items.PETALS_BROWN);
			case "petals/green" -> Ingredient.of(BotaniaTags.Items.PETALS_GREEN);
			case "petals/red" -> Ingredient.of(BotaniaTags.Items.PETALS_RED);
			case "petals/black" -> Ingredient.of(BotaniaTags.Items.PETALS_BLACK);
			default -> throw new IllegalArgumentException("Unknown Botania petal tag: " + path);
		};
	}

	private static void save(RecipeOutput recipeOutput, ItemLike output, Ingredient... inputs) {
		recipeOutput.accept(prefix("petal_apothecary/" + getItemName(output)),
				new PetalApothecaryRecipe(new ItemStack(output), Ingredient.of(BotaniaTags.Items.SEED_APOTHECARY_REAGENT), inputs), null);
	}

	@Override
	public String getName() {
		return "Extrabotany petal apothecary recipes";
	}
}
