package io.github.lounode.extrabotany.common.item.equipment.armor.pleiades_combat_maid;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import io.github.lounode.eventwrapper.event.entity.living.LivingEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

@EventBusSubscriberWrapper
public class PleiadesCombatMaidSuitDarkenedItem extends PleiadesCombatMaidSuitItem {
	public PleiadesCombatMaidSuitDarkenedItem(Properties properties) {
		super(properties);
	}

	@SubscribeEventWrapper
	public static void onLivingTick(LivingEventWrapper.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
		if (!chest.is(ExtraBotanyItems.pleiadesCombatMaidSuit)) {
			return;
		}

	}

	public void addAttributes(LivingEntity entity) {
		//entity.getAttribute()
	}

	public void tryRemoveAttributes(LivingEntity entity) {
		//AttributeInstance healthAttr = entity.getAttribute()
	}
}
