package io.github.lounode.extrabotany.common.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.EventBus;
import net.minecraftforge.eventbus.api.Event;

public class ItemCooldownFinishEvent extends Event {
    private final ServerPlayer player;

    private final Item item;

    public ItemCooldownFinishEvent(ServerPlayer player, Item item) {
        super();
        this.player = player;
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

}
