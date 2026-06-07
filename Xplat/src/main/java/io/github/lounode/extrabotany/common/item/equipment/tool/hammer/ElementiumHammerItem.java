package io.github.lounode.extrabotany.common.item.equipment.tool.hammer;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import vazkii.botania.common.handler.PixieHandler;
import vazkii.botania.common.lib.BotaniaTags;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

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

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers() {
		return super.getDefaultAttributeModifiers()
				.withModifierAdded(PixieHandler.PIXIE_SPAWN_CHANCE,
						PixieHandler.makeModifier(prefix("elementium_hammer_pixie"), 0.05),
						EquipmentSlotGroup.bySlot(EquipmentSlot.MAINHAND));
	}
}
