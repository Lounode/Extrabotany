package io.github.lounode.extrabotany.common.event.entity.player;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class ItemCooldownFinishEventWrapper {
	private final Player entity;
	private final Item item;

	public ItemCooldownFinishEventWrapper(Player entity, Item item) {
		this.entity = entity;
		this.item = item;
	}

	public Player getEntity() {
		return entity;
	}

	public Item getItem() {
		return item;
	}
}
