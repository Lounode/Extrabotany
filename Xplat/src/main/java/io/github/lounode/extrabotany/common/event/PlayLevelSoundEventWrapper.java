package io.github.lounode.extrabotany.common.event;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;

public class PlayLevelSoundEventWrapper {
	@Nullable
	private final Holder<SoundEvent> sound;

	protected PlayLevelSoundEventWrapper(@Nullable Holder<SoundEvent> sound) {
		this.sound = sound;
	}

	@Nullable
	public Holder<SoundEvent> getSound() {
		return sound;
	}

	public static class AtPosition extends PlayLevelSoundEventWrapper {
		private final Vec3 position;

		public AtPosition(Vec3 position, @Nullable Holder<SoundEvent> sound) {
			super(sound);
			this.position = position;
		}

		public Vec3 getPosition() {
			return position;
		}
	}

	public static class AtEntity extends PlayLevelSoundEventWrapper {
		private final Entity entity;

		public AtEntity(Entity entity, @Nullable Holder<SoundEvent> sound) {
			super(sound);
			this.entity = entity;
		}

		public Entity getEntity() {
			return entity;
		}
	}
}
