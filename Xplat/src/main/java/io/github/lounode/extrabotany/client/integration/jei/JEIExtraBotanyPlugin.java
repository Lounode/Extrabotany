package io.github.lounode.extrabotany.client.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;

import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Block;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.client.integration.jei.PureDaisyRecipeCategory;
import vazkii.botania.common.brew.BotaniaBrews;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.brew.BaseBrewItem;

import io.github.lounode.extrabotany.api.recipe.PedestalRecipe;
import io.github.lounode.extrabotany.api.recipe.StonesiaRecipe;
import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.block.flower.ExtrabotanyFlowerBlocks;
import io.github.lounode.extrabotany.common.brew.BrewUtil;
import io.github.lounode.extrabotany.common.brew.ExtraBotanyBrews;
import io.github.lounode.extrabotany.common.crafting.ExtraBotanyRecipeTypes;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.item.brew.ManaCocktailItem;
import io.github.lounode.extrabotany.common.item.relic.voidcore.CoreOfTheVoidItem;

import java.util.*;
import java.util.stream.Collectors;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

@JeiPlugin
public class JEIExtraBotanyPlugin implements IModPlugin {
	private static final ResourceLocation ID = prefix("main");

	@Override
	public void registerItemSubtypes(@NotNull ISubtypeRegistration registry) {
		IIngredientSubtypeInterpreter<ItemStack> interpreter = (stack, ctx) -> BaseBrewItem.getSubtype(stack);
		registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ExtraBotanyItems.manaCocktail, interpreter);
		registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ExtraBotanyItems.infiniteWine, interpreter);
		registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ExtraBotanyItems.holyWaterGrenade, interpreter);

		registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ExtraBotanyItems.coreOfTheVoid,
				(stack, ctx) -> CoreOfTheVoidItem.getVariant(stack));
		/*
		registry.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, ExtraBotanyItems.voidArchives,
				(stack, ctx) -> VoidArchivesItem.getTagVariant(stack));
				(FlugelTiara
		*/
	}

	@Override
	public void registerRecipes(@NotNull IRecipeRegistration registry) {
		registry.addRecipes(PedestalRecipeCategory.TYPE, sortRecipes(ExtraBotanyRecipeTypes.PEDESTAL_SMASH_TYPE, BY_SMASH_TOOLS.thenComparing(BY_GROUP).thenComparing(BY_ID)));
		registry.addRecipes(StonesiaRecipeCategory.TYPE, sortRecipes(ExtraBotanyRecipeTypes.STONESIA_RECIPE_TYPE, Comparator.<StonesiaRecipe, Integer>comparing(StonesiaRecipe::getManaOutput)
				.thenComparing(BY_GROUP)
				.thenComparing(BY_ID)).stream()
						.filter(recipe -> {
							StateIngredient input = recipe.getInput();
							return !input.getDisplayed().isEmpty();
						})
						.collect(Collectors.toList())
		);
		registerCocktailRecipes(registry);
		registerInfiniteWineRecipes(registry);
		registerHolyWaterGrenadeRecipes(registry);

		//registry.getIngredientManager().removeIngredientsAtRuntime()
	}

	@Override
	public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {
		//registration.getCraftingCategory().addCategoryExtension(CopyBrewFormFlaskRecipe.class, CopyBrewFormFlaskRecipeWrapper::new);
	}

	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(
				new PedestalRecipeCategory(registry.getJeiHelpers().getGuiHelper()),
				new StonesiaRecipeCategory(registry.getJeiHelpers().getGuiHelper())
		);
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
		for (Block pedestal : ExtraBotanyBlocks.ALL_PEDESTALS) {
			registry.addRecipeCatalyst(new ItemStack(pedestal), PedestalRecipeCategory.TYPE);
		}
		registry.addRecipeCatalyst(new ItemStack(ExtrabotanyFlowerBlocks.stonesia), StonesiaRecipeCategory.TYPE);
		registry.addRecipeCatalyst(new ItemStack(ExtrabotanyFlowerBlocks.stonesiaFloating), StonesiaRecipeCategory.TYPE);
		registry.addRecipeCatalyst(new ItemStack(ExtraBotanyItems.pureDaisyPendant), PureDaisyRecipeCategory.TYPE);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		IRecipeManager manager = jeiRuntime.getRecipeManager();
		ResourceLocation recipeId = prefix("das_rheingold_change_bind");

		List<CraftingRecipe> toHide = manager.createRecipeLookup(RecipeTypes.CRAFTING).get()
				.filter(r -> r.getId().equals(recipeId))
				.collect(Collectors.toList());

		if (!toHide.isEmpty()) {
			manager.hideRecipes(RecipeTypes.CRAFTING, toHide);
		}
	}

	private static final Comparator<Recipe<?>> BY_ID = Comparator.comparing(Recipe::getId);
	private static final Comparator<Recipe<?>> BY_GROUP = Comparator.comparing(Recipe::getGroup);
	private static final Comparator<PedestalRecipe> BY_SMASH_TOOLS = (l, r) -> {
		Ingredient left = l.getSmashTools();
		Ingredient right = r.getSmashTools();
		return left.toString().compareTo(right.toString());
	};

	private static <T extends Recipe<C>, C extends Container> List<T> sortRecipes(RecipeType<T> type, Comparator<? super T> comparator) {
		Collection<T> recipes = ExtraBotanyRecipeTypes.getRecipes(Minecraft.getInstance().level, type).values();
		List<T> list = new ArrayList<>(recipes);
		list.sort(comparator);
		return list;
	}

	private void registerCocktailRecipes(IRecipeRegistration registry) {
		List<CraftingRecipe> recipes = new ArrayList<>();
		for (var brew : BotaniaAPI.instance().getBrewRegistry()) {
			if (brew == ExtraBotanyBrews.manaCocktail) {
				continue;
			}
			if (brew == BotaniaBrews.fallbackBrew) {
				continue;
			}

			ItemStack cocktail = ManaCocktailItem.getDefaultCocktail();
			ItemStack flask = BotaniaItems.brewFlask.getDefaultInstance();
			BrewUtil.setBrew(flask, brew);

			ResourceLocation id = prefix("mana_cocktail_change_brew");
			var ingredients = NonNullList.of(Ingredient.EMPTY,
					Ingredient.of(cocktail),
					Ingredient.of(flask)
			);
			ItemStack result = ExtraBotanyItems.manaCocktail.getDefaultInstance();
			BrewUtil.setBrew(result, brew);

			ShapelessRecipe compose = new ShapelessRecipe(id, "mana_cocktail_change_brew", CraftingBookCategory.MISC, result, ingredients);
			recipes.add(compose);
		}
		registry.addRecipes(RecipeTypes.CRAFTING, recipes);
	}

	private void registerInfiniteWineRecipes(IRecipeRegistration registry) {
		List<CraftingRecipe> recipes = new ArrayList<>();
		for (var brew : BotaniaAPI.instance().getBrewRegistry()) {
			if (brew == BotaniaBrews.fallbackBrew) {
				continue;
			}

			ItemStack cocktail = ExtraBotanyItems.manaCocktail.getDefaultInstance();
			BrewUtil.setBrew(cocktail, brew);
			if (brew == ExtraBotanyBrews.manaCocktail) {
				cocktail = ManaCocktailItem.getDefaultCocktail();
			}
			ItemStack medal = ExtraBotanyItems.heroMedal.getDefaultInstance();

			ResourceLocation id = prefix("infinite_wine");
			var ingredients = NonNullList.of(Ingredient.EMPTY,
					Ingredient.of(cocktail),
					Ingredient.of(medal)
			);
			ItemStack result = ExtraBotanyItems.infiniteWine.getDefaultInstance();
			BrewUtil.setBrew(result, brew);

			ShapelessRecipe compose = new ShapelessRecipe(id, "infinite_wine", CraftingBookCategory.MISC, result, ingredients);
			recipes.add(compose);
		}
		registry.addRecipes(RecipeTypes.CRAFTING, recipes);
	}

	private void registerHolyWaterGrenadeRecipes(IRecipeRegistration registry) {
		List<CraftingRecipe> recipes = new ArrayList<>();
		for (var brew : BotaniaAPI.instance().getBrewRegistry()) {
			if (brew == BotaniaBrews.fallbackBrew) {
				continue;
			}

			ItemStack cocktail = ExtraBotanyItems.manaCocktail.getDefaultInstance();
			BrewUtil.setBrew(cocktail, brew);
			if (brew == ExtraBotanyBrews.manaCocktail) {
				cocktail = ManaCocktailItem.getDefaultCocktail();
			}
			ItemStack fruit = Items.POPPED_CHORUS_FRUIT.getDefaultInstance();

			ResourceLocation id = prefix("holy_water_grenade");
			var ingredients = NonNullList.of(Ingredient.EMPTY,
					Ingredient.of(cocktail),
					Ingredient.of(fruit)
			);
			ItemStack result = ExtraBotanyItems.holyWaterGrenade.getDefaultInstance();
			BrewUtil.setBrew(result, brew);

			ShapelessRecipe compose = new ShapelessRecipe(id, "holy_water_grenade", CraftingBookCategory.MISC, result, ingredients);
			recipes.add(compose);
		}
		registry.addRecipes(RecipeTypes.CRAFTING, recipes);
	}
}
