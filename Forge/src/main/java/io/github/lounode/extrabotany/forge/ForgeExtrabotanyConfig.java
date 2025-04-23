package io.github.lounode.extrabotany.forge;

import io.github.lounode.extrabotany.common.lib.LibMisc;
import io.github.lounode.extrabotany.xplat.ExtraBotanyConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;
import vazkii.botania.xplat.XplatAbstractions;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeExtrabotanyConfig
{
    private static class Client implements ExtraBotanyConfig.ClientConfigAccess {
        public final ForgeConfigSpec.BooleanValue testClientConfig;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("test");

            testClientConfig = builder
                    .comment("This is a test for client config")
                    .define("testClientConfig", true);

            builder.pop();
        }
        @Override
        public boolean testClientConfig() {
            return testClientConfig.get();
        }
    }

    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    private static class Common implements ExtraBotanyConfig.ConfigAccess {
        public final ForgeConfigSpec.BooleanValue testServerConfig;
        public final ForgeConfigSpec.BooleanValue disableGaiaDisArm;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("test");

            testServerConfig = builder
                    .comment("This is a test for server config")
                    .define("testServerConfig", true);
            disableGaiaDisArm = builder
                    .comment("Set true to disable Gaia's disarm")
                    .define("disableGaiaDisarm", false);

            builder.pop();
        }

        @Override
        public boolean testServerConfig() {
            return testServerConfig.get();
        }

        @Override
        public boolean disableGaiaDisArm() {
            return disableGaiaDisArm.get();
        }
    }

    private static final Common COMMON;
    private static final ForgeConfigSpec COMMON_SPEC;
    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }

    public static void setup() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        ExtraBotanyConfig.setCommon(COMMON);

        if (XplatAbstractions.INSTANCE.isPhysicalClient()) {
            ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
            ExtraBotanyConfig.setClient(CLIENT);
        }
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading evt) {
        var config = evt.getConfig();
        if (config.getType() == ModConfig.Type.COMMON && config.getModId().equals(LibMisc.MOD_ID)) {
            ExtraBotanyConfig.resetPatchouliFlags();
        }
    }

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Reloading evt) {
        var config = evt.getConfig();
        if (config.getType() == ModConfig.Type.COMMON && config.getModId().equals(LibMisc.MOD_ID)) {
            ExtraBotanyConfig.resetPatchouliFlags();
        }
    }
}
