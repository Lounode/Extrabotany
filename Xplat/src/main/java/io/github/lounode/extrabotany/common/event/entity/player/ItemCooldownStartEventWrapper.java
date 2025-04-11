package io.github.lounode.extrabotany.common.event.entity.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public class ItemCooldownStartEventWrapper extends PlayerEventWrapper {
    private final Item item;
    private int ticks;

    public ItemCooldownStartEventWrapper(ServerPlayer player, Item item, int ticks) {
        super(player);
        this.item = item;
        this.ticks = ticks;
    }

    public Item getItem() {
        return item;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
}
