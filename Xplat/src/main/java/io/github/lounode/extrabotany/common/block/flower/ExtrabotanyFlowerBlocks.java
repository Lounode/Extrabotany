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
import io.github.lounode.extrabotany.common.block.flower.functional.AnnoyingFlowerBlockEntity;
import io.github.lounode.extrabotany.common.block.flower.functional.TradeOrchidBlockEntity;
import io.github.lounode.extrabotany.common.block.flower.functional.WoodieniaBlockEntity;
import io.github.lounode.extrabotany.common.block.flower.generating.*;
import io.github.lounode.extrabotany.common.brew.ExtraBotanyMobEffects;
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

	public static final Block bellflower = createSpecialFlowerBlock(MobEffects.MOVEMENT_SPEED, 10 * 20, FLOWER_PROPS, () -> ExtrabotanyFlowerBlocks.BELLFLOWER);
	public static final Block bellflowerFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, () -> ExtrabotanyFlowerBlocks.BELLFLOWER);
	public static final Block bellflowerPotted = ExtraBotanyBlocks.flowerPot(bellflower, 0);

	public static final Block annoyingflower = createSpecialFlowerBlock(MobEffects.LUCK, 10 * 20, FLOWER_PROPS, () -> ExtrabotanyFlowerBlocks.ANNOYINGFLOWER);
	public static final Block annoyingflowerFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, () -> ExtrabotanyFlowerBlocks.ANNOYINGFLOWER);
	public static final Block annoyingflowerPotted = ExtraBotanyBlocks.flowerPot(annoyingflower, 0);

	public static final Block stonesia = createSpecialFlowerBlock(MobEffects.DIG_SPEED, 10 * 20, FLOWER_PROPS, () -> ExtrabotanyFlowerBlocks.STONESIA);
	public static final Block stonesiaFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, () -> ExtrabotanyFlowerBlocks.STONESIA);
	public static final Block stonesiaPotted = ExtraBotanyBlocks.flowerPot(stonesia, 0);

	public static final Block edelweiss = createSpecialFlowerBlock(ExtraBotanyMobEffects.WARM, 10 * 20, FLOWER_PROPS, () -> ExtrabotanyFlowerBlocks.EDELWEISS);
	public static final Block edelweissFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, () -> ExtrabotanyFlowerBlocks.EDELWEISS);
	public static final Block edelweissPotted = ExtraBotanyBlocks.flowerPot(edelweiss, 0);

	public static final Block noisling = createSpecialFlowerBlock(MobEffects.MOVEMENT_SPEED, 10 * 20, FLOWER_PROPS, () -> ExtrabotanyFlowerBlocks.NOISLING);
	public static final Block noislingFloating = new FloatingSpecialFlowerBlock(FLOATING_PROPS, () -> ExtrabotanyFlowerBlocks.NOISLING);
	public static final Block noislingPotted = ExtraBotanyBlocks.flowerPot(noisling, 0);

	public static final BlockEntityType<TradeOrchidBlockEntity> TRADE_ORCHID = EXplatAbstractions.INSTANCE.createBlockEntityType(TradeOrchidBlockEntity::new, tradeOrchid, tradeOrchidFloating);
	public static final BlockEntityType<WoodieniaBlockEntity> WOODIENIA = EXplatAbstractions.INSTANCE.createBlockEntityType(WoodieniaBlockEntity::new, woodienia, woodieniaFloating);
	public static final BlockEntityType<ReikarlilyBlockEntity> REIKARLILY = EXplatAbstractions.INSTANCE.createBlockEntityType(ReikarlilyBlockEntity::new, reikarlily, reikarlilyFloating);
	public static final BlockEntityType<BellflowerBlockEntity> BELLFLOWER = EXplatAbstractions.INSTANCE.createBlockEntityType(BellflowerBlockEntity::new, bellflower, bellflowerFloating);
	public static final BlockEntityType<AnnoyingFlowerBlockEntity> ANNOYINGFLOWER = EXplatAbstractions.INSTANCE.createBlockEntityType(AnnoyingFlowerBlockEntity::new, annoyingflower, annoyingflowerFloating);
	public static final BlockEntityType<StonesiaBlockEntity> STONESIA = EXplatAbstractions.INSTANCE.createBlockEntityType(StonesiaBlockEntity::new, stonesia, stonesiaFloating);
	public static final BlockEntityType<EdelweissBlockEntity> EDELWEISS = EXplatAbstractions.INSTANCE.createBlockEntityType(EdelweissBlockEntity::new, edelweiss, edelweissFloating);
	public static final BlockEntityType<NoislingBlockEntity> NOISLING = EXplatAbstractions.INSTANCE.createBlockEntityType(NoislingBlockEntity::new, noisling, noislingFloating);

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
		r.accept(tradeOrchid, LibBlockNames.TRADE_ORCHID);
		r.accept(tradeOrchidFloating, floating(LibBlockNames.TRADE_ORCHID));
		r.accept(tradeOrchidPotted, potted(LibBlockNames.TRADE_ORCHID));

		r.accept(woodienia, LibBlockNames.WOODIENIA);
		r.accept(woodieniaFloating, floating(LibBlockNames.WOODIENIA));
		r.accept(woodieniaPotted, potted(LibBlockNames.WOODIENIA));

		r.accept(reikarlily, LibBlockNames.REIKARLILY);
		r.accept(reikarlilyFloating, floating(LibBlockNames.REIKARLILY));
		r.accept(reikarlilyPotted, potted(LibBlockNames.REIKARLILY));

		r.accept(bellflower, LibBlockNames.BELLFLOWER);
		r.accept(bellflowerFloating, floating(LibBlockNames.BELLFLOWER));
		r.accept(bellflowerPotted, potted(LibBlockNames.BELLFLOWER));

		r.accept(annoyingflower, LibBlockNames.ANNOYINGFLOWER);
		r.accept(annoyingflowerFloating, floating(LibBlockNames.ANNOYINGFLOWER));
		r.accept(annoyingflowerPotted, potted(LibBlockNames.ANNOYINGFLOWER));

		r.accept(stonesia, LibBlockNames.STONESIA);
		r.accept(stonesiaFloating, floating(LibBlockNames.STONESIA));
		r.accept(stonesiaPotted, potted(LibBlockNames.STONESIA));

		r.accept(edelweiss, LibBlockNames.EDELWEISS);
		r.accept(edelweissFloating, floating(LibBlockNames.EDELWEISS));
		r.accept(edelweissPotted, potted(LibBlockNames.EDELWEISS));

		r.accept(noisling, LibBlockNames.NOISLING);
		r.accept(noislingFloating, floating(LibBlockNames.NOISLING));
		r.accept(noislingPotted, potted(LibBlockNames.NOISLING));
	}

	public static void registerItemBlocks(BiConsumer<Item, ResourceLocation> r) {
		Item.Properties props = BotaniaItems.defaultBuilder();

		r.accept(new SpecialFlowerBlockItem(tradeOrchid, props), getId(tradeOrchid));
		r.accept(new SpecialFlowerBlockItem(tradeOrchidFloating, props), getId(tradeOrchidFloating));

		r.accept(new SpecialFlowerBlockItem(woodienia, props), getId(woodienia));
		r.accept(new SpecialFlowerBlockItem(woodieniaFloating, props), getId(woodieniaFloating));

		r.accept(new SpecialFlowerBlockItem(reikarlily, props), getId(reikarlily));
		r.accept(new SpecialFlowerBlockItem(reikarlilyFloating, props), getId(reikarlilyFloating));

		r.accept(new SpecialFlowerBlockItem(bellflower, props), getId(bellflower));
		r.accept(new SpecialFlowerBlockItem(bellflowerFloating, props), getId(bellflowerFloating));

		r.accept(new SpecialFlowerBlockItem(annoyingflower, props), getId(annoyingflower));
		r.accept(new SpecialFlowerBlockItem(annoyingflowerFloating, props), getId(annoyingflowerFloating));

		r.accept(new SpecialFlowerBlockItem(stonesia, props), getId(stonesia));
		r.accept(new SpecialFlowerBlockItem(stonesiaFloating, props), getId(stonesiaFloating));

		r.accept(new SpecialFlowerBlockItem(edelweiss, props), getId(edelweiss));
		r.accept(new SpecialFlowerBlockItem(edelweissFloating, props), getId(edelweissFloating));

		//r.accept(new SpecialFlowerBlockItem(noisling, props), getId(noisling));
		//r.accept(new SpecialFlowerBlockItem(noislingFloating, props), getId(noislingFloating));
	}

	public static void registerTEs(BiConsumer<BlockEntityType<?>, ResourceLocation> r) {
		r.accept(TRADE_ORCHID, getId(tradeOrchid));
		r.accept(WOODIENIA, getId(woodienia));
		r.accept(REIKARLILY, getId(reikarlily));
		r.accept(BELLFLOWER, getId(bellflower));
		r.accept(ANNOYINGFLOWER, getId(annoyingflower));
		r.accept(STONESIA, getId(stonesia));
		r.accept(EDELWEISS, getId(edelweiss));
		r.accept(NOISLING, getId(noisling));
	}

	public static void registerWandHudCaps(BotaniaBlockEntities.BECapConsumer<WandHUD> consumer) {
		consumer.accept(be -> new BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<>((FunctionalFlowerBlockEntity) be),
				TRADE_ORCHID, WOODIENIA
		);
		consumer.accept(be -> new BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<>((GeneratingFlowerBlockEntity) be),
				REIKARLILY, STONESIA, EDELWEISS, NOISLING
		);
		consumer.accept(be -> new BellflowerBlockEntity.WandHud((BellflowerBlockEntity) be), BELLFLOWER);
		consumer.accept(be -> new AnnoyingFlowerBlockEntity.WandHud((AnnoyingFlowerBlockEntity) be), ANNOYINGFLOWER);
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
