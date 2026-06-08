package io.github.lounode.extrabotany.common.item.equipment.shield;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.handler.PixieHandler;

public class ElementiumShieldItem extends ManasteelShieldItem {

	private static final int MANA_PER_DAMAGE = 70;

	public ElementiumShieldItem(Properties properties, Tier tier) {
		super(properties, tier);
	}

	public ElementiumShieldItem(Properties properties) {
		this(properties, BotaniaAPI.instance().getElementiumItemTier());
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
		Multimap<Attribute, AttributeModifier> ret = super.getDefaultAttributeModifiers(slot);
		if (slot == EquipmentSlot.OFFHAND) {
			ret = HashMultimap.create(ret);
			ret.put(PixieHandler.PIXIE_SPAWN_CHANCE, PixieHandler.makeModifier(slot, "Shield modifier", 0.2F));
		}
		return ret;
	}

	@Override
	public void onShieldBlock(ItemStack stack, LivingEntity blocker, DamageSource source, float damage) {
		super.onShieldBlock(stack, blocker, source, damage);
		Entity entity = source.getEntity();
		if (entity != null) if (!entity.fireImmune() && entity != blocker) {
            entity.setSecondsOnFire(5);
        }
	}

	@Override
	public int getManaPerDamage() {
		return MANA_PER_DAMAGE;
	}
}
