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
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.SpecialFlowerBlockEntity;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.FloatingSpecialFlowerBlock;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.block.SpecialFlowerBlockItem;

import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.block.flower.functional.TradeOrchidBlockEntity;
import io.github.lounode.extrabotany.common.block.flower.functional.WoodieniaBlockEntity;
import io.github.lounode.extrabotany.common.block.flower.generating.ReikarlilyBlockEntity;
import io.github.lounode.extrabotany.common.lib.LibBlockNames;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ExtrabotanyFlowerBlocks {

	private static final BlockBehaviour.Properties FLOWER_PROPS = BlockBehaviour.Properties.copy(Blocks.POPPY);
	private static final BlockBehaviour.Properties FLOATING_PROPS = BotaniaBlocks.FLOATING_PROPS;

	public static final Block tradeOrchid = createSpecialFlowerBlock(MobEffects.HERO_OF_THE_VILLAGE, 5 * 20, FLOWER_PROPS, () -> ExtrabotanyFlowerBlocks.TRADE_ORCHID);
	public static final Block tradeOrchidFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, () -> ExtrabotanyFlowerBlocks.TRADE_ORCHID);
	public static final Block tradeOrchidPotted = ExtraBotanyBlocks.flowerPot(tradeOrchid, 0);

	public static final Block woodienia = createSpecialFlowerBlock(MobEffects.HUNGER, 10 * 20, FLOWER_PROPS, () -> ExtrabotanyFlowerBlocks.WOODIENIA);
	public static final Block woodieniaFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, () -> ExtrabotanyFlowerBlocks.WOODIENIA);
	public static final Block woodieniaPotted = ExtraBotanyBlocks.flowerPot(woodienia, 0);

	public static final Block reikarlily = createSpecialFlowerBlock(MobEffects.FIRE_RESISTANCE, 30 * 20, FLOWER_PROPS, () -> ExtrabotanyFlowerBlocks.REIKARLILY);
	public static final Block reikarlilyFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, () -> ExtrabotanyFlowerBlocks.REIKARLILY);
	public static final Block reikarlilyPotted = ExtraBotanyBlocks.flowerPot(reikarlily, 0);

	public static final BlockEntityType<TradeOrchidBlockEntity> TRADE_ORCHID = EXplatAbstractions.INSTANCE.createBlockEntityType(TradeOrchidBlockEntity::new, tradeOrchid, tradeOrchidFloating);
	public static final BlockEntityType<WoodieniaBlockEntity> WOODIENIA = EXplatAbstractions.INSTANCE.createBlockEntityType(WoodieniaBlockEntity::new, woodienia, woodieniaFloating);
	public static final BlockEntityType<ReikarlilyBlockEntity> REIKARLILY = EXplatAbstractions.INSTANCE.createBlockEntityType(ReikarlilyBlockEntity::new, reikarlily, reikarlilyFloating);

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

		r.accept(woodienia, LibBlockNames.SUBTILE_WOODIENIA);
		r.accept(woodieniaFloating, floating(LibBlockNames.SUBTILE_WOODIENIA));
		r.accept(woodieniaPotted, potted(LibBlockNames.SUBTILE_WOODIENIA));

		r.accept(reikarlily, LibBlockNames.SUBTILE_REIKARLILY);
		r.accept(reikarlilyFloating, floating(LibBlockNames.SUBTILE_REIKARLILY));
		r.accept(reikarlilyPotted, potted(LibBlockNames.SUBTILE_REIKARLILY));
	}

	public static void registerItemBlocks(BiConsumer<Item, ResourceLocation> r) {
		Item.Properties props = BotaniaItems.defaultBuilder();

		r.accept(new SpecialFlowerBlockItem(tradeOrchid, props), getId(tradeOrchid));
		r.accept(new SpecialFlowerBlockItem(tradeOrchidFloating, props), getId(tradeOrchidFloating));

		r.accept(new SpecialFlowerBlockItem(woodienia, props), getId(woodienia));
		r.accept(new SpecialFlowerBlockItem(woodieniaFloating, props), getId(woodieniaFloating));

		r.accept(new SpecialFlowerBlockItem(reikarlily, props), getId(reikarlily));
		r.accept(new SpecialFlowerBlockItem(reikarlilyFloating, props), getId(reikarlilyFloating));
	}

	public static void registerTEs(BiConsumer<BlockEntityType<?>, ResourceLocation> r) {
		r.accept(TRADE_ORCHID, getId(tradeOrchid));
		r.accept(WOODIENIA, getId(woodienia));
		r.accept(REIKARLILY, getId(reikarlily));
	}

	public static void registerWandHudCaps(BotaniaBlockEntities.BECapConsumer<WandHUD> consumer) {
		consumer.accept(be -> new BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<>((FunctionalFlowerBlockEntity) be),
				TRADE_ORCHID, WOODIENIA
		);
		consumer.accept(be -> new BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<>((GeneratingFlowerBlockEntity) be),
				REIKARLILY
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
