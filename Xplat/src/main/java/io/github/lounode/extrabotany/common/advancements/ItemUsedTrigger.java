package io.github.lounode.extrabotany.common.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ItemUsedTrigger extends SimpleCriterionTrigger<ItemUsedTrigger.Instance> {
	public static final ResourceLocation ID = prefix("item_used");
	public static final ItemUsedTrigger INSTANCE = new ItemUsedTrigger();

	private ItemUsedTrigger() {}

	public void trigger(ServerPlayer player, ItemStack stack, int count) {
		this.trigger(player, instance -> instance.matches(stack, count));
	}

	@Override
	public Codec<Instance> codec() {
		return Instance.CODEC;
	}

	public record Instance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item, MinMaxBounds.Ints count) implements SimpleInstance {
		public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
				ItemPredicate.CODEC.optionalFieldOf("item").forGetter(Instance::item),
				MinMaxBounds.Ints.CODEC.optionalFieldOf("count", MinMaxBounds.Ints.ANY).forGetter(Instance::count)
		).apply(instance, Instance::new));

		public static Criterion<Instance> itemUsed(ItemPredicate item, MinMaxBounds.Ints count) {
			return INSTANCE.createCriterion(new Instance(Optional.empty(), Optional.of(item), count));
		}

		public boolean matches(ItemStack stack, int count) {
			return this.item.map(predicate -> predicate.test(stack)).orElse(true) && this.count.matches(count);
		}
	}
}
