package io.github.lounode.extrabotany.common.item.equipment.shield;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;

import vazkii.botania.api.item.SortableTool;
import vazkii.botania.common.item.equipment.CustomDamageItem;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.function.Consumer;

public class ManasteelShieldItem extends ShieldItem implements CustomDamageItem, SortableTool {

	private static final int MANA_PER_DAMAGE = 60;

	public ManasteelShieldItem(Properties properties) {
		super(properties);
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		int manaPerDamage = ((ManasteelShieldItem) stack.getItem()).getManaPerDamage();
		return ToolCommons.damageItemIfPossible(stack, amount, entity, manaPerDamage);
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return repair.is(BotaniaTags.Items.INGOTS_MANASTEEL);
	}

	public int getManaPerDamage() {
		return MANA_PER_DAMAGE;
	}
}
