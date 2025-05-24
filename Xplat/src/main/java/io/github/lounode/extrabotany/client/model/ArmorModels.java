package io.github.lounode.extrabotany.client.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.client.model.armor.ArmorModel;

import io.github.lounode.extrabotany.common.item.equipment.armor.starry_idol.StarryIdolArmorItem;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class ArmorModels {

	private static Map<EquipmentSlot, ArmorModel> starryIdol = Collections.emptyMap();
	private static Map<EquipmentSlot, ArmorModel> pleiadesCombatMaid = Collections.emptyMap();

	private static Map<EquipmentSlot, ArmorModel> make(EntityRendererProvider.Context ctx, ModelLayerLocation inner, ModelLayerLocation outer) {
		Map<EquipmentSlot, ArmorModel> ret = new EnumMap<>(EquipmentSlot.class);
		for (var slot : EquipmentSlot.values()) {
			var mesh = ctx.bakeLayer(slot == EquipmentSlot.LEGS ? inner : outer);
			ret.put(slot, new ArmorModel(mesh, slot));
		}
		return ret;
	}

	private static Map<EquipmentSlot, ArmorModel> make(EntityRendererProvider.Context ctx, ModelLayerLocation inner) {
		Map<EquipmentSlot, ArmorModel> ret = new EnumMap<>(EquipmentSlot.class);
		for (var slot : EquipmentSlot.values()) {
			var mesh = ctx.bakeLayer(inner);
			ret.put(slot, new ArmorModel(mesh, slot));
		}
		return ret;
	}

	private static Map<EquipmentSlot, ArmorModel> makeIdol(EntityRendererProvider.Context ctx, ModelLayerLocation layer, ModelLayerLocation dressLayer) {
		Map<EquipmentSlot, ArmorModel> ret = new EnumMap<>(EquipmentSlot.class);
		for (var slot : EquipmentSlot.values()) {
			var mesh = ctx.bakeLayer(slot != EquipmentSlot.LEGS ? layer : dressLayer);
			ret.put(slot, new ArmorModel(mesh, slot));
		}
		return ret;
	}

	public static void init(EntityRendererProvider.Context ctx) {
		//starryIdol = make(ctx, ExtrabotanyModelLayers.STARRY_IDOL_INNER_ARMOR, ExtrabotanyModelLayers.STARRY_IDOL_OUTER_ARMOR);
		starryIdol = makeIdol(ctx, ExtrabotanyModelLayers.STARRY_IDOL_ARMOR_NORMAL, ExtrabotanyModelLayers.STARRY_IDOL_ARMOR_DRESS);
	}

	@Nullable
	public static ArmorModel get(ItemStack stack) {
		Item item = stack.getItem();

		if (item instanceof StarryIdolArmorItem armor) {
			return starryIdol.get(armor.getEquipmentSlot());
		}

		return null;
	}
}
