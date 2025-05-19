package io.github.lounode.extrabotany.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import org.apache.commons.lang3.tuple.Pair;

import vazkii.botania.xplat.XplatAbstractions;

import java.util.UUID;

import io.github.lounode.extrabotany.common.lib.LibMisc;
import io.github.lounode.extrabotany.xplat.ExtraBotanyConfig;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeExtrabotanyConfig {
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
		public final ForgeConfigSpec.BooleanValue disableGaiaDisArm;
		public final ForgeConfigSpec.BooleanValue enableTelemetry;
		public final ForgeConfigSpec.ConfigValue<String> telemetryUUID;

		public Common(ForgeConfigSpec.Builder builder) {

			builder.push("server");

			builder.push("telemetry");
			enableTelemetry = builder
					.comment("We use telemetry data to provide a better gameplay experience.")
					.comment("The following data will be collected during your play session:")
					.comment("    - Gaia III completion rate")
					.comment("    - etc...")
					.comment("")
					.comment("Find more on: https://github.com/Lounode/Extrabotany")
					.comment("If you prefer not to participate, set the option below to false.")
					.define("enableTelemetry", true);

			telemetryUUID = builder
					.comment("The UUID of the telemetry data")
					.define("telemetryUUID", UUID.randomUUID().toString());
			builder.pop();

			builder.push("gaia");
			disableGaiaDisArm = builder
					.comment("Set true to disable Gaia's disarm")
					.define("disableGaiaDisarm", false);
			builder.pop();

			builder.pop();
		}

		@Override
		public boolean disableGaiaDisArm() {
			return disableGaiaDisArm.get();
		}

		@Override
		public boolean enableTelemetry() {
			return enableTelemetry.get();
		}

		@Override
		public String telemetryUUID() {
			return telemetryUUID.get();
		}
	}

	private static final Common COMMON;
	private static final ForgeConfigSpec COMMON_SPEC;
	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	public static void setup(ModLoadingContext context) {
		context.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
		ExtraBotanyConfig.setCommon(COMMON);

		if (XplatAbstractions.INSTANCE.isPhysicalClient()) {
			context.registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
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
