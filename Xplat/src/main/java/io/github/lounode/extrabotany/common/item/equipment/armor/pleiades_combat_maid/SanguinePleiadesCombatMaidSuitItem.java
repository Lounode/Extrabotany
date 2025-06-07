package io.github.lounode.extrabotany.common.item.equipment.armor.pleiades_combat_maid;

import com.google.common.base.Suppliers;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.common.brew.BotaniaMobEffects;
import vazkii.botania.common.brew.effect.BloodthirstMobEffect;

import io.github.lounode.eventwrapper.event.entity.living.LivingDamageEventWrapper;
import io.github.lounode.eventwrapper.event.entity.living.MobEffectEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class SanguinePleiadesCombatMaidSuitItem extends PleiadesCombatMaidSuitItem {

	public static final Supplier<ItemStack[]> ARMOR_SET = Suppliers.memoize(() -> new ItemStack[] {
			new ItemStack(ExtraBotanyItems.pleiadesCombatMaidHeadgear),
			new ItemStack(ExtraBotanyItems.sanguinePleiadesCombatMaidSuit),
			new ItemStack(ExtraBotanyItems.pleiadesCombatMaidSkirt),
			new ItemStack(ExtraBotanyItems.pleiadesCombatMaidBoots)
	});
	public static int SANGUINE_KILL_REQUIRE = 50;
	public static final float HEAL_RATE = 0.3F;

	public SanguinePleiadesCombatMaidSuitItem(Properties properties) {
		super(properties);
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
		Multimap<Attribute, AttributeModifier> ret = super.getDefaultAttributeModifiers(slot);

		if (slot == getType().getSlot()) {
			UUID uuid = new UUID(BuiltInRegistries.ITEM.getKey(this).hashCode() + slot.toString().hashCode(), 0);
			ret = HashMultimap.create(ret);
			ret.removeAll(Attributes.MAX_HEALTH);
			ret.put(Attributes.MAX_HEALTH,
					new AttributeModifier(uuid, "Combatmaid modifier" + type, 15, AttributeModifier.Operation.ADDITION));
		}
		return ret;
	}

	@Override
	public ItemStack[] getArmorSetStacks() {
		return ARMOR_SET.get();
	}

	public static boolean isSenketsu(ItemStack stack) {
		String name = stack.getHoverName().getString().toLowerCase(Locale.ROOT).trim();
		return "senketsu".equals(name);
	}

	@EventBusSubscriberWrapper
	public static class EventHandler {
		@SubscribeEventWrapper
		public static void onAttackLiving(LivingDamageEventWrapper event) {
			Entity attacker = event.getSource().getEntity();
			if (!(attacker instanceof LivingEntity living)) {
				return;
			}
			if (!living.getItemBySlot(EquipmentSlot.CHEST).is(ExtraBotanyItems.sanguinePleiadesCombatMaidSuit)) {
				return;
			}

			living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 10, 1));
			living.heal(HEAL_RATE * event.getAmount());
		}

		//Get Blood Suit
		private static final Map<UUID, Integer> bloodthirstKilled = new ConcurrentHashMap<>();

		@SubscribeEventWrapper
		public static void onEffectAdded(MobEffectEventWrapper.Added event) {
			if (!(event.getEffectInstance().getEffect() instanceof BloodthirstMobEffect)) {
				return;
			}
			if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) {
				return;
			}
			bloodthirstKilled.put(serverPlayer.getUUID(), 0);
		}

		//TODO LivingDeathEvent
		@SubscribeEventWrapper
		public static void onKilled(LivingDamageEventWrapper event) {
			if (event.getEntity().getHealth() - event.getAmount() > 0) {
				return;
			}
			if (!(event.getSource().getEntity() instanceof ServerPlayer serverPlayer)) {
				return;
			}
			if (!serverPlayer.hasEffect(BotaniaMobEffects.bloodthrst)) {
				return;
			}

			bloodthirstKilled.computeIfPresent(serverPlayer.getUUID(), (uuid, count) -> count + 1);
		}

		@SubscribeEventWrapper
		public static void onEffectRemove(MobEffectEventWrapper.Remove event) {
			if (event.getEntity() instanceof ServerPlayer serverPlayer) {
				onEffectRemovedOrExpired(serverPlayer, event.getEffectInstance());
			}
		}

		@SubscribeEventWrapper
		public static void onEffectExpired(MobEffectEventWrapper.Expired event) {
			if (event.getEntity() instanceof ServerPlayer serverPlayer) {
				onEffectRemovedOrExpired(serverPlayer, event.getEffectInstance());
			}
		}

		private static void onEffectRemovedOrExpired(ServerPlayer serverPlayer, @Nullable MobEffectInstance instance) {
			if (instance == null) {
				return;
			}
			if (!(instance.getEffect() instanceof BloodthirstMobEffect)) {
				return;
			}
			if (!bloodthirstKilled.containsKey(serverPlayer.getUUID())) {
				return;
			}

			replaceArmor(serverPlayer);
		}

		private static void replaceArmor(ServerPlayer serverPlayer) {
			int killed = bloodthirstKilled.get(serverPlayer.getUUID());
			bloodthirstKilled.remove(serverPlayer.getUUID());

			ItemStack origin = serverPlayer.getItemBySlot(EquipmentSlot.CHEST);

			if (killed >= SANGUINE_KILL_REQUIRE && origin.is(ExtraBotanyItems.pleiadesCombatMaidSuit)) {
				ItemStack darkened = new ItemStack(ExtraBotanyItems.sanguinePleiadesCombatMaidSuit);

				CompoundTag originTag = origin.getTag();
				if (originTag != null) {
					CompoundTag newTag = originTag.copy();
					darkened.setTag(newTag);
				}

				serverPlayer.setItemSlot(EquipmentSlot.CHEST, darkened);
			}
		}
	}
}
