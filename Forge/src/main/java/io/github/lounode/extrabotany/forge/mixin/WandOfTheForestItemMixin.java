package io.github.lounode.extrabotany.forge.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.item.WandOfTheForestItem;

@Mixin(WandOfTheForestItem.class)
public class WandOfTheForestItemMixin {
    @Inject(method = "use", at = @At("TAIL"), remap = false)
    private void switchManaReaderMode(Level world, Player player, @NotNull InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (!world.isClientSide) {
            //player.sendSystemMessage(Component.literal("USE"));
        }
    }
}
