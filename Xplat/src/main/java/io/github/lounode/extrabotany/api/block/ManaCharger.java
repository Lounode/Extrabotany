package io.github.lounode.extrabotany.api.block;

import net.minecraft.world.item.ItemStack;

public interface ManaCharger {
    boolean addItem(ItemStack stack);
    ItemStack removeItem();
    float getChargeProcess();
}
