package io.github.lounode.extrabotany.data.recipes;

import com.google.common.collect.Sets;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.xplat.XplatAbstractions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class ExtraBotanyRecipeProvider implements DataProvider {
	private final PackOutput.PathProvider recipePathProvider;
	private final PackOutput.PathProvider advancementPathProvider;
	private CompletableFuture<HolderLookup.Provider> lookupProvider;

	public ExtraBotanyRecipeProvider(PackOutput packOutput) {
		this(packOutput, CompletableFuture.completedFuture(HolderLookup.Provider.create(java.util.stream.Stream.empty())));
	}

	public ExtraBotanyRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		this.recipePathProvider = packOutput.createRegistryElementsPathProvider(Registries.RECIPE);
		this.advancementPathProvider = packOutput.createRegistryElementsPathProvider(Registries.ADVANCEMENT);
		this.lookupProvider = lookupProvider;
	}

	public ExtraBotanyRecipeProvider withLookupProvider(CompletableFuture<HolderLookup.Provider> lookupProvider) {
		this.lookupProvider = lookupProvider;
		return this;
	}

	// [VanillaCopy] RecipeProvider
	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
		return lookupProvider.thenCompose(lookup -> run(cache, lookup));
	}

	private @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache, HolderLookup.Provider lookup) {
		Set<ResourceLocation> checkDuplicates = Sets.newHashSet();
		List<CompletableFuture<?>> output = new ArrayList<>();
		buildRecipes(XplatAbstractions.INSTANCE.createRecipeOutput((id, recipe, advancement) -> {
			if (!checkDuplicates.add(id)) {
				throw new IllegalStateException("Duplicate recipe " + id);
			} else {
				if (advancement != null) {
					output.add(DataProvider.saveStable(cache, lookup, Advancement.CODEC, advancement.value(), advancementPathProvider.json(advancement.id())));
				}
				output.add(DataProvider.saveStable(cache, lookup, Recipe.CODEC, recipe, recipePathProvider.json(id)));
			}
		}, () -> Advancement.Builder.recipeAdvancement().parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)));
		return CompletableFuture.allOf(output.toArray(CompletableFuture[]::new));
	}

	protected abstract void buildRecipes(RecipeOutput recipeOutput);

	protected static Criterion<InventoryChangeTrigger.TriggerInstance> conditionsFromItem(ItemLike itemLike) {
		return inventoryTrigger(ItemPredicate.Builder.item().of(itemLike));
	}

	protected static Criterion<InventoryChangeTrigger.TriggerInstance> conditionsFromTag(TagKey<Item> tag) {
		return inventoryTrigger(ItemPredicate.Builder.item().of(tag));
	}

	protected static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate.Builder... items) {
		return inventoryTrigger(Arrays.stream(items).map(ItemPredicate.Builder::build).toArray(ItemPredicate[]::new));
	}

	protected static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate... predicates) {
		return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
				new InventoryChangeTrigger.TriggerInstance(Optional.empty(), InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of(predicates)));
	}

	protected static String getItemName(ItemLike itemLike) {
		return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
	}

}
