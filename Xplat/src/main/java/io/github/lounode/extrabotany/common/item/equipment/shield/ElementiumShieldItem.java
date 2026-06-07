package io.github.lounode.extrabotany.common.item.equipment.shield;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.handler.PixieHandler;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ElementiumShieldItem extends ManasteelShieldItem {

	private static final int MANA_PER_DAMAGE = 70;

	public ElementiumShieldItem(Properties properties, Tier tier) {
		super(properties, tier);
	}

	public ElementiumShieldItem(Properties properties) {
		this(properties, BotaniaAPI.instance().getElementiumItemTier());
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers() {
		return super.getDefaultAttributeModifiers()
				.withModifierAdded(PixieHandler.PIXIE_SPAWN_CHANCE,
						PixieHandler.makeModifier(prefix("elementium_shield_pixie"), 0.2F),
						EquipmentSlotGroup.bySlot(EquipmentSlot.OFFHAND));
	}

	@Override
	public void onShieldBlock(ItemStack stack, LivingEntity blocker, DamageSource source, float damage) {
		super.onShieldBlock(stack, blocker, source, damage);
		Entity entity = source.getEntity();
		if (entity != null && !entity.fireImmune()) {
			entity.igniteForSeconds(5);
		}
	}

	@Override
	public int getManaPerDamage() {
		return MANA_PER_DAMAGE;
	}
}
