package io.github.lounode.extrabotany.mixin;

import com.google.common.collect.Lists;
import io.github.lounode.extrabotany.common.item.enchantment.ICustomEnchantable;
import net.minecraft.core.registries.BuiltInRegistries;
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

import java.util.List;

@Mixin(EnchantmentHelper.class)
public class ItemCustomEnchantableEnchantTableSupporter {
    @Inject(method = "getAvailableEnchantmentResults", at = @At("RETURN"), remap = false, cancellable = true)
    private static void getFixedAvailableEnchantmentResults(int level, ItemStack stack, boolean allowTreasure, CallbackInfoReturnable<List<EnchantmentInstance>> cir) {
        if (!(stack.getItem() instanceof ICustomEnchantable supporter)) {
            return;
        }
        List<EnchantmentInstance> list = Lists.newArrayList();
        Item item = stack.getItem();
        boolean flag = stack.is(Items.BOOK);

        for(Enchantment enchantment : BuiltInRegistries.ENCHANTMENT) {
            if ((!enchantment.isTreasureOnly() || allowTreasure) && enchantment.isDiscoverable() && (supporter.canEnchantOnTable(stack, enchantment) || flag)) {
                for(int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                    if (level >= enchantment.getMinCost(i) && level <= enchantment.getMaxCost(i)) {
                        list.add(new EnchantmentInstance(enchantment, i));
                        break;
                    }
                }
            }
        }

        cir.setReturnValue(list);
    }
}
