package io.github.lounode.extrabotany.common.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class AttributeUtil {

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
}
