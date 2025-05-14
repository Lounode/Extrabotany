package io.github.lounode.extrabotany.common.item.relic;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.lounode.eventwrapper.event.entity.player.AttackEntityEventWrapper;
import io.github.lounode.eventwrapper.event.entity.player.PlayerInteractEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.EventBusSubscriberWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.item.material.ItemTiers;
import io.github.lounode.extrabotany.common.sounds.ExtraBotanySounds;
import io.github.lounode.extrabotany.network.serverbound.LeftClickPacketExcalibur;
import io.github.lounode.extrabotany.xplat.ExClientXplatAbstractions;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import vazkii.botania.api.internal.ManaBurst;
import vazkii.botania.api.item.Relic;
import vazkii.botania.api.mana.BurstProperties;
import vazkii.botania.api.mana.LensEffectItem;
import vazkii.botania.common.entity.ManaBurstEntity;
import vazkii.botania.common.helper.VecHelper;
import vazkii.botania.common.item.equipment.tool.manasteel.ManasteelSwordItem;
import vazkii.botania.common.item.relic.RelicImpl;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@EventBusSubscriberWrapper
public class ExcaliburItem extends ManasteelSwordItem implements LensEffectItem {
    private static final int MANA_PER_DAMAGE = 200;
    public static final double SEARCH_TARGET_RADIUS = 5.0D;

    public ExcaliburItem(Properties props) {
        super(ItemTiers.EXCALIBUR, 8, -2F, props);
    }

    @SubscribeEventWrapper
    public static void leftClick(PlayerInteractEventWrapper.LeftClickEmpty event) {
        ItemStack stack = event.getItemStack();
        if (!stack.isEmpty() && stack.getItem() instanceof ExcaliburItem) {
            ExClientXplatAbstractions.INSTANCE.sendToServer(LeftClickPacketExcalibur.INSTANCE);
        }
    }
    @SubscribeEventWrapper
    public static void attackEntity(AttackEntityEventWrapper event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide && player.getMainHandItem().getItem() instanceof ExcaliburItem) {
            trySpawnBurst(player, player.getAttackStrengthScale(0F));
        }
    }

    public static void trySpawnBurst(Player player, float attackStrength) {
        ItemStack stack = player.getMainHandItem();
        if (!stack.is(ExtraBotanyItems.excalibur)) {
            return;
        }
        var relic = XplatAbstractions.INSTANCE.findRelic(stack);
        if (
                relic == null || !relic.isRightPlayer(player)

        ) {
            return;
        }
        trySpawnBurstUnsafe(player, attackStrength);
    }


    public static void trySpawnBurstUnsafe(Player player, float attackStrength) {
        if (
                player.isSpectator() ||
                attackStrength != 1
        ) {
            return;
        }

        ManaBurstEntity burst = getBurst(player, player.getMainHandItem());
        player.level().addFreshEntity(burst);

        player.getMainHandItem().hurtAndBreak(1, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ExtraBotanySounds.EXCALIBUR_ATTACK, SoundSource.PLAYERS, 1F, 1F);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> ret = super.getDefaultAttributeModifiers(slot);
        if (slot == EquipmentSlot.MAINHAND) {
            ret = HashMultimap.create(ret);
            ret.put(Attributes.MOVEMENT_SPEED,
                    new AttributeModifier(UUID.fromString("995829fa-94c0-41bd-b046-0468c509a488"),
                            "Excaliber modifier",
                            0.3D,
                            AttributeModifier.Operation.MULTIPLY_TOTAL
                    ));
        }
        return ret;
    }

    public static ManaBurstEntity getBurst(Player player, ItemStack stack) {
        ManaBurstEntity burst = new ManaBurstEntity(player){
            @Override
            public boolean shouldBeSaved() {
                return false;
            }
        };

        float motionModifier = 9F;

        burst.setColor(0xFFFF20);
        burst.setMana(MANA_PER_DAMAGE);
        burst.setStartingMana(MANA_PER_DAMAGE);
        burst.setMinManaLoss(40);
        burst.setManaLossPerTick(4F);
        burst.setGravity(0F);
        burst.setDeltaMovement(burst.getDeltaMovement().scale(motionModifier));

        burst.setSourceLens(stack.copy());
        return burst;
    }

    @Override
    public void updateBurst(ManaBurst burst, ItemStack stack) {
        ManaBurstEntity burstEntity = (ManaBurstEntity) burst.entity();
        Entity thrower = burstEntity.getOwner();

        if (!(thrower instanceof Player player) || !thrower.isAlive()) {
            burstEntity.discard();
            return;
        }

        rotateToEnemy(burstEntity);

        if (burstEntity.level().isClientSide()) {
            return;
        }

        AABB axis = new AABB(burstEntity.getX(), burstEntity.getY(), burstEntity.getZ(), burstEntity.xOld, burstEntity.yOld, burstEntity.zOld).inflate(1);
        var entities = burstEntity.level().getEntitiesOfClass(LivingEntity.class, axis).stream()
                .filter(e -> e != thrower)
                .filter(e -> !(e instanceof Player other && !player.canHarmPlayer(other)))
                .filter(e -> e.hurtTime == 0)
                .toList();

        for (var entity : entities) {
            int cost = MANA_PER_DAMAGE / 3;
            int mana = burst.getMana();
            if (mana < cost) {
                break;
            }
            burst.setMana(mana - cost);

            float damage = 5F + ItemTiers.EXCALIBUR.getAttackDamageBonus();
            //DamageSource source1 = ((DamageSourceAccessor)player.damageSources()).source(DamageTypes.PLAYER_ATTACK, player);
            DamageSource source = ExtraBotanyDamageTypes.Sources.excaliburDamage(player.level().registryAccess(), player);
            entity.hurt(source, damage);

            burstEntity.discard();
            break;
        }
    }

    private void rotateToEnemy(ManaBurstEntity burstEntity) {
        AABB searchBox = new AABB(burstEntity.getX(), burstEntity.getY(), burstEntity.getZ(),
                burstEntity.xOld, burstEntity.yOld, burstEntity.zOld).inflate(SEARCH_TARGET_RADIUS);
        burstEntity.level().getEntitiesOfClass(LivingEntity.class, searchBox).stream()
                .filter(ExcaliburItem::canTargetEntity)
                .filter(living -> living.hurtTime == 0)
                .filter(living -> living != burstEntity.getOwner())
                .sorted(Comparator.comparingInt(ExcaliburItem::getEntityPriority))
                .findFirst()
                .ifPresent(target -> {
                    Vec3 thisVec = VecHelper.fromEntityCenter(burstEntity);

                    Vec3 targetVec = VecHelper.fromEntityCenter(target);
                    Vec3 diffVec = targetVec.subtract(thisVec);

                    Vec3 motionVec = diffVec.normalize().scale(0.6);

                    burstEntity.setDeltaMovement(motionVec);
                });
    }

    private static boolean canTargetEntity(LivingEntity entity) {
        return entity instanceof Mob || entity instanceof Player;
    }

    private static int getEntityPriority(LivingEntity entity) {
        if (entity instanceof Mob) {
            return 3;
        } else if (entity instanceof Player) {
            return 2;
        } else if (entity instanceof Animal) {
            return 1;
        }
        return 0;
    }

    @Override
    public void apply(ItemStack stack, BurstProperties props, Level level) {}

    @Override
    public boolean collideBurst(ManaBurst burst, HitResult pos, boolean isManaBlock, boolean shouldKill, ItemStack stack) {
        return shouldKill;
    }

    @Override
    public boolean doParticles(ManaBurst burst, ItemStack stack) {
        return true;
    }

    @Override
    public int getManaPerDamage() {
        return MANA_PER_DAMAGE;
    }

    public static Relic makeRelic(ItemStack stack) {
        return new RelicImpl(stack, null);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!world.isClientSide && entity instanceof Player player) {
            var relic = XplatAbstractions.INSTANCE.findRelic(stack);
            if (relic != null) {
                relic.tickBinding(player);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return super.isValidRepairItem(toRepair, repair);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        tooltip.add(Component.translatable("tooltip.extrabotany.excalibur").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        tooltip.add(Component.literal(""));
        RelicImpl.addDefaultTooltip(stack, tooltip);
    }
}
