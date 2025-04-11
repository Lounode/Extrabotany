package io.github.lounode.extrabotany.common.event.entity.player;

import io.github.lounode.extrabotany.common.event.entity.living.LivingEntityEventWrapper;
import net.minecraft.world.entity.player.Player;

public class PlayerEventWrapper extends LivingEntityEventWrapper {
    private final Player player;

    public PlayerEventWrapper(Player player)
    {
        super(player);
        this.player = player;
    }

    @Override
    public Player getEntity()
    {
        return player;
    }

    public Player getPlayer() { return getEntity();}
}
