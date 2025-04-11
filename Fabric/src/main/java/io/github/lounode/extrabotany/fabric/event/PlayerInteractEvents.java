package io.github.lounode.extrabotany.fabric.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

public class PlayerInteractEvents {
    public static final Event<PlayerInteractEvents.LeftClickEmpty> LEFT_CLICK = EventFactory.createArrayBacked(PlayerInteractEvents.LeftClickEmpty.class, (callbacks) -> (player) -> {
        for(var callback : callbacks) {
            if (!callback.leftClickEmpty(player)) {
                return false;
            }
        }

        return true;
    });

    @FunctionalInterface
    public interface LeftClickEmpty {
        boolean leftClickEmpty(Player player);
    }
}
