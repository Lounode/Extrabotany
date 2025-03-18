package io.github.lounode.extrabotany.data;

import io.github.lounode.extrabotany.common.advancements.ItemUsedTrigger;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import vazkii.botania.common.lib.BotaniaTags;

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
                    .addCriterion("flower", onPickup(BotaniaTags.Items.MYSTICAL_FLOWERS))
                    .save(consumer, mainId("root"));
            Advancement senbonZakura = Advancement.Builder.advancement()
                    .display(simple(ExtraBotanyItems.manaReader, "senbon_zakura", FrameType.CHALLENGE))
                    .parent(root)
                    .addCriterion("item_used",
                            ItemUsedTrigger.TriggerInstance.itemUsed(
                                    ItemPredicate.Builder.item().of(ExtraBotanyItems.manaReader.asItem()).build(),
                                    MinMaxBounds.Ints.atLeast(1000)
                            ))
                    .save(consumer, mainId("senbon_zakura"));
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
