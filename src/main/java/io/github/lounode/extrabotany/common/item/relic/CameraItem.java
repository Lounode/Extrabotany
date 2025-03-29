package io.github.lounode.extrabotany.common.item.relic;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.lounode.extrabotany.ExtraBotany;
import io.github.lounode.extrabotany.common.brew.effect.ExtrabotanyMobEffects;
import io.github.lounode.extrabotany.common.event.ItemCooldownFinishEvent;
import io.github.lounode.extrabotany.common.lib.LibAdvancementNames;
import io.github.lounode.extrabotany.common.sounds.ExtrabotanySounds;
import io.github.lounode.extrabotany.common.util.SoundEventUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkRegistry;
import org.lwjgl.opengl.GL11;
import vazkii.botania.api.item.Relic;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.client.lib.ResourcesLib;
import vazkii.botania.common.helper.PlayerHelper;
import vazkii.botania.common.item.relic.RelicImpl;
import vazkii.botania.common.item.relic.RelicItem;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.lounode.extrabotany.client.gui.HUDHandler.CAMERA_UI_LOCATION;
import static io.github.lounode.extrabotany.client.gui.HUDHandler.manaBar1;
import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;
import static vazkii.botania.client.gui.HUDHandler.manaBar;
import static vazkii.botania.client.gui.HUDHandler.renderManaBar;

@Mod.EventBusSubscriber
public class CameraItem extends RelicItem {
    private static final int MANA_PER_USE = 1500;
    private static final int RANGE = 20;
    private static final int ADVANCEMENT_REQUIRE = 10;

    public CameraItem(Properties props) {
        super(props);
    }
    //TODO 拍照UI 投掷物变成P点飞过来
    //拿着相机受伤时播放东方受伤音效
    //Z键 饰品栏也能拍照
    //车万女仆联动
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        player.playNotifySound(ExtrabotanySounds.CAMERA_FOCUS, SoundSource.PLAYERS, .3F, SoundEventUtil.randomPitch(world));
        return ItemUtils.startUsingInstantly(world, player, hand);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;

        int useTime = this.getUseDuration(stack) - timeLeft;

        executeCapture(world, player, player.getUsedItemHand());
        /*
        if (useTime >= 10) {

        } else {
            executeNormal(world, player.getUsedItemHand(), player);
        }

         */
    }
    @Override
    public int getUseDuration(ItemStack stack) {
        return 1200;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPYGLASS;
    }
    public InteractionResultHolder<ItemStack> executeCapture(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        var relic = XplatAbstractions.INSTANCE.findRelic(stack);
        if (relic != null && relic.isRightPlayer(player) && ManaItemHandler.instance().requestManaExactForTool(stack, player, MANA_PER_USE, true)) {

            List<LivingEntity> livingEntities = world.getEntitiesOfClass(LivingEntity.class, getFreezeBounds(player)).stream()
                    .filter(entity -> !entity.equals(player))
                    .filter(entity -> entity.getTeam() == null || !entity.getTeam().isAlliedTo(player.getTeam()))
                    .toList();
            for (var livingEntity : livingEntities) {
                //livingEntity.addEffect(new MobEffectInstance(ExtrabotanyMobEffects.IMMOBILIZE, 100));
                livingEntity.addEffect(new MobEffectInstance(ExtrabotanyMobEffects.LINK, 20 * 10));
            }

            if (!world.isClientSide() && livingEntities.size() >= ADVANCEMENT_REQUIRE) {
                PlayerHelper.grantCriterion((ServerPlayer) player, prefix("main/" + LibAdvancementNames.I_SEE_EVERYTHING), "code_triggered");
            }

            List<Projectile> projectiles = world.getEntitiesOfClass(Projectile.class, getFreezeBounds(player)).stream()
                    .filter(entity -> entity.getOwner() != player )
                    .filter(entity -> entity.getTeam() == null || !entity.getTeam().isAlliedTo(player.getTeam()))
                    .toList();
            for (var projectile : projectiles) {
                projectile.remove(Entity.RemovalReason.DISCARDED);
            }

            //player.getCooldowns().addCooldown(this, 20);
            player.getCooldowns().addCooldown(this, 20 * 8);

            world.playSound(null, player.getOnPos(), ExtrabotanySounds.CAMERA_USE, SoundSource.PLAYERS);
        }
        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            ItemStack usingItem = player.getUseItem();
            if (!usingItem.isEmpty() && usingItem.getItem() instanceof CameraItem camera) {
                AABB bounds = getFreezeBounds(player);

                List<LivingEntity> targets = player.level().getEntitiesOfClass(LivingEntity.class, bounds).stream()
                        .filter(entity -> !entity.equals(player))
                        .filter(entity -> entity.getTeam() == null || !entity.getTeam().isAlliedTo(player.getTeam()))
                        .toList();

                for (LivingEntity target : targets) {
                    target.addEffect(new MobEffectInstance(
                            MobEffects.GLOWING,
                            2,
                            0,
                            false,
                            true,
                            true
                    ));
                }
            }
        }
    }
    @SubscribeEvent
    public static void onItemCooldownFinish(ItemCooldownFinishEvent event) {
        if (!(event.getItem() instanceof CameraItem)) {
            return;
        }
        Player player = event.getPlayer();
        player.playNotifySound(ExtrabotanySounds.CAMERA_CHARGE, SoundSource.PLAYERS, .3F, SoundEventUtil.randomPitch(player.level()));
    }


    public int getManaPerUse() {
        return MANA_PER_USE;
    }
    public int getRange() {
        return RANGE;
    }

    public static AABB getFreezeBounds(Player player) {
        HitResult hitResult = player.pick(RANGE, 1.0F, false);
        Vec3 center = hitResult.getType() == HitResult.Type.MISS
                ? player.getEyePosition().add(player.getLookAngle().scale(RANGE))
                : hitResult.getLocation();


        return new AABB(
                center.x - (double) RANGE /2, center.y - (double) RANGE /2, center.z - (double) RANGE /2,
                center.x + (double) RANGE /2, center.y + (double) RANGE /2, center.z + (double) RANGE /2
        );
    }
    public static Relic makeRelic(ItemStack stack) {
        return new RelicImpl(stack, null) {
            @Override
            public boolean shouldDamageWrongPlayer() {
                return false;
            }
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
        tooltip.add(Component.translatable("tooltip.extrabotany.camera").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
        tooltip.add(Component.literal(""));
        super.appendHoverText(stack, world, tooltip, flags);
    }

    public static class Hud {
        public static void renderSpyglassOverlay(GuiGraphics pGuiGraphics, float pScopeScale) {
            float f = (float)Math.min(pGuiGraphics.guiWidth(), pGuiGraphics.guiHeight());
            float f1 = Math.min((float)pGuiGraphics.guiWidth() / f, (float)pGuiGraphics.guiHeight() / f) * pScopeScale;
            int i = Mth.floor(f * f1);
            int j = Mth.floor(f * f1);
            int k = (pGuiGraphics.guiWidth() - i) / 2;
            int l = (pGuiGraphics.guiHeight() - j) / 2;
            int i1 = k + i;
            int j1 = l + j;
            pGuiGraphics.blit(CAMERA_UI_LOCATION, k, l, -90, 0.0F, 0.0F, i, j, i, j);
        }
    }
}
