package io.github.lounode.extrabotany.common.item;

import io.github.lounode.extrabotany.common.item.material.BossBattleItem;
import io.github.lounode.extrabotany.common.item.relic.MasterBandOfManaItem;
import io.github.lounode.extrabotany.common.lib.LibItemNames;
import io.github.lounode.extrabotany.data.recipes.WandOfTheForestExtendRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public final class ExtraBotanyItems {
    private static final Map<ResourceLocation, Item> ALL = new LinkedHashMap<>();

    //Mana Item
    public static final Item zadkiel = make(prefix(LibItemNames.ZADKIEL), new ZadkielItem(unstackable()));
    public static final Item manaReader = make(prefix(LibItemNames.MANA_READER), new ManaReaderItem(unstackable()));
    public static final Item natureOrb = make(prefix(LibItemNames.NATURE_ORB), new Item(unstackable()));

    //Material
    public static final Item orichalcos = make(prefix(LibItemNames.ORICHALCOS), new Item(defaultBuilder().rarity(Rarity.EPIC)));
    public static final Item photonium = make(prefix(LibItemNames.PHOTONIUM), new Item(defaultBuilder()));//OT
    public static final Item shadowium = make(prefix(LibItemNames.SHADOWIUM), new Item(defaultBuilder()));//OT
    public static final Item aerialite = make(prefix(LibItemNames.AERIALITE_INGOT), new Item(defaultBuilder()));
    public static final Item orichalcosNugget = make(prefix(LibItemNames.ORICHALCOS_NUGGET), new Item(defaultBuilder()));
    public static final Item photoniumNugget = make(prefix(LibItemNames.PHOTONIUM_NUGGET), new Item(defaultBuilder()));
    public static final Item shadowiumNugget = make(prefix(LibItemNames.SHADOWIUM_NUGGET), new Item(defaultBuilder()));
    public static final Item aerialiteNugget = make(prefix(LibItemNames.AERIALITE_NUGGET), new Item(defaultBuilder()));

    public static final Item dasRheingold = make(prefix(LibItemNames.DAS_RHEINGOLD), new Item(defaultBuilder()));//OT
    public static final Item gildedPotato = make(prefix(LibItemNames.GILDED_POTATO), new Item(defaultBuilder()));//OT
    public static final Item gildedPotatoMashed = make(prefix(LibItemNames.GILDED_POTATO_MASHED), new Item(defaultBuilder()));//OT
    public static final Item heroMedal = make(prefix(LibItemNames.HERO_MEDAL), new Item(defaultBuilder().rarity(Rarity.UNCOMMON)));//OT
    public static final Item challengeTicket = make(prefix(LibItemNames.CHALLENGE_TICKET), new BossBattleItem(defaultBuilder()));//OT
    public static final Item nightmareFuel = make(prefix(LibItemNames.NIGHTMARE_FUEL), new Item(defaultBuilder()));
    public static final Item spiritFuel = make(prefix(LibItemNames.SPIRIT_FUEL), new Item(defaultBuilder()));
    public static final Item spiritFragment = make(prefix(LibItemNames.SPIRIT_FRAGMENT), new Item(defaultBuilder()));

    public static final Item theChaos = make(prefix(LibItemNames.THE_CHAOS), new Item(defaultBuilder()));
    public static final Item theOrigin = make(prefix(LibItemNames.THE_ORIGIN), new Item(defaultBuilder()));
    public static final Item theEnd = make(prefix(LibItemNames.THE_END), new Item(defaultBuilder()));
    public static final Item theUniverse = make(prefix(LibItemNames.THE_UNIVERSE), new Item(defaultBuilder()));

    //Relic
    public static final Item manaRingMaster = make(prefix(LibItemNames.MANA_RING_MASTER), new MasterBandOfManaItem(unstackable().rarity(Rarity.UNCOMMON)));


    private static <T extends Item> T make(ResourceLocation id, T item) {
        var old = ALL.put(id, item);
        if (old != null) {
            throw new IllegalArgumentException("Typo? Duplicate id " + id);
        }
        return item;
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
