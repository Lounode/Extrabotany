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
		public final PropertyMirror<Boolean> otakuMode = PropertyMirror.create(BOOLEAN);

		public ConfigTree configure(ConfigTreeBuilder builder) {
			builder.fork("client")
					.beginValue("otakuMode", BOOLEAN, false)
					.withComment("""
							设为 true 来开启二刺螈模式
							（将会启用一些浓度较高、发癫的文本显示）
							
							Set true to enable Otaku Mode.
							(Enables otaku-style text display)
							""")
					.finishValue(otakuMode::mirror)
					.finishBranch();
			return builder.build();
		}

		@Override
		public boolean otakuMode() {
			return otakuMode.getValue();
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
					.withComment("""
							我们使用遥测数据来提供更好的游玩体验。
							以下数据在您的游玩过程中将被收集：
								- 盖亚三通过率
								- 其他...
							
							您可以在 https://github.com/Lounode/Extrabotany 上找到更多
							如果您不想被收集这些数据，在下方将配置项改为 false
							
							We use telemetry data to provide a better gameplay experience.
							The following data will be collected during your play session:
								- Gaia III completion rate
								- etc...
							
							Find more on: https://github.com/Lounode/Extrabotany
							If you prefer not to participate, set the option below to false.
							""")
					.finishValue(enableTelemetry::mirror)

					.beginValue("telemetryUUID", STRING, UUID.randomUUID().toString())
					.withComment("""
							遥测数据UUID
							The UUID of the telemetry data
							""")
					.finishValue(telemetryUUID::mirror)

					.finishBranch()

					.fork("gaia")

					.beginValue("disableGaiaDisarm", BOOLEAN, false)
					.withComment("""
							设为 true 来禁用盖亚的缴械技能
							Set true to disable Gaia's disarm
							""")
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

		try (InputStream s = new BufferedInputStream(Files.newInputStream(p))) {
			FiberSerialization.deserialize(config, s, serializer);
		} catch (IOException | ValueDeserializationException e) {
			ExtraBotanyAPI.LOGGER.error("Error loading config from {}", p, e);
		}

		try (OutputStream s = new BufferedOutputStream(Files.newOutputStream(p, StandardOpenOption.TRUNCATE_EXISTING))) {
			FiberSerialization.serialize(config, s, serializer);
		} catch (IOException e) {
			ExtraBotanyAPI.LOGGER.error("Error writing merged config to {}", p, e);
		}
	}
}
