package io.github.lounode.extrabotany.common.block.block_entity;

import io.github.lounode.extrabotany.api.recipe.PedestalRecipe;
import io.github.lounode.extrabotany.common.crafting.ExtraBotanyRecipeTypes;
import io.github.lounode.extrabotany.common.lib.LibAdvancementNames;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.common.block.block_entity.ExposedSimpleInventoryBlockEntity;
import vazkii.botania.common.helper.PlayerHelper;

import java.util.*;
import java.util.stream.Collectors;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class PedestalBlockEntity extends ExposedSimpleInventoryBlockEntity {
    private int strikes;
    private Map<ItemStack, ItemFrame> automaticHammers = new HashMap<>();
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

    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        if (world.isClientSide() && !isEmpty()) {
            //return InteractionResult.SUCCESS;
        }

        //TODO 锤子效率
        var result = InteractionResult.PASS;
        /*
        result = handleSmash(state, world, pos, player, hand, hit);
        if (!result.consumesAction() ||result == InteractionResult.CONSUME_PARTIAL) {
            return result;
        }

         */
        //Place&Get Item
        result = handlePlaceItem(state, world, pos, player, hand, hit);
        return result;
    }


    public InteractionResult handleSmash(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();
        boolean useOffHand = false;
        if (!isEmpty()) {
            boolean denyInteraction = false;
            for (Recipe<?> r : ExtraBotanyRecipeTypes.getRecipes(level, ExtraBotanyRecipeTypes.PEDESTAL_SMASH_TYPE).values()) {
                if (!(r instanceof PedestalRecipe recipe)) {
                    continue;
                }
                if (!recipe.getSmashTools().test(mainHandItem) && !recipe.getSmashTools().test(offHandItem)) {
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

                if (recipe.getSmashTools().test(mainHandItem)) {
                    mainHandItem.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
                } else if (recipe.getSmashTools().test(offHandItem)) {
                    offHandItem.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(InteractionHand.OFF_HAND));
                    useOffHand = true;
                }


                this.strikes++;
                if (!level.isClientSide()) {
                    PlayerHelper.grantCriterion((ServerPlayer) player, prefix("main/" + LibAdvancementNames.GOODTEK), "code_triggered");
                }

                if (strikes < recipe.getStrike()) {
                    level.playSound(null, pos, SoundEvents.STONE_HIT, SoundSource.PLAYERS, .8f,
                            ((player.level().random.nextFloat() - player.level().random.nextFloat()) * .7f + 1) * 2);
                    continue;
                }

                ItemStack output = recipe.getOutput();
                this.setInsideItem(output);
                level.playSound(null, pos, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, .8F,
                        ((player.level().random.nextFloat() - player.level().random.nextFloat()) * .7f + 1) * 2);
                if (!level.isClientSide()) {
                    createExperience((ServerLevel) level, player.position(), recipe.getExp());
                }
                break;
            }

            if (denyInteraction) {
                if (useOffHand) {
                    player.swing(InteractionHand.OFF_HAND);
                } else {
                    player.swing(InteractionHand.MAIN_HAND);
                }
                return InteractionResult.CONSUME_PARTIAL;

            }
        }
        return InteractionResult.CONSUME;
    }


    public InteractionResult handlePlaceItem(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();
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
    //TODO 精神燃料自动化
    public static void serverTick(Level level, BlockPos worldPosition, BlockState state, PedestalBlockEntity self) {
        if (level.getGameTime() % 20 == 0) {
            int lastHammers = self.automaticHammers.size();
            self.updateAutomaticHammers();
            if (lastHammers != self.automaticHammers.size() && self.automaticHammers.size() == 4) {
                Player nearestPlayer = level.getNearestPlayer(
                        worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(),
                        10, false
                );
                if (nearestPlayer != null) {
                    PlayerHelper.grantCriterion((ServerPlayer) nearestPlayer, prefix("main/" + LibAdvancementNames.KURUKURU), "code_triggered");
                }
            }
        }

        if (level.getGameTime() % 10 == 0 && !self.isEmpty()) {
            for (ItemStack hammer : self.automaticHammers.keySet()) {
                ItemFrame frame = self.automaticHammers.get(hammer);
                if (frame == null || !frame.isAlive()) {
                    continue;
                }
                if (self.tryAutoSmash(hammer)) {
                    break;
                }
            }
        }

        //if (level.getGameTime())
    }
    public static void clientTick(Level level, BlockPos worldPosition, BlockState state, PedestalBlockEntity self) {

    }

    public boolean tryAutoSmash(ItemStack hammer) {
        for (Recipe<?> r : ExtraBotanyRecipeTypes.getRecipes(level, ExtraBotanyRecipeTypes.PEDESTAL_SMASH_TYPE).values()) {
            if (!(r instanceof PedestalRecipe recipe)) {
                continue;
            }
            if (!recipe.getSmashTools().test(hammer)) {
                continue;
            }
            if (!recipe.getInput().test(this.getInsideItem())) {
                if (ItemStack.isSameItemSameTags(recipe.getOutput(), this.getInsideItem())) {
                    return false;
                }
                continue;
            }

            if (hammer.isDamageableItem() && hammer.hurt(1, level.random, null)) {
                automaticHammers.get(hammer).setItem(ItemStack.EMPTY);
            }


            this.strikes++;
            if (strikes < recipe.getStrike()) {
                level.playSound(null, worldPosition, SoundEvents.STONE_HIT, SoundSource.BLOCKS, .8f,
                        ((level.random.nextFloat() - level.random.nextFloat()) * .7f + 1) * 2);
                return true;
            }

            ItemStack output = recipe.getOutput();
            this.setInsideItem(output);
            level.playSound(null, worldPosition, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, .8F,
                    ((level.random.nextFloat() - level.random.nextFloat()) * .7f + 1) * 2);
            //createExperience((ServerLevel) level, player.position(), recipe.getExp());
            return true;
        }
        return false;
    }

    public Map<ItemStack, ItemFrame> getPerhapsHammers() {
        return level.getEntitiesOfClass(ItemFrame.class,
                        new AABB(worldPosition).inflate(2.0)
                ).stream()
                .filter(frame -> frame.getPos().relative(frame.getDirection().getOpposite()).equals(worldPosition))
                .filter(itemFrame -> !itemFrame.getItem().isEmpty())
                .collect(Collectors.toMap(ItemFrame::getItem, itemFrame -> itemFrame, (oldFrame, newFrame) -> newFrame));
    }

    public void updateAutomaticHammers() {
        this.automaticHammers = getPerhapsHammers();
        if (this.automaticHammers == null) {
            this.automaticHammers = new HashMap<>();
        }
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
