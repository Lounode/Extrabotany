package io.github.lounode.extrabotany.fabric.events;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class ItemCooldownEvents {

    public static final Event<ItemCooldownEvents.Start> START = EventFactory.createArrayBacked(ItemCooldownEvents.Start.class, (callbacks) -> (player, item, ticks) -> {
        for(ItemCooldownEvents.Start callback : callbacks) {
            if (!callback.cooldownStart(player, item, ticks)) {
                return false;
            }
        }

        return true;
    });

    public static final Event<ItemCooldownEvents.Finish> FINISH = EventFactory.createArrayBacked(ItemCooldownEvents.Finish.class, (callbacks) -> (player, item) -> {
        for(ItemCooldownEvents.Finish callback : callbacks) {
            if (!callback.cooldownFinish(player, item)) {
                return false;
            }
        }

        return true;
    });

    @FunctionalInterface
    public interface Start {
        boolean cooldownStart(ServerPlayer player, Item item, int ticks);
    }

    @FunctionalInterface
    public interface Finish {
        boolean cooldownFinish(ServerPlayer player, Item item);
    }
}
