package io.github.lounode.extrabotany.fabric;

import vazkii.botania.xplat.XplatAbstractions;

import io.github.lounode.extrabotany.api.ExtraBotanyAPI;
import io.github.lounode.extrabotany.common.lib.LibMisc;
import io.github.lounode.extrabotany.xplat.ExtraBotanyConfig;

import java.io.*;
import java.nio.file.*;
import java.util.UUID;

import static io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes.BOOLEAN;
import static io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes.STRING;

import io.github.fablabsmc.fablabs.api.fiber.v1.builder.ConfigTreeBuilder;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.ValueDeserializationException;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;

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
		public final PropertyMirror<Boolean> disableGaiaDisArm = PropertyMirror.create(BOOLEAN);
		public final PropertyMirror<Boolean> enableTelemetry = PropertyMirror.create(BOOLEAN);
		public final PropertyMirror<String> telemetryUUID = PropertyMirror.create(STRING);

		public ConfigTree configure(ConfigTreeBuilder builder) {
			builder

					.fork("server")
					.fork("telemetry")

					.beginValue("enableTelemetry", BOOLEAN, true)
					.withComment("We use telemetry data to provide a better gameplay experience.")
					.withComment("The following data will be collected during your play session:")
					.withComment("    - Gaia III completion rate")
					.withComment("    - etc...")
					.withComment("")
					.withComment("Find more on: https://github.com/Lounode/Extrabotany")
					.withComment("If you prefer not to participate, set the option below to false.")
					.finishValue(enableTelemetry::mirror)

					.beginValue("telemetryUUID", STRING, UUID.randomUUID().toString())
					.withComment("The UUID of the telemetry data")
					.finishValue(telemetryUUID::mirror)

					.finishBranch()

					.fork("gaia")

					.beginValue("disableGaiaDisarm", BOOLEAN, false)
					.withComment("Set true to disable Gaia's disarm")
					.finishValue(disableGaiaDisArm::mirror)

					.finishBranch()

					.finishBranch();

			return builder.build();
		}

		@Override
		public boolean disableGaiaDisArm() {
			return disableGaiaDisArm.getValue();
		}

		@Override
		public boolean enableTelemetry() {
			return enableTelemetry.getValue();
		}

		@Override
		public String telemetryUUID() {
			return telemetryUUID.getValue();
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
