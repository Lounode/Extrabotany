package io.github.lounode.extrabotany.data.loot;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.ApplyExplosionDecay;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import org.jetbrains.annotations.NotNull;

import io.github.lounode.extrabotany.common.block.flower.ExtrabotanyFlowerBlocks;
import io.github.lounode.extrabotany.common.block.flower.functional.WoodieniaBlockEntity;
import io.github.lounode.extrabotany.common.block.flower.generating.*;
import io.github.lounode.extrabotany.common.lib.LibMisc;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class BlockLootProvider implements DataProvider {
	private static final LootItemCondition.Builder SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item()
			.hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
	private static final Function<Block, LootTable.Builder> SKIP = b -> {
		throw new RuntimeException("shouldn't be executed");
	};

	private final PackOutput.PathProvider pathProvider;
	private final Map<Block, Function<Block, LootTable.Builder>> functionTable = new HashMap<>();

	public BlockLootProvider(PackOutput packOutput) {
		this.pathProvider = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "loot_tables/blocks");

		for (Block b : BuiltInRegistries.BLOCK) {
			ResourceLocation id = BuiltInRegistries.BLOCK.getKey(b);
			if (!LibMisc.MOD_ID.equals(id.getNamespace())) {
				continue;
			}
			if (b instanceof SlabBlock) {
				functionTable.put(b, BlockLootProvider::genSlab);
				//} else if (b instanceof BotaniaDoubleFlowerBlock) {
				//    functionTable.put(b, BlockLootProvider::genDoubleFlower);
				//} else if (b instanceof BotaniaGrassBlock) {
				//    functionTable.put(b, BlockLootProvider::genAltGrass);
			} else if (b instanceof FlowerPotBlock flowerPot) {
				functionTable.put(b, block -> createPotAndPlantItemTable(flowerPot.getContent()));
				//} else if (id.getPath().matches(LibBlockNames.METAMORPHIC_PREFIX + "\\w+" + "_stone")) {
				//    functionTable.put(b, BlockLootProvider::genMetamorphicStone);
			}
		}

		//FlowerNbt
		functionTable.put(ExtrabotanyFlowerBlocks.woodienia, b -> genCopyNbt(b, WoodieniaBlockEntity.TAG_COOLDOWN));
		functionTable.put(ExtrabotanyFlowerBlocks.woodieniaFloating, b -> genCopyNbt(b, WoodieniaBlockEntity.TAG_COOLDOWN));
		functionTable.put(ExtrabotanyFlowerBlocks.reikarlily, b -> genCopyNbt(b, ReikarlilyBlockEntity.TAG_COOLDOWN));
		functionTable.put(ExtrabotanyFlowerBlocks.reikarlilyFloating, b -> genCopyNbt(b, ReikarlilyBlockEntity.TAG_COOLDOWN));
		functionTable.put(ExtrabotanyFlowerBlocks.bellflower, b -> genCopyNbt(b, BellflowerBlockEntity.TAG_PASSIVE_DECAY_TICKS));
		functionTable.put(ExtrabotanyFlowerBlocks.bellflowerFloating, b -> genCopyNbt(b, BellflowerBlockEntity.TAG_PASSIVE_DECAY_TICKS));
		functionTable.put(ExtrabotanyFlowerBlocks.resoncund, b -> genCopyNbt(b, ResoncundBlockEntity.TAG_SOUND_HEARD));
		functionTable.put(ExtrabotanyFlowerBlocks.resoncundFloating, b -> genCopyNbt(b, ResoncundBlockEntity.TAG_SOUND_HEARD));
		functionTable.put(ExtrabotanyFlowerBlocks.sunshineLily, b -> genCopyNbt(b, SunshineLilyBlockEntity.TAG_PASSIVE_DECAY_TICKS));
		functionTable.put(ExtrabotanyFlowerBlocks.sunshineLilyFloating, b -> genCopyNbt(b, SunshineLilyBlockEntity.TAG_PASSIVE_DECAY_TICKS));
		functionTable.put(ExtrabotanyFlowerBlocks.moonlightLily, b -> genCopyNbt(b, MoonlightLilyBlockEntity.TAG_PASSIVE_DECAY_TICKS));
		functionTable.put(ExtrabotanyFlowerBlocks.moonlightLilyFloating, b -> genCopyNbt(b, MoonlightLilyBlockEntity.TAG_PASSIVE_DECAY_TICKS));
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

		for (Block b : BuiltInRegistries.BLOCK) {
			ResourceLocation id = BuiltInRegistries.BLOCK.getKey(b);
			if (!LibMisc.MOD_ID.equals(id.getNamespace())) {
				continue;
			}
			Function<Block, LootTable.Builder> func = functionTable.getOrDefault(b, BlockLootProvider::genRegular);
			if (func != SKIP) {
				tables.put(id, func.apply(b));
			}
		}

		List<CompletableFuture<?>> output = new ArrayList<>();
		for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet()) {
			Path path = pathProvider.json(e.getKey());
			output.add(DataProvider.saveStable(cache, Deserializers.createLootTableSerializer().create().toJsonTree(e.getValue().setParamSet(LootContextParamSets.BLOCK).build()), path));
		}
		return CompletableFuture.allOf(output.toArray(CompletableFuture[]::new));
	}

	protected static LootTable.Builder genCopyNbt(Block b, String... tags) {
		LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(b);
		CopyNbtFunction.Builder func = CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY);
		for (String tag : tags) {
			func = func.copy(tag, "BlockEntityTag." + tag);
		}
		LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(entry)
				.when(ExplosionCondition.survivesExplosion())
				.apply(func);
		return LootTable.lootTable().withPool(pool);
	}

	protected static LootTable.Builder genRegular(Block b) {
		LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(b);
		LootPool.Builder pool = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(entry)
				.when(ExplosionCondition.survivesExplosion());
		return LootTable.lootTable().withPool(pool);
	}

	protected static LootTable.Builder genSlab(Block b) {
		LootPoolEntryContainer.Builder<?> entry = LootItem.lootTableItem(b)
				.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))
						.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(b).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))
				.apply(ApplyExplosionDecay.explosionDecay());
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(entry));
	}

	protected static LootTable.Builder createPotAndPlantItemTable(ItemLike plant) {
		// based on BlockLootSubProvider.createPotFlowerItemTable(ItemLike)
		final var potPool = LootPool.lootPool().add(LootItem.lootTableItem(Blocks.FLOWER_POT))
				.setRolls(ConstantValue.exactly(1.0f))
				.when(ExplosionCondition.survivesExplosion());
		final var plantPool = LootPool.lootPool().add(LootItem.lootTableItem(plant))
				.setRolls(ConstantValue.exactly(1.0f))
				.when(ExplosionCondition.survivesExplosion());
		return LootTable.lootTable().withPool(potPool).withPool(plantPool);
	}

	@NotNull
	@Override
	public String getName() {
		return "ExtraBotany block loot tables";
	}
}
