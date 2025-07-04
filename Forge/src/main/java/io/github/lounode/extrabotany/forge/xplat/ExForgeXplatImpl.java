package io.github.lounode.extrabotany.forge.xplat;

import com.mojang.authlib.GameProfile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;

import vazkii.botania.forge.xplat.ForgeXplatImpl;

import io.github.lounode.extrabotany.api.ExtrabotanyForgeCapabilities;
import io.github.lounode.extrabotany.api.item.NatureEnergyItem;
import io.github.lounode.extrabotany.common.lib.LibMisc;
import io.github.lounode.extrabotany.forge.network.ForgePacketHandler;
import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;

import javax.annotation.Nullable;

public class ExForgeXplatImpl extends ForgeXplatImpl implements EXplatAbstractions {
	@Override
	public void sendToPlayer(ServerPlayer player, ExtrabotanyPacket packet) {
		ForgePacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Packet<ClientGamePacketListener> toVanillaClientboundPacket(ExtrabotanyPacket packet) {
		return (Packet<ClientGamePacketListener>) ForgePacketHandler.CHANNEL.toVanillaPacket(packet, NetworkDirection.PLAY_TO_CLIENT);
	}

	@Nullable
	@Override
	public NatureEnergyItem findNatureEnergyItem(ItemStack stack) {
		return stack.getCapability(ExtrabotanyForgeCapabilities.NATURE_ENERGY_ITEM).orElse(null);
	}

	@Override
	public String getExtraBotanyVersion() {
		return ModList.get().getModContainerById(LibMisc.MOD_ID).get().getModInfo().getVersion().toString();
	}

	@Override
	public Player createFakePlayer(ServerLevel level, GameProfile userName) {
		return FakePlayerFactory.get(level, userName);
	}

	@Override
	public int getFluidTemperature(Fluid fluid) {
		return fluid.getFluidType().getTemperature();
	}

	@Override
	public float getEnchantPowerBonus(ServerLevel level, BlockPos pos) {
		float j = 0;
		for (BlockPos blockpos : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
			if (EnchantmentTableBlock.isValidBookShelf(level, pos, blockpos)) {
				j += level.getBlockState(pos.offset(blockpos)).getEnchantPowerBonus(level, pos.offset(blockpos));
			}
		}
		return j;
	}
}
