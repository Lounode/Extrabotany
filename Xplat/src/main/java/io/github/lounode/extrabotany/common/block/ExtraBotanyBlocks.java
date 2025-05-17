package io.github.lounode.extrabotany.common.block;

import io.github.lounode.extrabotany.common.block.mana.DimensionCatalystBlock;
import io.github.lounode.extrabotany.common.block.mana.ManaChargerBlock;
import io.github.lounode.extrabotany.common.block.mana.PowerFrameBlock;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.lib.LibBlockNames;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.decor.stairs.BotaniaStairBlock;

import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;
import static vazkii.botania.common.lib.LibBlockNames.SLAB_SUFFIX;
import static vazkii.botania.common.lib.LibBlockNames.STAIR_SUFFIX;

public final class ExtraBotanyBlocks {
    public static final Block orichalcosBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.of().strength(3, 10).mapColor(MapColor.TERRACOTTA_PINK)
            .sound(SoundType.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops());
    public static final Block photoniumBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(orichalcosBlock).mapColor(MapColor.TERRACOTTA_WHITE));
    public static final Block shadowiumBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(orichalcosBlock).mapColor(MapColor.COLOR_BLACK));
    public static final Block aerialiteBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(orichalcosBlock).mapColor(MapColor.LAPIS));
    public static final Block dimensionCatalyst = new DimensionCatalystBlock(BlockBehaviour.Properties.copy(BotaniaBlocks.livingrock));
    public static final Block livingrockPedestal = new PedestalBlock(PedestalBlock.Variant.LIVINGROCK, BlockBehaviour.Properties.of()
            .strength(3.5F)
            .sound(SoundType.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .mapColor(MapColor.TERRACOTTA_WHITE));

    public static final Block[] ALL_PEDESTALS = new Block[] {livingrockPedestal};
    public static final Block powerFrame = new PowerFrameBlock(BlockBehaviour.Properties.copy(Blocks.SPAWNER));
    public static final Block manaCharger = new ManaChargerBlock(BlockBehaviour.Properties.copy(BotaniaBlocks.livingwood)
            .explosionResistance(1.0F)
            .mapColor(MapColor.TERRACOTTA_WHITE));

    //Quartz
    public static final Block gaiaQuartzBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.COLOR_LIGHT_GREEN));
    public static final Block gaiaQuartzStairs = new BotaniaStairBlock(gaiaQuartzBlock.defaultBlockState(), BlockBehaviour.Properties.copy(gaiaQuartzBlock));
    public static final Block gaiaQuartzSlab = new SlabBlock(BlockBehaviour.Properties.copy(gaiaQuartzBlock));
    public static final Block chiseledGaiaQuartzBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(gaiaQuartzBlock));
    public static final Block gaiaQuartzBricks = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(gaiaQuartzBlock));
    public static final Block gaiaQuartzPillar = new RotatedPillarBlock(BlockBehaviour.Properties.copy(gaiaQuartzBlock));
    public static final Block smoothGaiaQuartz = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(gaiaQuartzBlock));
    public static final Block smoothGaiaQuartzStairs = new BotaniaStairBlock(smoothGaiaQuartz.defaultBlockState(), BlockBehaviour.Properties.copy(smoothGaiaQuartz));
    public static final Block smoothGaiaQuartzSlab = new SlabBlock(BlockBehaviour.Properties.copy(smoothGaiaQuartz));

    public static final Block elementiumQuartzBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK).mapColor(MapColor.COLOR_PINK));
    public static final Block elementiumQuartzStairs = new BotaniaStairBlock(elementiumQuartzBlock.defaultBlockState(), BlockBehaviour.Properties.copy(elementiumQuartzBlock));
    public static final Block elementiumQuartzSlab = new SlabBlock(BlockBehaviour.Properties.copy(elementiumQuartzBlock));
    public static final Block chiseledElementiumQuartzBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(elementiumQuartzBlock));
    public static final Block elementiumQuartzBricks = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(elementiumQuartzBlock));
    public static final Block elementiumQuartzPillar = new RotatedPillarBlock(BlockBehaviour.Properties.copy(elementiumQuartzBlock));
    public static final Block smoothElementiumQuartz = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(elementiumQuartzBlock));
    public static final Block smoothElementiumQuartzStairs = new BotaniaStairBlock(smoothElementiumQuartz.defaultBlockState(), BlockBehaviour.Properties.copy(smoothElementiumQuartz));
    public static final Block smoothElementiumQuartzSlab = new SlabBlock(BlockBehaviour.Properties.copy(smoothElementiumQuartz));

    public static final Block[] ALL_QUARTZ = new Block[] {
            gaiaQuartzBlock, gaiaQuartzStairs, gaiaQuartzSlab,
            chiseledGaiaQuartzBlock, gaiaQuartzBricks, gaiaQuartzPillar,
            smoothGaiaQuartz, smoothGaiaQuartzStairs, smoothGaiaQuartzSlab,
            elementiumQuartzBlock, elementiumQuartzStairs, elementiumQuartzSlab,
            chiseledElementiumQuartzBlock, elementiumQuartzBricks, elementiumQuartzPillar,
            smoothElementiumQuartz, smoothElementiumQuartzStairs, smoothElementiumQuartzSlab
    };

    public static FlowerPotBlock flowerPot(Block block, int lightLevel) {
        BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
        return new FlowerPotBlock(block, lightLevel > 0 ? properties.lightLevel(blockState -> lightLevel) : properties);
    }

    public static void registerBlocks(BiConsumer<Block, ResourceLocation> r) {
        r.accept(orichalcosBlock, prefix(LibBlockNames.ORICHALCOS_BLOCK));
        r.accept(photoniumBlock, prefix(LibBlockNames.PHOTONIUM_BLOCK));
        r.accept(shadowiumBlock, prefix(LibBlockNames.SHADOWIUM_BLOCK));
        r.accept(aerialiteBlock, prefix(LibBlockNames.AERIALITE_BLOCK));

        //Pedestal
        r.accept(livingrockPedestal, prefix(LibBlockNames.PEDESTAL_PREFIX + PedestalBlock.Variant.LIVINGROCK.name().toLowerCase(Locale.ROOT)));
        r.accept(powerFrame, prefix(LibBlockNames.POWER_FRAME));
        r.accept(manaCharger, prefix(LibBlockNames.MANA_CHARGER));
        r.accept(dimensionCatalyst, prefix(LibBlockNames.DIMENSION_CATALYST));

        r.accept(gaiaQuartzBlock, prefix(LibBlockNames.GAIA_QUARTZ + "_block"));
        r.accept(chiseledGaiaQuartzBlock, prefix("chiseled_" + LibBlockNames.GAIA_QUARTZ + "_block"));
        r.accept(gaiaQuartzBricks, prefix(LibBlockNames.GAIA_QUARTZ + "_bricks"));
        r.accept(gaiaQuartzPillar, prefix(LibBlockNames.GAIA_QUARTZ + "_pillar"));
        r.accept(gaiaQuartzSlab, prefix(LibBlockNames.GAIA_QUARTZ + SLAB_SUFFIX));
        r.accept(gaiaQuartzStairs, prefix(LibBlockNames.GAIA_QUARTZ + STAIR_SUFFIX));
        r.accept(smoothGaiaQuartz, prefix("smooth_" + LibBlockNames.GAIA_QUARTZ));
        r.accept(smoothGaiaQuartzStairs, prefix("smooth_" + LibBlockNames.GAIA_QUARTZ + STAIR_SUFFIX));
        r.accept(smoothGaiaQuartzSlab, prefix("smooth_" + LibBlockNames.GAIA_QUARTZ + SLAB_SUFFIX));

        r.accept(elementiumQuartzBlock, prefix(LibBlockNames.ELEMENTIUM_QUARTZ + "_block"));
        r.accept(chiseledElementiumQuartzBlock, prefix("chiseled_" + LibBlockNames.ELEMENTIUM_QUARTZ + "_block"));
        r.accept(elementiumQuartzBricks, prefix(LibBlockNames.ELEMENTIUM_QUARTZ + "_bricks"));
        r.accept(elementiumQuartzPillar, prefix(LibBlockNames.ELEMENTIUM_QUARTZ + "_pillar"));
        r.accept(elementiumQuartzSlab, prefix(LibBlockNames.ELEMENTIUM_QUARTZ + SLAB_SUFFIX));
        r.accept(elementiumQuartzStairs, prefix(LibBlockNames.ELEMENTIUM_QUARTZ + STAIR_SUFFIX));
        r.accept(smoothElementiumQuartz, prefix("smooth_" + LibBlockNames.ELEMENTIUM_QUARTZ));
        r.accept(smoothElementiumQuartzStairs, prefix("smooth_" + LibBlockNames.ELEMENTIUM_QUARTZ + STAIR_SUFFIX));
        r.accept(smoothElementiumQuartzSlab, prefix("smooth_" + LibBlockNames.ELEMENTIUM_QUARTZ + SLAB_SUFFIX));

    }

    public static void registerItemBlocks(BiConsumer<Item, ResourceLocation> r) {
        Item.Properties props = ExtraBotanyItems.defaultBuilder();

        r.accept(new BlockItem(gaiaQuartzBlock, props), BuiltInRegistries.BLOCK.getKey(gaiaQuartzBlock));
        r.accept(new BlockItem(gaiaQuartzStairs, props), BuiltInRegistries.BLOCK.getKey(gaiaQuartzStairs));
        r.accept(new BlockItem(gaiaQuartzSlab, props), BuiltInRegistries.BLOCK.getKey(gaiaQuartzSlab));
        r.accept(new BlockItem(chiseledGaiaQuartzBlock, props), BuiltInRegistries.BLOCK.getKey(chiseledGaiaQuartzBlock));
        r.accept(new BlockItem(gaiaQuartzBricks, props), BuiltInRegistries.BLOCK.getKey(gaiaQuartzBricks));
        r.accept(new BlockItem(gaiaQuartzPillar, props), BuiltInRegistries.BLOCK.getKey(gaiaQuartzPillar));
        r.accept(new BlockItem(smoothGaiaQuartz, props), BuiltInRegistries.BLOCK.getKey(smoothGaiaQuartz));
        r.accept(new BlockItem(smoothGaiaQuartzStairs, props), BuiltInRegistries.BLOCK.getKey(smoothGaiaQuartzStairs));
        r.accept(new BlockItem(smoothGaiaQuartzSlab, props), BuiltInRegistries.BLOCK.getKey(smoothGaiaQuartzSlab));

        r.accept(new BlockItem(elementiumQuartzBlock, props), BuiltInRegistries.BLOCK.getKey(elementiumQuartzBlock));
        r.accept(new BlockItem(elementiumQuartzStairs, props), BuiltInRegistries.BLOCK.getKey(elementiumQuartzStairs));
        r.accept(new BlockItem(elementiumQuartzSlab, props), BuiltInRegistries.BLOCK.getKey(elementiumQuartzSlab));
        r.accept(new BlockItem(chiseledElementiumQuartzBlock, props), BuiltInRegistries.BLOCK.getKey(chiseledElementiumQuartzBlock));
        r.accept(new BlockItem(elementiumQuartzBricks, props), BuiltInRegistries.BLOCK.getKey(elementiumQuartzBricks));
        r.accept(new BlockItem(elementiumQuartzPillar, props), BuiltInRegistries.BLOCK.getKey(elementiumQuartzPillar));
        r.accept(new BlockItem(smoothElementiumQuartz, props), BuiltInRegistries.BLOCK.getKey(smoothElementiumQuartz));
        r.accept(new BlockItem(smoothElementiumQuartzStairs, props), BuiltInRegistries.BLOCK.getKey(smoothElementiumQuartzStairs));
        r.accept(new BlockItem(smoothElementiumQuartzSlab, props), BuiltInRegistries.BLOCK.getKey(smoothElementiumQuartzSlab));


        r.accept(new BlockItem(orichalcosBlock, ExtraBotanyItems.defaultBuilder().rarity(Rarity.EPIC)), BuiltInRegistries.BLOCK.getKey(orichalcosBlock));
        r.accept(new BlockItem(photoniumBlock, props), BuiltInRegistries.BLOCK.getKey(photoniumBlock));
        r.accept(new BlockItem(shadowiumBlock, props), BuiltInRegistries.BLOCK.getKey(shadowiumBlock));
        r.accept(new BlockItem(aerialiteBlock, props), BuiltInRegistries.BLOCK.getKey(aerialiteBlock));
        r.accept(new BlockItem(dimensionCatalyst, props), BuiltInRegistries.BLOCK.getKey(dimensionCatalyst));
        r.accept(new BlockItem(powerFrame, props), BuiltInRegistries.BLOCK.getKey(powerFrame));
        r.accept(new BlockItem(livingrockPedestal, props), BuiltInRegistries.BLOCK.getKey(livingrockPedestal));
        r.accept(new BlockItem(manaCharger, props), BuiltInRegistries.BLOCK.getKey(manaCharger));

    }

    public static void registerFlowerPotPlants(BiConsumer<ResourceLocation, Supplier<? extends Block>> consumer) {

    }
}
