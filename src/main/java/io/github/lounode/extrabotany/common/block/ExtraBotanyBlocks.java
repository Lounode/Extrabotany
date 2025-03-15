package io.github.lounode.extrabotany.common.block;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.lib.LibBlockNames;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

import java.util.function.BiConsumer;

public final class ExtraBotanyBlocks {
    public static final Block orichalcosBlock = new ExtraBotanyBlock(BlockBehaviour.Properties.of().strength(3, 10).mapColor(MapColor.TERRACOTTA_PINK)
            .sound(SoundType.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops());

    public static void registerBlocks(BiConsumer<Block, ResourceLocation> r) {
        r.accept(orichalcosBlock, prefix(LibBlockNames.ORICHALCOS_BLOCK));
    }

    public static void registerItemBlocks(BiConsumer<Item, ResourceLocation> r) {
        Item.Properties props = ExtraBotanyItems.defaultBuilder();

        r.accept(new BlockItem(orichalcosBlock, props), BuiltInRegistries.BLOCK.getKey(orichalcosBlock));
    }
}
