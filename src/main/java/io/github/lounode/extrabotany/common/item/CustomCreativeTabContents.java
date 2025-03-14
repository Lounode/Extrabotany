package io.github.lounode.extrabotany.common.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public interface CustomCreativeTabContents {
    void addToCreativeTab(Item me, CreativeModeTab.Output output);
}
