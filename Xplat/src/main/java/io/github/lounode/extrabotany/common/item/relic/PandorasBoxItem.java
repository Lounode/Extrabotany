package io.github.lounode.extrabotany.common.item.relic;

import io.github.lounode.extrabotany.common.item.RewardBagItem;
import io.github.lounode.extrabotany.common.sounds.ExtraBotanySounds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.item.Relic;
import vazkii.botania.common.item.relic.RelicImpl;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.List;

public class PandorasBoxItem extends RewardBagItem {
    public PandorasBoxItem(Properties properties, ResourceLocation lootTable) {
        super(properties, lootTable);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        var relic = XplatAbstractions.INSTANCE.findRelic(stack);
        if (
                relic == null ||
                !relic.isRightPlayer(player)
        ) {
            return InteractionResultHolder.fail(stack);
        }
        return super.use(level, player, usedHand);
    }

    public static Relic makeRelic(ItemStack stack) {
        return new RelicImpl(stack, null);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
        super.appendHoverText(stack, world, tooltip, flags);
        RelicImpl.addDefaultTooltip(stack, tooltip);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide && entity instanceof Player player) {
            var relic = XplatAbstractions.INSTANCE.findRelic(stack);
            if (relic != null) {
                relic.tickBinding(player);
            }
        }
    }

    @Override
    public SoundEvent getSound() {
        return ExtraBotanySounds.PANDORAS_BOX_OPEN;
    }
}
