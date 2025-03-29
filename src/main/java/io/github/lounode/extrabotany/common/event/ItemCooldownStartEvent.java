package io.github.lounode.extrabotany.common.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.Event;

public class ItemCooldownStartEvent extends Event {
    private final ServerPlayer player;
    private final Item item;
    private final int ticks;

    public ItemCooldownStartEvent(ServerPlayer player, Item item, int ticks) {
        super();
        this.player = player;
        this.item = item;
        this.ticks = ticks;
    }

    public Item getItem() {
        return item;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public int getTicks() {
        return ticks;
    }
}
