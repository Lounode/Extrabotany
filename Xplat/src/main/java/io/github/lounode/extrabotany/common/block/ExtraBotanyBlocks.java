package io.github.lounode.extrabotany.common.block;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.lib.LibBlockNames;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

import java.util.Locale;
import java.util.function.BiConsumer;

public final class ExtraBotanyBlocks {
    public static final Block orichalcosBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.of().strength(3, 10).mapColor(MapColor.TERRACOTTA_PINK)
            .sound(SoundType.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops());
    public static final Block photoniumBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(orichalcosBlock).mapColor(MapColor.TERRACOTTA_WHITE));
    public static final Block shadowiumBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(orichalcosBlock).mapColor(MapColor.COLOR_BLACK));
    public static final Block aerialiteBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.copy(orichalcosBlock).mapColor(MapColor.LAPIS));
    public static final Block livingrockPedestal = new PedestalBlock(PedestalBlock.Variant.LIVINGROCK, BlockBehaviour.Properties.of()
            .strength(3.5F)
            .sound(SoundType.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .mapColor(MapColor.TERRACOTTA_WHITE));

    public static final Block[] ALL_PEDESTALS = new Block[] {livingrockPedestal};
    public static void registerBlocks(BiConsumer<Block, ResourceLocation> r) {
        r.accept(orichalcosBlock, prefix(LibBlockNames.ORICHALCOS_BLOCK));
        r.accept(photoniumBlock, prefix(LibBlockNames.PHOTONIUM_BLOCK));
        r.accept(shadowiumBlock, prefix(LibBlockNames.SHADOWIUM_BLOCK));
        r.accept(aerialiteBlock, prefix(LibBlockNames.AERIALITE_BLOCK));

        //Pedestal
        r.accept(livingrockPedestal, prefix(LibBlockNames.PEDESTAL_PREFIX + PedestalBlock.Variant.LIVINGROCK.name().toLowerCase(Locale.ROOT)));
    }

    public static void registerItemBlocks(BiConsumer<Item, ResourceLocation> r) {
        Item.Properties props = ExtraBotanyItems.defaultBuilder();

        r.accept(new BlockItem(orichalcosBlock, ExtraBotanyItems.defaultBuilder().rarity(Rarity.EPIC)), BuiltInRegistries.BLOCK.getKey(orichalcosBlock));
        r.accept(new BlockItem(photoniumBlock, props), BuiltInRegistries.BLOCK.getKey(photoniumBlock));
        r.accept(new BlockItem(shadowiumBlock, props), BuiltInRegistries.BLOCK.getKey(shadowiumBlock));
        r.accept(new BlockItem(aerialiteBlock, props), BuiltInRegistries.BLOCK.getKey(aerialiteBlock));

        r.accept(new BlockItem(livingrockPedestal, props), BuiltInRegistries.BLOCK.getKey(livingrockPedestal));
    }
}
