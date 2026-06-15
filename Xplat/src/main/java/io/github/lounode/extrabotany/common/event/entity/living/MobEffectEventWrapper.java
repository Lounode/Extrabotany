package io.github.lounode.extrabotany.common.event.entity.living;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public abstract class MobEffectEventWrapper {
	private final LivingEntity entity;
	private final MobEffectInstance effectInstance;

	protected MobEffectEventWrapper(LivingEntity entity, MobEffectInstance effectInstance) {
		this.entity = entity;
		this.effectInstance = effectInstance;
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public MobEffectInstance getEffectInstance() {
		return effectInstance;
	}

	public static class Applicable extends MobEffectEventWrapper {
		private Result result = Result.DEFAULT;

		public Applicable(LivingEntity entity, MobEffectInstance effectInstance) {
			super(entity, effectInstance);
		}

		public Result getResult() {
			return result;
		}

		public void setResult(Result result) {
			this.result = result;
		}

		public enum Result {
			APPLY,
			DEFAULT,
			DO_NOT_APPLY
		}
	}

	public static class Added extends MobEffectEventWrapper {
		public Added(LivingEntity entity, MobEffectInstance effectInstance) {
			super(entity, effectInstance);
		}
	}

	public static class Remove extends MobEffectEventWrapper {
		public Remove(LivingEntity entity, MobEffectInstance effectInstance) {
			super(entity, effectInstance);
		}
	}

	public static class Expired extends MobEffectEventWrapper {
		public Expired(LivingEntity entity, MobEffectInstance effectInstance) {
			super(entity, effectInstance);
		}
	}
}
