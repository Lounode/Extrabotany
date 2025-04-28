package io.github.lounode.extrabotany.common.item.equipment.bauble;

import io.github.lounode.extrabotany.api.item.NatureEnergyItem;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.CustomCreativeTabContents;
import vazkii.botania.common.item.equipment.bauble.BaubleItem;

public class NatureOrbItem extends BaubleItem implements CustomCreativeTabContents {

    public static final long MAX_ENERGY = 500_000;

    private static final String TAG_ENERGY = "NatureEnergy";

    public NatureOrbItem(Properties properties) {
        super(properties);
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
