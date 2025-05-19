package io.github.lounode.extrabotany.common.block.block_entity;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import vazkii.botania.xplat.XplatAbstractions;

import io.github.lounode.extrabotany.common.lib.LibBlockNames;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks.*;
import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ExtraBotanyBlockEntities {
	private static final Map<ResourceLocation, BlockEntityType<?>> ALL = new HashMap<>();

	public static final BlockEntityType<PedestalBlockEntity> PEDESTAL = type(prefix(LibBlockNames.PEDESTAL), PedestalBlockEntity::new,
			livingrockPedestal
	);
	public static final BlockEntityType<ManaChargerBlockEntity> MANA_CHARGER =
			type(prefix(LibBlockNames.MANA_CHARGER), ManaChargerBlockEntity::new, manaCharger);
	public static final BlockEntityType<PowerFrameBlockEntity> POWER_FRAME =
			type(prefix(LibBlockNames.POWER_FRAME), PowerFrameBlockEntity::new, powerFrame);

	private static <T extends BlockEntity> BlockEntityType<T> type(ResourceLocation id, BiFunction<BlockPos, BlockState, T> func, Block... blocks) {
		var ret = XplatAbstractions.INSTANCE.createBlockEntityType(func, blocks);
		var old = ALL.put(id, ret);
		if (old != null) {
			throw new IllegalArgumentException("Duplicate id " + id);
		}
		return ret;
	}

	public static void registerTiles(BiConsumer<BlockEntityType<?>, ResourceLocation> r) {
		for (var e : ALL.entrySet()) {
			r.accept(e.getValue(), e.getKey());
		}
	}
}
