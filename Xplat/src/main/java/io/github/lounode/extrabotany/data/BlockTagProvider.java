package io.github.lounode.extrabotany.data;

import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;
import io.github.lounode.extrabotany.common.lib.LibMisc;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import static io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks.*;

public class BlockTagProvider extends IntrinsicHolderTagsProvider<Block> {
    public static final Predicate<Block> EXTRABOTANY_BLOCK = b -> LibMisc.MOD_ID.equals(BuiltInRegistries.BLOCK.getKey(b).getNamespace());

    public BlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Registries.BLOCK, lookupProvider, (block) -> block.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.BEACON_BASE_BLOCKS).add(
                aerialiteBlock,
                orichalcosBlock,
                photoniumBlock,
                shadowiumBlock
                );
        tag(ExtraBotanyTags.Blocks.BLOCKS_AERIALITE).add(aerialiteBlock);
        tag(ExtraBotanyTags.Blocks.BLOCKS_ORICHALCOS).add(orichalcosBlock);
        tag(ExtraBotanyTags.Blocks.BLOCKS_PHOTONIUM).add(photoniumBlock);
        tag(ExtraBotanyTags.Blocks.BLOCKS_SHADOWIUM).add(shadowiumBlock);

        tag(ExtraBotanyTags.Blocks.PEDESTALS).add(ALL_PEDESTALS);

        registerMiningTags();
    }

    private void registerMiningTags() {
        Set<Block> pickaxe = new HashSet<>(Set.of(
                aerialiteBlock, orichalcosBlock, photoniumBlock, shadowiumBlock,
                dimensionCatalyst
        ));
        pickaxe.addAll(List.of(ALL_PEDESTALS));
        pickaxe.addAll(List.of(ALL_QUARTZ));

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(getModBlocks(pickaxe::contains));
    }

    @NotNull
    private Block[] getModBlocks(Predicate<Block> predicate) {
        return BuiltInRegistries.BLOCK.stream().filter(EXTRABOTANY_BLOCK.and(predicate))
                .sorted(Comparator.comparing(BuiltInRegistries.BLOCK::getKey))
                .toArray(Block[]::new);
    }
}
