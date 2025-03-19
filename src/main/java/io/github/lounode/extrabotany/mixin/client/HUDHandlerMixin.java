package io.github.lounode.extrabotany.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.lounode.extrabotany.common.item.relic.MasterBandOfManaItem;
import io.github.lounode.extrabotany.mixinutils.ManaAccumulator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.client.gui.HUDHandler;
import vazkii.botania.xplat.XplatAbstractions;

import java.math.BigInteger;

@Mixin(HUDHandler.class)
public class HUDHandlerMixin {
    //ManaReader
    @Inject(method = "drawSimpleManaHUD", at = @At("TAIL"), remap = false)
    private static void drawManaNumber(GuiGraphics gui, int color, int mana, int maxMana, String name, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        boolean display = false;
        for (ItemStack stack : player.getHandSlots()) {
            if (stack.isEmpty()) {
                continue;
            }

            CompoundTag tag = stack.getTag();
            if (tag != null && tag.contains("extrabotany", Tag.TAG_COMPOUND)) {
                CompoundTag extraBotany = tag.getCompound("extrabotany");
                if (extraBotany.contains("mana_reader", Tag.TAG_COMPOUND)) {
                    CompoundTag manaReader = extraBotany.getCompound("mana_reader");
                    if (manaReader.getBoolean("enable")) {
                        display = true;
                        break;
                    }
                }
            }
        }

        if (!display) {
            return;
        }

        RenderSystem.enableBlend();
        String manaString = mana + "/" + maxMana;

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int x = mc.getWindow().getGuiScaledWidth() / 2 - mc.font.width(name) / 2;
        int y = mc.getWindow().getGuiScaledHeight() / 2 + 10;
        x = (int) (((float) screenWidth / 2 - mc.font.width(manaString) * 0.75f / 2));
        y += 15;

        gui.pose().pushPose();
        gui.pose().scale(0.75f, 0.75f, 1f);
        gui.drawString(mc.font, manaString, (int)(x/0.75f), (int)(y/0.75f), color);
        gui.pose().popPose();

        RenderSystem.disableBlend();
    }
    //BUGFix: 玩家拥有超过INT_MAX的魔力后，魔力条渲染不正确
    /*
    思路：用long暂存玩家的mana和maxmana，因为渲染魔力条只需要比例，所以做一个放缩即可
     */
    @Redirect(
            method = "onDrawScreenPost",
            at = @At(
                    value = "INVOKE",
                    target = "Lvazkii/botania/xplat/XplatAbstractions;findManaItem(Lnet/minecraft/world/item/ItemStack;)Lvazkii/botania/api/mana/ManaItem;"
            ),
            remap = false
    )
    private static ManaItem handleManaSum(XplatAbstractions instance, ItemStack itemStack) {
        ManaItem item = XplatAbstractions.INSTANCE.findManaItem(itemStack);
        if (item != null) {
            BigInteger mana = BigInteger.valueOf(item.getMana());
            BigInteger maxMana = BigInteger.valueOf(item.getMaxMana());

            if (item instanceof MasterBandOfManaItem.ExtendManaItemImpl extendManaItem) {
                mana = BigInteger.valueOf(extendManaItem.getRealMana());
                maxMana = BigInteger.valueOf(extendManaItem.getRealMaxMana());
            }

            ManaAccumulator.accumulate(mana, maxMana);
        }
        return item;
    }

    @Redirect(
            method = "onDrawScreenPost",
            at = @At(
                    value = "INVOKE",
                    target = "Lvazkii/botania/client/gui/HUDHandler;renderManaInvBar(Lnet/minecraft/client/gui/GuiGraphics;II)V"
            ),
            remap = false
    )
    private static void redirectRenderMana(GuiGraphics gui, int mana, int maxMana) {
        BigInteger realMana = ManaAccumulator.getTotalMana();
        BigInteger realMax = ManaAccumulator.getTotalMaxMana();

        if (realMax.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) {
            double ratio = realMana.doubleValue() / realMax.doubleValue();
            mana = (int) (Integer.MAX_VALUE * ratio);
            maxMana = Integer.MAX_VALUE;
        } else {
            mana = realMana.intValueExact();
            maxMana = realMax.intValueExact();
        }

        HUDHandlerInvoker.renderManaInvBar(gui, mana, maxMana);
    }

    @Inject(
            method = "onDrawScreenPost",
            at = @At(
                    value = "INVOKE",
                    target = "Lvazkii/botania/client/gui/HUDHandler;renderManaInvBar(Lnet/minecraft/client/gui/GuiGraphics;II)V",
                    shift = At.Shift.AFTER
            ),
            remap = false
    )
    private static void afterRender(CallbackInfo ci) {
        ManaAccumulator.reset();
    }
}

