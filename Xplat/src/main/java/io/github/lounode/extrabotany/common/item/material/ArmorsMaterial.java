package io.github.lounode.extrabotany.common.item.material;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import vazkii.botania.common.helper.RegistryHelper;
import vazkii.botania.common.item.BotaniaItems;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.sounds.ExtraBotanySounds;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public final class ArmorsMaterial {
	private static final List<RegistryHelper.HolderProxy<ArmorMaterial>> ALL = new ArrayList<>();
	public static final Holder<ArmorMaterial> STARRY_IDOL = create("starry_idol", 16,
			Map.of(
					ArmorItem.Type.BOOTS, 2,
					ArmorItem.Type.LEGGINGS, 5,
					ArmorItem.Type.CHESTPLATE, 6,
					ArmorItem.Type.HELMET, 2
			),
			18, Holder.direct(ExtraBotanySounds.ARMOR_EQUIP_IDOL), 0, 0,
			() -> Ingredient.of(BotaniaItems.manaweaveCloth));

	public static final Holder<ArmorMaterial> PLEIADS_MAID_COMBAT = create("pleiads_maid_combat", 50,
			Map.of(
					ArmorItem.Type.BOOTS, 4,
					ArmorItem.Type.LEGGINGS, 7,
					ArmorItem.Type.CHESTPLATE, 8,
					ArmorItem.Type.HELMET, 3
			),
			50, Holder.direct(ExtraBotanySounds.ARMOR_EQUIP_MAID), 4, 0,
			() -> Ingredient.of(ExtraBotanyItems.dasRheingold));

	public static final Holder<ArmorMaterial> GOBLIN_SLAYER = create("goblin_slayer", 21,
			Map.of(
					ArmorItem.Type.BOOTS, 3,
					ArmorItem.Type.LEGGINGS, 6,
					ArmorItem.Type.CHESTPLATE, 6,
					ArmorItem.Type.HELMET, 3
			),
			40, Holder.direct(ExtraBotanySounds.ARMOR_EQUIP_GOBLIN), 2.5F, 0,
			() -> Ingredient.of(ExtraBotanyItems.photonium));

	public static final Holder<ArmorMaterial> SHADOW_WARRIOR = create("shadow_warrior", 23,
			Map.of(
					ArmorItem.Type.BOOTS, 3,
					ArmorItem.Type.LEGGINGS, 7,
					ArmorItem.Type.CHESTPLATE, 6,
					ArmorItem.Type.HELMET, 3
			),
			28, Holder.direct(ExtraBotanySounds.ARMOR_EQUIP_WARRIOR), 1.5F, 0,
			() -> Ingredient.of(ExtraBotanyItems.shadowium));

	private ArmorsMaterial() {}

	private static Holder<ArmorMaterial> create(
			String name,
			int durabilityMultiplier,
			Map<ArmorItem.Type, Integer> defense,
			int enchantmentValue,
			Holder<SoundEvent> equipSound,
			float toughness,
			float knockbackResistance,
			Supplier<Ingredient> repairIngredient) {
		EnumMap<ArmorItem.Type, Integer> defenseByType = new EnumMap<>(ArmorItem.Type.class);
		for (ArmorItem.Type type : ArmorItem.Type.values()) {
			Integer typeDefense = defense.get(type);
			if (typeDefense != null) {
				defenseByType.put(type, typeDefense);
			}
		}

		ResourceLocation id = prefix(name);
		RegistryHelper.HolderProxy<ArmorMaterial> proxy = RegistryHelper.lazyHolderProxy(Registries.ARMOR_MATERIAL, id,
				() -> new ArmorMaterial(
						defenseByType,
						enchantmentValue,
						equipSound,
						repairIngredient,
						List.of(new ArmorMaterial.Layer(id)),
						toughness,
						knockbackResistance));
		ALL.add(proxy);
		return proxy;
	}

	public static void registerArmorMaterials(Registry<ArmorMaterial> registry) {
		ALL.forEach(proxy -> proxy.register(registry));
	}
}
