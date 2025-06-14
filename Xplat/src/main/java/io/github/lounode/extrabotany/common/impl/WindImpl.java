package io.github.lounode.extrabotany.common.impl;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import io.github.lounode.extrabotany.api.level.Wind;

import java.util.LinkedHashMap;
import java.util.Map;

public class WindImpl implements Wind {

	private static final Map<Level, LevelWind> WIND_MAP = new LinkedHashMap<>();

	@Override
	public double getWindLevel(Level level, Vec3 position) {
		if (!WIND_MAP.containsKey(level)) {
			WIND_MAP.put(level, new LevelWind(level));
		}
		return WIND_MAP.get(level).getWindLevel(position);
	}

	public static class LevelWind {

		protected final Level level;
		public int ticksExisted;
		private int baseStrength;

		private double peakHeight;
		private static final double CENTER_OFFSET_RANGE = 10.0;
		private static final int UPDATE_PEAK_HEIGHT_INTERVAL = 36000;
		private static final int UPDATE_STRENGTH_INTERVAL = 200;

		public LevelWind(Level level) {
			this.level = level;
			this.baseStrength = 5 + level.random.nextInt(10);
			this.updatePeakHeight();
		}

		public void tick() {
			if (ticksExisted % UPDATE_STRENGTH_INTERVAL == 0) {
				int delta = level.random.nextInt(3) - 1; // -1, 0, 1
				baseStrength = Math.max(1, Math.min(20, baseStrength + delta));
			}

			if (ticksExisted % UPDATE_PEAK_HEIGHT_INTERVAL == 0) {
				updatePeakHeight();
			}

			ticksExisted++;
		}

		private void updatePeakHeight() {
			double seaLevel = level.getSeaLevel();
			double maxHeight = level.getMaxBuildHeight();
			double center = (seaLevel + maxHeight) / 2.0;

			peakHeight = center + (level.random.nextDouble() * 2.0 - 1.0) * CENTER_OFFSET_RANGE;
		}

		public double getWindLevel(Vec3 position) {
			double height = position.y();

			double seaLevel = level.getSeaLevel();
			double maxHeight = level.getMaxBuildHeight();
			double range = maxHeight - seaLevel;

			double delta = height - peakHeight;
			double sigma = 20.0;

			double heightMultiplier = Math.exp(-Math.pow(delta / sigma, 2));

			double result = baseStrength * (0.5 + heightMultiplier * 1.5);

			double rainOffset = 1.0;
			if (level.isRaining()) {
				rainOffset += 0.25;
			}
			if (level.isThundering()) {
				rainOffset += 0.25;
			}

			return result * rainOffset;
		}
	}

	//@EventBusSubscriberWrapper
	public static class EventHandler {
		//public static void onLevelLoad()
		public static void onLevelLoad(Level level) {
			if (!WIND_MAP.containsKey(level)) {
				WIND_MAP.put(level, new LevelWind(level));
			}
		}

		public static void onLevelUnLoad(Level level) {
			WIND_MAP.remove(level);
		}

		public static void onLevelTick(Level level) {
			if (WIND_MAP.containsKey(level)) {
				LevelWind wind = WIND_MAP.get(level);
				wind.tick();
			}
		}
	}
}
