package io.github.lounode.extrabotany.common.item.enchantment;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public interface ICustomEnchantable {
    boolean canEnchant(ItemStack stack, Enchantment enchantment);
    boolean canEnchantOnTable(ItemStack stack, Enchantment enchantment);
}
