package io.github.lounode.extrabotany.fabric.xplat;

import io.github.lounode.extrabotany.api.ExtrabotanyFabricCapabilities;
import io.github.lounode.extrabotany.api.item.NatureEnergyItem;
import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.fabric.xplat.FabricXplatImpl;

public class ExFabricXplatImpl extends FabricXplatImpl implements EXplatAbstractions {
    @Override
    public void sendToPlayer(ServerPlayer player, ExtrabotanyPacket packet) {
        ServerPlayNetworking.send(player, packet.getFabricId(), packet.toBuf());
    }

    @Override
    public Packet<ClientGamePacketListener> toVanillaClientboundPacket(ExtrabotanyPacket packet) {
        return ServerPlayNetworking.createS2CPacket(packet.getFabricId(), packet.toBuf());
    }

    @Override
    public NatureEnergyItem findNatureEnergyItem(ItemStack stack) {
        return ExtrabotanyFabricCapabilities.NATURE_ENERGY_ITEM.find(stack, Unit.INSTANCE);
    }
}
