package io.github.lounode.extrabotany.data;

import io.github.lounode.extrabotany.ExtraBotany;
import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks.*;

public class BlockTagProvider extends IntrinsicHolderTagsProvider<Block> {
    public static final Predicate<Block> EXTRABOTANY_BLOCK = b -> ExtraBotany.MODID.equals(BuiltInRegistries.BLOCK.getKey(b).getNamespace());

    public BlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Registries.BLOCK, lookupProvider, (block) -> block.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.BEACON_BASE_BLOCKS).add(
                aerialiteBlock,
                ExtraBotanyBlocks.orichalcosBlock,
                ExtraBotanyBlocks.photoniumBlock,
                ExtraBotanyBlocks.shadowiumBlock
                );
        tag(ExtraBotanyTags.Blocks.BLOCKS_AERIALITE).add(aerialiteBlock);
        tag(ExtraBotanyTags.Blocks.BLOCKS_ORICHALCOS).add(ExtraBotanyBlocks.orichalcosBlock);
        tag(ExtraBotanyTags.Blocks.BLOCKS_PHOTONIUM).add(ExtraBotanyBlocks.photoniumBlock);
        tag(ExtraBotanyTags.Blocks.BLOCKS_SHADOWIUM).add(ExtraBotanyBlocks.shadowiumBlock);

        registerMiningTags();
    }

    private void registerMiningTags() {
        var pickaxe = Set.of(
            aerialiteBlock, orichalcosBlock, photoniumBlock, shadowiumBlock
        );
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                getModBlocks(block -> pickaxe.contains(block))
        );
    }

    @NotNull
    private Block[] getModBlocks(Predicate<Block> predicate) {
        return BuiltInRegistries.BLOCK.stream().filter(EXTRABOTANY_BLOCK.and(predicate))
                .sorted(Comparator.comparing(BuiltInRegistries.BLOCK::getKey))
                .toArray(Block[]::new);
    }
}
