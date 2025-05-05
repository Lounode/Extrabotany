package io.github.lounode.extrabotany.forge;

import com.google.common.base.Suppliers;
import com.mojang.logging.LogUtils;
import io.github.lounode.extrabotany.api.ExtraBotaniaRegistries;
import io.github.lounode.extrabotany.api.ExtrabotanyForgeCapabilities;
import io.github.lounode.extrabotany.api.item.NatureEnergyItem;
import io.github.lounode.extrabotany.common.advancements.ExtrabotanyCriteriaTriggers;
import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.block.block_entity.ExtraBotanyBlockEntities;
import io.github.lounode.extrabotany.common.brew.effect.ExtraBotanyMobEffects;
import io.github.lounode.extrabotany.common.crafting.ExtraBotanyRecipeTypes;
import io.github.lounode.extrabotany.common.entity.ExtraBotanyEntityType;
import io.github.lounode.extrabotany.common.entity.ExtraBotanyMemoryType;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.item.equipment.bauble.NatureOrbItem;
import io.github.lounode.extrabotany.common.item.relic.*;
import io.github.lounode.extrabotany.common.item.relic.voidcore.CoreOfTheVoidItem;
import io.github.lounode.extrabotany.common.lib.LibMisc;
import io.github.lounode.extrabotany.common.sounds.ExtraBotanySounds;
import io.github.lounode.extrabotany.common.telemetry.ExtraBotanyTelemetry;
import io.github.lounode.extrabotany.forge.network.ForgePacketHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.api.item.Relic;
import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.common.handler.EquipmentHandler;
import vazkii.botania.common.item.CustomCreativeTabContents;
import vazkii.botania.common.item.equipment.bauble.BaubleItem;
import vazkii.botania.forge.CapabilityUtil;
import vazkii.botania.forge.integration.curios.CurioIntegration;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

@Mod(LibMisc.MOD_ID)
public class ForgeCommonInitializer
{
    private static final Logger LOGGER = LogUtils.getLogger();
    public ForgeCommonInitializer(FMLJavaModLoadingContext context)
    {
        coreInit(context);
        registryInit(context);
        context.getModEventBus().addListener(this::commonSetup);
    }

    public void commonSetup(FMLCommonSetupEvent evt) {
        ForgePacketHandler.init();
        registerEvents();

        //Integration
        if (ModList.get().isLoaded("tconstruc")) {
            //modEventBus.addListener(this::registerTinkersMaterials);
        }
    }

    private void coreInit(ModLoadingContext context) {
        ForgeExtrabotanyConfig.setup(context);
    }

    private void registryInit(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        bind(modEventBus, Registries.SOUND_EVENT, ExtraBotanySounds::init);
        //Block&ItemBlock&Items
        bind(modEventBus, Registries.BLOCK, ExtraBotanyBlocks::registerBlocks);
        bindForItems(modEventBus, ExtraBotanyBlocks::registerItemBlocks);
        bind(modEventBus, Registries.BLOCK_ENTITY_TYPE, ExtraBotanyBlockEntities::registerTiles);
        bindForItems(modEventBus, ExtraBotanyItems::registerItems);

        //GUI & Recipe
        bind(modEventBus, Registries.RECIPE_SERIALIZER, ExtraBotanyItems::registerRecipeSerializers);
        bind(modEventBus, Registries.RECIPE_TYPE, ExtraBotanyRecipeTypes::submitRecipeTypes);
        bind(modEventBus, Registries.RECIPE_SERIALIZER, ExtraBotanyRecipeTypes::submitRecipeSerializers);

        // Entities
        bind(modEventBus, Registries.ENTITY_TYPE, ExtraBotanyEntityType::registerEntities);
        modEventBus.addListener((EntityAttributeCreationEvent e) -> ExtraBotanyEntityType.registerAttributes((type, builder) -> e.put(type, builder.build())));
        bind(modEventBus, Registries.MEMORY_MODULE_TYPE, ExtraBotanyMemoryType::registerMemories);

        // Potions
        bind(modEventBus, Registries.MOB_EFFECT, ExtraBotanyMobEffects::registerPotions);

        // Rest
        //registerDatas(modEventBus);

        ExtrabotanyCriteriaTriggers.init();

        //Creative tab
        bind(modEventBus, Registries.CREATIVE_MODE_TAB, consumer -> {
            consumer.accept(CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.extrabotany").withStyle(style -> style.withColor(ChatFormatting.WHITE)))
                            .hideTitle()
                            .icon(() -> new ItemStack(ExtraBotanyItems.zadkiel))
                            //.withTabsBefore(CreativeModeTabs.NATURAL_BLOCKS)
                            .backgroundSuffix("extrabotany.png")
                            //.withSearchBar()
                            .build(),
                    ExtraBotaniaRegistries.EXTRA_BOTANIA_TAB_KEY.location());
        });

        modEventBus.addListener((BuildCreativeModeTabContentsEvent e) -> {
            if (e.getTabKey() == ExtraBotaniaRegistries.EXTRA_BOTANIA_TAB_KEY) {
                for (Item item : this.itemsToAddToCreativeTab) {
                    if (item instanceof CustomCreativeTabContents cc) {
                        cc.addToCreativeTab(item, e);
                    } else if (item instanceof BlockItem bi && bi.getBlock() instanceof CustomCreativeTabContents cc) {
                        cc.addToCreativeTab(item, e);
                    } else {
                        e.accept(item);
                    }
                }
            }
        });

    }

    private void registerEvents() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addGenericListener(ItemStack.class, this::attachItemCaps);
        bus.addGenericListener(Level.class, this::attachLevelCaps);

        bus.addListener(this::registerFuels);

        bus.addListener((ServerStartedEvent event) -> {
            ExtraBotanyTelemetry.getInstance();
            ExtraBotanyTelemetry.onServerStarted(event.getServer());
        });
        bus.addListener((ServerStoppingEvent event) -> {
            ExtraBotanyTelemetry.onServerStopping(event.getServer());
        });
    }

    private void registerFuels(FurnaceFuelBurnTimeEvent e) {
        //TODO 可配置燃烧时间
        if (e.getItemStack().is(ExtraBotanyItems.nightmareFuel)) {
            e.setBurnTime(3200);
        }
        if (e.getItemStack().is(ExtraBotanyItems.spiritFuel)) {
            e.setBurnTime(12800);
        }
    }

    private void attachLevelCaps(AttachCapabilitiesEvent<Level> event) {

    }

    private void attachItemCaps(AttachCapabilitiesEvent<ItemStack> e) {
        var stack = e.getObject();

        if (stack.getItem() instanceof BaubleItem
                && EquipmentHandler.instance instanceof CurioIntegration ci) {
            e.addCapability(prefix("curio"), ci.initCapability(stack));
        }

        var makeManaItem = MANA_ITEM.get().get(stack.getItem());
        if (makeManaItem != null) {
            e.addCapability(prefix("mana_item"),
                    CapabilityUtil.makeProvider(BotaniaForgeCapabilities.MANA_ITEM, makeManaItem.apply(stack)));
        }

        var makeNatureEnergyItem = NATURE_ENERGY_ITEM.get().get(stack.getItem());
        if (makeNatureEnergyItem != null) {
            e.addCapability(prefix("nature_energy_item"),
                    CapabilityUtil.makeProvider(ExtrabotanyForgeCapabilities.NATURE_ENERGY_ITEM, makeNatureEnergyItem.apply(stack)));
        }

        var makeRelic = RELIC.get().get(stack.getItem());
        if (makeRelic != null) {
            e.addCapability(prefix("relic"),
                    CapabilityUtil.makeProvider(BotaniaForgeCapabilities.RELIC, makeRelic.apply(stack)));
        }
    }

    private static final Supplier<Map<Item, Function<ItemStack, NatureEnergyItem>>> NATURE_ENERGY_ITEM = Suppliers.memoize(() -> Map.of(
            ExtraBotanyItems.natureOrb, NatureOrbItem.NatureEnergyImpl::new
    ));

    private static final Supplier<Map<Item, Function<ItemStack, ManaItem>>> MANA_ITEM = Suppliers.memoize(() -> Map.of(
            ExtraBotanyItems.manaRingMaster, MasterBandOfManaItem.ExtendManaItemImpl::new
    ));
    private static final Supplier<Map<Item, Function<ItemStack, Relic>>> RELIC = Suppliers.memoize(() -> Map.of(
            ExtraBotanyItems.manaRingMaster, MasterBandOfManaItem::makeRelic,
            ExtraBotanyItems.camera, CameraItem::makeRelic,
            ExtraBotanyItems.failnaught, FailnaughtItem::makeRelic,
            ExtraBotanyItems.excalibur, ExcaliburItem::makeRelic,
            ExtraBotanyItems.coreOfTheVoid, CoreOfTheVoidItem::makeRelic,
            ExtraBotanyItems.pandorasBox, PandorasBoxItem::makeRelic
    ));



    private static <T> void bind(IEventBus modEventBus, ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        modEventBus.addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }

    private final Set<Item> itemsToAddToCreativeTab = new LinkedHashSet<>();
    private void bindForItems(IEventBus modEventBus, Consumer<BiConsumer<Item, ResourceLocation>> source) {
        modEventBus.addListener((RegisterEvent event) -> {
            if (event.getRegistryKey().equals(Registries.ITEM)) {
                source.accept((t, rl) -> {
                    itemsToAddToCreativeTab.add(t);
                    event.register(Registries.ITEM, rl, () -> t);
                });
            }
        });
    }
}
