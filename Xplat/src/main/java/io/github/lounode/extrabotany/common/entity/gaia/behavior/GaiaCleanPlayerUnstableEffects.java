package io.github.lounode.extrabotany.common.entity.gaia.behavior;

import com.google.common.collect.ImmutableMap;
import io.github.lounode.extrabotany.common.entity.gaia.Gaia;
import net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GaiaCleanPlayerUnstableEffects <E extends Gaia> extends Behavior<E> {

    public GaiaCleanPlayerUnstableEffects() {
        super(ImmutableMap.of(
                MemoryModuleType.NEAREST_PLAYERS, MemoryStatus.VALUE_PRESENT
        ));
    }

    @Override
    protected boolean canStillUse(ServerLevel level, E entity, long gameTime) {
        return true;
    }

    @Override
    protected void tick(ServerLevel level, E gaia, long gameTime) {
        List<Player> players = getPlayers(gaia);
        for (Player player : players) {
            clearUnstablePotions(player);
        }
    }

    protected void clearUnstablePotions(Player player) {
        Set<MobEffect> effectsToRemove = new HashSet<>();
        for (var effectInstance : player.getActiveEffects()) {
            if (effectInstance.getDuration() < 160 && effectInstance.isAmbient() && effectInstance.getEffect().getCategory() != MobEffectCategory.HARMFUL) {
                effectsToRemove.add(effectInstance.getEffect());
            }
        }

        for (var effect : effectsToRemove) {
            player.removeEffect(effect);
            ((ServerLevel) player.level()).getChunkSource().broadcastAndSend(player,
                    new ClientboundRemoveMobEffectPacket(player.getId(), effect));
        }
    }

    protected List<Player> getPlayers(Gaia gaia) {
        return gaia.getBrain().getMemory(MemoryModuleType.NEAREST_PLAYERS).orElse(new ArrayList<>());
    }
}
