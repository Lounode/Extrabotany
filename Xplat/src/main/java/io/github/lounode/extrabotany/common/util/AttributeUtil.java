package io.github.lounode.extrabotany.common.util;

import com.google.common.collect.Multimap;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AttributeUtil {
	public static final DecimalFormat ATTRIBUTE_MODIFIER_FORMAT = Util.make(new DecimalFormat("#.##"), (format) -> {
		format.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
	});

	public static void addAttributeModifier(ItemStack stack, Attribute attribute, AttributeModifier modifier, @Nullable EquipmentSlot slot) {
		stack.getOrCreateTag();
		if (!stack.getTag().contains("AttributeModifiers", Tag.TAG_LIST)) {
			stack.getTag().put("AttributeModifiers", new ListTag());
		}

		ListTag listtag = stack.getTag().getList("AttributeModifiers", Tag.TAG_COMPOUND);
		CompoundTag compoundtag = modifier.save();
		compoundtag.putString("AttributeName", BuiltInRegistries.ATTRIBUTE.getKey(attribute).toString());
		if (slot != null) {
			compoundtag.putString("Slot", slot.getName());
		}

		listtag.add(compoundtag);
	}

	public static void removeAttributeModifier(ItemStack stack, String modifierName) {
		CompoundTag tag = stack.getTag();
		if (tag == null || !tag.contains("AttributeModifiers", Tag.TAG_LIST)) {
			return;
		}

		ListTag modifiers = tag.getList("AttributeModifiers", Tag.TAG_COMPOUND);
		ListTag newModifiers = new ListTag();

		for (Tag t : modifiers) {
			CompoundTag modifier = (CompoundTag) t;
			if (!modifier.getString("Name").equals(modifierName)) {
				newModifiers.add(modifier);
			}
		}

		if (newModifiers.size() != modifiers.size()) {
			tag.put("AttributeModifiers", newModifiers);
		}

		if (newModifiers.isEmpty()) {
			tag.remove("AttributeModifiers");
		}
	}

	public static List<Component> getTooltips(Multimap<Attribute, AttributeModifier> multimap) {
		List<Component> list = new ArrayList<>();
		for (Map.Entry<Attribute, AttributeModifier> entry : multimap.entries()) {
			AttributeModifier attributemodifier = entry.getValue();
			double d0 = attributemodifier.getAmount();
			boolean flag = false;

			double d1;
			if (attributemodifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
				if (entry.getKey().equals(Attributes.KNOCKBACK_RESISTANCE)) {
					d1 = d0 * 10.0D;
				} else {
					d1 = d0;
				}
			} else {
				d1 = d0 * 100.0D;
			}

			if (d0 > 0.0D) {
				list.add(Component.translatable("attribute.modifier.plus." + attributemodifier.getOperation().toValue(), ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(entry.getKey().getDescriptionId())).withStyle(ChatFormatting.BLUE));
			} else if (d0 < 0.0D) {
				d1 *= -1.0D;
				list.add(Component.translatable("attribute.modifier.take." + attributemodifier.getOperation().toValue(), ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(entry.getKey().getDescriptionId())).withStyle(ChatFormatting.RED));
			}
		}

		return list;
	}
}
