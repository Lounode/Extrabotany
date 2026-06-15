package io.github.lounode.extrabotany.common.event.furnace;

import net.minecraft.world.item.ItemStack;

public class FurnaceFuelBurnTimeEventWrapper {
	private final ItemStack itemStack;
	private int burnTime;
	private boolean modified;

	public FurnaceFuelBurnTimeEventWrapper(ItemStack itemStack, int burnTime) {
		this.itemStack = itemStack;
		this.burnTime = burnTime;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public int getBurnTime() {
		return burnTime;
	}

	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
		this.modified = true;
	}

	public boolean isModified() {
		return modified;
	}
}
