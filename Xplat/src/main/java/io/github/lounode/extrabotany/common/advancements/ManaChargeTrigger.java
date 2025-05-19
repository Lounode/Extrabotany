package io.github.lounode.extrabotany.common.advancements;

import com.google.gson.JsonObject;

import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ManaChargeTrigger extends SimpleCriterionTrigger<ManaChargeTrigger.TriggerInstance> {
	public static final ResourceLocation ID = prefix("mana_charge");
	public static final ManaChargeTrigger INSTANCE = new ManaChargeTrigger();

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	public void trigger(ServerPlayer player, ItemStack itemStack, long mana) {
		this.trigger(player, instance -> instance.matches(itemStack, mana));
	}

	@Override
	public TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
		ItemPredicate itemPredicate = ItemPredicate.fromJson(json.get("item"));
		MinMaxBoundsExtension.Longs countPredicate = MinMaxBoundsExtension.Longs.fromJson(json.get("mana"));
		return new ManaChargeTrigger.TriggerInstance(predicate, itemPredicate, countPredicate);
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {
		private final ItemPredicate item;
		private final MinMaxBoundsExtension.Longs mana;

		public TriggerInstance(ContextAwarePredicate predicate, ItemPredicate item, MinMaxBoundsExtension.Longs count) {
			super(ID, predicate);
			this.item = item;
			this.mana = count;
		}

		public static ManaChargeTrigger.TriggerInstance manaCharged(ItemPredicate item, MinMaxBoundsExtension.Longs mana) {
			return new ManaChargeTrigger.TriggerInstance(ContextAwarePredicate.ANY, item, mana);
		}

		public boolean matches(ItemStack stack, long mana) {
			return this.item.matches(stack) && this.mana.matches(mana);
		}

		@Override
		public JsonObject serializeToJson(SerializationContext context) {
			JsonObject json = super.serializeToJson(context);
			json.add("item", this.item.serializeToJson());
			json.add("mana", this.mana.serializeToJson());
			return json;
		}
	}
}
