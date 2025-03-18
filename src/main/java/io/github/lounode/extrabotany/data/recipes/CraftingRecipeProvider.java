package io.github.lounode.extrabotany.data.recipes;

import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
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
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.data.recipes.WrapperResult;

import java.util.function.Consumer;

public class CraftingRecipeProvider extends vazkii.botania.data.recipes.CraftingRecipeProvider {
    public CraftingRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        registerMain(consumer);
        registerTools(consumer);
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

    private void registerTools(Consumer<FinishedRecipe> consumer) {
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
