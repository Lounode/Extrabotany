package io.github.lounode.extrabotany.xplat;


import vazkii.botania.api.BotaniaAPI;

public class ExtraBotanyConfig {
    private static ConfigAccess config = null;
    private static ClientConfigAccess clientConfig = null;

    public static ConfigAccess common() {
        return config;
    }

    public static ClientConfigAccess client() {
        return clientConfig;
    }

    public static void setCommon(ConfigAccess access) {
        if (config != null) {
            BotaniaAPI.LOGGER.warn("ConfigAccess was replaced! Old {} New {}", config.getClass().getName(), access.getClass().getName());
        }

        config = access;
    }

    public static void setClient(ClientConfigAccess access) {
        if (clientConfig != null) {
            BotaniaAPI.LOGGER.warn("ClientConfigAccess was replaced! Old {} New {}", clientConfig.getClass().getName(), access.getClass().getName());
        }

        clientConfig = access;
    }

    public static void resetPatchouliFlags() {

    }

    public interface ClientConfigAccess {
        boolean testClientConfig();
    }

    public interface ConfigAccess {
        boolean disableGaiaDisArm();
        boolean enableTelemetry();
        String telemetryUUID();
    }
}
