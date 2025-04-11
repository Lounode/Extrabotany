package io.github.lounode.extrabotany.data;

import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.data.patchouli.*;
import io.github.lounode.extrabotany.data.patchouli.page.botania.ManaInfusionPage;
import io.github.lounode.extrabotany.data.patchouli.page.botania.RunicAltarPage;
import io.github.lounode.extrabotany.data.patchouli.page.extrabotany.EatPage;
import io.github.lounode.extrabotany.data.patchouli.page.extrabotany.PedestalPage;
import io.github.lounode.extrabotany.data.patchouli.page.extrabotany.SmithingPage;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.function.Consumer;

public final class ExtraBotanyPatchouliProvider extends PatchouliProvider {
    private static final ResourceLocation CATEGORY = new ResourceLocation("botania:extrabotanies");

    public ExtraBotanyPatchouliProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildEntries(Consumer<PatchouliEntry> consumer) {
        /*
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.test")
                .withIcon(ExtraBotanyItems.zadkiel)
                .pages(
                        text("extrabotany.page.test0")
                )
                .save(consumer, id("test"));

         */
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
                .pages(
                        text("extrabotany.page.mana_ring_master0"),
                        crafting(ExtraBotanyItems.manaRingMaster)
                                .withText("extrabotany.page.mana_ring_master1")
                )
                .save(consumer, id("mana_ring_master"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.failnaught")
                .withIcon(ExtraBotanyItems.failnaught)
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
                        new PedestalPage("extrabotany:pedestal_smash/gilded_potato_mashed")
                                .withText("extrabotany.page.pedestals3"),
                        new PedestalPage("extrabotany:pedestal_smash/spirit_fragment")
                                .withText("extrabotany.page.pedestals5"),
                        text("extrabotany.page.pedestals4")
                )
                .save(consumer, id("pedestals"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.fuels")
                .withIcon(ExtraBotanyItems.nightmareFuel)
                .pages(
                        text("extrabotany.page.fuels0"),
                        new ManaInfusionPage("extrabotany:mana_infusion/nightmare_fuel")
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
                        new RunicAltarPage("extrabotany:runic_altar/zadkiel")
                                .withText("extrabotany.page.zadkiel1")
                )
                .save(consumer, id("zadkiel"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.excalibur")
                .withIcon(ExtraBotanyItems.excalibur)
                .pages(
                        text("extrabotany.page.excalibur0"),
                        new SmithingPage("extrabotany:smithing/excalibur")
                                .withText("extrabotany.page.excalibur1")
                )
                .save(consumer, id("excalibur"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.jingwei")
                .withIcon(ExtraBotanyItems.featherOfJingwei)
                .pages(
                        text("extrabotany.page.jingwei0"),
                        crafting(ExtraBotanyItems.featherOfJingwei)
                                .withText("extrabotany.page.jingwei1")
                )
                .save(consumer, id("jingwei"));
        PatchouliBuilder.entry(CATEGORY)
                .withName("extrabotany.entry.magic_finger")
                .withIcon(ExtraBotanyItems.magicFinger)
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
                .pages(
                        text("extrabotany.page.core_of_the_void0"),
                        text("extrabotany.page.core_of_the_void1")

                )
                .save(consumer, id("core_of_the_void"));
    }





    private ResourceLocation id(String fileName) {
        return new ResourceLocation(CATEGORY.getPath(), fileName);
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
