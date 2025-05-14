package io.github.lounode.extrabotany.forge.data;


import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;
import io.github.lounode.extrabotany.common.lib.LibMisc;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static io.github.lounode.extrabotany.common.item.ExtraBotanyItems.*;
import static net.minecraftforge.common.Tags.Items;

public class ForgeItemTagProvider extends net.minecraft.data.tags.ItemTagsProvider {
    public ForgeItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider, ExistingFileHelper helper) {
        super(packOutput, lookupProvider, blockTagProvider, LibMisc.MOD_ID, helper);
    }

    @Override
    public String getName() {
        return "ExtraBotany item tags (Forge-specific)";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //Material
        this.tag(forge("ingots/orichalcos")).addTag(ExtraBotanyTags.Items.INGOTS_ORICHALCOS);
        this.tag(forge("ingots/photonium")).addTag(ExtraBotanyTags.Items.INGOTS_PHOTONIUM);
        this.tag(forge("ingots/shadowium")).addTag(ExtraBotanyTags.Items.INGOTS_SHADOWIUM);
        this.tag(forge("ingots/aerialite")).addTag(ExtraBotanyTags.Items.INGOTS_AERIALITE);
        this.tag(Items.INGOTS)
                .addTag(forge("ingots/orichalcos"))
                .addTag(forge("ingots/photonium"))
                .addTag(forge("ingots/shadowium"))
                .addTag(forge("ingots/aerialite"));

        this.tag(forge("nuggets/orichalcos")).addTag(ExtraBotanyTags.Items.NUGGETS_ORICHALCOS);
        this.tag(forge("nuggets/photonium")).addTag(ExtraBotanyTags.Items.NUGGETS_PHOTONIUM);
        this.tag(forge("nuggets/shadowium")).addTag(ExtraBotanyTags.Items.NUGGETS_SHADOWIUM);
        this.tag(forge("nuggets/aerialite")).addTag(ExtraBotanyTags.Items.NUGGETS_AERIALITE);
        this.tag(Items.NUGGETS)
                .addTag(forge("nuggets/orichalcos"))
                .addTag(forge("nuggets/photonium"))
                .addTag(forge("nuggets/shadowium"))
                .addTag(forge("nuggets/aerialite"));


        this.copyToSameName(ForgeBlockTagProvider.ORICHALCOS);
        this.copyToSameName(ForgeBlockTagProvider.PHOTONIUM);
        this.copyToSameName(ForgeBlockTagProvider.SHADOWIUM);
        this.copyToSameName(ForgeBlockTagProvider.AERIALITE);

        this.tag(Items.GEMS_QUARTZ).add(gaiaQuartz, elementiumQuartz);

        //Tools
        this.tag(Items.TOOLS_BOWS).add(BOWS);

        this.generateAccessoryTags();
        //TODO 盖亚bossTag
    }

    private void generateAccessoryTags() {
        tag(accessory("ring")).add(RINGS);
        tag(accessory(("body"))).add(BODY);
        tag(accessory("curio")).add(ALL_SLOT);
    }

    private static TagKey<Item> accessory(String name) {
        return ItemTags.create(ResourceLocation.tryBuild("curios", name));
    }

    private static TagKey<Item> forge(String name) {
        return ItemTags.create(ResourceLocation.tryBuild("forge", name));
    }

    private void copyToSameName(TagKey<Block> source) {
        this.copy(source, ItemTags.create(source.location()));
    }
}
