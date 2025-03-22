package io.github.lounode.extrabotany.common.block.block_entity;

import io.github.lounode.extrabotany.api.recipe.PedestalRecipe;
import io.github.lounode.extrabotany.common.crafting.ExtraBotanyRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.common.block.block_entity.ExposedSimpleInventoryBlockEntity;

public class PedestalBlockEntity extends ExposedSimpleInventoryBlockEntity {
    private int strikes;
    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ExtraBotanyBlockEntities.PEDESTAL, pos, state);
        this.setStrikes(0);
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(1){
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
    }
    //TODO feature:自动合成，活石祭坛上放展示框，展示框里放锤子，消耗耐久自动合成
    //成就：转圈圈，活石祭坛四面都放上锤子
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        if (world.isClientSide() && !isEmpty()) {
            return InteractionResult.SUCCESS;
        }
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        if (!isEmpty()) {
            boolean denyInteraction = false;
            for (Recipe<?> r : ExtraBotanyRecipeTypes.getRecipes(level, ExtraBotanyRecipeTypes.PEDESTAL_SMASH_TYPE).values()) {
                if (!(r instanceof PedestalRecipe recipe)) {
                    continue;
                }
                if (!recipe.getSmashTools().test(mainHandItem)) {
                    continue;
                } else {
                    denyInteraction = true;
                }
                if (!recipe.getInput().test(this.getInsideItem())) {
                    if (ItemStack.isSameItemSameTags(recipe.getOutput(), this.getInsideItem())) {
                        ItemStack stack = extractPedestal();
                        if (player.addItem(stack)) {
                            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, .2f,
                                    ((player.level().random.nextFloat() - player.level().random.nextFloat()) * .7f + 1) * 2);
                        } else {
                            Containers.dropItemStack(world, player.getX(), player.getY(), player.getZ(), stack);
                        }
                    }
                    continue;
                }
                if (mainHandItem.isDamageableItem()) {
                    mainHandItem.hurt(1, player.level().random, (ServerPlayer) player);
                }

                this.strikes++;
                if (strikes < recipe.getStrike()) {
                    level.playSound(null, pos, SoundEvents.STONE_HIT, SoundSource.PLAYERS, .8f,
                            ((player.level().random.nextFloat() - player.level().random.nextFloat()) * .7f + 1) * 2);
                    continue;
                }

                ItemStack output = recipe.getOutput();
                this.setInsideItem(output);
                level.playSound(null, pos, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, .8F,
                        ((player.level().random.nextFloat() - player.level().random.nextFloat()) * .7f + 1) * 2);
                createExperience((ServerLevel) level, player.position(), recipe.getExp());
                break;
            }

            if (denyInteraction) {
                return InteractionResult.SUCCESS;
            }
        }

        //Place&Get Item
        if (isEmpty()) {
            boolean reversePriority = false;
            if(!mainHandItem.isEmpty()) {
                for (Recipe<?> r : ExtraBotanyRecipeTypes.getRecipes(level, ExtraBotanyRecipeTypes.PEDESTAL_SMASH_TYPE).values()) {
                    if (!(r instanceof PedestalRecipe recipe)) {
                        continue;
                    }
                    if (recipe.getSmashTools().test(mainHandItem)) {
                        reversePriority = true;
                        break;
                    }
                }
            }
            if(reversePriority) {
                if (!offHandItem.isEmpty()) {
                    insertPedestal(offHandItem);
                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, .2f,
                            ((player.level().random.nextFloat() - player.level().random.nextFloat()) * .7f + 1) * 2);
                    player.swing(InteractionHand.OFF_HAND);
                } else if (!mainHandItem.isEmpty()) {
                    insertPedestal(mainHandItem);
                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, .2f,
                            ((player.level().random.nextFloat() - player.level().random.nextFloat()) * .7f + 1) * 2);
                    player.swing(InteractionHand.MAIN_HAND);
                }
            } else {
                if (!mainHandItem.isEmpty()) {
                    insertPedestal(mainHandItem);
                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, .2f,
                            ((player.level().random.nextFloat() - player.level().random.nextFloat()) * .7f + 1) * 2);
                    player.swing(InteractionHand.MAIN_HAND);
                } else if (!offHandItem.isEmpty()) {
                    insertPedestal(offHandItem);
                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, .2f,
                            ((player.level().random.nextFloat() - player.level().random.nextFloat()) * .7f + 1) * 2);
                    player.swing(InteractionHand.OFF_HAND);
                }
            }
            return InteractionResult.CONSUME;
        } else {
            ItemStack stack = extractPedestal();
            if (player.addItem(stack)) {
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, .2f,
                        ((player.level().random.nextFloat() - player.level().random.nextFloat()) * .7f + 1) * 2);
            } else {
                Containers.dropItemStack(world, player.getX(), player.getY(), player.getZ(), stack);
            }
            return InteractionResult.SUCCESS;
        }
    }

    private static void createExperience(ServerLevel world, Vec3 pos, int amount) {

        ExperienceOrb.award(world, pos, amount);
    }

    public void insertPedestal(ItemStack itemStack) {
        ItemStack stack = itemStack.split(1);
        this.setInsideItem(stack);
    }

    public ItemStack extractPedestal() {
        ItemStack stack = getInsideItem().copy();
        this.setInsideItem(ItemStack.EMPTY);
        return stack;
    }

    public ItemStack getInsideItem() {
        return getItemHandler().getItem(0);
    }

    public void setInsideItem(ItemStack itemStack) {
        getItemHandler().setItem(0, itemStack);
        this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(this.getBlockState()));
        this.markUpdated();
        this.setStrikes(0);
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public boolean isEmpty() {
        for (int i = 0; i < inventorySize(); i++) {
            if (!getItemHandler().getItem(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void trySmash(ItemStack hammer) {

    }

    public static void serverTick(Level level, BlockPos worldPosition, BlockState state, PedestalBlockEntity self) {

    }
    public static void clientTick(Level level, BlockPos worldPosition, BlockState state, PedestalBlockEntity self) {

    }

    public int getStrikes() {
        return strikes;
    }

    public void setStrikes(int strikes) {
        this.strikes = strikes;
    }

    @Override
    public void readPacketNBT(CompoundTag tag) {
        super.readPacketNBT(tag);
        this.setStrikes(tag.getInt("strikes"));
    }

    @Override
    public void writePacketNBT(CompoundTag tag) {
        super.writePacketNBT(tag);
        tag.putInt("strikes", strikes);
    }

    //TODO 活石祭坛HUD
    public static class HUD {

    }
}
