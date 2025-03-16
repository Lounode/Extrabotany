package io.github.lounode.extrabotany.common.item;

import io.github.lounode.extrabotany.common.lib.LibItemNames;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public final class ExtraBotanyItems {
    private static final Map<ResourceLocation, Item> ALL = new LinkedHashMap<>();

    //Mana Item
    public static final Item zadkiel = make(prefix(LibItemNames.ZADKIEL), new ZadkielItem(unstackable()));

    //Material
    public static final Item orichalcos = make(prefix(LibItemNames.ORICHALCOS), new Item(defaultBuilder().rarity(Rarity.EPIC)));
    public static final Item photonium = make(prefix(LibItemNames.PHOTONIUM), new Item(defaultBuilder()));//OT
    public static final Item shadowium = make(prefix(LibItemNames.SHADOWIUM), new Item(defaultBuilder()));//OT
    public static final Item aerialite = make(prefix(LibItemNames.AERIALITE_INGOT), new Item(defaultBuilder()));

    public static final Item theChaos = make(prefix(LibItemNames.THE_CHAOS), new Item(defaultBuilder()));
    public static final Item theOrigin = make(prefix(LibItemNames.THE_ORIGIN), new Item(defaultBuilder()));
    public static final Item theEnd = make(prefix(LibItemNames.THE_END), new Item(defaultBuilder()));
    public static final Item theUniverse = make(prefix(LibItemNames.THE_UNIVERSE), new Item(defaultBuilder()));



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

    public static void registerItems(BiConsumer<Item, ResourceLocation> r) {
        for (var e : ALL.entrySet()) {
            r.accept(e.getValue(), e.getKey());
        }
    }
}
