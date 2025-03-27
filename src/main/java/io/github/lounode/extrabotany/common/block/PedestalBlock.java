package io.github.lounode.extrabotany.common.block;

import io.github.lounode.extrabotany.common.block.block_entity.ExtraBotanyBlockEntities;
import io.github.lounode.extrabotany.common.block.block_entity.PedestalBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.block.block_entity.SimpleInventoryBlockEntity;

public class PedestalBlock extends ExtraBotanyBlock implements EntityBlock {
    private static final VoxelShape SHAPE = Shapes.or(
            // Base layers (0-4 y-level)
            Block.box(0, 0, 0, 16, 2, 16),      // baseBottom
            Block.box(2, 2, 2, 14, 4, 14),      // baseTop

            // Main pillar (4-12 y-level)
            Block.box(4, 4, 4, 12, 12, 12),     // pillar

            // Goblet base (12-14 y-level)
            Block.box(2, 12, 2, 14, 14, 14),    // baseGoblet

            // Side panels (14-20 y-level)
            Shapes.or(
                    Block.box(2, 14, 13, 14, 20, 14),  // sideSouth
                    Block.box(2, 14, 2, 14, 20, 3),    // sideNorth
                    Block.box(2, 14, 3, 3, 20, 13),    // sideWest
                    Block.box(13, 14, 3, 14, 20, 13)   // sideEast
            )
    );

    public enum Variant {
        LIVINGROCK,
    }
    public final Variant variant;

    protected PedestalBlock(Variant v, BlockBehaviour.Properties builder) {
        super(builder);
        this.variant = v;
    }
    @NotNull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @NotNull
    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return SHAPE;
    }
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(world.getBlockEntity(pos) instanceof PedestalBlockEntity pedestal)) {
            return InteractionResult.PASS;
        }
        return pedestal.use(state, world, pos, player, hand, hit);
    }

    @NotNull
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new PedestalBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return createTickerHelper(type, ExtraBotanyBlockEntities.PEDESTAL, PedestalBlockEntity::clientTick);
        } else {
            return createTickerHelper(type, ExtraBotanyBlockEntities.PEDESTAL, PedestalBlockEntity::serverTick);
        }
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof SimpleInventoryBlockEntity inventory) {
            Containers.dropContents(world, pos, inventory.getItemHandler());
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    //TODO 比较器红石信号
    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return 0;
    }
}
