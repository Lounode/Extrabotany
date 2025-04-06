package io.github.lounode.extrabotany.common.item;

import io.github.lounode.extrabotany.common.item.equipment.bauble.FeatherOfJingweiItem;
import io.github.lounode.extrabotany.common.item.equipment.tool.hammer.*;
import io.github.lounode.extrabotany.common.item.material.BossBattleItem;
import io.github.lounode.extrabotany.common.item.material.HammerTiers;
import io.github.lounode.extrabotany.common.item.relic.CameraItem;
import io.github.lounode.extrabotany.common.item.relic.ExcaliburItem;
import io.github.lounode.extrabotany.common.item.relic.FailnaughtItem;
import io.github.lounode.extrabotany.common.item.relic.MasterBandOfManaItem;
import io.github.lounode.extrabotany.common.lib.LibItemNames;
import io.github.lounode.extrabotany.data.recipes.WandOfTheForestExtendRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeSerializer;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public final class ExtraBotanyItems {
    public static final Map<ResourceLocation, Item> ALL = new LinkedHashMap<>();


    //Mana Item
    public static final Item zadkiel = make(prefix(LibItemNames.ZADKIEL), new ZadkielItem(unstackable()));
    public static final Item manaReader = make(prefix(LibItemNames.MANA_READER), new ManaReaderItem(unstackable()));
    public static final Item natureOrb = make(prefix(LibItemNames.NATURE_ORB), new Item(unstackable()));
    public static final Item featherOfJingwei = make(prefix(LibItemNames.FEATHER_OF_JINGWEI), new FeatherOfJingweiItem(unstackable()));

    //Hammer
    public static final Item manasteelHammer = make(prefix(LibItemNames.MANASTEEL_HAMMER),
            new ManasteelHammerItem(HammerTiers.MANASTEEL, 6, -3.1F, unstackableCustomDamage()));
    public static final Item elementiumHammer = make(prefix(LibItemNames.ELEMENTIUM_HAMMER),
            new ManasteelHammerItem(HammerTiers.ELEMENTIUM, 6, -3.1F ,unstackableCustomDamage()));
    public static final Item terrasteelHammer = make(prefix(LibItemNames.TERRASTEEL_HAMMER),
            new ManasteelHammerItem(HammerTiers.TERRASTEEL, 5, -3.0F, unstackableCustomDamage().rarity(Rarity.UNCOMMON)));
    public static final Item gaiaHammer = make(prefix(LibItemNames.GAIA_HAMMER),
            new ManasteelHammerItem(HammerTiers.GAIA, 5, -3.0F, unstackableCustomDamage().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final Item photoniumHammer = make(prefix(LibItemNames.PHOTONIUM_HAMMER),
            new ManasteelHammerItem(HammerTiers.PHOTONIUM, 6, -3.1F, unstackableCustomDamage()));
    public static final Item shadowiumHammer = make(prefix(LibItemNames.SHADOWIUM_HAMMER),
            new ManasteelHammerItem(HammerTiers.SHADOWIUM, 6, -3.1F, unstackableCustomDamage()));
    public static final Item aerialiteHammer = make(prefix(LibItemNames.AERIALITE_HAMMER),
            new ManasteelHammerItem(HammerTiers.AERIALITE, 5, -3.0F, unstackableCustomDamage().rarity(Rarity.UNCOMMON)));
    public static final Item orichalcosHammer = make(prefix(LibItemNames.ORICHALCOS_HAMMER),
            new ManasteelHammerItem(HammerTiers.ORICHALCOS, 5, -3.0F, unstackableCustomDamage().rarity(Rarity.EPIC).fireResistant()));
    public static final Item rheinHammer = make(prefix(LibItemNames.RHEIN_HAMMER),
            new RheinHammerItem(unstackableCustomDamage().rarity(Rarity.EPIC).fireResistant()));


    //Material
    public static final Item orichalcos = make(prefix(LibItemNames.ORICHALCOS), new Item(defaultBuilder().rarity(Rarity.EPIC).fireResistant()));
    public static final Item photonium = make(prefix(LibItemNames.PHOTONIUM), new Item(defaultBuilder()));
    public static final Item shadowium = make(prefix(LibItemNames.SHADOWIUM), new Item(defaultBuilder()));
    public static final Item aerialite = make(prefix(LibItemNames.AERIALITE_INGOT), new Item(defaultBuilder()));
    public static final Item orichalcosNugget = make(prefix(LibItemNames.ORICHALCOS_NUGGET), new Item(defaultBuilder().fireResistant()));
    public static final Item photoniumNugget = make(prefix(LibItemNames.PHOTONIUM_NUGGET), new Item(defaultBuilder()));
    public static final Item shadowiumNugget = make(prefix(LibItemNames.SHADOWIUM_NUGGET), new Item(defaultBuilder()));
    public static final Item aerialiteNugget = make(prefix(LibItemNames.AERIALITE_NUGGET), new Item(defaultBuilder()));

    public static final Item dasRheingold = make(prefix(LibItemNames.DAS_RHEINGOLD), new Item(defaultBuilder()));
    public static final Item gildedPotato = make(prefix(LibItemNames.GILDED_POTATO), new Item(defaultBuilder()));
    public static final Item gildedPotatoMashed = make(prefix(LibItemNames.GILDED_POTATO_MASHED), new Item(defaultBuilder()));
    public static final Item heroMedal = make(prefix(LibItemNames.HERO_MEDAL), new Item(defaultBuilder().rarity(Rarity.UNCOMMON)));//OT
    public static final Item challengeTicket = make(prefix(LibItemNames.CHALLENGE_TICKET), new BossBattleItem(defaultBuilder()));//OT
    public static final Item nightmareFuel = make(prefix(LibItemNames.NIGHTMARE_FUEL), new NightmareFuelItem(defaultBuilder()
            .food(new FoodProperties.Builder().nutrition(0).saturationMod(0.3F).alwaysEat()
                    .effect(new MobEffectInstance(MobEffects.HARM, 1, 1), 1.0F)
                    .effect(new MobEffectInstance(MobEffects.BLINDNESS, 500, 1), 1.0F)
                    .effect(new MobEffectInstance(MobEffects.WEAKNESS, 500, 1), 1.0F)
                    .effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 500, 1), 1.0F)
                    .effect(new MobEffectInstance(MobEffects.UNLUCK, 500, 1), 1.0F)
                    .build()
            )
    ));
    public static final Item spiritFuel = make(prefix(LibItemNames.SPIRIT_FUEL), new Item(defaultBuilder()
            .food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3F).alwaysEat()
                    .effect(new MobEffectInstance(MobEffects.HEAL, 1, 1), 1.0F)
                    .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 500, 1), 1.0F)
                    .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 500, 1), 1.0F)
                    .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 500, 1), 1.0F)
                    .effect(new MobEffectInstance(MobEffects.LUCK, 500, 1), 1.0F)
                    .build()
            )
    ));
    public static final Item spiritFragment = make(prefix(LibItemNames.SPIRIT_FRAGMENT), new Item(defaultBuilder()));

    public static final Item theChaos = make(prefix(LibItemNames.THE_CHAOS), new Item(defaultBuilder()));
    public static final Item theOrigin = make(prefix(LibItemNames.THE_ORIGIN), new Item(defaultBuilder()));
    public static final Item theEnd = make(prefix(LibItemNames.THE_END), new Item(defaultBuilder()));
    public static final Item theUniverse = make(prefix(LibItemNames.THE_UNIVERSE), new Item(defaultBuilder()));

    //Relic
    public static final Item manaRingMaster = make(prefix(LibItemNames.MANA_RING_MASTER), new MasterBandOfManaItem(unstackable().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final Item camera = make(prefix(LibItemNames.CAMERA), new CameraItem(unstackable().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final Item failnaught = make(prefix(LibItemNames.FAILNAUGHT), new FailnaughtItem(unstackable().rarity(Rarity.UNCOMMON).fireResistant()));
    public static final Item excalibur = make(prefix(LibItemNames.EXCALIBUR), new ExcaliburItem(unstackable().rarity(Rarity.UNCOMMON).fireResistant()));

    //For tags
    public static final Item[] HAMMERS = {
            manasteelHammer, elementiumHammer, terrasteelHammer, gaiaHammer,
            photoniumHammer, shadowiumHammer, aerialiteHammer, orichalcosHammer,
            rheinHammer
    };
    public static final Item[] PICKAXES = {

    };

    public static final Item[] SWORDS = {
            excalibur,
    };
    public static final Item[] BOWS = {
            failnaught
    };
    public static final Item[] MANA_USING_ITEM = {
            camera, failnaught, excalibur, featherOfJingwei
    };

    public static final Item[] REPLICATOR_BLACKLIST = {
            failnaught, manaRingMaster, excalibur
    };
    //Bauble
    public static final Item[] RINGS = {
            manaRingMaster,
    };

    public static final Item[] ALL_SLOT = {
            featherOfJingwei
    };

    private static <T extends Item> T make(ResourceLocation id, T item) {
        var old = ALL.put(id, item);
        if (old != null) {
            throw new IllegalArgumentException("Typo? Duplicate id " + id);
        }
        return item;
    }
    public static Item.Properties defaultBuilderCustomDamage() {
        return XplatAbstractions.INSTANCE.defaultItemBuilderWithCustomDamageOnFabric();
    }

    public static Item.Properties unstackableCustomDamage() {
        return defaultBuilderCustomDamage().stacksTo(1);
    }

    public static Item.Properties defaultBuilder() {
        return new Item.Properties();
    }

    private static Item.Properties stackTo16() {
        return defaultBuilder().stacksTo(16);
    }

    private static Item.Properties unstackable() {
        return defaultBuilder().stacksTo(1);
    }

    public static void registerRecipeSerializers(BiConsumer<RecipeSerializer<?>, ResourceLocation> r) {
        r.accept(WandOfTheForestExtendRecipe.SERIALIZER, prefix("wand_of_the_forest_extension"));
    }

    public static void registerItems(BiConsumer<Item, ResourceLocation> r) {
        for (var e : ALL.entrySet()) {
            r.accept(e.getValue(), e.getKey());
        }
    }
}
