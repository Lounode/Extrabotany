package io.github.lounode.extrabotany.common.item.material;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import vazkii.botania.common.item.BotaniaItems;

import java.util.function.Supplier;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

public enum HammerTiers implements Tier {
	MANASTEEL(300, 6.2F, 2, 3, 20, () -> BotaniaItems.manaSteel),
	ELEMENTIUM(720, 6.2F, 2, 3, 20, () -> BotaniaItems.elementium),
	TERRASTEEL(2300, 9, 4, 4, 26, () -> BotaniaItems.terrasteel),

	GAIA(3600, 10, 5, 5, 28, () -> BotaniaItems.gaiaIngot),
	PHOTONIUM(900, 6.2F, 2, 3, 20, () -> ExtraBotanyItems.photonium),
	SHADOWIUM(900, 6.2F, 2, 3, 20, () -> ExtraBotanyItems.shadowium),
	AERIALITE(2300, 9, 4, 4, 26, () -> ExtraBotanyItems.aerialite),
	ORICHALCOS(4200, 10, 6, 6, 30, () -> ExtraBotanyItems.orichalcos),
	RHEIN(5000, 12, 10, 10, 50, () -> ExtraBotanyItems.dasRheingold);

	private final int maxUses;
	private final float efficiency;
	private final float attackDamage;
	private final int harvestLevel;
	private final int enchantability;
	private final Supplier<Item> repairItem;

	HammerTiers(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Item> repairItem) {
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.attackDamage = attackDamage;
		this.harvestLevel = harvestLevel;
		this.enchantability = enchantability;
		this.repairItem = repairItem;
	}

	@Override
	public int getUses() {
		return maxUses;
	}

	@Override
	public float getSpeed() {
		return efficiency;
	}

	@Override
	public float getAttackDamageBonus() {
		return attackDamage;
	}

	@Override
	public int getLevel() {
		return harvestLevel;
	}

	@Override
	public int getEnchantmentValue() {
		return enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.of(repairItem.get());
	}
}
