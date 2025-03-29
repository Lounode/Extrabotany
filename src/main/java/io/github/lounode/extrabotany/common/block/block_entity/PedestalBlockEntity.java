package io.github.lounode.extrabotany.common.block.block_entity;

import io.github.lounode.extrabotany.api.recipe.PedestalRecipe;
import io.github.lounode.extrabotany.common.block.PedestalBlock;
import io.github.lounode.extrabotany.common.crafting.ExtraBotanyRecipeTypes;
import io.github.lounode.extrabotany.common.lib.LibAdvancementNames;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.block.block_entity.ExposedSimpleInventoryBlockEntity;
import vazkii.botania.common.helper.PlayerHelper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class PedestalBlockEntity extends ExposedSimpleInventoryBlockEntity {
    private int strikes;
    private final int FINISH_CRAFT_STRIKE_FLAG = -1;
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
        if(hand == InteractionHand.OFF_HAND) {
            return InteractionResult.PASS;
        }


        List<Function<Void, Map.Entry<InteractionResult, Boolean>>> handlers = Arrays.asList(
                (Void v) -> handleExtractFinishItem(state, world, pos, player, hand, hit),
                (Void v) -> handleSmashNew(state, world, pos, player, hand, hit),
                (Void v) -> handleReversePlaceItem(state, world, pos, player, hand, hit),
                (Void v) -> handlePlaceItemNew(state, world, pos, player, hand, hit)
        );
        Map.Entry<InteractionResult, Boolean> result = new AbstractMap.SimpleEntry<>(InteractionResult.CONSUME, false);

        for (var handler : handlers) {
            result = handler.apply(null);

            if (result.getKey() != InteractionResult.CONSUME) {
                break;
            }
        }
        player.swing(result.getValue() ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);

        return result.getKey();
    }
    public Map.Entry<InteractionResult, Boolean> handleExtractFinishItem(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        if (!isEmpty() && strikes == FINISH_CRAFT_STRIKE_FLAG) {
            ItemStack stack = extractPedestal();
            if (player.addItem(stack)) {
                playSound();
            } else {
                Containers.dropItemStack(world, player.getX(), player.getY(), player.getZ(), stack);
            }
            return new AbstractMap.SimpleEntry<>(InteractionResult.CONSUME_PARTIAL, false);
        }
        return new AbstractMap.SimpleEntry<>(InteractionResult.CONSUME, false);
    }
    public Map.Entry<InteractionResult, Boolean> handleSmashNew(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
        boolean swingOffHand = false;

        if (!isEmpty()) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();

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
                    continue;
                }

                ItemStack smashTools = null;
                if (recipe.getSmashTools().test(mainHandItem)) {
                    smashTools = mainHandItem;
                } else if (recipe.getSmashTools().test(offHandItem)) {
                    smashTools = offHandItem;
                    swingOffHand = true;
                }
                boolean finalSwingOffHand = swingOffHand;

                //TODO 过于OP暂未启用
                /*
                * 时运：经验加成
                * 效率：击打次数加成
                * 经验修补：自动化自我修复
                * */
                int expBoost = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_FORTUNE, smashTools);
                int efficiency = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, smashTools);

                smashTools.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(finalSwingOffHand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND));


                this.strikes += 1 /*+ efficiency*/;
                if (!level.isClientSide()) {
                    PlayerHelper.grantCriterion((ServerPlayer) player, prefix("main/" + LibAdvancementNames.GOODTEK), "code_triggered");
                }

                if (strikes > 0 && strikes < recipe.getStrike()) {
                    level.playSound(null, pos, SoundEvents.STONE_HIT, SoundSource.PLAYERS, .8f,
                            ((player.level().random.nextFloat() - player.level().random.nextFloat()) * .7f + 1) * 2);
                    continue;
                }

                ItemStack output = recipe.getOutput().copy();
                this.setInsideItem(output);
                level.playSound(null, pos, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, .8F,
                        ((player.level().random.nextFloat() - player.level().random.nextFloat()) * .7f + 1) * 2);
                if (!level.isClientSide()) {

                    createExperience((ServerLevel) level, player.position(), recipe.getExp() /* * expBoost*/);
                }
                strikes = FINISH_CRAFT_STRIKE_FLAG;
                break;
            }

            return denyInteraction ?
                    new AbstractMap.SimpleEntry<>(InteractionResult.CONSUME_PARTIAL, swingOffHand) :
                    new AbstractMap.SimpleEntry<>(InteractionResult.CONSUME, swingOffHand);
        }

        return new AbstractMap.SimpleEntry<>(InteractionResult.CONSUME, swingOffHand);
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
    public Map.Entry<InteractionResult, Boolean> handleReversePlaceItem(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        boolean swingOffHand = false;
        if (isEmpty()) {
            boolean reversePriority = false;
            ItemStack mainHandItem = player.getMainHandItem();
            if (!mainHandItem.isEmpty()) {
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
            if (reversePriority) {
                ItemStack offHandItem = player.getOffhandItem();

                if (!offHandItem.isEmpty()) {
                    insertPedestal(offHandItem);
                    playSound();
                    swingOffHand = true;
                } else if (!mainHandItem.isEmpty()) {
                    insertPedestal(mainHandItem);
                    playSound();
                }

                return new AbstractMap.SimpleEntry<>(InteractionResult.CONSUME_PARTIAL, swingOffHand);
            }
        }
        return new AbstractMap.SimpleEntry<>(InteractionResult.CONSUME, swingOffHand);
    }
    public Map.Entry<InteractionResult, Boolean> handlePlaceItemNew(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        boolean swingOffHand = false;
        if (isEmpty()) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offHandItem = player.getOffhandItem();

            if (!mainHandItem.isEmpty()) {
                insertPedestal(mainHandItem);
                playSound();
            }
            else if (!offHandItem.isEmpty()) {
                insertPedestal(offHandItem);
                playSound();
                swingOffHand = true;
            }
        } else {
            ItemStack stack = extractPedestal();
            if (player.addItem(stack)) {
                playSound();
            } else {
                Containers.dropItemStack(world, player.getX(), player.getY(), player.getZ(), stack);
            }
        }
        return new AbstractMap.SimpleEntry<>(InteractionResult.CONSUME, swingOffHand);
    }

    private void playSound() {
        level.playSound(null, worldPosition, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, .2f,
                ((level.random.nextFloat() - level.random.nextFloat()) * .7f + 1) * 2);
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
        return getItem(0);
    }

    public void setInsideItem(ItemStack itemStack) {
        setItem(0, itemStack);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        this.setStrikes(0);

        BlockState state = getBlockState();

        this.level.blockEvent(getBlockPos(), state.getBlock(), 0, 0);
        this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(this.getBlockState()));

        this.level.setBlock(getBlockPos(), state.setValue(PedestalBlock.HAS_ITEM, !stack.isEmpty()), 3);

        super.setItem(index, stack);

        this.markUpdated();
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        this.setStrikes(0);


        BlockState state = getBlockState();

        this.level.blockEvent(getBlockPos(), state.getBlock(), 0, 0);
        this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(this.getBlockState()));

        this.level.setBlock(getBlockPos(), state.setValue(PedestalBlock.HAS_ITEM, false), 3);

        ItemStack superItemStack = super.removeItem(index, count);
        this.markUpdated();
        return superItemStack;
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
        if (level.getGameTime() % 10 == 0) {
            self.markUpdated();
        }

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

            int expBoost = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_FORTUNE, hammer);
            int efficiency = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_EFFICIENCY, hammer);

            if (hammer.isDamageableItem() && hammer.hurt(1, level.random, null)) {
                automaticHammers.get(hammer).setItem(ItemStack.EMPTY);
            }

            this.strikes+= 1 /*+ efficiency*/;
            if (strikes > 0 && strikes < recipe.getStrike()) {
                level.playSound(null, worldPosition, SoundEvents.STONE_HIT, SoundSource.BLOCKS, .8f,
                        ((level.random.nextFloat() - level.random.nextFloat()) * .7f + 1) * 2);
                return true;
            }

            ItemStack output = recipe.getOutput().copy();
            this.setInsideItem(output);
            level.playSound(null, worldPosition, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, .8F,
                    ((level.random.nextFloat() - level.random.nextFloat()) * .7f + 1) * 2);
            strikes = FINISH_CRAFT_STRIKE_FLAG;

            //Mending
            /*
            int exp = recipe.getExp() * expBoost;
            if (hammer != null && !hammer.isEmpty() && hammer.isDamageableItem()) {
                int mendingLevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.MENDING, hammer);

                if (mendingLevel > 0 && exp > 0) {
                    int repairAmount = Math.min(exp * 2, hammer.getDamageValue());
                    hammer.setDamageValue(hammer.getDamageValue() - repairAmount);
                }
            }

             */

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

    public int getAnalogOutputSignal() {
        return this.strikes == FINISH_CRAFT_STRIKE_FLAG ? 15 : 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        return this.strikes == FINISH_CRAFT_STRIKE_FLAG;
    }


    //TODO 活石祭坛HUD
    public static class HUD {

    }
}
