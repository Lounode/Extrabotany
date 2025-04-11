package io.github.lounode.extrabotany.fabric.data;

import io.github.lounode.extrabotany.data.ItemTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

import static io.github.lounode.extrabotany.common.item.ExtraBotanyItems.*;

public class FabricItemTagProvider extends ItemTagProvider {
    public FabricItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider) {
        super(packOutput, lookupProvider, blockTagProvider);
    }

    private static TagKey<Item> itemTag(ResourceLocation location) {
        return TagKey.create(Registries.ITEM, location);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        generateToolTags();
        generateAccessoryTags();
        generateCompatTags();
    }

    private void generateToolTags() {
        this.tag(ConventionalItemTags.BOWS).add(BOWS);

    }

    private void generateAccessoryTags() {
        this.tag(accessory("hand/ring")).add(RINGS);
        this.tag(accessory("offhand/ring")).add(RINGS);
        this.tag(accessory("all")).add(ALL_SLOT);
        this.tag(accessory("chest/cape")).add(BODY);
    }

    private void generateCompatTags() {
        this.tag(itemTag(new ResourceLocation("modern_industrialization", "replicator_blacklist")))
                .add(REPLICATOR_BLACKLIST);
    }

    private static TagKey<Item> accessory(String name) {
        return itemTag(new ResourceLocation("trinkets", name));
    }
}
