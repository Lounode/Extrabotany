package io.github.lounode.extrabotany.common.item.material;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import vazkii.botania.common.item.BotaniaItems;

import java.util.function.Supplier;

public enum ItemTiers implements Tier {
    EXCALIBUR(2031, 9.0F, 4, 4, 30, () -> ExtraBotanyItems.dasRheingold);

    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int harvestLevel;
    private final int enchantability;
    private final Supplier<Item> repairItem;

    ItemTiers(int maxUses, float efficiency, float attackDamage, int harvestLevel, int enchantability, Supplier<Item> repairItem) {
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
