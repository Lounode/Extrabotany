package io.github.lounode.extrabotany.common.block.flower.generating;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

import io.github.lounode.extrabotany.common.block.flower.ExtrabotanyFlowerBlocks;

public class NoislingBlockEntity extends GeneratingFlowerBlockEntity {
	public NoislingBlockEntity(BlockPos pos, BlockState blockState) {
		super(ExtrabotanyFlowerBlocks.NOISLING, pos, blockState);
	}

	@Override
	public int getMaxMana() {
		return 0;
	}

	@Override
	public int getColor() {
		return 0;
	}

	@Override
	public @Nullable RadiusDescriptor getRadius() {
		return null;
	}
}
