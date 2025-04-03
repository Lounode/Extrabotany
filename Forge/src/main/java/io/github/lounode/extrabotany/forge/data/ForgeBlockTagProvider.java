package io.github.lounode.extrabotany.forge.data;

import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;
import io.github.lounode.extrabotany.common.lib.LibMisc;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ForgeBlockTagProvider extends IntrinsicHolderTagsProvider<Block> {
    public static final TagKey<Block> MANASTEEL = forge("storage_blocks/manasteel");
    public static final TagKey<Block> ORICHALCOS = forge("storage_blocks/orichalcos");
    public static final TagKey<Block> PHOTONIUM = forge("storage_blocks/photonium");
    public static final TagKey<Block> SHADOWIUM = forge("storage_blocks/shadowium");
    public static final TagKey<Block> AERIALITE = forge("storage_blocks/aerialite");

    public ForgeBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider,
                                 ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, provider, (block) -> block.builtInRegistryHolder().key(), LibMisc.MOD_ID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        IntrinsicTagAppender<Block> storageBlocks = tag(Tags.Blocks.STORAGE_BLOCKS);

        tag(ORICHALCOS).addTag(ExtraBotanyTags.Blocks.BLOCKS_ORICHALCOS);
        tag(PHOTONIUM).addTag(ExtraBotanyTags.Blocks.BLOCKS_PHOTONIUM);
        tag(SHADOWIUM).addTag(ExtraBotanyTags.Blocks.BLOCKS_SHADOWIUM);
        tag(AERIALITE).addTag(ExtraBotanyTags.Blocks.BLOCKS_AERIALITE);

        storageBlocks
                .addTag(ORICHALCOS)
                .addTag(PHOTONIUM)
                .addTag(SHADOWIUM)
                .addTag(AERIALITE);
    }
    @Override
    public String getName() {
        return "ExtraBotany block tags (Forge-specific)";
    }

    private static TagKey<Block> forge(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation("forge", name));
    }
}
