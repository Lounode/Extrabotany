package io.github.lounode.extrabotany.common.advancements;

import com.google.gson.JsonObject;

import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

import java.util.Arrays;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class HasArmorSetTrigger extends SimpleCriterionTrigger<HasArmorSetTrigger.TriggerInstance> {

	public static final ResourceLocation ID = prefix("has_armor_set");
	public static final HasArmorSetTrigger INSTANCE = new HasArmorSetTrigger();

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	public void trigger(ServerPlayer player) {
		super.trigger(player, instance -> instance.matches(player));
	}

	@Override
	protected HasArmorSetTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
		JsonObject armorJson = json.getAsJsonObject("armor");
		ItemPredicate[] predicates = new ItemPredicate[4];

		predicates[0] = ItemPredicate.fromJson(armorJson.get("head"));
		predicates[1] = ItemPredicate.fromJson(armorJson.get("chest"));
		predicates[2] = ItemPredicate.fromJson(armorJson.get("legs"));
		predicates[3] = ItemPredicate.fromJson(armorJson.get("feet"));

		return new TriggerInstance(predicate, predicates);
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {
		private final ItemPredicate[] armorPredicates;

		public TriggerInstance(ContextAwarePredicate predicate, ItemPredicate[] armorPredicates) {
			super(ID, predicate);
			this.armorPredicates = armorPredicates;
		}

		public static TriggerInstance forArmorSet(ItemStack[] armorSet) {
			ItemPredicate[] predicates = Arrays.stream(armorSet)
					.map(item -> ItemPredicate.Builder.item().of(item.getItem()).build())
					.toArray(ItemPredicate[]::new);
			return new TriggerInstance(ContextAwarePredicate.ANY, predicates);
		}

		public boolean matches(Player player) {
			if (player.getItemBySlot(EquipmentSlot.CHEST).is(ExtraBotanyItems.sanguinePleiadesCombatMaidSuit)) {
				return matchesDarkened(player);
			}
			return armorPredicates[0].matches(player.getItemBySlot(EquipmentSlot.HEAD)) &&
					armorPredicates[1].matches(player.getItemBySlot(EquipmentSlot.CHEST)) &&
					armorPredicates[2].matches(player.getItemBySlot(EquipmentSlot.LEGS)) &&
					armorPredicates[3].matches(player.getItemBySlot(EquipmentSlot.FEET));
		}

		private boolean matchesDarkened(Player player) {
			return player.getItemBySlot(EquipmentSlot.HEAD).is(ExtraBotanyItems.pleiadesCombatMaidHeadgear) && armorPredicates[0].matches(player.getItemBySlot(EquipmentSlot.HEAD)) &&
					player.getItemBySlot(EquipmentSlot.LEGS).is(ExtraBotanyItems.pleiadesCombatMaidSkirt) && armorPredicates[2].matches(player.getItemBySlot(EquipmentSlot.LEGS)) &&
					player.getItemBySlot(EquipmentSlot.FEET).is(ExtraBotanyItems.pleiadesCombatMaidBoots) && armorPredicates[3].matches(player.getItemBySlot(EquipmentSlot.FEET));
		}

		@Override
		public JsonObject serializeToJson(SerializationContext context) {
			JsonObject json = super.serializeToJson(context);
			JsonObject armorJson = new JsonObject();
			armorJson.add("head", armorPredicates[0].serializeToJson());
			armorJson.add("chest", armorPredicates[1].serializeToJson());
			armorJson.add("legs", armorPredicates[2].serializeToJson());
			armorJson.add("feet", armorPredicates[3].serializeToJson());
			json.add("armor", armorJson);
			return json;
		}
	}
}
