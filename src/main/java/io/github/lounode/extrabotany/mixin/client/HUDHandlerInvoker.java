package io.github.lounode.extrabotany.mixin.client;

import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import vazkii.botania.client.gui.HUDHandler;

@Mixin(HUDHandler.class)
public interface HUDHandlerInvoker {
    @Invoker(value = "renderManaInvBar", remap = false)
    public static void renderManaInvBar(GuiGraphics gui, int totalMana, int totalMaxMana) {
        throw new AssertionError();
    }
}
