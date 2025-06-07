package io.github.lounode.extrabotany.fabric.xplat;

import com.mojang.authlib.GameProfile;

import net.fabricmc.fabric.api.entity.FakePlayer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.fabric.xplat.FabricXplatImpl;

import io.github.lounode.extrabotany.api.ExtrabotanyFabricCapabilities;
import io.github.lounode.extrabotany.api.item.NatureEnergyItem;
import io.github.lounode.extrabotany.common.lib.LibMisc;
import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;

public class ExFabricXplatImpl extends FabricXplatImpl implements EXplatAbstractions {
	@Override
	public void sendToPlayer(ServerPlayer player, ExtrabotanyPacket packet) {
		ServerPlayNetworking.send(player, packet.getFabricId(), packet.toBuf());
	}

	@Override
	public Packet<ClientGamePacketListener> toVanillaClientboundPacket(ExtrabotanyPacket packet) {
		return ServerPlayNetworking.createS2CPacket(packet.getFabricId(), packet.toBuf());
	}

	@Nullable
	@Override
	public NatureEnergyItem findNatureEnergyItem(ItemStack stack) {
		return ExtrabotanyFabricCapabilities.NATURE_ENERGY_ITEM.find(stack, Unit.INSTANCE);
	}

	@Override
	public String getExtraBotanyVersion() {
		return FabricLoader.getInstance().getModContainer(LibMisc.MOD_ID).get().getMetadata().getVersion().getFriendlyString();
	}

	@Override
	public Player createFakePlayer(ServerLevel level, GameProfile userName) {
		return FakePlayer.get(level, userName);
	}
}
