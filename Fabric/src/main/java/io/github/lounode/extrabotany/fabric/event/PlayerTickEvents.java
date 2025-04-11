package io.github.lounode.extrabotany.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

public class PlayerTickEvents {
    public static final Event<PlayerTickEvents.TickStart> START = EventFactory.createArrayBacked(PlayerTickEvents.TickStart.class, (callbacks) -> (player) -> {
        for(PlayerTickEvents.TickStart callback : callbacks) {
            if (!callback.tickStart(player)) {
                return false;
            }
        }

        return true;
    });

    public static final Event<PlayerTickEvents.TickEnd> END = EventFactory.createArrayBacked(PlayerTickEvents.TickEnd.class, (callbacks) -> (player) -> {
        for(PlayerTickEvents.TickEnd callback : callbacks) {
            if (!callback.tickEnd(player)) {
                return false;
            }
        }

        return true;
    });

    @FunctionalInterface
    public interface TickStart {
        boolean tickStart(Player player);
    }

    @FunctionalInterface
    public interface TickEnd {
        boolean tickEnd(Player player);
    }
}
