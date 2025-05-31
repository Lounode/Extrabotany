package io.github.lounode.extrabotany.common.item.equipment.armor.goblin_slayer;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import io.github.lounode.eventwrapper.event.entity.living.LivingHurtEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.lib.ExtraBotanyTags;

public class GoblinSlayerHelmetItem extends GoblinSlayerArmorItem {

	private static final float UNDEAD_DAMAGE_BONUS = 0.5F;

	public GoblinSlayerHelmetItem(Properties properties) {
		super(Type.HELMET, properties);
	}

	@EventBusSubscriberWrapper
	public static class EventHandler {

		@SubscribeEventWrapper
		public static void onPlayerAttack(LivingHurtEventWrapper event) {
			if (!(event.getSource().getEntity() instanceof Player player)) {
				return;
			}
			ItemStack armorStack = player.getItemBySlot(EquipmentSlot.HEAD);
			if (!armorStack.is(ExtraBotanyItems.goblinSlayerHelmet)) {
				return;
			}
			if (!(armorStack.getItem() instanceof GoblinSlayerArmorItem suit)) {
				return;
			}
			if (!suit.hasArmorSet(player)) {
				return;
			}

			if (event.getEntity().getMobType() == MobType.UNDEAD) {
				float origin = event.getAmount();
				event.setAmount(origin * (1 + UNDEAD_DAMAGE_BONUS));
			}

			if (event.getEntity().getType().is(ExtraBotanyTags.Entities.GOBLINS)) {
				event.setAmount(Integer.MAX_VALUE);
			}
		}
	}
}
