package io.github.lounode.extrabotany.common.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ItemUsedTrigger extends SimpleCriterionTrigger<ItemUsedTrigger.TriggerInstance> {
    public static final ResourceLocation ID = prefix("item_used");
    public static final ItemUsedTrigger INSTANCE = new ItemUsedTrigger();

    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player, ItemStack stack, int count) {
        this.trigger(player, instance -> instance.matches(stack, count));
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
        ItemPredicate itemPredicate = ItemPredicate.fromJson(json.get("item"));
        MinMaxBounds.Ints countPredicate = MinMaxBounds.Ints.fromJson(json.get("count"));
        return new TriggerInstance(predicate, itemPredicate, countPredicate);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;
        private final MinMaxBounds.Ints count;

        public TriggerInstance(ContextAwarePredicate predicate, ItemPredicate item, MinMaxBounds.Ints count) {
            super(ID, predicate);
            this.item = item;
            this.count = count;
        }

        public static TriggerInstance itemUsed(ItemPredicate item, MinMaxBounds.Ints count) {
            return new TriggerInstance(ContextAwarePredicate.ANY, item, count);
        }

        public boolean matches(ItemStack stack, int count) {
            return this.item.matches(stack) && this.count.matches(count);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            json.add("item", this.item.serializeToJson());
            json.add("count", this.count.serializeToJson());
            return json;
        }
    }

    public static class ItemUsedCounter {
        private static final String TAG = "item_used";
        /*


        public static int getCount(Player player, Item item) {
            return player.getPersistentData()
                    .getCompound(ExtraBotany.MODID)
                    .getCompound(TAG)
                    .getInt(ForgeRegistries.ITEMS.getKey(item).toString());
        }

        public static void increment(Player player, Item item) {
            CompoundTag root = player.getPersistentData();
            CompoundTag modData = root.getCompound(ExtraBotany.MODID);
            CompoundTag itemUsedData = modData.getCompound(TAG);
            String key = ForgeRegistries.ITEMS.getKey(item).toString();
            itemUsedData.putInt(key, itemUsedData.getInt(key) + 1);
            modData.put(TAG, itemUsedData);
            root.put(ExtraBotany.MODID, modData);
        }
        */

    }
}