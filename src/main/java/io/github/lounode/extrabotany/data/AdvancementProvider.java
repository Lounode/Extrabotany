package io.github.lounode.extrabotany.data;

import io.github.lounode.extrabotany.common.advancements.ItemUsedTrigger;
import io.github.lounode.extrabotany.common.advancements.ManaChargeTrigger;
import io.github.lounode.extrabotany.common.advancements.MinMaxBoundsExtension;
import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.item.relic.MasterBandOfManaItem;
import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;
import io.github.lounode.extrabotany.common.lib.LibAdvancementNames;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import vazkii.botania.common.advancements.UseItemSuccessTrigger;
import vazkii.botania.common.item.BotaniaItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class AdvancementProvider extends vazkii.botania.data.AdvancementProvider {
    public static net.minecraft.data.advancements.AdvancementProvider create(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        return new net.minecraft.data.advancements.AdvancementProvider(packOutput, lookupProvider, List.of(new ExtrabotanyAdvancements()));
    }
    public static class ExtrabotanyAdvancements implements AdvancementSubProvider{
        public void generate(HolderLookup.Provider lookup, Consumer<Advancement> consumer) {
            Advancement root = Advancement.Builder.advancement()
                    .display(rootDisplay(ExtraBotanyItems.zadkiel,"itemGroup.extrabotany", "extrabotany.desc", prefix("textures/block/photonium_block.png")))
                    .addCriterion("use_lexicon", new UseItemSuccessTrigger.Instance(ContextAwarePredicate.ANY,
                            ItemPredicate.Builder.item().of(BotaniaItems.lexicon).build(), LocationPredicate.ANY))
                    .save(consumer, mainId(LibAdvancementNames.ROOT));
            Advancement senbonZakura = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.manaReader, LibAdvancementNames.SENBON_ZAKURA, FrameType.CHALLENGE))
                    .parent(root)
                    .addCriterion("item_used",
                            ItemUsedTrigger.TriggerInstance.itemUsed(
                                    ItemPredicate.Builder.item().of(ExtraBotanyItems.manaReader.asItem()).build(),
                                    MinMaxBounds.Ints.atLeast(1000)
                            ))
                    .save(consumer, mainId(LibAdvancementNames.SENBON_ZAKURA));
            Advancement iSeeEveryThing = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.camera, LibAdvancementNames.I_SEE_EVERYTHING, FrameType.GOAL))
                    .parent(root)
                    .addCriterion("code_triggered", new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, mainId(LibAdvancementNames.I_SEE_EVERYTHING));
            //ManaRing
            Advancement craftRing = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.manaRingMaster, LibAdvancementNames.PANDA_DO_NOT_WEAR_RINGS, FrameType.GOAL))
                    .parent(root)
                    .addCriterion("craft_ring", onPickup(ExtraBotanyItems.manaRingMaster))
                    .save(consumer, mainId(LibAdvancementNames.PANDA_DO_NOT_WEAR_RINGS));

            Advancement overlord = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.manaRingMaster, LibAdvancementNames.OVERLOAD, FrameType.CHALLENGE))
                    .parent(craftRing)
                    .addCriterion("mana_charge", ManaChargeTrigger.TriggerInstance.manaCharged(
                            ItemPredicate.Builder.item().of(ExtraBotanyItems.manaRingMaster).build(),
                            MinMaxBoundsExtension.Longs.atLeast(MasterBandOfManaItem.ADVANCEMENT_PHASE1_REQUIRE)
                    ))
                    .save(consumer, mainId(LibAdvancementNames.OVERLOAD));
            Advancement lordOfKing = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.manaRingMaster, LibAdvancementNames.LOAD_OF_RING, FrameType.CHALLENGE))
                    .parent(overlord)
                    .addCriterion("mana_charge", ManaChargeTrigger.TriggerInstance.manaCharged(
                            ItemPredicate.Builder.item().of(ExtraBotanyItems.manaRingMaster).build(),
                            MinMaxBoundsExtension.Longs.atLeast(MasterBandOfManaItem.ADVANCEMENT_PHASE2_REQUIRE)
                    ))
                    .save(consumer, mainId(LibAdvancementNames.LOAD_OF_RING));
            //Pedestal
            Advancement pedestal = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyBlocks.livingrockPedestal, LibAdvancementNames.CRAFT_PEDESTAL, FrameType.TASK))
                    .parent(root)
                    .addCriterion("craft_pedestal", onPickup(ExtraBotanyTags.Items.PEDESTALS))
                    .save(consumer, mainId(LibAdvancementNames.CRAFT_PEDESTAL));
            Advancement kurukuru = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyBlocks.livingrockPedestal, LibAdvancementNames.KURUKURU, FrameType.GOAL))
                    .parent(pedestal)
                    .addCriterion("code_triggered", new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, mainId(LibAdvancementNames.KURUKURU));
            Advancement goodtek = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.manasteelHammer, LibAdvancementNames.GOODTEK, FrameType.TASK))
                    .parent(pedestal)
                    .addCriterion("code_triggered", new ImpossibleTrigger.TriggerInstance())
                    .save(consumer, mainId(LibAdvancementNames.GOODTEK));

            Advancement deepDarkFantasy = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.nightmareFuel, LibAdvancementNames.DEEP_DARK_FANTASY, FrameType.TASK))
                    .parent(root)
                    .addCriterion("eat_nightmare_fuel", new UseItemSuccessTrigger.Instance(
                            ContextAwarePredicate.ANY,
                            ItemPredicate.Builder.item().of(ExtraBotanyItems.nightmareFuel).build(),
                            LocationPredicate.ANY
                            )
                    )
                    .save(consumer, mainId(LibAdvancementNames.DEEP_DARK_FANTASY));
        }
    }

    protected static DisplayInfo simple(ItemLike icon, String name, FrameType frameType) {
        String expandedName = "advancement.extrabotany:" + name;
        return new DisplayInfo(new ItemStack(icon.asItem()),
                Component.translatable(expandedName),
                Component.translatable(expandedName + ".desc"),
                null, frameType, true, true, false);
    }

    protected static DisplayInfo hidden(ItemLike icon, String name, FrameType frameType) {
        String expandedName = "advancement.extrabotany:" + name;
        return new DisplayInfo(new ItemStack(icon.asItem()),
                Component.translatable(expandedName),
                Component.translatable(expandedName + ".desc"),
                null, frameType, true, true, true);
    }

    private static String mainId(String name) {
        return prefix("main/" + name).toString();
    }

    private static String challengeId(String name) {
        return prefix("challenge/" + name).toString();
    }
}
