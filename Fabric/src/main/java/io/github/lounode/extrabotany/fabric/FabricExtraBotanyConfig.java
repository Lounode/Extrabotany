package io.github.lounode.extrabotany.fabric;

import io.github.fablabsmc.fablabs.api.fiber.v1.builder.ConfigTreeBuilder;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.ValueDeserializationException;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;
import io.github.lounode.extrabotany.api.ExtraBotanyAPI;
import io.github.lounode.extrabotany.common.lib.LibMisc;
import io.github.lounode.extrabotany.xplat.ExtraBotanyConfig;
import vazkii.botania.xplat.XplatAbstractions;

import java.io.*;
import java.nio.file.*;

import static io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes.BOOLEAN;

public class FabricExtraBotanyConfig {
    private static final Client CLIENT = new Client();
    private static final COMMON COMMON = new COMMON();

    public static void setup() {
        try {
            Files.createDirectory(Paths.get("config"));
        } catch (FileAlreadyExistsException ignored) {} catch (IOException e) {
            ExtraBotanyAPI.LOGGER.warn("Failed to make config dir", e);
        }

        JanksonValueSerializer serializer = new JanksonValueSerializer(false);
        ConfigTree common = COMMON.configure(ConfigTree.builder());
        setupConfig(common, Paths.get("config", LibMisc.MOD_ID + "-common.json5"), serializer);
        ExtraBotanyConfig.setCommon(COMMON);

        if (XplatAbstractions.INSTANCE.isPhysicalClient()) {
            ConfigTree client = CLIENT.configure(ConfigTree.builder());
            setupConfig(client, Paths.get("config", LibMisc.MOD_ID + "-client.json5"), serializer);
            ExtraBotanyConfig.setClient(CLIENT);
        }
        ExtraBotanyConfig.resetPatchouliFlags();
    }

    private static class Client implements ExtraBotanyConfig.ClientConfigAccess {
        public final PropertyMirror<Boolean> testClientConfig = PropertyMirror.create(BOOLEAN);

        public ConfigTree configure(ConfigTreeBuilder builder) {
            builder.fork("client")
                    .beginValue("testClientConfig", BOOLEAN, true)
                    .withComment("Test Fabric Client Config")
                    .finishValue(testClientConfig::mirror)
                    .finishBranch();
            return builder.build();
        }
        @Override
        public boolean testClientConfig() {
            return testClientConfig.getValue();
        }
    }


    private static class COMMON implements ExtraBotanyConfig.ConfigAccess {
        public final PropertyMirror<Boolean> testServerConfig = PropertyMirror.create(BOOLEAN);

        public ConfigTree configure(ConfigTreeBuilder builder) {
            builder.fork("server")
                    .beginValue("testServerConfig", BOOLEAN, true)
                    .withComment("Test Fabric Server Config")
                    .finishValue(testServerConfig::mirror)
                    .finishBranch();
            return builder.build();
        }

        @Override
        public boolean testServerConfig() {
            return testServerConfig.getValue();
        }
    }

    private static void writeDefaultConfig(ConfigTree config, Path path, JanksonValueSerializer serializer) {
        try (OutputStream s = new BufferedOutputStream(Files.newOutputStream(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW))) {
            FiberSerialization.serialize(config, s, serializer);
        } catch (FileAlreadyExistsException ignored) {} catch (IOException e) {
            ExtraBotanyAPI.LOGGER.error("Error writing default config", e);
        }
    }

    private static void setupConfig(ConfigTree config, Path p, JanksonValueSerializer serializer) {
        writeDefaultConfig(config, p, serializer);

        try (InputStream s = new BufferedInputStream(Files.newInputStream(p, StandardOpenOption.READ, StandardOpenOption.CREATE))) {
            FiberSerialization.deserialize(config, s, serializer);
        } catch (IOException | ValueDeserializationException e) {
            ExtraBotanyAPI.LOGGER.error("Error loading config from {}", p, e);
        }
    }
}
