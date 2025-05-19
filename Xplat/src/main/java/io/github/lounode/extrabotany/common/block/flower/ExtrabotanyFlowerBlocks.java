package io.github.lounode.extrabotany.common.block.flower;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import vazkii.botania.api.block.WandHUD;
import vazkii.botania.api.block_entity.BindableSpecialFlowerBlockEntity;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.SpecialFlowerBlockEntity;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.FloatingSpecialFlowerBlock;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.block.SpecialFlowerBlockItem;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.block.flower.functional.TradeOrchidBlockEntity;
import io.github.lounode.extrabotany.common.lib.LibBlockNames;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;

public class ExtrabotanyFlowerBlocks {

	private static final BlockBehaviour.Properties FLOWER_PROPS = BlockBehaviour.Properties.copy(Blocks.POPPY);
	private static final BlockBehaviour.Properties FLOATING_PROPS = BotaniaBlocks.FLOATING_PROPS;

	public static final Block tradeOrchid = createSpecialFlowerBlock(MobEffects.HERO_OF_THE_VILLAGE, 1, FLOWER_PROPS, () -> ExtrabotanyFlowerBlocks.TRADE_ORCHID);
	public static final Block tradeOrchidFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, () -> ExtrabotanyFlowerBlocks.TRADE_ORCHID);
	public static final Block tradeOrchidPotted = ExtraBotanyBlocks.flowerPot(tradeOrchid, 0);

	public static final BlockEntityType<TradeOrchidBlockEntity> TRADE_ORCHID = EXplatAbstractions.INSTANCE.createBlockEntityType(TradeOrchidBlockEntity::new, tradeOrchid, tradeOrchidFloating);

	private static ResourceLocation floating(ResourceLocation orig) {
		return new ResourceLocation(orig.getNamespace(), "floating_" + orig.getPath());
	}

	private static ResourceLocation potted(ResourceLocation orig) {
		return new ResourceLocation(orig.getNamespace(), "potted_" + orig.getPath());
	}

	private static ResourceLocation chibi(ResourceLocation orig) {
		return new ResourceLocation(orig.getNamespace(), orig.getPath() + "_chibi");
	}

	private static ResourceLocation getId(Block b) {
		return BuiltInRegistries.BLOCK.getKey(b);
	}

	private static FlowerBlock createSpecialFlowerBlock(
			MobEffect effect, int effectDuration,
			BlockBehaviour.Properties props,
			Supplier<BlockEntityType<? extends SpecialFlowerBlockEntity>> beType) {
		return EXplatAbstractions.INSTANCE.createSpecialFlowerBlock(
				effect, effectDuration, props, beType);
	}

	private static FlowerBlock createSpecialFlowerBlock(
			MobEffect effect, int effectDuration,
			BlockBehaviour.Properties props,
			Supplier<BlockEntityType<? extends SpecialFlowerBlockEntity>> beType,
			boolean hasComparatorOutput) {
		return EXplatAbstractions.INSTANCE.createSpecialFlowerBlock(
				effect, effectDuration, props, beType, hasComparatorOutput
		);
	}

	public static void registerBlocks(BiConsumer<Block, ResourceLocation> r) {
		r.accept(tradeOrchid, LibBlockNames.SUBTILE_TRADE_ORCHID);
		r.accept(tradeOrchidFloating, floating(LibBlockNames.SUBTILE_TRADE_ORCHID));
		r.accept(tradeOrchidPotted, potted(LibBlockNames.SUBTILE_TRADE_ORCHID));

	}

	public static void registerItemBlocks(BiConsumer<Item, ResourceLocation> r) {
		Item.Properties props = BotaniaItems.defaultBuilder();

		r.accept(new SpecialFlowerBlockItem(tradeOrchid, props), getId(tradeOrchid));
		r.accept(new SpecialFlowerBlockItem(tradeOrchidFloating, props), getId(tradeOrchidFloating));
	}

	public static void registerTEs(BiConsumer<BlockEntityType<?>, ResourceLocation> r) {
		r.accept(TRADE_ORCHID, getId(tradeOrchid));
	}

	public static void registerWandHudCaps(BotaniaBlockEntities.BECapConsumer<WandHUD> consumer) {
		consumer.accept(be -> new BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<>((FunctionalFlowerBlockEntity) be),
				TRADE_ORCHID
		);
	}

	public static void registerFlowerPotPlants(BiConsumer<ResourceLocation, Supplier<? extends Block>> consumer) {
		registerBlocks((block, resourceLocation) -> {
			if (block instanceof FlowerPotBlock) {
				var id = getId(block);
				consumer.accept(new ResourceLocation(id.getNamespace(), id.getPath().substring(vazkii.botania.common.lib.LibBlockNames.POTTED_PREFIX.length())), () -> block);
			}
		});
	}
}
