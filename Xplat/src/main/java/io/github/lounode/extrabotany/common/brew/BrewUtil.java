package io.github.lounode.extrabotany.common.brew;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import net.minecraft.ChatFormatting;
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

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.brew.BotaniaBrews;
import vazkii.botania.common.helper.ItemNBTHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BrewUtil {
	public static final Brew EMPTY = BotaniaBrews.fallbackBrew;
	public static final String TAG_BREW_KEY = "brewKey";

	public static Brew getBrew(ItemStack stack) {
		String key = ItemNBTHelper.getString(stack, TAG_BREW_KEY, "");
		Registry<Brew> registry = BotaniaAPI.instance().getBrewRegistry();
		if (registry == null) {
			return BotaniaBrews.fallbackBrew;
		}
		ResourceLocation location = ResourceLocation.tryParse(key);
		if (location == null) {
			return BotaniaBrews.fallbackBrew;
		}
		Brew brew = registry.get(location);
		return brew != null ? brew : BotaniaBrews.fallbackBrew;
	}

	public static void setBrew(ItemStack stack, Brew brew) {
		ResourceLocation id = Objects.requireNonNull(BotaniaAPI.instance().getBrewRegistry()).getKey(brew);
		ItemNBTHelper.setString(stack, TAG_BREW_KEY, id.toString());
	}

	public static boolean hasInstantEffects(Brew brew) {
		if (!getPotionEffects(brew).isEmpty()) {
			for (MobEffectInstance mobeffectinstance : getPotionEffects(brew)) {
				if (mobeffectinstance.getEffect().isInstantenous()) {
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

		List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
		if (list.isEmpty()) {
			lores.add((Component.translatable("effect.none")).withStyle(ChatFormatting.GRAY));
		} else {
			for (MobEffectInstance effectinstance : list) {
				MutableComponent iformattabletextcomponent = Component.translatable(effectinstance.getDescriptionId());
				MobEffect effect = effectinstance.getEffect();
				Map<Attribute, AttributeModifier> map = effect.getAttributeModifiers();
				if (!map.isEmpty()) {
					for (Map.Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
						AttributeModifier attributemodifier = entry.getValue();
						AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), effect.getAttributeModifierValue(effectinstance.getAmplifier() + amplifierAddition, attributemodifier), attributemodifier.getOperation());
						list1.add(new Pair<>(entry.getKey(), attributemodifier1));
					}
				}

				if (effectinstance.getAmplifier() + amplifierAddition > 0) {
					iformattabletextcomponent = Component.translatable("potion.withAmplifier", iformattabletextcomponent, Component.translatable("potion.potency." + (effectinstance.getAmplifier() + amplifierAddition)));
				}

				if (effectinstance.getDuration() > 20) {
					iformattabletextcomponent = Component.translatable("potion.withDuration", iformattabletextcomponent, MobEffectUtil.formatDuration(effectinstance, durationFactor));
				}

				lores.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));
			}
		}

		if (!list1.isEmpty()) {
			lores.add(Component.empty());
			lores.add((Component.translatable("potion.whenDrank")).withStyle(ChatFormatting.DARK_PURPLE));

			for (Pair<Attribute, AttributeModifier> pair : list1) {
				AttributeModifier attributemodifier2 = pair.getSecond();
				double d0 = attributemodifier2.getAmount();
				double d1;
				if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
					d1 = attributemodifier2.getAmount();
				} else {
					d1 = attributemodifier2.getAmount() * 100.0D;
				}

				if (d0 > 0.0D) {
					lores.add((Component.translatable("attribute.modifier.plus." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
				} else if (d0 < 0.0D) {
					d1 = d1 * -1.0D;
					lores.add((Component.translatable("attribute.modifier.take." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), Component.translatable(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.RED));
				}
			}
		}
	}
}
