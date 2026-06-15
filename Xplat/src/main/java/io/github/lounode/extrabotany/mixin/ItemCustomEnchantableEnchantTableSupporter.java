package io.github.lounode.extrabotany.mixin;

import com.google.common.collect.Lists;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.lounode.extrabotany.common.item.enchantment.ICustomEnchantable;

import java.util.List;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public class ItemCustomEnchantableEnchantTableSupporter {
	@Inject(method = "getAvailableEnchantmentResults", at = @At("HEAD"), cancellable = true)
	private static void getFixedAvailableEnchantmentResults(int level, ItemStack stack, Stream<Holder<Enchantment>> possibleEnchantments, CallbackInfoReturnable<List<EnchantmentInstance>> cir) {
		if (!(stack.getItem() instanceof ICustomEnchantable supporter)) {
			return;
		}
		List<EnchantmentInstance> list = Lists.newArrayList();
		Item item = stack.getItem();
		boolean flag = stack.is(Items.BOOK);

		possibleEnchantments.forEach(enchantmentHolder -> {
			Enchantment enchantment = enchantmentHolder.value();
			if (supporter.canEnchantOnTable(stack, enchantmentHolder) || flag) {
				for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
					if (level >= enchantment.getMinCost(i) && level <= enchantment.getMaxCost(i)) {
						list.add(new EnchantmentInstance(enchantmentHolder, i));
						break;
					}
				}
			}
		});

		cir.setReturnValue(list);
	}
}
