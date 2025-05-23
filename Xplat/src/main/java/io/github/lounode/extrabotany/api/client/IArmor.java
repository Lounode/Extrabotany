package io.github.lounode.extrabotany.api.client;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import vazkii.botania.common.annotations.SoftImplement;

public interface IArmor {
	@SoftImplement("IForgeItem")
	String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type);
}
