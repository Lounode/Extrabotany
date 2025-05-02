package io.github.lounode.extrabotany.xplat;

import com.mojang.serialization.Codec;
import io.github.lounode.extrabotany.api.item.NatureEnergyItem;
import io.github.lounode.extrabotany.common.telemetry.TelemetryProperty;
import io.github.lounode.extrabotany.network.ExtrabotanyPacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.api.ServiceUtil;
import vazkii.botania.xplat.XplatAbstractions;

public interface EXplatAbstractions extends XplatAbstractions {
    EXplatAbstractions INSTANCE = ServiceUtil.findService(EXplatAbstractions.class, null);

    void sendToPlayer(ServerPlayer player, ExtrabotanyPacket packet);
    Packet<ClientGamePacketListener> toVanillaClientboundPacket(ExtrabotanyPacket packet);

    NatureEnergyItem findNatureEnergyItem(ItemStack stack);
    String getExtraBotanyVersion();

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

        public String getSerializedName() {
            return this.key;
        }
    }
}
