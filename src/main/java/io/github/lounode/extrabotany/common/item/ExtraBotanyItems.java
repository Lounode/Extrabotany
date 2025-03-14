package io.github.lounode.extrabotany.common.item;

import io.github.lounode.extrabotany.common.item.lib.LibItemNames;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static io.github.lounode.extrabotany.common.item.lib.ResourceLocationHelper.prefix;

public final class ExtraBotanyItems {
    private static final Map<ResourceLocation, Item> ALL = new LinkedHashMap<>();

    public static final Item ZADKIEL = make(prefix(LibItemNames.ZADKIEL), new Item(defaultBuilder()));

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
