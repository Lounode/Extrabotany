package io.github.lounode.extrabotany.common.item.lens;

import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import vazkii.botania.api.internal.ManaBurst;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.common.item.lens.Lens;

import java.util.Objects;

public class SuperconductorLens extends Lens {

	@Override
	public void apply(ItemStack stack, BurstProperties props) {
		props.maxMana *= 8;
		props.motionModifier *= 1.5F;
		props.manaLossPerTick *= 16F;
		props.ticksBeforeManaLoss *= 0.8F;
		props.color = Objects.requireNonNull(ChatFormatting.BLUE.getColor());
	}

	@Override
	public void updateBurst(ManaBurst burst, ItemStack stack) {
		ThrowableProjectile burstEntity = burst.entity();
		Level level = burstEntity.level();
		if (level.isClientSide()) {
			return;
		}

	}
}
