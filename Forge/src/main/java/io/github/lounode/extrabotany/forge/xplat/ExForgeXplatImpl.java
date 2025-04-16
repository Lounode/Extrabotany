package io.github.lounode.extrabotany.forge.xplat;

import io.github.lounode.extrabotany.forge.network.ForgePacketHandler;
import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import vazkii.botania.forge.xplat.ForgeXplatImpl;

public class ExForgeXplatImpl extends ForgeXplatImpl implements EXplatAbstractions {
    @Override
    public void sendToPlayer(ServerPlayer player, ExtrabotanyPacket packet) {
        ForgePacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

}
