package io.github.lounode.extrabotany.common.block.flower;

import com.mojang.blaze3d.platform.Window;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import vazkii.botania.api.block_entity.BindableSpecialFlowerBlockEntity;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;

public abstract class ExtraFunctionalFlowerBlockEntity extends FunctionalFlowerBlockEntity {
	protected long ticksExisted;

	public ExtraFunctionalFlowerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void tickFlower() {
		super.tickFlower();
		ticksExisted++;
	}

	public boolean acceptsRedstone() {
		return false;
	}

	protected void sync() {
		markForImmediateSync();
	}

	public static class BindableFlowerWandHud<F extends BindableSpecialFlowerBlockEntity<?>>
			extends vazkii.botania.api.block_entity.BindableSpecialFlowerBlockEntity.BindableFlowerWandHud<F> {
		public BindableFlowerWandHud(F flower) {
			super(flower);
		}

		public void renderHUD(GuiGraphics gui, Minecraft mc, int minLeft, int minRight, int minDown) {
			super.renderHUD(gui, mc.getWindow(), mc.font, minLeft, minRight, minDown);
		}

		@Override
		public void renderHUD(GuiGraphics gui, Window window, Font font, float partialTick) {
			renderHUD(gui, Minecraft.getInstance(), 0, 0, 0);
		}
	}
}
