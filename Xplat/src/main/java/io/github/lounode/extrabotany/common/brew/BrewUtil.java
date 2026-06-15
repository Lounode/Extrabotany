package io.github.lounode.extrabotany.common.brew;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.brew.BotaniaBrews;
import vazkii.botania.common.component.BotaniaDataComponents;
import io.github.lounode.extrabotany.common.util.ItemStackDataHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BrewUtil {
	public static final Brew EMPTY = BotaniaBrews.fallbackBrew;
	public static final String TAG_BREW_KEY = "brewKey";

	public static Brew getBrew(ItemStack stack) {
		Registry<Brew> registry = BotaniaAPI.instance().getBrewRegistry();
		if (registry == null) {
			return BotaniaBrews.fallbackBrew;
		}

		ResourceLocation componentLocation = stack.get(BotaniaDataComponents.BREW);
		if (componentLocation != null) {
			Brew componentBrew = registry.get(componentLocation);
			if (componentBrew != null) {
				return componentBrew;
			}
		}

		String key = ItemStackDataHelper.getString(stack, TAG_BREW_KEY, "");
		ResourceLocation location = ResourceLocation.tryParse(key);
		if (location == null) {
			return BotaniaBrews.fallbackBrew;
		}
		Brew brew = registry.get(location);
		return brew != null ? brew : BotaniaBrews.fallbackBrew;
	}

	public static void setBrew(ItemStack stack, Brew brew) {
		ResourceLocation id = Objects.requireNonNull(BotaniaAPI.instance().getBrewRegistry()).getKey(brew);
		setBrew(stack, id);
	}

	public static void setBrew(ItemStack stack, ResourceLocation id) {
		stack.set(BotaniaDataComponents.BREW, id);
		ItemStackDataHelper.setString(stack, TAG_BREW_KEY, id.toString());
	}

	public static boolean hasInstantEffects(Brew brew) {
		if (!getPotionEffects(brew).isEmpty()) {
			for (MobEffectInstance mobeffectinstance : getPotionEffects(brew)) {
				if (mobeffectinstance.getEffect().value().isInstantenous()) {
					return true;
				}
			}
		}

		return false;
	}

	public static List<MobEffectInstance> getPotionEffects(Brew brew) {
		//Why need an itemstack and unused in code???
		if (!brew.getPotionEffects(new ItemStack(Items.AIR)).isEmpty()) {
			return brew.getPotionEffects(new ItemStack(Items.AIR));
		}
		return new ArrayList<>();
	}

	public static int getColor(Brew brew) {
		//Also why???
		return brew.getColor(new ItemStack(Items.AIR));
	}

	public static void addPotionTooltip(Brew brew, List<Component> lores, float durationFactor, int amplifierAddition) {
		List<MobEffectInstance> list = getPotionEffects(brew);

		List<Pair<Holder<Attribute>, AttributeModifier>> list1 = Lists.newArrayList();
		if (list.isEmpty()) {
			lores.add((Component.translatable("effect.none")).withStyle(ChatFormatting.GRAY));
		} else {
			for (MobEffectInstance effectinstance : list) {
				MutableComponent iformattabletextcomponent = Component.translatable(effectinstance.getDescriptionId());
				MobEffect effect = effectinstance.getEffect().value();
				effect.createModifiers(effectinstance.getAmplifier() + amplifierAddition, (attribute, modifier) -> list1.add(new Pair<>(attribute, modifier)));

				if (effectinstance.getAmplifier() + amplifierAddition > 0) {
					iformattabletextcomponent = Component.translatable("potion.withAmplifier", iformattabletextcomponent, Component.translatable("potion.potency." + (effectinstance.getAmplifier() + amplifierAddition)));
				}

				if (effectinstance.getDuration() > 20) {
					iformattabletextcomponent = Component.translatable("potion.withDuration", iformattabletextcomponent, MobEffectUtil.formatDuration(effectinstance, durationFactor, 20));
				}

				lores.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
			}
		}

		if (!list1.isEmpty()) {
			lores.add(Component.empty());
			lores.add((Component.translatable("potion.whenDrank")).withStyle(ChatFormatting.DARK_PURPLE));

			for (Pair<Holder<Attribute>, AttributeModifier> pair : list1) {
				AttributeModifier attributemodifier2 = pair.getSecond();
				double d0 = attributemodifier2.amount();
				double d1;
				if (attributemodifier2.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_BASE && attributemodifier2.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
					d1 = attributemodifier2.amount();
				} else {
					d1 = attributemodifier2.amount() * 100.0D;
				}

				if (d0 > 0.0D) {
					lores.add((Component.translatable("attribute.modifier.plus." + attributemodifier2.operation().id(), ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().value().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
				} else if (d0 < 0.0D) {
					d1 = d1 * -1.0D;
					lores.add((Component.translatable("attribute.modifier.take." + attributemodifier2.operation().id(), ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().value().getDescriptionId()))).withStyle(ChatFormatting.RED));
				}
			}
		}
	}
}
