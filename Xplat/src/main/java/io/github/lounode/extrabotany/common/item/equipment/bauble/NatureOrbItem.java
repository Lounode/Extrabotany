package io.github.lounode.extrabotany.common.item.equipment.bauble;

import io.github.lounode.eventwrapper.event.entity.player.PlayerInteractEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.extrabotany.api.item.NatureEnergyItem;
import io.github.lounode.extrabotany.common.entity.gaia.GaiaIII;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import vazkii.botania.common.handler.EquipmentHandler;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.CustomCreativeTabContents;
import vazkii.botania.common.item.equipment.bauble.BaubleItem;


@EventBusSubscriberWrapper
public class NatureOrbItem extends BaubleItem implements CustomCreativeTabContents {

    public static final long MAX_ENERGY = 500_000;

    private static final String TAG_ENERGY = "NatureEnergy";

    public NatureOrbItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        var tile = level.getBlockEntity(pos);

        if (tile instanceof BeaconBlockEntity && stack.getItem() instanceof NatureOrbItem) {
            var natureItem = EXplatAbstractions.INSTANCE.findNatureEnergyItem(stack);
            if (natureItem != null && natureItem.getEnergy() > 1000) {
                if (GaiaIII.spawn(player, stack, level, pos)) {
                    natureItem.addEnergy(-1000);
                    return InteractionResult.SUCCESS;

                }
            }
        }

        return InteractionResult.CONSUME;
    }

    //TODO Fabric wrapper mixin
    @SubscribeEventWrapper
    public static void onPlayerInteract(PlayerInteractEventWrapper.RightClickBlock event) {
        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        ItemStack stack = EquipmentHandler.findOrEmpty(ExtraBotanyItems.natureOrb, player);
        Level level = player.level();
        var tile = level.getBlockEntity(pos);

        if (tile instanceof BeaconBlockEntity && stack.getItem() instanceof NatureOrbItem) {
            var natureItem = EXplatAbstractions.INSTANCE.findNatureEnergyItem(stack);
            if (natureItem != null && natureItem.getEnergy() > 1000) {
                if (GaiaIII.spawn(player, stack, level, pos)) {
                    natureItem.addEnergy(-1000);
                }
            }
        }
    }

    @Override
    public void addToCreativeTab(Item me, CreativeModeTab.Output output) {
        output.accept(this);

        ItemStack fullPower = new ItemStack(this);
        setEnergy(fullPower, MAX_ENERGY);
        output.accept(fullPower);
    }

    protected static void setEnergy(ItemStack stack, long energy) {
        if (energy > 0) {
            ItemNBTHelper.setLong(stack, TAG_ENERGY, energy);
        } else {
            ItemNBTHelper.removeEntry(stack, TAG_ENERGY);
        }
    }

    @Override
    public int getBarColor(ItemStack stack) {
        var natureItem = EXplatAbstractions.INSTANCE.findNatureEnergyItem(stack);
        return Mth.hsvToRgb((natureItem.getEnergy() / (float) natureItem.getMaxEnergy()) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        var natureItem = EXplatAbstractions.INSTANCE.findNatureEnergyItem(stack);
        return Math.round(13 * (natureItem.getEnergy() / (float) natureItem.getMaxEnergy()));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        var natureItem = EXplatAbstractions.INSTANCE.findNatureEnergyItem(stack);
        return natureItem.getEnergy() < natureItem.getMaxEnergy();
    }

    public static class NatureEnergyImpl implements NatureEnergyItem {

        private final ItemStack stack;

        public NatureEnergyImpl(ItemStack stack) {
            this.stack = stack;
        }

        @Override
        public long getEnergy() {
            return ItemNBTHelper.getLong(stack, TAG_ENERGY, 0) * stack.getCount();
        }

        @Override
        public long getMaxEnergy() {
            return MAX_ENERGY * stack.getCount();
        }

        @Override
        public boolean addEnergy(long energy) {
            long current = getEnergy() / stack.getCount();
            long maxSingle = getMaxEnergy() / stack.getCount();

            long newEnergy;
            if (energy > 0 && current > maxSingle - energy) {
                newEnergy = maxSingle;
            } else if (energy < 0 && current < -energy) {
                newEnergy = 0;
            } else {
                newEnergy = current + energy;
            }


            newEnergy = Math.min(newEnergy, maxSingle);
            newEnergy = Math.max(newEnergy, 0);

            setEnergy(stack, newEnergy);
            return newEnergy != current;
        }
    }
}
