package io.github.lounode.extrabotany.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import org.apache.commons.lang3.tuple.Pair;

import vazkii.botania.xplat.XplatAbstractions;

import io.github.lounode.extrabotany.common.block.flower.functional.AnnoyingFlowerBlockEntity;
import io.github.lounode.extrabotany.common.block.flower.functional.SerenitianBlockEntity;
import io.github.lounode.extrabotany.common.block.flower.functional.TradeOrchidBlockEntity;
import io.github.lounode.extrabotany.common.block.flower.generating.*;
import io.github.lounode.extrabotany.common.lib.LibMisc;
import io.github.lounode.extrabotany.xplat.ExtraBotanyConfig;

import java.util.List;
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
							(Enables otaku-style text display)""")
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
		public final ForgeConfigSpec.ConfigValue<String> fakePlayerId;
		public final ForgeConfigSpec.ConfigValue<List<? extends Integer>> woodieniaRange;
		public final ForgeConfigSpec.IntValue woodieniaCooldown;
		public final ForgeConfigSpec.IntValue woodieniaMaxMana;
		public final ForgeConfigSpec.IntValue woodieniaWorkManaCost;
		public final ForgeConfigSpec.IntValue reikarlilyMaxMana;
		public final ForgeConfigSpec.IntValue reikarlilyProduceCooldown;
		public final ForgeConfigSpec.IntValue reikarlilyProduceMana;
		public final ForgeConfigSpec.IntValue reikarlilySpawnLightningCooldown;
		public final ForgeConfigSpec.IntValue reikarlilyPassiveGenerateTime;
		public final ForgeConfigSpec.IntValue reikarlilyPassiveGenerateMana;
		public final ForgeConfigSpec.IntValue tradeOrchidMaxMana;
		public final ForgeConfigSpec.IntValue tradeOrchidManaCost;
		public final ForgeConfigSpec.IntValue tradeOrchidCooldown;
		public final ForgeConfigSpec.DoubleValue tradeOrchidDiscountPercentage;
		public final ForgeConfigSpec.IntValue bellflowerMaxMana;
		public final ForgeConfigSpec.DoubleValue bellflowerGenerateModify;
		public final ForgeConfigSpec.IntValue annoyingflowerMaxMana;
		public final ForgeConfigSpec.IntValue annoyingflowerFishingCost;
		public final ForgeConfigSpec.IntValue annoyingflowerCooldown;
		public final ForgeConfigSpec.IntValue annoyingflowerFoodBoostMax;
		public final ForgeConfigSpec.IntValue annoyingflowerFoodBoostTimes;
		public final ForgeConfigSpec.DoubleValue annoyingflowerFoodBoostCooldownMultiplier;
		public final ForgeConfigSpec.IntValue stonesiaMaxMana;
		public final ForgeConfigSpec.IntValue stonesiaCooldown;
		public final ForgeConfigSpec.IntValue edelweissMaxMana;
		public final ForgeConfigSpec.IntValue edelweissCooldown;
		public final ForgeConfigSpec.IntValue resoncundMaxMana;
		public final ForgeConfigSpec.IntValue resoncundLossPerHeard;
		public final ForgeConfigSpec.IntValue sunshineLilyMaxMana;
		public final ForgeConfigSpec.IntValue sunshineLilyProduceMana;
		public final ForgeConfigSpec.IntValue moonlightLilyMaxMana;
		public final ForgeConfigSpec.IntValue moonlightLilyProduceMana;
		public final ForgeConfigSpec.IntValue twinstarMaxMana;
		public final ForgeConfigSpec.IntValue twinstarMaxTemperature;
		public final ForgeConfigSpec.IntValue twinstarMinTemperature;
		public final ForgeConfigSpec.IntValue omnivioletMaxMana;
		public final ForgeConfigSpec.IntValue tinkleMaxMana;
		public final ForgeConfigSpec.IntValue tinkleProduceMana;
		public final ForgeConfigSpec.IntValue bloodEnchantressMaxMana;
		public final ForgeConfigSpec.IntValue bloodEnchantressProduceMana;
		public final ForgeConfigSpec.IntValue serenitianRange;

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
							If you prefer not to participate, set the option below to false""")
					.define("enableTelemetry", true);

			telemetryUUID = builder
					.comment("""
							遥测数据UUID
							The UUID of the telemetry data""")
					.define("telemetryUUID", UUID.randomUUID().toString());
			builder.pop();//End telemetry

			builder.push("gaia");
			disableGaiaDisArm = builder
					.comment("""
							设为 true 来禁用盖亚的缴械技能
							Set true to disable Gaia's disarm""")
					.define("disableGaiaDisarm", false);
			builder.pop();//End gaia

			builder.push("fakePlayer");
			fakePlayerId = builder
					.comment("""
							假玩家ID（用于权限配置）
							Fake Player ID (for permission configuration)""")
					.define("fakePlayerId", "[Extrabotany]");
			builder.pop();//End fakePlayer

			builder.push("flower");
			builder.comment("""
					商友兰
					Trade Orchid""");
			builder.push("tradeOrchid");
			tradeOrchidMaxMana = builder
					.comment("""
							最大魔力值
							Maximum mana""")
					.defineInRange("maxMana", TradeOrchidBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			tradeOrchidManaCost = builder
					.comment("""
							每只村民消耗的魔力量
							Mana cost per villager""")
					.defineInRange("manaCost", TradeOrchidBlockEntity.MANA_PER_USE, 0, Integer.MAX_VALUE);
			tradeOrchidCooldown = builder
					.comment("""
							冷却时间(ticks)
							Cooldown time in ticks""")
					.defineInRange("cooldown", TradeOrchidBlockEntity.COOLDOWN, 0, Integer.MAX_VALUE);
			tradeOrchidDiscountPercentage = builder
					.comment("""
							折扣百分比(仅支持精确到两位小数)
							(例如：0.85 = 八五折)
							Discount percentage (max precision: 0.01)
							(e.g., 0.85 = 15% off)""")
					.defineInRange("discountPercentage", TradeOrchidBlockEntity.DISCOUNT_RATE, 0, 1.0D);
			builder.pop();//End tradeOrchid
			builder.comment("""
					伐木花
					Woodienia""");
			builder.push("woodienia");
			woodieniaMaxMana = builder
					.comment("""
							最大魔力
							Maximum Mana""")
					.defineInRange("maxMana", 10_000, 0, Integer.MAX_VALUE);
			woodieniaWorkManaCost = builder
					.comment("""
							破坏原木的魔力消耗
							Cost when break Logs""")
					.defineInRange("workManaCost", 200, 0, Integer.MAX_VALUE);
			woodieniaRange = builder
					.comment("""
							以自身为中心的工作范围（±X轴，+Y轴，±Z轴）
							Working range centered on self (±X axis, +Y axis, ±Z axis)""")
					.defineList("range", List.of(8, 16, 8), o -> o instanceof Integer i && i > 0 && i < Integer.MAX_VALUE);
			woodieniaCooldown = builder
					.comment("""
							工作间隔
							Cooldown interval""")
					.defineInRange("cooldown", 10, 0, Integer.MAX_VALUE);
			builder.pop();//End woodienia
			builder.comment("""
					雷卡兰
					Reikarlily""");
			builder.push("reikarlily");
			reikarlilyMaxMana = builder
					.comment("""
							最大魔力
							Maximum Mana""")
					.defineInRange("maxMana", ReikarlilyBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			reikarlilyProduceCooldown = builder
					.comment("""
							雷击后再次产出魔力的冷却时间
							Cooldown time for regenerating mana after a lightning strike""")
					.defineInRange("produceCooldown", ReikarlilyBlockEntity.COOLDOWN, 0, Integer.MAX_VALUE);
			reikarlilyProduceMana = builder
					.comment("""
							雷击生成的魔力量
							Mana generated per lightning strike""")
					.defineInRange("produceMana", ReikarlilyBlockEntity.PRODUCE_MANA, 0, Integer.MAX_VALUE);
			reikarlilyPassiveGenerateTime = builder
					.comment("""
							雷击后被动生成魔力的时间
							Passive mana generation duration after lightning strike""")
					.defineInRange("passiveGenerateTime", ReikarlilyBlockEntity.RESIDUAL_HEAT_AFTER_PRODUCE, 0, Integer.MAX_VALUE);
			reikarlilyPassiveGenerateMana = builder
					.comment("""
							雷击后每Tick被动生成的魔力量
							Mana generated per passive tick""")
					.defineInRange("passiveGenerateMana", ReikarlilyBlockEntity.RESIDUAL_HEAT_PRODUCE_MANA, 0, Integer.MAX_VALUE);
			reikarlilySpawnLightningCooldown = builder
					.comment("""
							雨天生成闪电的冷却时间
							Cooldown for spawning lightning when raining""")
					.defineInRange("spawnLightningCooldown", ReikarlilyBlockEntity.SPAWN_LIGHTNING_COOLDOWN, 0, Integer.MAX_VALUE);
			builder.pop();//End reikarlily
			builder.comment("""
					风铃草
					Bellflower""");
			builder.push("bellflower");
			bellflowerMaxMana = builder
					.comment("""
							最大魔力值
							Maximum mana""")
					.defineInRange("maxMana", BellflowerBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			bellflowerGenerateModify = builder
					.comment("""
							魔力生成修正值
							Mana generation modifier""")
					.defineInRange("generateModify", BellflowerBlockEntity.GENERATE_MODIFY, 0, Integer.MAX_VALUE);
			builder.pop(); // End bellflower
			builder.comment("""
					神烦花
					Annoying Flower""");
			builder.push("annoyingflower");
			annoyingflowerMaxMana = builder
					.comment("""
							最大魔力值
							Maximum mana""")
					.defineInRange("maxMana", AnnoyingFlowerBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			annoyingflowerFishingCost = builder
					.comment("""
							钓鱼消耗的魔力量
							Mana cost per fishing""")
					.defineInRange("fishingCost", AnnoyingFlowerBlockEntity.FISHING_COST, 0, Integer.MAX_VALUE);
			annoyingflowerCooldown = builder
					.comment("""
							工作冷却时间(ticks)
							Cooldown time in ticks""")
					.defineInRange("cooldown", AnnoyingFlowerBlockEntity.COOLDOWN_AFTER_WORK, 0, Integer.MAX_VALUE);
			annoyingflowerFoodBoostMax = builder
					.comment("""
							最大食物加成次数
							Maximum food boost""")
					.defineInRange("foodBoostMax", AnnoyingFlowerBlockEntity.FOOD_BOOST_MAX, 0, Integer.MAX_VALUE);
			annoyingflowerFoodBoostTimes = builder
					.comment("""
							每次进食增加的次数
							Boost added per food consumed""")
					.defineInRange("foodBoostPerEat", AnnoyingFlowerBlockEntity.FOOD_BOOST_TIMES, 0, Integer.MAX_VALUE);
			annoyingflowerFoodBoostCooldownMultiplier = builder
					.comment("""
							食物加成时的冷却时间乘数
							Cooldown multiplier when food boosted""")
					.defineInRange("foodBoostCooldownMultiplier", AnnoyingFlowerBlockEntity.FOOD_BOOST_COOLDOWN_MULTIPLIER, 0, 1.0D);
			builder.pop(); // End annoyingflower
			builder.comment("""
					石中姬
					Stonesia""");
			builder.push("stonesia");
			stonesiaMaxMana = builder
					.comment("""
							最大魔力值
							Maximum mana""")
					.defineInRange("maxMana", StonesiaBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			stonesiaCooldown = builder
					.comment("""
							冷却时间(ticks)
							Cooldown time in ticks""")
					.defineInRange("cooldown", StonesiaBlockEntity.COOLDOWN, 0, Integer.MAX_VALUE);
			builder.pop(); // End stonesia

			builder.comment("""
					雪绒花
					Edelweiss""");
			builder.push("edelweiss");
			edelweissMaxMana = builder
					.comment("""
							最大魔力值
							Maximum mana""")
					.defineInRange("maxMana", EdelweissBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			edelweissCooldown = builder
					.comment("""
							冷却时间(ticks)
							Cooldown time in ticks""")
					.defineInRange("cooldown", EdelweissBlockEntity.COOLDOWN, 0, Integer.MAX_VALUE);
			builder.pop(); // End edelweiss
			builder.comment("""
					回音花
					Resoncund""");
			builder.push("resoncund");
			resoncundMaxMana = builder
					.comment("""
							最大魔力值
							Maximum mana""")
					.defineInRange("maxMana", ResoncundBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			resoncundLossPerHeard = builder
					.comment("""
							同种声音重复听到后的魔力生产衰减
							Mana produce decreases per same sound heard""")
					.defineInRange("lossPerHeard", ResoncundBlockEntity.MANA_LOSS_PER_HEARD, 0, Integer.MAX_VALUE);
			builder.pop(); // End resoncund
			builder.comment("""
					日曜百合
					Sunshine Lily""");
			builder.push("sunshineLily");
			sunshineLilyMaxMana = builder
					.comment("""
							最大魔力值
							Maximum mana""")
					.defineInRange("maxMana", SunshineLilyBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			sunshineLilyProduceMana = builder
					.comment("""
							每次生成的魔力量
							Mana generated per produce""")
					.defineInRange("produceMana", SunshineLilyBlockEntity.MANA_PER_GENERATE, 0, Integer.MAX_VALUE);
			builder.pop();//End sunshineLily
			builder.comment("""
					月耀百合
					Moonlight Lily""");
			builder.push("moonlightLily");
			moonlightLilyMaxMana = builder
					.comment("""
							最大魔力值
							Maximum mana""")
					.defineInRange("maxMana", MoonlightLilyBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			moonlightLilyProduceMana = builder
					.comment("""
							每次生成的魔力量
							Mana generated per produce""")
					.defineInRange("produceMana", MoonlightLilyBlockEntity.MANA_PER_GENERATE, 0, Integer.MAX_VALUE);
			builder.pop();//End moonlightLily
			builder.comment("""
					双子兰
					Twinstar""");
			builder.push("twinstar");
			twinstarMaxMana = builder
					.comment("""
							最大魔力值
							Maximum mana""")
					.defineInRange("maxMana", TwinstarBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			twinstarMaxTemperature = builder
					.comment("""
							最大接受流体温度
							Maximum accepted temperature cap for liquid""")
					.defineInRange("maxTemperatureCap", TwinstarBlockEntity.TEMPERATURE_MAX, 0, Integer.MAX_VALUE);
			twinstarMinTemperature = builder
					.comment("""
							最低接受流体温度
							Minimum accepted temperature cap for liquid""")
					.defineInRange("minTemperatureCap", TwinstarBlockEntity.TEMPERATURE_MIN, 0, Integer.MAX_VALUE);
			builder.pop();//End twinstar
			builder.comment("""
					全知瑾
					Omniviolet""");
			builder.push("omniviolet");
			omnivioletMaxMana = builder
					.comment("""
							最大魔力值
							Maximum mana""")
					.defineInRange("maxMana", OmnivioletBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			builder.pop();//End omniviolet
			builder.comment("""
					叮当花
					Tinkle""");
			builder.push("tinkle");
			tinkleMaxMana = builder
					.comment("""
							最大魔力值
							Maximum mana""")
					.defineInRange("maxMana", TinkleBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			tinkleProduceMana = builder
					.comment("""
							每次生成的魔力量
							Mana generated per produce""")
					.defineInRange("produceMana", TinkleBlockEntity.PRODUCE_MANA, 0, Integer.MAX_VALUE);
			builder.pop();//End tinkle
			builder.comment("""
					鲜血妖姬
					Blood Enchantress""");
			builder.push("bloodEnchantress");
			bloodEnchantressMaxMana = builder
					.comment("""
							最大魔力值
							Maximum mana""")
					.defineInRange("maxMana", BloodEnchantressBlockEntity.MAX_MANA, 0, Integer.MAX_VALUE);
			bloodEnchantressProduceMana = builder
					.comment("""
							每次生成的魔力量
							Mana generated per produce""")
					.defineInRange("produceMana", BloodEnchantressBlockEntity.PRODUCE_MANA, 0, Integer.MAX_VALUE);
			builder.pop();//End bloodEnchantress
			builder.comment("""
					永寂龙胆
					Serenitian""");
			builder.push("serenitian");
			serenitianRange = builder
					.comment("""
							作用范围(方块半径)
							Working range in blocks""")
					.defineInRange("range", SerenitianBlockEntity.RANGE, 1, 16);
			builder.pop();//End serenitian

			builder.pop();//End flower
			builder.pop();//End server
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
		public String fakePlayerId() {
			return fakePlayerId.get();
		}

		@Override
		public String telemetryUUID() {
			return telemetryUUID.get();
		}

		@Override
		public int[] woodieniaRange() {
			return woodieniaRange.get().stream()
					.mapToInt(Integer::intValue)
					.toArray();
		}

		@Override
		public int woodieniaCooldown() {
			return woodieniaCooldown.get();
		}

		@Override
		public int woodieniaMaxMana() {
			return woodieniaMaxMana.get();
		}

		@Override
		public int woodieniaWorkManaCost() {
			return woodieniaWorkManaCost.get();
		}

		@Override
		public int reikarlilyMaxMana() {
			return reikarlilyMaxMana.get();
		}

		@Override
		public int reikarlilyProduceCooldown() {
			return reikarlilyProduceCooldown.get();
		}

		@Override
		public int reikarlilyProduceMana() {
			return reikarlilyProduceMana.get();
		}

		@Override
		public int reikarlilySpawnLightningCooldown() {
			return reikarlilySpawnLightningCooldown.get();
		}

		@Override
		public int reikarlilyPassiveGenerateTime() {
			return reikarlilyPassiveGenerateTime.get();
		}

		@Override
		public int reikarlilyPassiveGenerateMana() {
			return reikarlilyPassiveGenerateMana.get();
		}

		@Override
		public int tradeOrchidMaxMana() {
			return tradeOrchidMaxMana.get();
		}

		@Override
		public int tradeOrchidManaCost() {
			return tradeOrchidManaCost.get();
		}

		@Override
		public int tradeOrchidCooldown() {
			return tradeOrchidCooldown.get();
		}

		@Override
		public double tradeOrchidDiscountPercentage() {
			return tradeOrchidDiscountPercentage.get();
		}

		@Override
		public int bellflowerMaxMana() {
			return bellflowerMaxMana.get();
		}

		@Override
		public double bellflowerGenerateModify() {
			return bellflowerGenerateModify.get();
		}

		@Override
		public int annoyingflowerMaxMana() {
			return annoyingflowerMaxMana.get();
		}

		@Override
		public int annoyingflowerFishingCost() {
			return annoyingflowerFishingCost.get();
		}

		@Override
		public int annoyingflowerCooldown() {
			return annoyingflowerCooldown.get();
		}

		@Override
		public int annoyingflowerFoodBoostMax() {
			return annoyingflowerFoodBoostMax.get();
		}

		@Override
		public int annoyingflowerFoodBoostTimes() {
			return annoyingflowerFoodBoostTimes.get();
		}

		@Override
		public double annoyingflowerFoodBoostCooldownMultiplier() {
			return annoyingflowerFoodBoostCooldownMultiplier.get();
		}

		@Override
		public int stonesiaMaxMana() {
			return stonesiaMaxMana.get();
		}

		@Override
		public int stonesiaCooldown() {
			return stonesiaCooldown.get();
		}

		@Override
		public int edelweissMaxMana() {
			return edelweissMaxMana.get();
		}

		@Override
		public int edelweissCooldown() {
			return edelweissCooldown.get();
		}

		@Override
		public int resoncundMaxMana() {
			return resoncundMaxMana.get();
		}

		@Override
		public int resoncundLossPerHeard() {
			return resoncundLossPerHeard.get();
		}

		@Override
		public int sunshineLilyMaxMana() {
			return sunshineLilyMaxMana.get();
		}

		@Override
		public int sunshineLilyProduceMana() {
			return sunshineLilyProduceMana.get();
		}

		@Override
		public int moonlightLilyMaxMana() {
			return moonlightLilyMaxMana.get();
		}

		@Override
		public int moonlightLilyProduceMana() {
			return moonlightLilyProduceMana.get();
		}

		@Override
		public int twinstarMaxMana() {
			return twinstarMaxMana.get();
		}

		@Override
		public int twinstarMaxTemperature() {
			return twinstarMaxTemperature.get();
		}

		@Override
		public int twinstarMinTemperature() {
			return twinstarMinTemperature.get();
		}

		@Override
		public int omnivioletMaxMana() {
			return omnivioletMaxMana.get();
		}

		@Override
		public int tinkleMaxMana() {
			return tinkleMaxMana.get();
		}

		@Override
		public int tinkleProduceMana() {
			return tinkleProduceMana.get();
		}

		@Override
		public int bloodEnchantressMaxMana() {
			return bloodEnchantressMaxMana.get();
		}

		@Override
		public int bloodEnchantressProduceMana() {
			return bloodEnchantressProduceMana.get();
		}

		@Override
		public int serenitianRange() {
			return serenitianRange.get();
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
