package io.github.lounode.extrabotany.common.item.brew;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.item.Relic;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.item.brew.BaseBrewItem;
import vazkii.botania.common.item.relic.RelicImpl;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.List;
import java.util.function.Supplier;

public class InfiniteWineItem extends BaseBrewItem {

    public static final int UPDATE_MODULO = 4000;
    private static final int MANA_PER_REGEN = 12_000;

    public InfiniteWineItem(Properties builder, int swigs, int drinkSpeed, Supplier<Item> baseItem) {
        super(builder, swigs, drinkSpeed, baseItem);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        var relic = XplatAbstractions.INSTANCE.findRelic(stack);
        if (relic == null || !relic.isRightPlayer(player) || getSwigsLeft(stack) < 1) {
            return InteractionResultHolder.pass(stack);
        }

        return super.use(world, player, hand);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level world, LivingEntity living) {
        if (!world.isClientSide) {
            for (MobEffectInstance effect : getBrew(stack).getPotionEffects(stack)) {
                MobEffectInstance newEffect = new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier(), true, true);
                if (effect.getEffect().isInstantenous()) {
                    effect.getEffect().applyInstantenousEffect(living, living, living, newEffect.getAmplifier(), 1F);
                } else {
                    living.addEffect(newEffect);
                }
            }

            if (world.random.nextBoolean()) {
                world.playSound(null, living.getX(), living.getY(), living.getZ(), SoundEvents.PLAYER_BURP, SoundSource.PLAYERS, 1F, 1F);
            }

            int swigs = getSwigsLeft(stack);
            if (living instanceof Player player && !player.getAbilities().instabuild) {
                setSwigsLeft(stack, swigs - 1);
            }
        }

        return stack;
    }

    public static Relic makeRelic(ItemStack stack) {
        return new RelicImpl(stack, null);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
        RelicImpl.addDefaultTooltip(stack, tooltip);
        super.appendHoverText(stack, world, tooltip, flags);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide && entity instanceof Player player) {
            var relic = XplatAbstractions.INSTANCE.findRelic(stack);
            if (relic != null) {
                relic.tickBinding(player);
            }

            if (
                    world.getGameTime() % getUpdateModulo() == 0 &&
                    getSwigsLeft(stack) < getSwigs() &&
                    ManaItemHandler.instance().requestManaExactForTool(stack, player, getManaPerRegen(), true)
            ) {
                setSwigsLeft(stack, getSwigsLeft(stack) + 1);
            }
        }
    }

    public int getUpdateModulo() {
        return UPDATE_MODULO;
    }

    public int getManaPerRegen() {
        return MANA_PER_REGEN;
    }
}
