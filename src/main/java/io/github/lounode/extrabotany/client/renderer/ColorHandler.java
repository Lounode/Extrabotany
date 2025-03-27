package io.github.lounode.extrabotany.client.renderer;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import vazkii.botania.client.core.handler.ClientTickHandler;

public class ColorHandler {
    public interface BlockHandlerConsumer {
        void register(BlockColor handler, Block... blocks);
    }

    public interface ItemHandlerConsumer {
        void register(ItemColor handler, ItemLike... items);
    }

    public static void submitBlocks(BlockHandlerConsumer blocks) {

    }

    public static void submitItems(ItemHandlerConsumer items) {
        items.register((s, t) -> t == 0 ? Mth.hsvToRgb(ClientTickHandler.ticksInGame * 2 % 360 / 360F, 0.25F, 1F) : -1,
                ExtraBotanyItems.gaiaHammer);
    }
}
