package io.github.lounode.extrabotany.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import vazkii.botania.common.advancements.RelicBindTrigger;
import vazkii.botania.common.advancements.UseItemSuccessTrigger;
import vazkii.botania.common.item.BotaniaItems;

import io.github.lounode.extrabotany.common.advancements.ManaChargeTrigger;
import io.github.lounode.extrabotany.common.advancements.MinMaxBoundsExtension;
import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.entity.ExtraBotanyEntityType;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.item.relic.MasterBandOfManaItem;
import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;
import io.github.lounode.extrabotany.common.lib.LibAdvancementNames;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class AdvancementProvider extends vazkii.botania.data.AdvancementProvider {

	public static String CODE_TRIGGE = "code_triggered";

	public static net.minecraft.data.advancements.AdvancementProvider create(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		return new net.minecraft.data.advancements.AdvancementProvider(packOutput, lookupProvider, List.of(new ExtrabotanyAdvancements()));
	}

	public static class ExtrabotanyAdvancements implements AdvancementSubProvider {
		@Override
		public void generate(HolderLookup.Provider lookup, Consumer<Advancement> consumer) {
			Advancement root = Advancement.Builder.advancement()
					.display(rootDisplay(ExtraBotanyItems.zadkiel, "itemGroup.extrabotany", "extrabotany.desc", prefix("textures/block/photonium_block.png")))
					.addCriterion("use_lexicon", new UseItemSuccessTrigger.Instance(ContextAwarePredicate.ANY,
							ItemPredicate.Builder.item().of(BotaniaItems.lexicon).build(), LocationPredicate.ANY))
					.save(consumer, mainId(LibAdvancementNames.ROOT));
			Advancement senbonZakura = Advancement.Builder.advancement()
					.display(simple(ExtraBotanyItems.manaReader, LibAdvancementNames.SENBON_ZAKURA, FrameType.CHALLENGE))
					.parent(root)
					.addCriterion("code_triggered", new ImpossibleTrigger.TriggerInstance())
					.save(consumer, mainId(LibAdvancementNames.SENBON_ZAKURA));
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
			Advancement stygianTwins = Advancement.Builder.advancement()
					.display(simple(ExtraBotanyItems.shadowium, LibAdvancementNames.STYGIAN_TWINS, FrameType.TASK))
					.parent(goodtek)
					.addCriterion("craft_shadowium", onPickup(ExtraBotanyTags.Items.INGOTS_SHADOWIUM))
					.addCriterion("craft_photonium", onPickup(ExtraBotanyTags.Items.INGOTS_SHADOWIUM))
					.save(consumer, mainId(LibAdvancementNames.STYGIAN_TWINS));
			Advancement gaiaTrial = Advancement.Builder.advancement()
					.display(simple(ExtraBotanyItems.challengeTicket, LibAdvancementNames.GAIA_TRIAL, FrameType.CHALLENGE))
					.parent(stygianTwins)
					.addCriterion("kill_gaia_3rd",
							KilledTrigger.TriggerInstance.playerKilledEntity(
									EntityPredicate.Builder.entity()
											.of(ExtraBotanyEntityType.GAIA_III)
											.build()
							)
					)
					.save(consumer, mainId(LibAdvancementNames.GAIA_TRIAL));
			Advancement theSourceOfHonkai = Advancement.Builder.advancement()
					.display(simple(ExtraBotanyItems.pandorasBox, LibAdvancementNames.THE_SOURCE_OF_HONKAI, FrameType.TASK))
					.parent(gaiaTrial)
					.addCriterion("open_pandoras_box", new UseItemSuccessTrigger.Instance(
							ContextAwarePredicate.ANY,
							ItemPredicate.Builder.item().of(ExtraBotanyItems.pandorasBox).build(),
							LocationPredicate.ANY
					))
					.save(consumer, mainId(LibAdvancementNames.THE_SOURCE_OF_HONKAI));
			Advancement theOriginalDivineKey = relicBindAdvancement(
					ExtraBotanyItems.voidArchives, LibAdvancementNames.THE_ORIGINAL_DIVINE_KEY)
							.parent(theSourceOfHonkai)
							.save(consumer, mainId(LibAdvancementNames.THE_ORIGINAL_DIVINE_KEY));
			Advancement iSeeEveryThing = Advancement.Builder.advancement()
					.display(simple(ExtraBotanyItems.camera, LibAdvancementNames.I_SEE_EVERYTHING, FrameType.GOAL))
					.parent(stygianTwins)
					.addCriterion("code_triggered", new ImpossibleTrigger.TriggerInstance())
					.save(consumer, mainId(LibAdvancementNames.I_SEE_EVERYTHING));
			Advancement onePunch = Advancement.Builder.advancement()
					.display(simple(ExtraBotanyItems.featherOfJingwei, LibAdvancementNames.ONE_PUNCH, FrameType.CHALLENGE))
					.parent(theSourceOfHonkai)
					.addCriterion("code_triggered", new ImpossibleTrigger.TriggerInstance())
					.save(consumer, mainId(LibAdvancementNames.ONE_PUNCH));
			Advancement soulSteel = Advancement.Builder.advancement()
					.display(simple(ExtraBotanyItems.orichalcos, LibAdvancementNames.SOULSTEEL, FrameType.CHALLENGE))
					.parent(theSourceOfHonkai)
					.addCriterion("craft_orichalcos", onPickup(ExtraBotanyTags.Items.INGOTS_ORICHALCOS))
					.save(consumer, mainId(LibAdvancementNames.SOULSTEEL));
			//ManaRing
			Advancement craftRing = relicBindAdvancement(
					ExtraBotanyItems.manaRingMaster, LibAdvancementNames.PANDA_DO_NOT_WEAR_RINGS)
							.parent(soulSteel)
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
			Advancement hundredBlockPierce = Advancement.Builder.advancement()
					.display(simple(ExtraBotanyItems.failnaught, LibAdvancementNames.HUNDRED_BLOCK_PIERCE, FrameType.CHALLENGE))
					.parent(soulSteel)
					.addCriterion("code_triggered", new ImpossibleTrigger.TriggerInstance())
					.save(consumer, mainId(LibAdvancementNames.HUNDRED_BLOCK_PIERCE));
			Advancement skyIsNotTheLimit = Advancement.Builder.advancement()
					.display(simple(ExtraBotanyItems.aerialite, LibAdvancementNames.SKY_IS_NOT_THE_LIMIT, FrameType.TASK))
					.parent(stygianTwins)
					.addCriterion("craft_aerialite", onPickup(ExtraBotanyTags.Items.INGOTS_AERIALITE))
					.save(consumer, mainId(LibAdvancementNames.SKY_IS_NOT_THE_LIMIT));

			Advancement potatoServer = Advancement.Builder.advancement()
					.display(simple(ExtraBotanyItems.gildedPotatoMashed, LibAdvancementNames.POTATO_SERVER, FrameType.TASK))
					.parent(root)
					.addCriterion("code_triggered", new ImpossibleTrigger.TriggerInstance())
					.save(consumer, mainId(LibAdvancementNames.POTATO_SERVER));

			Advancement rheinKraft = relicBindAdvancement(ExtraBotanyItems.rheinHammer, LibAdvancementNames.RHEIN_KRAFT)
					.parent(theSourceOfHonkai)
					.save(consumer, mainId(LibAdvancementNames.RHEIN_KRAFT));
			Advancement spongeHammer = Advancement.Builder.advancement()
					.display(simple(Items.SPONGE, LibAdvancementNames.SPONGE_HAMMER, FrameType.TASK))
					.parent(rheinKraft)
					.addCriterion(CODE_TRIGGE, new ImpossibleTrigger.TriggerInstance())
					.save(consumer, mainId(LibAdvancementNames.SPONGE_HAMMER));
		}
	}

	private static Advancement.Builder relicBindAdvancement(Item relicItem, String titleKey) {
		return Advancement.Builder.advancement()
				.display(simple(relicItem, titleKey, FrameType.CHALLENGE))
				.rewards(AdvancementRewards.Builder.experience(50))
				.addCriterion("has_relic", new RelicBindTrigger.Instance(ContextAwarePredicate.ANY, ItemPredicate.Builder.item().of(relicItem).build()));
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
