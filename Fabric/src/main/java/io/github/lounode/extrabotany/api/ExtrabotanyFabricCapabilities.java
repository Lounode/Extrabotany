package io.github.lounode.extrabotany.api;

import io.github.lounode.extrabotany.api.item.NatureEnergyItem;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;

public final class ExtrabotanyFabricCapabilities {
    private ExtrabotanyFabricCapabilities() {}

    public static final ItemApiLookup<NatureEnergyItem, Unit> NATURE_ENERGY_ITEM = ItemApiLookup.get(new ResourceLocation("extrabotany", "nature_energy_item"), NatureEnergyItem.class, Unit.class);
}
