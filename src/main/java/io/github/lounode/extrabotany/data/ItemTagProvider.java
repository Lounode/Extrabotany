package io.github.lounode.extrabotany.data;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends ItemTagsProvider {
    public ItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider) {
        super(packOutput, lookupProvider, blockTagProvider);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ExtraBotanyTags.Items.INGOTS_ORICHALCOS).add(ExtraBotanyItems.orichalcos);
        this.tag(ExtraBotanyTags.Items.INGOTS_PHOTONIUM).add(ExtraBotanyItems.photonium);
        this.tag(ExtraBotanyTags.Items.INGOTS_SHADOWIUM).add(ExtraBotanyItems.shadowium);
        this.tag(ExtraBotanyTags.Items.INGOTS_AERIALITE).add(ExtraBotanyItems.aerialite);

        this.tag(ExtraBotanyTags.Items.NUGGETS_ORICHALCOS).add(ExtraBotanyItems.orichalcosNugget);
        this.tag(ExtraBotanyTags.Items.NUGGETS_PHOTONIUM).add(ExtraBotanyItems.photoniumNugget);
        this.tag(ExtraBotanyTags.Items.NUGGETS_SHADOWIUM).add(ExtraBotanyItems.shadowiumNugget);
        this.tag(ExtraBotanyTags.Items.NUGGETS_AERIALITE).add(ExtraBotanyItems.aerialiteNugget);

        this.copy(ExtraBotanyTags.Blocks.BLOCKS_ORICHALCOS, ExtraBotanyTags.Items.BLOCKS_ORICHALCOS);
        this.copy(ExtraBotanyTags.Blocks.BLOCKS_PHOTONIUM, ExtraBotanyTags.Items.BLOCKS_PHOTONIUM);
        this.copy(ExtraBotanyTags.Blocks.BLOCKS_SHADOWIUM, ExtraBotanyTags.Items.BLOCKS_SHADOWIUM);
        this.copy(ExtraBotanyTags.Blocks.BLOCKS_AERIALITE, ExtraBotanyTags.Items.BLOCKS_AERIALITE);

    }
}
