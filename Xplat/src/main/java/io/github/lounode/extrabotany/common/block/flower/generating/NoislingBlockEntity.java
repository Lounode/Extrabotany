package io.github.lounode.extrabotany.common.block.flower.generating;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

import io.github.lounode.extrabotany.common.block.flower.ExtrabotanyFlowerBlocks;

public class NoislingBlockEntity extends GeneratingFlowerBlockEntity {

	private static final int RANGE = 4;

	public static final int MAX_MANA = 1200;
	public static final int CACHE_SIZE = 32;

	public NoislingBlockEntity(BlockPos pos, BlockState blockState) {
		super(ExtrabotanyFlowerBlocks.NOISLING, pos, blockState);
	}

	public int getCacheSize() {
		return CACHE_SIZE;
	}

	@Override
	public int getMaxMana() {
		return MAX_MANA;
	}

	@Override
	public int getColor() {
		return 0xF54DAF;
	}

	@Override
	public @Nullable RadiusDescriptor getRadius() {
		return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
	}

	public static class EventHandler {
		public static void onPlayLevelSound() {

		}
	}
}
