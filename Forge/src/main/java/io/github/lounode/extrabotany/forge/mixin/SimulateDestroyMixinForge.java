package io.github.lounode.extrabotany.forge.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayerGameMode;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.lounode.extrabotany.common.util.PlayerUtil;

@Mixin(ServerPlayerGameMode.class)
public class SimulateDestroyMixinForge {

	@Inject(
		method = "destroyBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/ItemStack;mineBlock(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;)V"
		),
		cancellable = true
	)
	private void onDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (pos instanceof PlayerUtil.SimulateDestroyBlockPos) {
			cir.setReturnValue(true);
		}
	}

	@Inject(
		method = "destroyBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerPlayerGameMode;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z",
			ordinal = 0
		),
		cancellable = true
	)
	private void onCreativeDestroyBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		if (pos instanceof PlayerUtil.SimulateDestroyBlockPos) {
			cir.setReturnValue(true);
		}
	}
}
