package io.github.lounode.extrabotany.api.block;

import io.github.lounode.extrabotany.xplat.EXplatAbstractions;
import net.minecraft.world.item.ItemStack;

public interface Charger {
    ItemStack getItem();
    void setItem(ItemStack stack);
    float getChargeProcess();

    default boolean isValidItem(ItemStack stack) {
        return EXplatAbstractions.INSTANCE.findManaItem(stack) != null;
    }
}
