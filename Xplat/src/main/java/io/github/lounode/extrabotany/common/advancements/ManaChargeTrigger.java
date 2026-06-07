package io.github.lounode.extrabotany.common.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ManaChargeTrigger extends SimpleCriterionTrigger<ManaChargeTrigger.Instance> {
	public static final ResourceLocation ID = prefix("mana_charge");
	public static final ManaChargeTrigger INSTANCE = new ManaChargeTrigger();

	private ManaChargeTrigger() {}

	public void trigger(ServerPlayer player, ItemStack itemStack, long mana) {
		this.trigger(player, instance -> instance.matches(itemStack, mana));
	}

	@Override
	public Codec<Instance> codec() {
		return Instance.CODEC;
	}

	public record Instance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> item, MinMaxBoundsExtension.Longs mana) implements SimpleInstance {
		public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
				ItemPredicate.CODEC.optionalFieldOf("item").forGetter(Instance::item),
				MinMaxBoundsExtension.Longs.CODEC.optionalFieldOf("mana", MinMaxBoundsExtension.Longs.ANY).forGetter(Instance::mana)
		).apply(instance, Instance::new));

		public static Criterion<Instance> manaCharged(ItemPredicate item, MinMaxBoundsExtension.Longs mana) {
			return INSTANCE.createCriterion(new Instance(Optional.empty(), Optional.of(item), mana));
		}

		public boolean matches(ItemStack stack, long mana) {
			return this.item.map(predicate -> predicate.test(stack)).orElse(true) && this.mana.matches(mana);
		}
	}
}
