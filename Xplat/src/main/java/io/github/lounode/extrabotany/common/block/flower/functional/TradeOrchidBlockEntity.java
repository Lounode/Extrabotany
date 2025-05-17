package io.github.lounode.extrabotany.common.block.flower.functional;

import io.github.lounode.extrabotany.common.block.flower.ExtrabotanyFlowerBlocks;
import io.github.lounode.extrabotany.common.brew.ExtraBotanyMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.block_entity.FunctionalFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

import java.util.List;

public class TradeOrchidBlockEntity extends FunctionalFlowerBlockEntity {

    private static final int RANGE = 8;
    private static final int MAX_MANA = 10000;
    private static final int MANA_PER_USE = 1000;

    public TradeOrchidBlockEntity(BlockPos pos, BlockState state) {
        super(ExtrabotanyFlowerBlocks.TRADE_ORCHID, pos, state);
    }

    @Override
    public void tickFlower() {
        super.tickFlower();
        if (getLevel().isClientSide) {
            return;
        }
        if (ticksExisted % 20 == 0) {
            sync();
        }
        if (ticksExisted % getCooldown() != 0) {
            return;
        }
        if (getMana() < getManaPerUse()) {
            return;
        }

        List<Villager> villagers = getLevel()
                .getEntitiesOfClass(Villager.class, ((RadiusDescriptor.Rectangle)getRadius()).aabb()).stream()
                .filter(villager -> !villager.isRemoved())
                .filter(LivingEntity::isAlive)
                .toList();
        List<Villager> farVillagers = getLevel()
                .getEntitiesOfClass(Villager.class, ((RadiusDescriptor.Rectangle)getRadius()).aabb().inflate(5)).stream()
                .filter(villager -> !villager.isRemoved())
                .filter(LivingEntity::isAlive)
                .filter(villager -> !villagers.contains(villager))
                .toList();

        for (var villager : villagers) {
            if (getMana() < getManaPerUse()) {
                break;
            }
            if (!villager.addEffect(getNewEffect())) {
                continue;
            }

            addMana(-getManaPerUse());
        }

        for (var villager : farVillagers) {
            villager.removeEffect(ExtraBotanyMobEffects.DISCOUNT);
        }
    }

    @Override
    public void setRemoved() {
        List<Villager> villagers = getLevel().getEntitiesOfClass(Villager.class, ((RadiusDescriptor.Rectangle)getRadius()).aabb());
        for (var villager : villagers) {
            villager.removeEffect(ExtraBotanyMobEffects.DISCOUNT);
        }
        super.setRemoved();
    }

    @Override
    public int getMaxMana() {
        return MAX_MANA;
    }

    @Override
    public int getColor() {
        return 0x54eb89;
    }

    public int getManaPerUse() {
        return MANA_PER_USE;
    }

    public MobEffectInstance getNewEffect() {
        return new MobEffectInstance(ExtraBotanyMobEffects.DISCOUNT, 3 * 20, 2, true, false);
    }

    public int getCooldown() {
        return 2 * 20;
    }

    @Override
    public @Nullable RadiusDescriptor getRadius() {
        return new RadiusDescriptor.Rectangle(getEffectivePos(), new AABB(getEffectivePos()).inflate(RANGE));
    }
}
