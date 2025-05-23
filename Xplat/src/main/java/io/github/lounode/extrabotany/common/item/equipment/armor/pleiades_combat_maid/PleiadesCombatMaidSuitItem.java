package io.github.lounode.extrabotany.common.item.equipment.armor.pleiades_combat_maid;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.mana.ManaDiscountArmor;
import vazkii.botania.api.mana.ManaItemHandler;

import io.github.lounode.extrabotany.common.item.equipment.bauble.NatureOrbItem;

public class PleiadesCombatMaidSuitItem extends PleiadesCombatMaidArmorItem implements ManaDiscountArmor {
	public PleiadesCombatMaidSuitItem(Properties properties) {
		super(Type.CHESTPLATE, properties);
	}

	@Override
	public float getDiscount(ItemStack stack, int slot, Player player, @Nullable ItemStack tool) {
		return hasArmorSet(player) ? 0.3F : 0;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, world, entity, slot, selected);
		if (!(entity instanceof Player player)) {
			return;
		}
		if (!player.getInventory().armor.contains(stack)) {
			return;
		}
		if (!hasArmorSet(player)) {
			return;
		}

		if (player.tickCount % 80 == 0 && ManaItemHandler.instance().requestManaExactForTool(stack, player, 20, true)) {
			player.heal(1F);
		}

		if (player.tickCount % 40 == 0) {
			NatureOrbItem.clearHarmfulPotion(player);
		}

		ManaItemHandler.instance().dispatchManaExact(stack, player, 1, true);
	}
}
