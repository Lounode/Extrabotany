package io.github.lounode.extrabotany.common.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.Enchantments;

public final class FrostWalkerEffectHelper {
	private FrostWalkerEffectHelper() {}

	public static void apply(LivingEntity entity, ItemStack stack, int level) {
		if (!(entity.level() instanceof ServerLevel serverLevel) || level <= 0) {
			return;
		}

		boolean wasOnGround = entity.onGround();
		entity.setOnGround(true);
		try {
			var frostWalker = serverLevel.holderLookup(Registries.ENCHANTMENT)
					.getOrThrow(Enchantments.FROST_WALKER)
					.value();
			var itemInUse = new EnchantedItemInUse(stack, EquipmentSlot.FEET, entity);
			for (var effect : frostWalker.getEffects(EnchantmentEffectComponents.LOCATION_CHANGED)) {
				effect.effect().onChangedBlock(serverLevel, level, itemInUse, entity, entity.position(), false);
			}
		} finally {
			entity.setOnGround(wasOnGround);
		}
	}
}
