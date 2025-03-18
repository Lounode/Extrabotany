package io.github.lounode.extrabotany.xplat;

import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import io.github.lounode.extrabotany.network.PacketHandler;
import io.github.lounode.extrabotany.network.clientbound.ManaReaderPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import vazkii.botania.forge.xplat.ForgeXplatImpl;

public class ExForgeXplatImpl extends ForgeXplatImpl implements EXplatAbstractions {
    @Override
    public void sendToPlayer(Player player, ExtrabotanyPacket packet) {
        if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), packet);
        }
    }
}
