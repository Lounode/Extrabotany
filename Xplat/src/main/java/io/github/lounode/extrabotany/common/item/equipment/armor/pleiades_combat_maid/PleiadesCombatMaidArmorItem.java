package io.github.lounode.extrabotany.common.item.equipment.armor.pleiades_combat_maid;

import com.google.common.base.Suppliers;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

import io.github.lounode.extrabotany.api.ExtraBotanyAPI;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.item.equipment.armor.starry_idol.StarryIdolArmorItem;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class PleiadesCombatMaidArmorItem extends StarryIdolArmorItem {

	private static final Supplier<ItemStack[]> ARMOR_SET = Suppliers.memoize(() -> new ItemStack[] {
			new ItemStack(ExtraBotanyItems.pleiadesCombatMaidHeadgear),
			new ItemStack(ExtraBotanyItems.pleiadesCombatMaidSuit),
			new ItemStack(ExtraBotanyItems.pleiadesCombatMaidSkirt),
			new ItemStack(ExtraBotanyItems.pleiadesCombatMaidBoots)
	});

	public PleiadesCombatMaidArmorItem(Type type, Properties properties) {
		this(ExtraBotanyAPI.instance().getPleiadsMaidCombatArmorMaterial(), type, properties);
	}

	public PleiadesCombatMaidArmorItem(ArmorMaterial material, Type type, Properties properties) {
		super(material, type, properties);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
		Multimap<Attribute, AttributeModifier> ret = super.getDefaultAttributeModifiers(slot);

		//UUID uuid = new UUID((getDescriptionId() + equipmentSlot).hashCode(), 0);
		/*
		result.put(Attributes.MAX_HEALTH,
				new AttributeModifier("Combatmaid modifier" + slot, 5.0D, AttributeModifier.Operation.ADDITION));
		
		switch (slot) {
			case HEAD -> result.put(Attributes.KNOCKBACK_RESISTANCE,
					new AttributeModifier("Combatmaid modifier" + slot, 0.15D, AttributeModifier.Operation.ADDITION));
			case CHEST -> result.put(Attributes.KNOCKBACK_RESISTANCE,
					new AttributeModifier("Combatmaid modifier" + slot, 0.4D, AttributeModifier.Operation.ADDITION));
			case LEGS -> result.put(Attributes.KNOCKBACK_RESISTANCE,
					new AttributeModifier("Combatmaid modifier" + slot, 0.35D, AttributeModifier.Operation.ADDITION));
			case FEET -> result.put(Attributes.KNOCKBACK_RESISTANCE,
					new AttributeModifier("Combatmaid modifier" + slot, 0.2D, AttributeModifier.Operation.ADDITION));
		}
		
		*/

		if (slot == getType().getSlot()) {
			UUID uuid = new UUID(BuiltInRegistries.ITEM.getKey(this).hashCode() + slot.toString().hashCode(), 0);
			ret = HashMultimap.create(ret);
			int reduction = getMaterial().getDefenseForType(getType());
			ret.put(Attributes.KNOCKBACK_RESISTANCE,
					new AttributeModifier(uuid, "Combatmaid modifier " + type, (double) reduction / 20, AttributeModifier.Operation.ADDITION));
		}
		return ret;
	}

	@Override
	public MutableComponent getArmorSetName() {
		return Component.translatable("extrabotany.armorset.pleiades_combat_maid.name");
	}

	@Override
	public void addArmorSetDescription(ItemStack stack, List<Component> list) {
		list.add(Component.translatable("extrabotany.armorset.pleiades_combat_maid.desc0").withStyle(ChatFormatting.GRAY));
		list.add(Component.translatable("extrabotany.armorset.pleiades_combat_maid.desc1").withStyle(ChatFormatting.GRAY));
		list.add(Component.translatable("extrabotany.armorset.pleiades_combat_maid.desc2").withStyle(ChatFormatting.GRAY));
	}

	@Override
	public boolean hasArmorSetItem(Player player, EquipmentSlot slot) {
		if (player == null || player.getInventory() == null || player.getInventory().armor == null) {
			return false;
		}

		ItemStack stack = player.getItemBySlot(slot);
		if (stack.isEmpty()) {
			return false;
		}

		return switch (slot) {
			case HEAD -> stack.is(ExtraBotanyItems.pleiadesCombatMaidHeadgear);
			case CHEST -> stack.is(ExtraBotanyItems.pleiadesCombatMaidSuit) || stack.is(ExtraBotanyItems.pleiadesCombatMaidSuitDarkened);
			case LEGS -> stack.is(ExtraBotanyItems.pleiadesCombatMaidSkirt);
			case FEET -> stack.is(ExtraBotanyItems.pleiadesCombatMaidBoots);
			default -> false;
		};
	}

	@Override
	public ItemStack[] getArmorSetStacks() {
		return ARMOR_SET.get();
	}
}
