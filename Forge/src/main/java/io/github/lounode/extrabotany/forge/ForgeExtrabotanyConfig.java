package io.github.lounode.extrabotany.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import org.apache.commons.lang3.tuple.Pair;

import vazkii.botania.xplat.XplatAbstractions;

import io.github.lounode.extrabotany.common.lib.LibMisc;
import io.github.lounode.extrabotany.xplat.ExtraBotanyConfig;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeExtrabotanyConfig {
	private static class Client implements ExtraBotanyConfig.ClientConfigAccess {
		public final ForgeConfigSpec.BooleanValue otakuMode;

		public Client(ForgeConfigSpec.Builder builder) {
			builder.push("client");

			otakuMode = builder
					.comment("""
							设为 true 来开启二刺螈模式
							（将会启用一些浓度较高、发癫的文本显示）
							
							Set true to enable Otaku Mode.
							(Enables otaku-style text display)
							""")
					.define("otakuMode", false);

			builder.pop();
		}

		@Override
		public boolean otakuMode() {
			return otakuMode.get();
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
					.comment("""
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
					.define("enableTelemetry", true);

			telemetryUUID = builder
					.comment("""
							遥测数据UUID
							The UUID of the telemetry data
							""")
					.define("telemetryUUID", UUID.randomUUID().toString());
			builder.pop();

			builder.push("gaia");
			disableGaiaDisArm = builder
					.comment("""
							设为 true 来禁用盖亚的缴械技能
							Set true to disable Gaia's disarm
							""")
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
