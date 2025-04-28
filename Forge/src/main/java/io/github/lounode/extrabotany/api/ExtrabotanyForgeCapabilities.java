package io.github.lounode.extrabotany.api;

import io.github.lounode.extrabotany.api.item.NatureEnergyItem;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public final class ExtrabotanyForgeCapabilities {
    private ExtrabotanyForgeCapabilities() {}

    public static final Capability<NatureEnergyItem> NATURE_ENERGY_ITEM = CapabilityManager.get(new CapabilityToken<NatureEnergyItem>() {});
}
