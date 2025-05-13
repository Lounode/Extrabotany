package io.github.lounode.extrabotany.common.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.lounode.extrabotany.common.sounds.ExtraBotanySounds;
import io.github.lounode.extrabotany.common.util.SoundEventUtil;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.api.mana.ManaItemHandler;

import java.util.UUID;

public class WalkingCaneItem extends Item implements Vanishable {
    private static final int MANA_PER_USE = 40;
    private static final double ADDITION_SPEED = 0.3D;
    private static final int COOLDOWN_TICKS = 20;
    private static final float EXHAUSTION = 0.2F;

    public WalkingCaneItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> ret = super.getDefaultAttributeModifiers(slot);
        if (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.MOVEMENT_SPEED,
                    new AttributeModifier(UUID.fromString("995829fa-94c0-41bd-b046-0468c509a488"),
                            "Cane modifier",
                            getAdditionSpeed(),
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    )
            );
        }

        return ret;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (!(entityLiving instanceof Player player)) {
            return;
        }

        int time = getUseDuration(stack) - timeLeft;
        if (ManaItemHandler.instance().requestManaExactForTool(stack, player, getManaPerUse(), true)) {
            player.getFoodData().addExhaustion(getExhaustion());
            player.setSprinting(true);

            player.addDeltaMovement(getAdditionDeltaMovement(player, time));

            if (level.isClientSide()) {
                player.playNotifySound(ExtraBotanySounds.WALKING_CANE_USE, SoundSource.PLAYERS, 1.0F, SoundEventUtil.randomPitch(level));
            }

            player.getCooldowns().addCooldown(this, getCooldownTicks());
        }
    }

    public Vec3 getAdditionDeltaMovement(Player player, int time) {
        float yIncrease = (0.07F * time + 0.67F);
        if(yIncrease > 0.7f) {
            yIncrease = 0.7f;
        }

        float speed = 0.12F * time + 1.1F;
        if(speed > 1.825f) {
            speed = 1.825f;
        }

        float yaw = player.getYRot() * ((float)Math.PI / 180F);

        float xIncrease = -Mth.sin(yaw) * speed;
        float zIncrease = Mth.cos(yaw) * speed;

        return new Vec3(xIncrease, yIncrease, zIncrease);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    public double getAdditionSpeed() {
        return ADDITION_SPEED;
    }

    public int getCooldownTicks() {
        return COOLDOWN_TICKS;
    }

    public float getExhaustion() {
        return EXHAUSTION;
    }

    public int getManaPerUse() {
        return MANA_PER_USE;
    }
}
