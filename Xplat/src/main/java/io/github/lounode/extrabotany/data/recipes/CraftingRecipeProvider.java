package io.github.lounode.extrabotany.data.recipes;

import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.block.PedestalBlock;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;
import io.github.lounode.extrabotany.common.lib.ResourceLocationHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.crafting.recipe.ManaUpgradeRecipe;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.data.recipes.WrapperResult;

import java.util.Locale;
import java.util.function.Consumer;

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
    }
    private void registerMain(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.theChaos)
                .define('S', ExtraBotanyItems.shadowium)
                .define('P', ExtraBotanyItems.photonium)
                .define('F', ExtraBotanyItems.spiritFragment)
                .pattern(" S ")
                .pattern("SFP")
                .pattern(" P ")
                .unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.nightmareFuel))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.theOrigin)
                .define('S', BotaniaItems.terrasteel)
                .define('P', ExtraBotanyItems.aerialite)
                .define('F', ExtraBotanyItems.spiritFragment)
                .pattern(" S ")
                .pattern("SFP")
                .pattern(" P ")
                .unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.nightmareFuel))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.theEnd)
                .define('S', ExtraBotanyItems.orichalcos)
                .define('P', BotaniaItems.gaiaIngot)
                .define('F', ExtraBotanyItems.spiritFragment)
                .pattern(" S ")
                .pattern("SFP")
                .pattern(" P ")
                .unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.nightmareFuel))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ExtraBotanyItems.challengeTicket)
                .define('S', ExtraBotanyItems.shadowium)
                .define('P', BotaniaItems.gaiaIngot)
                .define('F', ExtraBotanyItems.photonium)
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
                .define('S', BotaniaItems.dragonstone)
                .define('P', BotaniaItems.manaPearl)
                .define('A', BotaniaItems.terrasteel)
                .pattern("ASA")
                .pattern("SPS")
                .pattern("ASA")
                .unlockedBy("has_item", conditionsFromItem(BotaniaItems.terrasteel))
                .save(consumer);


    }
    private void registerTrinkets(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ExtraBotanyItems.manaRingMaster)
                .define('R', BotaniaItems.manaRingGreater)
                .define('O', ExtraBotanyItems.orichalcos)
                .define('H', ExtraBotanyItems.heroMedal)
                .define('Y', ExtraBotanyItems.theOrigin)
                .define('C', ExtraBotanyItems.theChaos)
                .define('E', ExtraBotanyItems.theEnd)
                .pattern("OHO")
                .pattern("YRE")
                .pattern("OCO")
                .unlockedBy("has_item", conditionsFromItem(BotaniaItems.manaRingGreater))
                .save(WrapperResult.ofType(ManaUpgradeRecipe.SERIALIZER, consumer));
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ExtraBotanyItems.camera)
                .define('B', ExtraBotanyBlocks.shadowiumBlock)
                .define('G', BotaniaItems.gaiaIngot)
                .define('S', Items.SPYGLASS)
                .pattern("BBB")
                .pattern("BSB")
                .pattern("GGG")
                .unlockedBy("has_item", conditionsFromItem(BotaniaItems.gaiaIngot))
                .save(consumer);
    }
    private void registerTools(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ExtraBotanyItems.failnaught)
                .define('O', ExtraBotanyItems.orichalcos)
                .define('S', BotaniaItems.manaString)
                .define('G', BotaniaItems.gaiaIngot)
                .pattern(" GS")
                .pattern("GOS")
                .pattern(" GS")
                .unlockedBy("has_item", conditionsFromItem(ExtraBotanyItems.orichalcos))
                .save(consumer);
        //ManaReader&WandExtend
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ExtraBotanyItems.manaReader)
                .define('D', BotaniaItems.manaDiamond)
                .define('P', BotaniaItems.manaPowder)
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
        hammer(BotaniaItems.manaSteel, BotaniaItems.livingwoodTwig, ExtraBotanyItems.manasteelHammer).save(consumer);
        hammer(BotaniaItems.elementium, BotaniaItems.dreamwoodTwig, ExtraBotanyItems.elementiumHammer).save(consumer);
        hammer(BotaniaItems.terrasteel, BotaniaItems.livingwoodTwig, ExtraBotanyItems.terrasteelHammer).save(consumer);
        //hammer(BotaniaItems.gaiaIngot, ExtraBotanyItems.gaiaHammer).save(consumer);
        hammer(ExtraBotanyItems.photonium, BotaniaItems.livingwoodTwig, ExtraBotanyItems.photoniumHammer).save(consumer);
        hammer(ExtraBotanyItems.shadowium, BotaniaItems.livingwoodTwig, ExtraBotanyItems.shadowiumHammer).save(consumer);
        hammer(ExtraBotanyItems.aerialite, BotaniaItems.dreamwoodTwig, ExtraBotanyItems.aerialiteHammer).save(consumer);
        //hammer(ExtraBotanyItems.orichalcos, ExtraBotanyItems.orichalcosHammer).save(consumer);
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

    private void registerMisc(Consumer<FinishedRecipe> consumer) {

        //Pedestal
        pedestal(BotaniaBlocks.livingrock, ExtraBotanyBlocks.livingrockPedestal)
                .unlockedBy("has_item", conditionsFromItem(BotaniaBlocks.livingrock))
                .save(consumer);
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
