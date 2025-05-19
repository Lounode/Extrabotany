package io.github.lounode.extrabotany.forge.client;

import com.google.common.base.Suppliers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import vazkii.botania.api.BotaniaForgeClientCapabilities;
import vazkii.botania.api.block.WandHUD;
import vazkii.botania.forge.CapabilityUtil;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import io.github.lounode.extrabotany.client.ExtraBotanyItemProperties;
import io.github.lounode.extrabotany.client.core.ExtraBotanyModels;
import io.github.lounode.extrabotany.client.gui.HUD;
import io.github.lounode.extrabotany.client.renderer.BlockRenderLayers;
import io.github.lounode.extrabotany.client.renderer.ColorHandler;
import io.github.lounode.extrabotany.client.renderer.entity.EntityRenderers;
import io.github.lounode.extrabotany.common.block.flower.ExtrabotanyFlowerBlocks;
import io.github.lounode.extrabotany.common.lib.LibMisc;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientInitializer {
	public static HUD hud;

	@SubscribeEvent
	public static void clientInit(FMLClientSetupEvent evt) {
		BlockRenderLayers.skipPlatformBlocks = true; // platforms can use standard rendering on Forge
		BlockRenderLayers.init(ItemBlockRenderTypes::setRenderLayer);

		var bus = MinecraftForge.EVENT_BUS;
		hud = new HUD(Minecraft.getInstance());
		/*
		bus.addListener((CustomizeGuiOverlayEvent.BossEventProgress e) -> {
			var result = BossBarHandler.onBarRender(e.getGuiGraphics(), e.getX(), e.getY(),
					e.getBossEvent(), true);
			result.ifPresent(increment -> {
				e.setCanceled(true);
				e.setIncrement(increment);
			});
		});
		
		//MasterRingToolTip TODO
		
		bus.addListener(EventPriority.LOWEST, (RenderTooltipEvent.Color e) -> {
			var manaItem = XplatAbstractions.INSTANCE.findManaItem(e.getItemStack());
			if (manaItem == null) {
				return;
			}
			// Forge does not pass the tooltip width to any tooltip event.
			// To avoid a mixin here, we just duplicate the width checking part.
			int width = 0;
			MasterBandOfManaTooltipComponent manaBar = null;
			for (ClientTooltipComponent component : e.getComponents()) {
				width = Math.max(width, component.getWidth(e.getFont()));
				if (component instanceof MasterBandOfManaTooltipComponent c) {
					manaBar = c;
				}
			}
			if (manaBar != null) {
				manaBar.setContext(e.getX(), e.getY(), width);
			}
		});
		
		*/
		bus.addGenericListener(BlockEntity.class, ForgeClientInitializer::attachBeCapabilities);
	}

	private static final Supplier<Map<BlockEntityType<?>, Function<BlockEntity, WandHUD>>> WAND_HUD = Suppliers.memoize(() -> {
		var ret = new IdentityHashMap<BlockEntityType<?>, Function<BlockEntity, WandHUD>>();
		ExtrabotanyFlowerBlocks.registerWandHudCaps((factory, types) -> {
			for (var type : types) {
				ret.put(type, factory);
			}
		});

		return Collections.unmodifiableMap(ret);
	});

	private static void attachBeCapabilities(AttachCapabilitiesEvent<BlockEntity> e) {
		var be = e.getObject();

		var makeWandHud = WAND_HUD.get().get(be.getType());
		if (makeWandHud != null) {
			e.addCapability(vazkii.botania.common.lib.ResourceLocationHelper.prefix("wand_hud"),
					CapabilityUtil.makeProvider(BotaniaForgeClientCapabilities.WAND_HUD, makeWandHud.apply(be)));
		}
	}

	@SubscribeEvent
	public static void registerGuiOverlays(RegisterGuiOverlaysEvent e) {
		e.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(), "hud",
				(gui, poseStack, partialTick, width, height) -> hud.onDrawScreenPost(poseStack, partialTick));
	}

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers evt) {
		EntityRenderers.registerBlockEntityRenderers(evt::registerBlockEntityRenderer);
		EntityRenderers.registerEntityRenderers(evt::registerEntityRenderer);
	}

	@SubscribeEvent
	public static void registerBlockColors(RegisterColorHandlersEvent.Block evt) {
		ColorHandler.submitBlocks(evt::register);
	}

	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item evt) {
		ColorHandler.submitItems(evt::register);
	}

	@SubscribeEvent
	public static void onModelRegister(ModelEvent.RegisterAdditional evt) {
		var resourceManager = Minecraft.getInstance().getResourceManager();
		ExtraBotanyModels.INSTANCE.onModelRegister(resourceManager, evt::register);
		ExtraBotanyItemProperties.init((item, id, prop) -> ItemProperties.register(item.asItem(), id, prop));
	}

	@SubscribeEvent
	public static void onModelBake(ModelEvent.ModifyBakingResult evt) {
		ExtraBotanyModels.INSTANCE.onModelBake(evt.getModelBakery(), evt.getModels());
	}
}
