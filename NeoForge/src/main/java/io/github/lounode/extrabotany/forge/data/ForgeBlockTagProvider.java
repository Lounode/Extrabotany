package io.github.lounode.extrabotany.forge.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;
import io.github.lounode.extrabotany.common.lib.LibMisc;

import java.util.concurrent.CompletableFuture;

public class ForgeBlockTagProvider extends IntrinsicHolderTagsProvider<Block> {
	public static final TagKey<Block> MANASTEEL = c("storage_blocks/manasteel");
	public static final TagKey<Block> ORICHALCOS = c("storage_blocks/orichalcos");
	public static final TagKey<Block> PHOTONIUM = c("storage_blocks/photonium");
	public static final TagKey<Block> SHADOWIUM = c("storage_blocks/shadowium");
	public static final TagKey<Block> AERIALITE = c("storage_blocks/aerialite");

	@SuppressWarnings("deprecated")
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

	private static TagKey<Block> c(String name) {
		return TagKey.create(Registries.BLOCK, ResourceLocation.tryBuild("c", name));
	}
}
