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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;

import java.util.Arrays;
import java.util.Optional;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class HasArmorSetTrigger extends SimpleCriterionTrigger<HasArmorSetTrigger.Instance> {

	public static final ResourceLocation ID = prefix("has_armor_set");
	public static final HasArmorSetTrigger INSTANCE = new HasArmorSetTrigger();

	private HasArmorSetTrigger() {}

	public void trigger(ServerPlayer player) {
		super.trigger(player, instance -> instance.matches(player));
	}

	@Override
	public Codec<Instance> codec() {
		return Instance.CODEC;
	}

	public record Instance(Optional<ContextAwarePredicate> player, ItemPredicate head, ItemPredicate chest, ItemPredicate legs, ItemPredicate feet) implements SimpleInstance {
		public static final Codec<Instance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(Instance::player),
				ItemPredicate.CODEC.fieldOf("head").forGetter(Instance::head),
				ItemPredicate.CODEC.fieldOf("chest").forGetter(Instance::chest),
				ItemPredicate.CODEC.fieldOf("legs").forGetter(Instance::legs),
				ItemPredicate.CODEC.fieldOf("feet").forGetter(Instance::feet)
		).apply(instance, Instance::new));

		public static Criterion<Instance> forArmorSet(ItemStack[] armorSet) {
			ItemPredicate[] predicates = Arrays.stream(armorSet)
					.map(item -> ItemPredicate.Builder.item().of(item.getItem()).build())
					.toArray(ItemPredicate[]::new);
			return INSTANCE.createCriterion(new Instance(Optional.empty(), predicates[0], predicates[1], predicates[2], predicates[3]));
		}

		public boolean matches(Player player) {
			if (player.getItemBySlot(EquipmentSlot.CHEST).is(ExtraBotanyItems.sanguinePleiadesCombatMaidSuit)) {
				return matchesDarkened(player);
			}
			return this.head.test(player.getItemBySlot(EquipmentSlot.HEAD)) &&
					this.chest.test(player.getItemBySlot(EquipmentSlot.CHEST)) &&
					this.legs.test(player.getItemBySlot(EquipmentSlot.LEGS)) &&
					this.feet.test(player.getItemBySlot(EquipmentSlot.FEET));
		}

		private boolean matchesDarkened(Player player) {
			return player.getItemBySlot(EquipmentSlot.HEAD).is(ExtraBotanyItems.pleiadesCombatMaidHeadgear) && this.head.test(player.getItemBySlot(EquipmentSlot.HEAD)) &&
					player.getItemBySlot(EquipmentSlot.LEGS).is(ExtraBotanyItems.pleiadesCombatMaidSkirt) && this.legs.test(player.getItemBySlot(EquipmentSlot.LEGS)) &&
					player.getItemBySlot(EquipmentSlot.FEET).is(ExtraBotanyItems.pleiadesCombatMaidBoots) && this.feet.test(player.getItemBySlot(EquipmentSlot.FEET));
		}
	}
}
