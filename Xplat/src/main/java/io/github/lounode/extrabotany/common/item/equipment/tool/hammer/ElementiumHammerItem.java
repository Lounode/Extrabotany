package io.github.lounode.extrabotany.common.item.equipment.tool.hammer;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import org.jetbrains.annotations.NotNull;

import vazkii.botania.common.handler.PixieHandler;
import vazkii.botania.common.lib.BotaniaTags;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

public class ElementiumHammerItem extends ManasteelHammerItem {

	private static final int MANA_PER_DAMAGE = 70;

	public ElementiumHammerItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
		super(tier, attackDamageModifier, attackSpeedModifier, properties);
	}

	public static boolean shouldFilterOut(Entity e, ItemStack tool, ItemStack drop) {
		if (tool.isEmpty() || !tool.is(ExtraBotanyItems.elementiumHammer)) {
			return false;
		}
		if (e.isShiftKeyDown()) {
			return false;
		}

		return (isDisposable(drop) || isSemiDisposable(drop));
	}

	private static boolean isDisposable(ItemStack stack) {
		if (stack.isEmpty()) {
			return false;
		}

		return stack.is(BotaniaTags.Items.DISPOSABLE);
	}

	private static boolean isSemiDisposable(ItemStack stack) {
		return stack.is(BotaniaTags.Items.SEMI_DISPOSABLE);
	}

	@Override
	public int getManaPerDamage() {
		return MANA_PER_DAMAGE;
	}

	@NotNull
	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot slot) {
		Multimap<Attribute, AttributeModifier> ret = super.getDefaultAttributeModifiers(slot);
		if (slot == EquipmentSlot.MAINHAND) {
			ret = HashMultimap.create(ret);
			ret.put(PixieHandler.PIXIE_SPAWN_CHANCE, PixieHandler.makeModifier(slot, "Sword modifier", 0.05));
		}
		return ret;
	}
}
