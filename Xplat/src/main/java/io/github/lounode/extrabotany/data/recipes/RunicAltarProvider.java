package io.github.lounode.extrabotany.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.BotaniaItems;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class RunicAltarProvider extends ExtraBotanyRecipeProvider {

	public RunicAltarProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public String getName() {
		return "ExtraBotany runic altar recipes";
	}

	@Override
	protected void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
		final int costTier1 = 5200;
		final int costTier2 = 8000;
		final int costTier3 = 12000;

		//Ingredient manaSteel = Ingredient.of(BotaniaTags.Items.INGOTS_MANASTEEL);

		//Zadkiel
		consumer.accept(new FinishedRecipe(idFor("zadkiel"), new ItemStack(ExtraBotanyItems.zadkiel), 500000,
				Ingredient.of(Items.ICE),
				Ingredient.of(Items.BLUE_ICE),
				Ingredient.of(Items.PACKED_ICE),
				Ingredient.of(Items.SNOW_BLOCK),
				Ingredient.of(Items.POWDER_SNOW_BUCKET),
				Ingredient.of(Items.TOTEM_OF_UNDYING)
		));
		consumer.accept(new FinishedRecipe(idFor("orichalcos_ingot"), new ItemStack(ExtraBotanyItems.orichalcos), 150000,
				Ingredient.of(ExtraBotanyItems.heroMedal),
				Ingredient.of(ExtraBotanyItems.gildedPotatoMashed),
				Ingredient.of(BotaniaItems.gaiaIngot),
				Ingredient.of(BotaniaItems.gaiaIngot),
				Ingredient.of(BotaniaItems.lifeEssence),
				Ingredient.of(BotaniaItems.lifeEssence),
				Ingredient.of(BotaniaItems.lifeEssence),
				Ingredient.of(BotaniaItems.lifeEssence)
		));
		consumer.accept(new FinishedRecipe(idFor("shadowium_ingot"), new ItemStack(ExtraBotanyItems.shadowium), 4200,
				Ingredient.of(BotaniaItems.elementium),
				Ingredient.of(ExtraBotanyItems.gildedPotatoMashed),
				Ingredient.of(ExtraBotanyItems.nightmareFuel),
				Ingredient.of(ExtraBotanyItems.nightmareFuel),
				Ingredient.of(ExtraBotanyItems.nightmareFuel)
		));
		consumer.accept(new FinishedRecipe(idFor("photonium_ingot"), new ItemStack(ExtraBotanyItems.photonium), 4200,
				Ingredient.of(BotaniaItems.elementium),
				Ingredient.of(ExtraBotanyItems.gildedPotatoMashed),
				Ingredient.of(ExtraBotanyItems.spiritFragment),
				Ingredient.of(ExtraBotanyItems.spiritFragment),
				Ingredient.of(ExtraBotanyItems.spiritFragment)
		));
		consumer.accept(new FinishedRecipe(idFor("gilded_potato"), new ItemStack(ExtraBotanyItems.gildedPotato), 800,
				Ingredient.of(Items.POTATO),
				Ingredient.of(Items.GOLD_NUGGET)
		));
		consumer.accept(new FinishedRecipe(idFor(ExtraBotanyItems.orichalcosHammer), new ItemStack(ExtraBotanyItems.orichalcosHammer), ManaPoolBlockEntity.MAX_MANA,
				Ingredient.of(ExtraBotanyItems.heroMedal),
				Ingredient.of(ExtraBotanyItems.gildedPotatoMashed),
				Ingredient.of(ExtraBotanyItems.theChaos),
				Ingredient.of(ExtraBotanyItems.theOrigin),
				Ingredient.of(ExtraBotanyItems.theEnd)
		));

	}

	private static ResourceLocation idFor(String s) {
		return prefix("runic_altar/" + s);
	}

	private static ResourceLocation idFor(ItemLike itemLike) {
		return prefix("runic_altar/" + BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath());
	}

	protected static class FinishedRecipe implements net.minecraft.data.recipes.FinishedRecipe {
		private final ResourceLocation id;
		private final ItemStack output;
		private final int mana;
		private final Ingredient[] inputs;

		protected FinishedRecipe(ResourceLocation id, ItemStack output, int mana, Ingredient... inputs) {
			this.id = id;
			this.output = output;
			this.mana = mana;
			this.inputs = inputs;
		}

		@Override
		public void serializeRecipeData(JsonObject json) {
			json.add("output", ItemNBTHelper.serializeStack(output));
			JsonArray ingredients = new JsonArray();
			for (Ingredient ingr : inputs) {
				ingredients.add(ingr.toJson());
			}
			json.addProperty("mana", mana);
			json.add("ingredients", ingredients);
		}

		@Override
		public ResourceLocation getId() {
			return id;
		}

		@Override
		public RecipeSerializer<?> getType() {
			return BotaniaRecipeTypes.RUNE_SERIALIZER;
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement() {
			return null;
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId() {
			return null;
		}
	}
}
