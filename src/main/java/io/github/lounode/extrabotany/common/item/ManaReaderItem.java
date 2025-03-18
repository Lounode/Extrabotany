package io.github.lounode.extrabotany.common.item;

import io.github.lounode.extrabotany.common.advancements.ItemUsedTrigger;
import io.github.lounode.extrabotany.network.clientbound.ManaReaderPacket;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkEvent;
import vazkii.botania.api.block.Wandable;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.mana.ManaReceiver;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;
import vazkii.botania.common.handler.BotaniaSounds;
import vazkii.botania.xplat.XplatAbstractions;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ManaReaderItem extends Item {
    public ManaReaderItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        Level world = ctx.getLevel();
        Player player = ctx.getPlayer();
        BlockPos pos = ctx.getClickedPos();

        if (player == null)
            return InteractionResult.PASS;

        BlockEntity tile = world.getBlockEntity(pos);
        if (tile instanceof ManaReceiver|| tile instanceof GeneratingFlowerBlockEntity) {
            if (!world.isClientSide) {
                int manaValue = 0;

                if (tile instanceof ManaReceiver receiver) {
                    manaValue = receiver.getCurrentMana();
                }
                if (tile instanceof GeneratingFlowerBlockEntity flower) {
                    manaValue = flower.getMana();
                }

                EXplatAbstractions.INSTANCE.sendToPlayer(player, new ManaReaderPacket(manaValue));

                int currentCount = ItemUsedTrigger.ItemUsedCounter.getCount(player, this);
                ItemUsedTrigger.ItemUsedCounter.increment(player, this);

                ItemUsedTrigger.INSTANCE.trigger(
                        (ServerPlayer) player,
                        ctx.getItemInHand(),
                        currentCount + 1
                );
            } else {
                player.playSound(BotaniaSounds.ding, 0.6F, 0.1F + world.random.nextFloat() * 0.5F);
            }
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return InteractionResult.PASS;
    }
    //TODO 千本樱
}


