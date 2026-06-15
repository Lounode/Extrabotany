package io.github.lounode.extrabotany.common.block.mana;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.common.block.BotaniaBlock;
import vazkii.botania.common.block.block_entity.SimpleInventoryBlockEntity;

import io.github.lounode.extrabotany.api.block.Charger;
import io.github.lounode.extrabotany.common.block.block_entity.ChargerBlockEntity;
import io.github.lounode.extrabotany.common.block.block_entity.ExtraBotanyBlockEntities;
import io.github.lounode.extrabotany.common.block.block_entity.ManaChargerBlockEntity;
import io.github.lounode.extrabotany.common.block.block_entity.PowerFrameBlockEntity;

public class PowerFrameBlock extends BotaniaBlock implements EntityBlock {

	public PowerFrameBlock(Properties builder) {
		super(builder);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!(level.getBlockEntity(pos) instanceof ChargerBlockEntity charger)) {
			return InteractionResult.PASS;
		}
		return charger.use(state, level, pos, player, InteractionHand.MAIN_HAND, hit);
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!(level.getBlockEntity(pos) instanceof ChargerBlockEntity charger)) {
			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
		}

		InteractionResult result = charger.use(state, level, pos, player, hand, hit);
		return result.consumesAction()
				? ItemInteractionResult.sidedSuccess(level.isClientSide())
				: ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new PowerFrameBlockEntity(blockPos, blockState);
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
		if (!state.is(newState.getBlock())) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof SimpleInventoryBlockEntity inventory) {
				Containers.dropContents(level, pos, inventory.getItemHandler());
				level.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, level, pos, newState, movedByPiston);
		}
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		if (level.isClientSide()) {
			return createTickerHelper(blockEntityType, ExtraBotanyBlockEntities.POWER_FRAME, ManaChargerBlockEntity::clientTick);
		} else {
			return createTickerHelper(blockEntityType, ExtraBotanyBlockEntities.POWER_FRAME, ManaChargerBlockEntity::serverTick);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
		if (world.getBlockEntity(pos) instanceof Charger charger) {
			return (int) (charger.getChargeProcess() * 15.0D);
		}
		return 0;
	}
}
