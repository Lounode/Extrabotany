package io.github.lounode.extrabotany.api.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import vazkii.botania.common.annotations.SoftImplement;

public interface IArmor {
	@SoftImplement("IForgeItem")
	ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel);
}
