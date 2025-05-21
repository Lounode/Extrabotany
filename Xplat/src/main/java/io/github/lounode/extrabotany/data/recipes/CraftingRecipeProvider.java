package io.github.lounode.extrabotany.data.recipes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.FloatingSpecialFlowerBlock;
import vazkii.botania.common.crafting.recipe.ManaUpgradeRecipe;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.lib.BotaniaTags;
import vazkii.botania.common.lib.LibBlockNames;
import vazkii.botania.data.recipes.WrapperResult;

import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.crafting.recipe.CopyBrewFormFlaskRecipe;
import io.github.lounode.extrabotany.common.crafting.recipe.CopyBrewFromManaCocktailRecipe;
import io.github.lounode.extrabotany.common.crafting.recipe.WandOfTheForestExtendRecipe;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;
import io.github.lounode.extrabotany.common.lib.LibItemNames;
import io.github.lounode.extrabotany.common.lib.ResourceLocationHelper;

import java.util.Comparator;
import java.util.function.Consumer;

import static io.github.lounode.extrabotany.data.BlockTagProvider.EXTRABOTANY_BLOCK;

public class CraftingRecipeProvider extends vazkii.botania.data.recipes.CraftingRecipeProvider {
	public CraftingRecipeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	public void buildRecipes(Consumer<FinishedRecipe> consumer) {
		registerMain(consumer);
		registerMisc(consumer);
		registerTools(consumer);
		registerTrinkets(consumer);
		registerConversions(consumer);
		registerDecor(consumer);
		registerFloatingFlowers(consumer);
	}

	private void registerMain(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.theChaos)
				.define('S', ExtraBotanyTags.Items.INGOTS_SHADOWIUM)
				.define('P', ExtraBotanyTags.Items.INGOTS_PHOTONIUM)
				.define('F', ExtraBotanyItems.spiritFragment)
				.pattern(" S ")
				.pattern("SFP")
				.pattern(" P ")
				.unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.nightmareFuel))
				.save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.theOrigin)
				.define('S', BotaniaTags.Items.INGOTS_TERRASTEEL)
				.define('P', ExtraBotanyTags.Items.INGOTS_AERIALITE)
				.define('F', ExtraBotanyItems.spiritFragment)
				.pattern(" S ")
				.pattern("SFP")
				.pattern(" P ")
				.unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.nightmareFuel))
				.save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.theEnd)
				.define('S', ExtraBotanyTags.Items.INGOTS_ORICHALCOS)
				.define('P', BotaniaItems.gaiaIngot)
				.define('F', ExtraBotanyItems.spiritFragment)
				.pattern(" S ")
				.pattern("SFP")
				.pattern(" P ")
				.unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.nightmareFuel))
				.save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.challengeTicket)
				.define('S', ExtraBotanyTags.Items.INGOTS_SHADOWIUM)
				.define('P', BotaniaItems.gaiaIngot)
				.define('F', ExtraBotanyTags.Items.INGOTS_PHOTONIUM)
				.define('A', BotaniaItems.lifeEssence)
				.pattern("ASA")
				.pattern("FPF")
				.pattern("ASA")
				.unlockedBy("has_item", conditionsFromItem(BotaniaItems.gaiaIngot))
				.save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.dasRheingold)
				.define('S', BotaniaItems.manaweaveCloth)
				.define('P', Items.GOLD_INGOT)
				.define('A', BotaniaItems.lifeEssence)
				.pattern("ASA")
				.pattern("SPS")
				.pattern("ASA")
				.unlockedBy("has_item", conditionsFromItem(BotaniaItems.lifeEssence))
				.save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.natureOrb)
				.define('S', BotaniaTags.Items.GEMS_DRAGONSTONE)
				.define('P', BotaniaItems.manaPearl)
				.define('A', BotaniaTags.Items.INGOTS_TERRASTEEL)
				.pattern("ASA")
				.pattern("SPS")
				.pattern("ASA")
				.unlockedBy("has_item", conditionsFromTag(BotaniaTags.Items.INGOTS_TERRASTEEL))
				.save(consumer);

	}

	private void registerTrinkets(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ExtraBotanyItems.manaRingMaster)
				.define('R', BotaniaItems.manaRingGreater)
				.define('O', ExtraBotanyTags.Items.INGOTS_ORICHALCOS)
				.define('H', ExtraBotanyItems.heroMedal)
				.define('Y', ExtraBotanyItems.theOrigin)
				.define('C', ExtraBotanyItems.theChaos)
				.define('E', ExtraBotanyItems.theEnd)
				.pattern("OHO")
				.pattern("YRE")
				.pattern("OCO")
				.unlockedBy("has_item", conditionsFromItem(BotaniaItems.manaRingGreater))
				.save(WrapperResult.ofType(ManaUpgradeRecipe.SERIALIZER, consumer));
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ExtraBotanyItems.camera)
				.define('B', ExtraBotanyTags.Items.INGOTS_SHADOWIUM)
				.define('G', BotaniaItems.gaiaIngot)
				.define('S', Items.SPYGLASS)
				.pattern("BBB")
				.pattern("BSB")
				.pattern("GGG")
				.unlockedBy("has_item", conditionsFromItem(BotaniaItems.gaiaIngot))
				.save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ExtraBotanyItems.featherOfJingwei)
				.define('L', Items.LAVA_BUCKET)
				.define('F', Items.FEATHER)
				.define('H', ExtraBotanyItems.heroMedal)
				.define('B', Items.BLAZE_POWDER)
				.pattern("BLB")
				.pattern("BFB")
				.pattern("BHB")
				.unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.heroMedal))
				.save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.manaGlassBottle, 3)
				.define('G', BotaniaBlocks.manaGlass)
				.pattern("G G")
				.pattern("G G")
				.pattern(" G ")
				.unlockedBy("has_item", conditionsFromItem(BotaniaBlocks.manaGlass))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ExtraBotanyItems.nineAndThreeQuartersRewardBag, 3)
				.requires(BotaniaItems.dice)
				.unlockedBy("has_item", conditionsFromItem(BotaniaItems.dice))
				.save(consumer);
	}

	private void registerTools(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ExtraBotanyItems.failnaught)
				.define('O', ExtraBotanyTags.Items.INGOTS_ORICHALCOS)
				.define('S', BotaniaItems.manaString)
				.define('G', BotaniaItems.gaiaIngot)
				.pattern(" GS")
				.pattern("GOS")
				.pattern(" GS")
				.unlockedBy("has_item", conditionsFromTag(ExtraBotanyTags.Items.INGOTS_ORICHALCOS))
				.save(consumer);
		//ManaReader&WandExtend
		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ExtraBotanyItems.manaReader)
				.define('D', BotaniaTags.Items.GEMS_MANA_DIAMOND)
				.define('P', BotaniaTags.Items.DUSTS_MANA)
				.define('S', BotaniaItems.livingwoodTwig)
				.pattern(" PD")
				.pattern(" SP")
				.pattern("S  ")
				.unlockedBy("has_item", conditionsFromItem(BotaniaItems.twigWand))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, BotaniaItems.twigWand)
				.requires(BotaniaItems.twigWand)
				.requires(ExtraBotanyItems.manaReader)
				.unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.manaReader))
				.save(WrapperResult.ofType(WandOfTheForestExtendRecipe.SERIALIZER, consumer), prefix("twig_wand_extension"));
		ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, BotaniaItems.dreamwoodWand)
				.requires(BotaniaItems.dreamwoodWand)
				.requires(ExtraBotanyItems.manaReader)
				.unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.manaReader))
				.save(WrapperResult.ofType(WandOfTheForestExtendRecipe.SERIALIZER, consumer), prefix("dreamwood_wand_extension"));
		//Hammer
		hammer(BotaniaTags.Items.INGOTS_MANASTEEL, BotaniaItems.livingwoodTwig, ExtraBotanyItems.manasteelHammer).save(consumer);
		hammer(BotaniaTags.Items.INGOTS_ELEMENTIUM, BotaniaItems.dreamwoodTwig, ExtraBotanyItems.elementiumHammer).save(consumer);
		hammer(BotaniaTags.Items.INGOTS_TERRASTEEL, BotaniaItems.livingwoodTwig, ExtraBotanyItems.terrasteelHammer).save(consumer);
		//hammer(BotaniaItems.gaiaIngot, ExtraBotanyItems.gaiaHammer).save(consumer);
		hammer(ExtraBotanyTags.Items.INGOTS_PHOTONIUM, BotaniaItems.livingwoodTwig, ExtraBotanyItems.photoniumHammer).save(consumer);
		hammer(ExtraBotanyTags.Items.INGOTS_SHADOWIUM, BotaniaItems.livingwoodTwig, ExtraBotanyItems.shadowiumHammer).save(consumer);
		hammer(ExtraBotanyTags.Items.INGOTS_AERIALITE, BotaniaItems.dreamwoodTwig, ExtraBotanyItems.aerialiteHammer).save(consumer);
		//hammer(ExtraBotanyItems.orichalcos, ExtraBotanyItems.orichalcosHammer).save(consumer);

		ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ExtraBotanyItems.walkingCane)
				.define('T', BotaniaItems.livingwoodTwig)
				.define('G', Items.GOLD_BLOCK)
				.define('P', Items.PRISMARINE_CRYSTALS)
				.pattern("TGP")
				.pattern(" TG")
				.pattern("T T")
				.unlockedBy("has_item", conditionsFromItem(BotaniaItems.livingwoodTwig))
				.save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.magicFinger)
				.define('C', Items.CARROT)
				.define('H', ExtraBotanyItems.heroMedal)
				.define('P', BotaniaItems.manaPowder)
				.pattern(" P ")
				.pattern("PCP")
				.pattern(" H ")
				.unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.heroMedal))
				.save(consumer);
	}

	private void registerConversions(Consumer<FinishedRecipe> consumer) {
		compression(ExtraBotanyItems.orichalcos, ExtraBotanyTags.Items.NUGGETS_ORICHALCOS)
				.save(consumer, prefix("conversions/orichalcos_from_nuggets"));

		compression(ExtraBotanyItems.photonium, ExtraBotanyTags.Items.NUGGETS_PHOTONIUM)
				.save(consumer, prefix("conversions/photonium_from_nuggets"));

		compression(ExtraBotanyItems.shadowium, ExtraBotanyTags.Items.NUGGETS_SHADOWIUM)
				.save(consumer, prefix("conversions/shadowium_from_nuggets"));

		compression(ExtraBotanyItems.aerialite, ExtraBotanyTags.Items.NUGGETS_AERIALITE)
				.save(consumer, prefix("conversions/aerialite_from_nuggets"));

		compression(ExtraBotanyBlocks.orichalcosBlock, ExtraBotanyTags.Items.INGOTS_ORICHALCOS).save(consumer);
		compression(ExtraBotanyBlocks.photoniumBlock, ExtraBotanyTags.Items.INGOTS_PHOTONIUM).save(consumer);
		compression(ExtraBotanyBlocks.shadowiumBlock, ExtraBotanyTags.Items.INGOTS_SHADOWIUM).save(consumer);
		compression(ExtraBotanyBlocks.aerialiteBlock, ExtraBotanyTags.Items.INGOTS_AERIALITE).save(consumer);

		deconstruct(consumer, ExtraBotanyItems.orichalcos, ExtraBotanyTags.Items.BLOCKS_ORICHALCOS, "orichalcos_block_deconstruct");
		deconstruct(consumer, ExtraBotanyItems.photonium, ExtraBotanyTags.Items.BLOCKS_PHOTONIUM, "photonium_block_deconstruct");
		deconstruct(consumer, ExtraBotanyItems.shadowium, ExtraBotanyTags.Items.BLOCKS_SHADOWIUM, "shadowium_block_deconstruct");
		deconstruct(consumer, ExtraBotanyItems.aerialite, ExtraBotanyTags.Items.BLOCKS_AERIALITE, "aerialite_block_deconstruct");

		deconstruct(consumer, ExtraBotanyItems.orichalcosNugget, ExtraBotanyTags.Items.INGOTS_ORICHALCOS, "orichalcos_to_nuggets");
		deconstruct(consumer, ExtraBotanyItems.photoniumNugget, ExtraBotanyTags.Items.INGOTS_PHOTONIUM, "photonium_to_nuggets");
		deconstruct(consumer, ExtraBotanyItems.shadowiumNugget, ExtraBotanyTags.Items.INGOTS_SHADOWIUM, "shadowium_to_nuggets");
		deconstruct(consumer, ExtraBotanyItems.aerialiteNugget, ExtraBotanyTags.Items.INGOTS_AERIALITE, "aerialite_to_nuggets");

	}

	private void registerDecor(Consumer<FinishedRecipe> consumer) {
		registerForQuartz(consumer, LibItemNames.GAIA_QUARTZ, ExtraBotanyItems.gaiaQuartz);
		registerForQuartz(consumer, LibItemNames.ELEMENTIUM_QUARTZ, ExtraBotanyItems.elementiumQuartz);
	}

	@Override
	protected void registerForQuartz(Consumer<FinishedRecipe> consumer, String variant, ItemLike baseItem) {
		Block block = getBlockOrThrow(prefix(variant + "_block"));
		Block stairs = getBlockOrThrow(prefix(variant + LibBlockNames.STAIR_SUFFIX));
		Block slab = getBlockOrThrow(prefix(variant + LibBlockNames.SLAB_SUFFIX));
		Block chiseled = getBlockOrThrow(prefix("chiseled_" + variant + "_block"));
		Block bricks = getBlockOrThrow(prefix(variant + "_bricks"));
		Block pillar = getBlockOrThrow(prefix(variant + "_pillar"));
		Block smooth = getBlockOrThrow(prefix("smooth_" + variant));
		Block smoothStairs = getBlockOrThrow(prefix("smooth_" + variant + LibBlockNames.STAIR_SUFFIX));
		Block smoothSlab = getBlockOrThrow(prefix("smooth_" + variant + LibBlockNames.SLAB_SUFFIX));

		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block)
				.define('Q', baseItem)
				.pattern("QQ")
				.pattern("QQ")
				.group("extrabotany:quartz_block")
				.unlockedBy("has_item", conditionsFromItem(baseItem))
				.save(consumer);
		stairs(stairs, block).group("extrabotany:quartz_stairs").save(consumer);
		slabShape(slab, block).group("extrabotany:quartz_slab").save(consumer);
		chiseled(chiseled, slab).group("extrabotany:quartz_chiseled")
				.unlockedBy("has_base_item", conditionsFromItem(block)).save(consumer);
		brick(bricks, block).group("extrabotany:quartz_bricks").save(consumer);
		pillar(pillar, block).group("extrabotany:quartz_pillar").save(consumer);
		//smooth: see SmeltingProvider
		stairs(smoothStairs, smooth).group("extrabotany:quartz_stairs").save(consumer);
		slabShape(smoothSlab, smooth).group("extrabotany:quartz_slab").save(consumer);
	}

	private void registerMisc(Consumer<FinishedRecipe> consumer) {

		//Pedestal
		pedestal(BotaniaBlocks.livingrock, ExtraBotanyBlocks.livingrockPedestal)
				.unlockedBy("has_item", conditionsFromItem(BotaniaBlocks.livingrock))
				.save(consumer);
		pedestal(Items.CALCITE, ExtraBotanyBlocks.calcitePedestal)
				.unlockedBy("has_item", conditionsFromItem(BotaniaBlocks.livingrock))
				.save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyBlocks.manaCharger)
				.define('R', BotaniaBlocks.livingrockSlab)
				.define('S', BotaniaItems.livingwoodTwig)
				.pattern("   ")
				.pattern(" R ")
				.pattern("S S")
				.unlockedBy("has_item", conditionsFromItem(BotaniaBlocks.livingrock))
				.save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyBlocks.dimensionCatalyst)
				.define('R', BotaniaBlocks.livingrock)
				.define('P', Items.ENDER_PEARL)
				.define('C', BotaniaBlocks.alchemyCatalyst)
				.define('S', ExtraBotanyItems.theChaos)
				.define('Q', ExtraBotanyItems.elementiumQuartz)
				.pattern("RPR")
				.pattern("QCQ")
				.pattern("RSR")
				.unlockedBy("has_item", conditionsFromItem(BotaniaBlocks.alchemyCatalyst))
				.save(consumer);
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.gaiaQuartz)
				.define('T', BotaniaItems.terrasteelNugget)
				.define('Q', Items.QUARTZ)
				.pattern("QQQ")
				.pattern("QTQ")
				.pattern("QQQ")
				.unlockedBy("has_item", conditionsFromItem(BotaniaItems.terrasteel))
				.save(consumer);
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ExtraBotanyItems.manaCocktail)
				.requires(ExtraBotanyItems.manaCocktail)
				.requires(BotaniaItems.brewFlask)
				.unlockedBy("has_item", conditionsFromItem(BotaniaItems.brewFlask))
				.save(WrapperResult.ofType(CopyBrewFormFlaskRecipe.SERIALIZER, consumer), prefix("mana_cocktail_change_brew"));
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ExtraBotanyItems.infiniteWine)
				.requires(ExtraBotanyItems.manaCocktail)
				.requires(ExtraBotanyItems.infiniteWine)
				.unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.heroMedal))
				.save(WrapperResult.ofType(CopyBrewFromManaCocktailRecipe.SERIALIZER, consumer), prefix("infinite_wine_change_brew"));
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ExtraBotanyItems.infiniteWine)
				.requires(ExtraBotanyItems.manaCocktail)
				.requires(ExtraBotanyItems.heroMedal)
				.unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.heroMedal))
				.save(WrapperResult.ofType(CopyBrewFromManaCocktailRecipe.SERIALIZER, consumer), prefix("infinite_wine"));
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ExtraBotanyItems.holyWaterGrenade)
				.requires(ExtraBotanyItems.manaCocktail)
				.requires(Items.POPPED_CHORUS_FRUIT)
				.unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.manaCocktail))
				.save(WrapperResult.ofType(CopyBrewFromManaCocktailRecipe.SERIALIZER, consumer), prefix("holy_water_grenade"));

	}

	private void registerFloatingFlowers(Consumer<FinishedRecipe> consumer) {
		var floatings = BuiltInRegistries.BLOCK.stream().filter(EXTRABOTANY_BLOCK.and(b -> b instanceof FloatingSpecialFlowerBlock))
				.sorted(Comparator.comparing(BuiltInRegistries.BLOCK::getKey))
				.toArray(Block[]::new);

		for (var block : floatings) {
			ResourceLocation inputName = BuiltInRegistries.ITEM.getKey(block.asItem());
			Item origin = this.getItemOrThrow(new ResourceLocation(inputName.getNamespace(), inputName.getPath().replace("floating_", "")));
			createFloatingFlowerRecipe(consumer, origin);
		}
	}

	protected ShapedRecipeBuilder hammer(ItemLike head, ItemLike handle, ItemLike hammer) {
		return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hammer)
				.define('H', head)
				.define('S', handle)
				.pattern("HHH")
				.pattern("HHH")
				.pattern(" S ")
				.unlockedBy("has_item", conditionsFromItem(head));
	}

	protected ShapedRecipeBuilder hammer(TagKey<Item> item, ItemLike handle, ItemLike hammer) {
		return ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, hammer)
				.define('H', item)
				.define('S', handle)
				.pattern("HHH")
				.pattern("HHH")
				.pattern(" S ")
				.unlockedBy("has_item", conditionsFromTag(item));
	}

	protected ShapedRecipeBuilder pedestal(ItemLike block, ItemLike pedestal) {
		return ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, pedestal)
				.define('P', Items.GOLD_NUGGET)
				.define('C', block)
				.pattern("CPC")
				.pattern(" C ")
				.pattern("CCC");
	}

	private ShapedRecipeBuilder compression(ItemLike output, TagKey<Item> input) {
		return ShapedRecipeBuilder.shaped(output instanceof Block ? RecipeCategory.BUILDING_BLOCKS : RecipeCategory.MISC, output)
				.define('I', input)
				.pattern("III")
				.pattern("III")
				.pattern("III")
				.unlockedBy("has_item", conditionsFromTag(input));
	}

	@Override
	protected ResourceLocation prefix(String path) {
		return ResourceLocationHelper.prefix(path);
	}

	@Override
	public String getName() {
		return "ExtraBotany crafting recipes";
	}
}
