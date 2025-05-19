package io.github.lounode.extrabotany.mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.lounode.extrabotany.common.item.enchantment.ICustomEnchantable;

@Mixin(Enchantment.class)
public abstract class ItemCustomEnchantableSupporter {
	@Inject(
		method = "canEnchant",
		at = @At("RETURN"),
		cancellable = true
	)
	private void onCanEnchant(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		if (stack.getItem() instanceof ICustomEnchantable checker) {
			cir.setReturnValue(checker.canEnchant(stack, (Enchantment) (Object) this));
		}
	}
}
