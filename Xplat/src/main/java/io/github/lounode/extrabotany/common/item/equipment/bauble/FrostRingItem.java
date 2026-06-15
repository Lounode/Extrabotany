package io.github.lounode.extrabotany.common.item.equipment.bauble;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import vazkii.botania.common.item.equipment.bauble.BaubleItem;

import io.github.lounode.extrabotany.common.util.FrostWalkerEffectHelper;

import java.util.List;

public class FrostRingItem extends BaubleItem {

	private static final int RANGE = 6;

	public FrostRingItem(Properties props) {
		super(props);
	}

	@Override
	public void onWornTick(ItemStack stack, LivingEntity entity) {
		super.onWornTick(stack, entity);
		if (entity.level().isClientSide()) {
			return;
		}
		FrostWalkerEffectHelper.apply(entity, stack, getRange());
		FrostRingItem.freezeLava(entity, entity.level(), entity.blockPosition(), getRange());
		slowDownMobsNearby(entity);
	}

	public void slowDownMobsNearby(LivingEntity owner) {
		List<LivingEntity> entities = owner.level().getEntitiesOfClass(LivingEntity.class, new AABB(owner.position(), owner.position()).inflate(getRange()));
		for (var living : entities) {
			if (living == owner) {
				continue;
			}
			if (isLookingAt(owner, living)) {
				living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4));
			}
		}
	}

	private static boolean isLookingAt(LivingEntity from, LivingEntity toCheck) {
		Vec3 vec3 = from.getViewVector(1.0F).normalize();
		Vec3 vec31 = new Vec3(toCheck.getX() - from.getX(), toCheck.getEyeY() - from.getEyeY(), toCheck.getZ() - from.getZ());
		double d0 = vec31.length();
		vec31 = vec31.normalize();
		double d1 = vec3.dot(vec31);
		return d1 > (double) 1.0F - 0.025 / d0 && from.hasLineOfSight(toCheck);
	}

	public static void freezeLava(LivingEntity living, Level level, BlockPos pos, int levelConflicting) {
		if (living.onGround()) {
			BlockState blockstate = Blocks.OBSIDIAN.defaultBlockState();
			int i = Math.min(16, 2 + levelConflicting);
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-i, -1, -i), pos.offset(i, -1, i))) {
				if (blockpos.closerToCenterThan(living.position(), i)) {
					blockpos$mutableblockpos.set(blockpos.getX(), blockpos.getY() + 1, blockpos.getZ());
					BlockState blockstate1 = level.getBlockState(blockpos$mutableblockpos);
					if (blockstate1.isAir()) {
						BlockState blockstate2 = level.getBlockState(blockpos);
						if (blockstate2 == Blocks.LAVA.defaultBlockState() && blockstate.canSurvive(level, blockpos) && level.isUnobstructed(blockstate, blockpos, CollisionContext.empty())) {
							level.setBlockAndUpdate(blockpos, blockstate);
							level.scheduleTick(blockpos, Blocks.OBSIDIAN, Mth.nextInt(living.getRandom(), 60, 120));
						}
					}
				}
			}
		}

	}

	public int getRange() {
		return RANGE;
	}
}
