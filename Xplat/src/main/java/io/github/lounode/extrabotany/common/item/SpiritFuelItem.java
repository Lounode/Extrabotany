package io.github.lounode.extrabotany.common.item;

import net.minecraft.world.item.Item;


import io.github.lounode.eventwrapper.event.furnace.FurnaceFuelBurnTimeEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;

@EventBusSubscriberWrapper
public class SpiritFuelItem extends Item {
	public SpiritFuelItem(Properties properties) {
		super(properties);
	}

	@SubscribeEventWrapper
	public static void makeFuel(FurnaceFuelBurnTimeEventWrapper wrapper) {
		if (wrapper.getItemStack().is(ExtraBotanyItems.spiritFuel)) {
			wrapper.setBurnTime(12800);
		}
	}
}
