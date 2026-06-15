package io.github.lounode.extrabotany.xplat;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.ServiceUtil;
import vazkii.botania.api.item.Relic;
import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.xplat.XplatAbstractions;

import io.github.lounode.extrabotany.api.item.NatureEnergyItem;
import io.github.lounode.extrabotany.network.ExtrabotanyPacket;

public interface EXplatAbstractions extends XplatAbstractions {
	EXplatAbstractions INSTANCE = ServiceUtil.findService(EXplatAbstractions.class, null);

	void sendToPlayer(ServerPlayer player, ExtrabotanyPacket packet);
	Packet<ClientGamePacketListener> toVanillaClientboundPacket(ExtrabotanyPacket packet);

	@Nullable
	NatureEnergyItem findNatureEnergyItem(ItemStack stack);
	String getExtraBotanyVersion();

	@Nullable
	default ManaItem findManaItem(ItemStack stack) {
		return findItemApi(ManaItem.LOOKUP, stack);
	}

	@Nullable
	default Relic findRelic(ItemStack stack) {
		return findItemApi(Relic.LOOKUP, stack);
	}

	default ModLoaderType getModLoader() {
		try {
			Class.forName("net.minecraftforge.fml.loading.FMLLoader");
			return ModLoaderType.FORGE;
		} catch (ClassNotFoundException ignored) {}

		try {
			Class.forName("net.neoforged.fml.loading.FMLLoader");
			return ModLoaderType.NEOFORGE;
		} catch (ClassNotFoundException ignored) {}

		try {
			Class.forName("net.fabricmc.loader.api.FabricLoader");
			return ModLoaderType.FABRIC;
		} catch (ClassNotFoundException ignored) {}

		try {
			Class.forName("org.quiltmc.loader.api.QuiltLoader");
			return ModLoaderType.QUILT;
		} catch (ClassNotFoundException ignored) {}

		return ModLoaderType.UNKNOWN;
	}

	Player createFakePlayer(ServerLevel level, GameProfile userName);

	default int getFluidTemperature(Fluid fluid) {
		return 0;
	}

	default float getEnchantPowerBonus(ServerLevel level, BlockPos pos) {
		return 0;
	}

	enum ModLoaderType implements StringRepresentable {
		FORGE("forge"),
		FABRIC("fabric"),
		NEOFORGE("neoforge"),
		QUILT("quilt"),
		UNKNOWN("unknown");

		public static final Codec<ModLoaderType> CODEC = StringRepresentable.fromEnum(ModLoaderType::values);
		private final String key;

		ModLoaderType(String key) {
			this.key = key;
		}

		@Override
		public String getSerializedName() {
			return this.key;
		}
	}
}
