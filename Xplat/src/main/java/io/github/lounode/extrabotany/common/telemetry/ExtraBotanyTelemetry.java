package io.github.lounode.extrabotany.common.telemetry;

import com.google.gson.JsonObject;
import io.github.lounode.eventwrapper.event.server.ServerStartedEventWrapper;
import io.github.lounode.eventwrapper.event.server.ServerStoppingEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.extrabotany.common.entity.gaia.Gaia;
import io.github.lounode.extrabotany.common.entity.gaia.GaiaIII;
import io.github.lounode.extrabotany.common.entity.gaia.GaiaIIIAI;
import io.github.lounode.extrabotany.common.telemetry.charts.SimplePie;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;
import io.github.lounode.extrabotany.xplat.ExtraBotanyConfig;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Difficulty;

import java.util.UUID;

@EventBusSubscriberWrapper
public class ExtraBotanyTelemetry {
    private static ExtraBotanyTelemetry INSTANCE;
    public static ExtraBotanyTelemetry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ExtraBotanyTelemetry();
        }
        return INSTANCE;
    }
    private final int serviceId = 25178;
    private Metrics metrics;
    private final TelemetryPropertyMap deviceSessionProperties;
    private MetricsNew metricsNew;

    private ExtraBotanyTelemetry() {
        TelemetryPropertyMap.Builder builder = TelemetryPropertyMap.builder();
        builder.put(TelemetryProperty.GAME_VERSION, SharedConstants.getCurrentVersion().getId());
        builder.put(TelemetryProperty.OS_NAME, Util.getPlatform().telemetryName());
        builder.put(TelemetryProperty.OS_ARCH, System.getProperty("os.arch"));
        builder.put(TelemetryProperty.OS_VERSION, System.getProperty("os.version"));
        builder.put(TelemetryProperty.JAVA_VERSION, System.getProperty("java.version"));
        builder.put(TelemetryProperty.CORE_COUNT, Runtime.getRuntime().availableProcessors());
        builder.put(TelemetryProperty.MOD_LOADER, EXplatAbstractions.INSTANCE.getModLoader());

        builder.put(TelemetryProperty.MOD_SERVICE_ID, serviceId);
        builder.put(TelemetryProperty.MOD_VERSION, EXplatAbstractions.INSTANCE.getExtraBotanyVersion());
        this.deviceSessionProperties = builder.build();
    }

    @SubscribeEventWrapper
    public static void onServerStarted(ServerStartedEventWrapper event) {
        var server = event.getServer();
        boolean enableTelemetry = ExtraBotanyConfig.common().enableTelemetry();
        if (!enableTelemetry) {
            return;
        }
        String telemetryUUID = ExtraBotanyConfig.common().telemetryUUID();

        getInstance().metrics = new Metrics(
                "sponge",
                telemetryUUID,
                getInstance().serviceId,
                appendPlatformData(server),
                appendServiceData()
        );

        getInstance().metrics.addCustomChart(new SimplePie("modLoader", () -> EXplatAbstractions.INSTANCE.isForge() ? "forge" : "fabric"));

        //new
        var metricsNew = getInstance().metricsNew;
        if (metricsNew != null) {
            metricsNew.shutdown();
            getInstance().metricsNew = null;
        }


        getInstance().metricsNew = new MetricsNew(
                getInstance().deviceSessionProperties,
                MetricsNew.getServerUUID(server, UUID.fromString(telemetryUUID)),
                server
        );
        getInstance().metricsNew.start();
    }

    @SubscribeEventWrapper
    public static void onServerStopping(ServerStoppingEventWrapper event) {
        var metricsNew = getInstance().metricsNew;
        if (metricsNew != null) {
            metricsNew.shutdown();
            getInstance().metricsNew = null;
        }
    }

    private static JsonObject appendPlatformData(MinecraftServer server) {
        JsonObject builder = new JsonObject();
        builder.addProperty("playerAmount", server.getPlayerCount());
        builder.addProperty("onlineMode", server.usesAuthentication() ? 1 : 0);
        builder.addProperty("minecraftVersion", server.getServerVersion());

        builder.addProperty("javaVersion", System.getProperty("java.version"));
        builder.addProperty("osName", System.getProperty("os.name"));
        builder.addProperty("osArch", System.getProperty("os.arch"));
        builder.addProperty("osVersion", System.getProperty("os.version"));
        builder.addProperty("coreCount", Runtime.getRuntime().availableProcessors());

        return builder;
    }

    private static JsonObject appendServiceData() {
        JsonObject builder = new JsonObject();
        builder.addProperty("pluginVersion", EXplatAbstractions.INSTANCE.getExtraBotanyVersion());
        return builder;
    }



    public static class Event {
        public static void onGaiaBattleFinish(Gaia gaia) {
            if (gaia.getClass() != GaiaIII.class) {
                return;
            }

            String status = gaia.getHealth() <= 0 ? "success" : "failed";
            double health = gaia.getHealth();
            int playerCount = gaia.getPlayerCount();
            Difficulty difficulty = gaia.level().getDifficulty();
            int durationTicks = gaia.tickCount;

            if (playerCount < 1 || playerCount > 20) {return;}
            if (difficulty == Difficulty.PEACEFUL) {return;}
            if (durationTicks < GaiaIIIAI.EMERGE_TIME) {return;}

            JsonObject data = new JsonObject();
            data.addProperty("status", status);
            data.addProperty("healthRemain", health);
            data.addProperty("playerCount", playerCount);
            data.addProperty("difficulty", difficulty.getKey());
            data.addProperty("duration", durationTicks);

            //markUpload(data);
        }
    }


}
