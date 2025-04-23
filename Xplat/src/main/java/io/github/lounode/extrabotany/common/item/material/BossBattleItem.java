package io.github.lounode.extrabotany.common.item.material;

import io.github.lounode.extrabotany.common.entity.gaia.Gaia;
import io.github.lounode.extrabotany.common.entity.gaia.GaiaIII;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class BossBattleItem extends Item {

    public BossBattleItem(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        ItemStack stack = ctx.getItemInHand();

        if (stack.is(ExtraBotanyItems.heroMedal)) {
            return Gaia.spawn(ctx.getPlayer(), stack, ctx.getLevel(), ctx.getClickedPos())
                    ? InteractionResult.sidedSuccess(ctx.getLevel().isClientSide())
                    : InteractionResult.FAIL;
        }

        if (stack.is(ExtraBotanyItems.challengeTicket)) {
            return GaiaIII.spawn(ctx.getPlayer(), stack, ctx.getLevel(), ctx.getClickedPos())
                    ? InteractionResult.sidedSuccess(ctx.getLevel().isClientSide())
                    : InteractionResult.FAIL;
        }

        return super.useOn(ctx);
    }
}
