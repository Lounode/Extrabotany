package io.github.lounode.extrabotany.data.loot;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import vazkii.botania.common.item.BotaniaItems;


import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.lib.LibMisc;

public class RewardBagLootProvider extends RewardBagLootSubProvider {
	@Override
	public void generate() {
		this.add("eins", petalPoll());
		this.add("zwei", runeTable());
		this.add("drei", usefulTable());
		this.add("vier", miscTable());
		this.add("nine_and_three_quarters", harryPotterTable());
		this.add("pandoras_box", pandoraTable());
		this.add("limited_edition_supply_bag", LootTable.lootTable());
	}

	@Override
	public String getNameSpace() {
		return LibMisc.MOD_ID;
	}

	public static LootTable.Builder pandoraTable() {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(ExtraBotanyItems.einsRewardBag)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(32)))))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(ExtraBotanyItems.zweiRewardBag)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(16)))))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(ExtraBotanyItems.dreiRewardBag)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(10)))))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(ExtraBotanyItems.vierRewardBag)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(10)))))
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(ExtraBotanyItems.heroMedal)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))));
	}

	public static LootTable.Builder harryPotterTable() {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(ExtraBotanyItems.heroMedal)
								.setWeight(1)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(ExtraBotanyItems.einsRewardBag)
								.setWeight(30)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(16))))
						.add(LootItem.lootTableItem(ExtraBotanyItems.zweiRewardBag)
								.setWeight(20)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(10))))
						.add(LootItem.lootTableItem(ExtraBotanyItems.dreiRewardBag)
								.setWeight(10)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(6))))
						.add(LootItem.lootTableItem(ExtraBotanyItems.vierRewardBag)
								.setWeight(10)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(6))))
						.add(LootItem.lootTableItem(BotaniaItems.gaiaIngot)
								.setWeight(14)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.lifeEssence)
								.setWeight(20)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
						.add(LootItem.lootTableItem(ExtraBotanyItems.challengeTicket)
								.setWeight(45)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
				);
	}

	public static LootTable.Builder usefulTable() {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(BotaniaItems.manaSteel)
								.setWeight(15)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
						.add(LootItem.lootTableItem(BotaniaItems.manaPearl)
								.setWeight(15)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
						.add(LootItem.lootTableItem(BotaniaItems.manaDiamond)
								.setWeight(15)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
						.add(LootItem.lootTableItem(BotaniaItems.elementium)
								.setWeight(11)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))))
						.add(LootItem.lootTableItem(BotaniaItems.pixieDust)
								.setWeight(11)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))))
						.add(LootItem.lootTableItem(BotaniaItems.dragonstone)
								.setWeight(11)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(3))))
						.add(LootItem.lootTableItem(BotaniaItems.manaPowder)
								.setWeight(10)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(8))))
						.add(LootItem.lootTableItem(BotaniaItems.terrasteel)
								.setWeight(9)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.lifeEssence)
								.setWeight(8)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))))
						.add(LootItem.lootTableItem(BotaniaItems.gaiaIngot)
								.setWeight(7)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(ExtraBotanyItems.heroMedal)
								.setWeight(1)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
				);
	}

	public static LootTable.Builder miscTable() {
		LootPool.Builder pool = LootPool.lootPool()
				.add(LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction.setCount(ConstantValue.exactly(6))).setWeight(40))
				.add(LootItem.lootTableItem(Items.IRON_INGOT).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))).setWeight(36))
				.add(LootItem.lootTableItem(Items.GOLD_INGOT).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))).setWeight(24))
				.add(LootItem.lootTableItem(Items.REDSTONE).apply(SetItemCountFunction.setCount(ConstantValue.exactly(8))).setWeight(22))
				.add(LootItem.lootTableItem(Items.ENDER_PEARL).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4))).setWeight(20))
				.add(LootItem.lootTableItem(Items.DIAMOND).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))).setWeight(18))
				.add(LootItem.lootTableItem(BotaniaItems.blackerLotus).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))).setWeight(16))
				.add(LootItem.lootTableItem(BotaniaItems.overgrowthSeed).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))).setWeight(12))
				.add(LootItem.lootTableItem(ExtraBotanyItems.voidArchives).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))).setWeight(1))
				.setRolls(ConstantValue.exactly(1));

		return LootTable.lootTable().withPool(pool);
	}

	public static LootTable.Builder runeTable() {
		return LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(ConstantValue.exactly(1))
						.add(LootItem.lootTableItem(BotaniaItems.runeWater)
								.setWeight(50)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))))
						.add(LootItem.lootTableItem(BotaniaItems.runeFire)
								.setWeight(50)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))))
						.add(LootItem.lootTableItem(BotaniaItems.runeEarth)
								.setWeight(50)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))))
						.add(LootItem.lootTableItem(BotaniaItems.runeAir)
								.setWeight(50)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))))
						.add(LootItem.lootTableItem(BotaniaItems.runeSpring)
								.setWeight(30)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.runeSummer)
								.setWeight(30)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.runeAutumn)
								.setWeight(30)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.runeWinter)
								.setWeight(30)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.runeMana)
								.setWeight(10)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.runeLust)
								.setWeight(10)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.runeGluttony)
								.setWeight(10)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.runeGreed)
								.setWeight(10)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.runeSloth)
								.setWeight(10)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.runeWrath)
								.setWeight(10)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.runeEnvy)
								.setWeight(10)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
						.add(LootItem.lootTableItem(BotaniaItems.runePride)
								.setWeight(10)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1))))
				);
	}

	public static LootTable.Builder petalPoll() {
		Item[] petals = new Item[] {
				BotaniaItems.whitePetal,
				BotaniaItems.orangePetal,
				BotaniaItems.magentaPetal,
				BotaniaItems.lightBluePetal,
				BotaniaItems.yellowPetal,
				BotaniaItems.limePetal,
				BotaniaItems.pinkPetal,
				BotaniaItems.grayPetal,
				BotaniaItems.lightGrayPetal,
				BotaniaItems.cyanPetal,
				BotaniaItems.purplePetal,
				BotaniaItems.bluePetal,
				BotaniaItems.brownPetal,
				BotaniaItems.greenPetal,
				BotaniaItems.redPetal,
				BotaniaItems.blackPetal
		};
		LootTable.Builder builder = LootTable.lootTable();

		LootPool.Builder pool = LootPool.lootPool();

		for (var petal : petals) {
			pool.add(LootItem.lootTableItem(petal));
		}

		pool.apply(SetItemCountFunction.setCount(ConstantValue.exactly(6)));
		pool.setRolls(ConstantValue.exactly(1));

		builder.withPool(pool);

		return builder;
	}
}
