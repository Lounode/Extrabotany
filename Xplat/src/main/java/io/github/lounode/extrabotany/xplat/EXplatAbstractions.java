package io.github.lounode.extrabotany.xplat;

import io.github.lounode.extrabotany.api.item.NatureEnergyItem;
import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.api.ServiceUtil;
import vazkii.botania.xplat.XplatAbstractions;

public interface EXplatAbstractions extends XplatAbstractions {
    EXplatAbstractions INSTANCE = ServiceUtil.findService(EXplatAbstractions.class, null);

    void sendToPlayer(ServerPlayer player, ExtrabotanyPacket packet);
    Packet<ClientGamePacketListener> toVanillaClientboundPacket(ExtrabotanyPacket packet);

    NatureEnergyItem findNatureEnergyItem(ItemStack stack);

}
