package io.github.lounode.extrabotany.data;

import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.lib.LibAdvancementNames;
import io.github.lounode.extrabotany.data.patchouli.PatchouliBuilder;
import io.github.lounode.extrabotany.data.patchouli.PatchouliEntry;
import io.github.lounode.extrabotany.data.patchouli.PatchouliProvider;
import io.github.lounode.extrabotany.data.patchouli.page.botania.ManaInfusionPage;
import io.github.lounode.extrabotany.data.patchouli.page.botania.RunicAltarPage;
import io.github.lounode.extrabotany.data.patchouli.page.extrabotany.EatPage;
import io.github.lounode.extrabotany.data.patchouli.page.extrabotany.PedestalPage;
import io.github.lounode.extrabotany.data.patchouli.page.extrabotany.SmithingPage;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.nio.file.Path;
import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public final class PatchouliBookProvider extends PatchouliProvider {
    private static final ResourceLocation CATEGORY = ResourceLocation.tryParse("botania:extrabotanies");

    public PatchouliBookProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildEntries(Consumer<PatchouliEntry> consumer) {
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.mana_reader")
                .withIcon(ExtraBotanyItems.manaReader)
                .pages(
                        text("extrabotany.page.mana_reader0"),
                        crafting(ExtraBotanyItems.manaReader)
                                .withText("extrabotany.page.mana_reader1"),
                        text("extrabotany.page.mana_reader2"),
                        crafting("extrabotany:twig_wand_extension")
                                .withRecipe2("extrabotany:dreamwood_wand_extension")
                                .withTitle("extrabotany.page.mana_reader3")
                )
                .save(consumer, id("mana_reader"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.hammers")
                .withIcon(ExtraBotanyItems.manasteelHammer)
                .pages(
                        text("extrabotany.page.hammers0"),
                        crafting(ExtraBotanyItems.manasteelHammer)
                                .withText("extrabotany.page.hammers1")
                )
                .save(consumer, id("hammers"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.camera")
                .withIcon(ExtraBotanyItems.camera)
                .withAdvancement(mainAdvancement(LibAdvancementNames.STYGIAN_TWINS))
                .pages(
                        text("extrabotany.page.camera0"),
                        text("extrabotany.page.camera1"),
                        crafting(ExtraBotanyItems.camera)
                                .withText("extrabotany.page.camera2")
                )
                .save(consumer, id("camera"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.mana_ring_master")
                .withIcon(ExtraBotanyItems.manaRingMaster)
                .withAdvancement(mainAdvancement(LibAdvancementNames.THE_SOURCE_OF_HONKAI))
                .pages(
                        text("extrabotany.page.mana_ring_master0"),
                        crafting(ExtraBotanyItems.manaRingMaster)
                                .withText("extrabotany.page.mana_ring_master1")
                )
                .save(consumer, id("mana_ring_master"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.failnaught")
                .withIcon(ExtraBotanyItems.failnaught)
                .withAdvancement(mainAdvancement(LibAdvancementNames.THE_SOURCE_OF_HONKAI))
                .pages(
                        text("extrabotany.page.failnaught0"),
                        crafting(ExtraBotanyItems.failnaught)
                                .withText("extrabotany.page.failnaught1"),
                        text("extrabotany.page.failnaught2"),
                        text("extrabotany.page.failnaught3")
                )
                .save(consumer, id("failnaught"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.pedestals")
                .withIcon(ExtraBotanyBlocks.livingrockPedestal)
                .pages(
                        text("extrabotany.page.pedestals0"),
                        crafting(ExtraBotanyBlocks.livingrockPedestal)
                                .withText("extrabotany.page.pedestals1"),
                        text("extrabotany.page.pedestals2"),
                        pedestal(ExtraBotanyItems.gildedPotatoMashed)
                                .withText("extrabotany.page.pedestals3"),
                        pedestal(ExtraBotanyItems.spiritFragment)
                                .withText("extrabotany.page.pedestals5"),
                        text("extrabotany.page.pedestals4")
                )
                .save(consumer, id("pedestals"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.fuels")
                .withIcon(ExtraBotanyItems.nightmareFuel)
                .withAdvancement(ResourceLocation.tryParse("botania:main/mana_pool_pickup"))
                .pages(
                        text("extrabotany.page.fuels0"),
                        manaInfusion(ExtraBotanyItems.nightmareFuel)
                                .withText("extrabotany.page.fuels1"),
                        text("extrabotany.page.fuels2"),
                        new EatPage(ExtraBotanyItems.nightmareFuel, ExtraBotanyItems.spiritFuel)
                                .withText("extrabotany.page.fuels3"),
                        text("extrabotany.page.fuels4")
                )
                .save(consumer, id("fuels"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.zadkiel")
                .withIcon(ExtraBotanyItems.zadkiel)
                .pages(
                        text("extrabotany.page.zadkiel0"),
                        runicAlter(ExtraBotanyItems.zadkiel)
                                .withText("extrabotany.page.zadkiel1")
                )
                .save(consumer, id("zadkiel"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.excalibur")
                .withIcon(ExtraBotanyItems.excalibur)
                .withAdvancement(mainAdvancement(LibAdvancementNames.THE_SOURCE_OF_HONKAI))
                .pages(
                        text("extrabotany.page.excalibur0"),
                        new SmithingPage("extrabotany:smithing/excalibur")
                                .withText("extrabotany.page.excalibur1")
                )
                .save(consumer, id("excalibur"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.jingwei")
                .withIcon(ExtraBotanyItems.featherOfJingwei)
                .withAdvancement(mainAdvancement(LibAdvancementNames.THE_SOURCE_OF_HONKAI))
                .pages(
                        text("extrabotany.page.jingwei0"),
                        crafting(ExtraBotanyItems.featherOfJingwei)
                                .withText("extrabotany.page.jingwei1")
                )
                .save(consumer, id("jingwei"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.magic_finger")
                .withIcon(ExtraBotanyItems.magicFinger)
                .withAdvancement(mainAdvancement(LibAdvancementNames.THE_SOURCE_OF_HONKAI))
                .pages(
                        text("extrabotany.page.magic_finger0"),
                        crafting(ExtraBotanyItems.magicFinger)
                                .withText("extrabotany.page.magic_finger1")

                )
                .save(consumer, id("magic_finger"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.walking_cane")
                .withIcon(ExtraBotanyItems.walkingCane)
                .pages(
                        text("extrabotany.page.walking_cane0"),
                        crafting(ExtraBotanyItems.walkingCane)
                                .withText("extrabotany.page.walking_cane1")
                )
                .save(consumer, id("walking_cane"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.core_of_the_void")
                .withIcon(ExtraBotanyItems.coreOfTheVoid)
                .withAdvancement(mainAdvancement(LibAdvancementNames.THE_SOURCE_OF_HONKAI))
                .pages(
                        text("extrabotany.page.core_of_the_void0"),
                        text("extrabotany.page.core_of_the_void1")

                )
                .save(consumer, id("core_of_the_void"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.gaia_3rd")
                .withIcon(ExtraBotanyItems.challengeTicket)
                .withAdvancement(mainAdvancement(LibAdvancementNames.STYGIAN_TWINS))
                .pages(
                        text("extrabotany.page.gaia_3rd0"),
                        crafting(ExtraBotanyItems.challengeTicket)
                                .withText("extrabotany.page.gaia_3rd1"),
                        text("extrabotany.page.gaia_3rd2"),
                        text("extrabotany.page.gaia_3rd3"),
                        text("extrabotany.page.gaia_3rd4")
                )
                .save(consumer, id("gaia_3rd"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.yin_and_yang")
                .withIcon(ExtraBotanyItems.shadowium)
                .withAdvancement(ResourceLocation.tryParse("botania:main/elf_portal_open"))
                .pages(
                        text("extrabotany.page.yin_and_yang0"),
                        text("extrabotany.page.yin_and_yang1"),
                        runicAlter(ExtraBotanyItems.photonium)
                                .withText("extrabotany.page.yin_and_yang2"),
                        runicAlter(ExtraBotanyItems.shadowium)
                                .withText("extrabotany.page.yin_and_yang3")
                )
                .save(consumer, id("yin_and_yang"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.pandoras_box")
                .withIcon(ExtraBotanyItems.pandorasBox)
                .withAdvancement(mainAdvancement(LibAdvancementNames.GAIA_TRIAL))
                .pages(
                        spotlight(ExtraBotanyItems.pandorasBox)
                                .withText("extrabotany.page.pandoras_box0"),

                        empty(),
                        text("extrabotany.page.pandoras_box1")
                                .withAdvancement(mainAdvancement(LibAdvancementNames.THE_SOURCE_OF_HONKAI)),
                        spotlight(ExtraBotanyItems.heroMedal)
                                .withText("extrabotany.page.pandoras_box2")
                                .withAdvancement(mainAdvancement(LibAdvancementNames.THE_SOURCE_OF_HONKAI))
                )
                .save(consumer, id("pandoras_box"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.reward_bags")
                .withIcon(ExtraBotanyItems.zweiRewardBag)
                .withAdvancement(mainAdvancement(LibAdvancementNames.THE_SOURCE_OF_HONKAI))
                .pages(
                        text("extrabotany.page.reward_bags0"),
                        spotlight(ExtraBotanyItems.einsRewardBag)
                                .withText("extrabotany.page.reward_bags1"),
                        spotlight(ExtraBotanyItems.zweiRewardBag)
                                .withText("extrabotany.page.reward_bags2"),
                        spotlight(ExtraBotanyItems.dreiRewardBag)
                                .withText("extrabotany.page.reward_bags3"),
                        spotlight(ExtraBotanyItems.vierRewardBag)
                                .withText("extrabotany.page.reward_bags4"),
                        spotlight(ExtraBotanyItems.nineAndThreeQuartersRewardBag)
                                .withText("extrabotany.page.reward_bags5")

                )
                .save(consumer, id("reward_bags"));
    }

    private RunicAltarPage runicAlter(ItemLike item) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item.asItem());
        ResourceLocation recipeLocation = location.withPrefix("runic_altar/");
        return new RunicAltarPage(recipeLocation.toString());
    }

    private PedestalPage pedestal(ItemLike item) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item.asItem());
        ResourceLocation recipeLocation = location.withPrefix("pedestal_smash/");
        return new PedestalPage(recipeLocation.toString());
    }

    private ManaInfusionPage manaInfusion(ItemLike item) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item.asItem());
        ResourceLocation recipeLocation = location.withPrefix("mana_infusion/");
        return new ManaInfusionPage(recipeLocation.toString());
    }

    private static ResourceLocation mainAdvancement(String name) {
        return prefix("main/" + name);
    }

    private ResourceLocation id(String fileName) {
        return ResourceLocation.tryBuild(CATEGORY.getPath(), fileName);
    }
    @Override
    protected Path getOutputPath (PatchouliEntry entry) {
        return packOutput.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve("botania/patchouli_books/lexicon/en_us/entries")
                .resolve(entry.getID().getNamespace())
                .resolve(entry.getID().getPath() + ".json");
    }

    @Override
    public String getName() {
        return "ExtraBotany Patchouli Entries";
    }
}
