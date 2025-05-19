package io.github.lounode.extrabotany.api;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;


import io.github.lounode.extrabotany.api.item.NatureEnergyItem;

public final class ExtrabotanyForgeCapabilities {
	private ExtrabotanyForgeCapabilities() {}

	public static final Capability<NatureEnergyItem> NATURE_ENERGY_ITEM = CapabilityManager.get(new CapabilityToken<NatureEnergyItem>() {});
}
