package io.github.lounode.extrabotany.common.event.entity.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class ItemCooldownFinishEventWrapper extends PlayerEventWrapper {
    private final Item item;

    public ItemCooldownFinishEventWrapper(ServerPlayer player, Item item) {
        super(player);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
