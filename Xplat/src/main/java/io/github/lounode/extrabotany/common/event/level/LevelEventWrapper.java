package io.github.lounode.extrabotany.common.event.level;

import net.minecraft.world.level.LevelAccessor;

public class LevelEventWrapper {
	private final LevelAccessor level;

	protected LevelEventWrapper(LevelAccessor level) {
		this.level = level;
	}

	public LevelAccessor getLevel() {
		return level;
	}

	public static class Load extends LevelEventWrapper {
		public Load(LevelAccessor level) {
			super(level);
		}
	}

	public static class Unload extends LevelEventWrapper {
		public Unload(LevelAccessor level) {
			super(level);
		}
	}
}
