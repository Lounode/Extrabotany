package io.github.lounode.extrabotany.common.item;

import net.minecraft.world.item.Item;

import io.github.lounode.extrabotany.common.event.furnace.FurnaceFuelBurnTimeEventWrapper;

public class SpiritFuelItem extends Item {
	public SpiritFuelItem(Properties properties) {
		super(properties);
	}

	public static void makeFuel(FurnaceFuelBurnTimeEventWrapper wrapper) {
		if (wrapper.getItemStack().is(ExtraBotanyItems.spiritFuel)) {
			wrapper.setBurnTime(12800);
		}
	}
}
