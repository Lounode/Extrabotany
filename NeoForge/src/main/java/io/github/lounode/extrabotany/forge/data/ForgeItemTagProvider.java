package io.github.lounode.extrabotany.forge.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;
import io.github.lounode.extrabotany.common.lib.LibMisc;

import java.util.concurrent.CompletableFuture;

import static io.github.lounode.extrabotany.common.item.ExtraBotanyItems.*;
import static net.neoforged.neoforge.common.Tags.Items;

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
		this.generateMaterialTags();
		this.generateToolTags();
		this.generateAccessoryTags();
	}

	private void generateMaterialTags() {
		//Material
		this.tag(c("ingots/orichalcos")).addTag(ExtraBotanyTags.Items.INGOTS_ORICHALCOS);
		this.tag(c("ingots/photonium")).addTag(ExtraBotanyTags.Items.INGOTS_PHOTONIUM);
		this.tag(c("ingots/shadowium")).addTag(ExtraBotanyTags.Items.INGOTS_SHADOWIUM);
		this.tag(c("ingots/aerialite")).addTag(ExtraBotanyTags.Items.INGOTS_AERIALITE);
		this.tag(Items.INGOTS)
				.addTag(c("ingots/orichalcos"))
				.addTag(c("ingots/photonium"))
				.addTag(c("ingots/shadowium"))
				.addTag(c("ingots/aerialite"));

		this.tag(c("nuggets/orichalcos")).addTag(ExtraBotanyTags.Items.NUGGETS_ORICHALCOS);
		this.tag(c("nuggets/photonium")).addTag(ExtraBotanyTags.Items.NUGGETS_PHOTONIUM);
		this.tag(c("nuggets/shadowium")).addTag(ExtraBotanyTags.Items.NUGGETS_SHADOWIUM);
		this.tag(c("nuggets/aerialite")).addTag(ExtraBotanyTags.Items.NUGGETS_AERIALITE);
		this.tag(Items.NUGGETS)
				.addTag(c("nuggets/orichalcos"))
				.addTag(c("nuggets/photonium"))
				.addTag(c("nuggets/shadowium"))
				.addTag(c("nuggets/aerialite"));

		this.copyToSameName(ForgeBlockTagProvider.ORICHALCOS);
		this.copyToSameName(ForgeBlockTagProvider.PHOTONIUM);
		this.copyToSameName(ForgeBlockTagProvider.SHADOWIUM);
		this.copyToSameName(ForgeBlockTagProvider.AERIALITE);

		this.tag(Items.GEMS_QUARTZ).add(gaiaQuartz, elementiumQuartz);
	}

	private void generateAccessoryTags() {
		tag(accessory("ring")).add(RINGS);
		tag(accessory("necklace")).add(pureDaisyPendant);
		tag(accessory(("body"))).add(BODY);
		tag(accessory("curio")).add(ALL_SLOT);
	}

	private void generateToolTags() {
		this.tag(ItemTags.HEAD_ARMOR).add(starryIdolHeadgear, pleiadesCombatMaidHeadgear, shadowWarriorHelmet, goblinSlayerHelmet);
		this.tag(ItemTags.CHEST_ARMOR).add(starryIdolSuit, pleiadesCombatMaidSuit, sanguinePleiadesCombatMaidSuit, shadowWarriorChestplate, goblinSlayerChestplate);
		this.tag(ItemTags.LEG_ARMOR).add(starryIdolSkirt, pleiadesCombatMaidSkirt, shadowWarriorLeggings, goblinSlayerLeggings);
		this.tag(ItemTags.FOOT_ARMOR).add(starryIdolBoots, pleiadesCombatMaidBoots, shadowWarriorBoots, goblinSlayerBoots);
		this.tag(Items.TOOLS_SHIELD).add(SHIELDS);
		this.tag(Items.TOOLS_BOW).add(BOWS);
	}

	private static TagKey<Item> accessory(String name) {
		return ItemTags.create(ResourceLocation.tryBuild("curios", name));
	}

	private static TagKey<Item> c(String name) {
		return ItemTags.create(ResourceLocation.tryBuild("c", name));
	}

	private void copyToSameName(TagKey<Block> source) {
		this.copy(source, ItemTags.create(source.location()));
	}
}
