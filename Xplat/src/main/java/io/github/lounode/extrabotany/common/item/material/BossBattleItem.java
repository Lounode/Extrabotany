package io.github.lounode.extrabotany.common.item.material;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.common.entity.GaiaGuardianEntity;

public class BossBattleItem extends Item {

    public BossBattleItem(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        ItemStack stack = ctx.getItemInHand();

        if (stack.is(ExtraBotanyItems.challengeTicket)) {
            return GaiaGuardianEntity.spawn(ctx.getPlayer(), stack, ctx.getLevel(), ctx.getClickedPos(), true)
                    ? InteractionResult.sidedSuccess(ctx.getLevel().isClientSide())
                    : InteractionResult.FAIL;
        }

        return super.useOn(ctx);
    }
}
