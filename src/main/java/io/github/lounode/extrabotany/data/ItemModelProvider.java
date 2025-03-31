package io.github.lounode.extrabotany.data;

import com.google.gson.JsonElement;
import io.github.lounode.extrabotany.ExtraBotany;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.DelegatedModel;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;


import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static io.github.lounode.extrabotany.common.item.ExtraBotanyItems.*;
import static vazkii.botania.data.ItemModelProvider.takeAll;

public class ItemModelProvider implements DataProvider {
    private final PackOutput packOutput;

    public ItemModelProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Set<Item> items = BuiltInRegistries.ITEM.stream().filter(i -> ExtraBotany.MODID.equals(BuiltInRegistries.ITEM.getKey(i).getNamespace()))
                .collect(Collectors.toSet());
        Map<ResourceLocation, Supplier<JsonElement>> map = new HashMap<>();

        registerItemBlocks(takeAll(items, i -> i instanceof BlockItem).stream().map(i -> (BlockItem) i).collect(Collectors.toSet()), map::put);
        registerItemOverrides(items, map::put);
        registerItems(items, map::put);

        PackOutput.PathProvider modelPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
        List<CompletableFuture<?>> output = new ArrayList<>();

        for (Map.Entry<ResourceLocation, Supplier<JsonElement>> e : map.entrySet()) {
            ResourceLocation id = e.getKey();
            output.add(DataProvider.saveStable(cache, e.getValue().get(), modelPathProvider.json(id)));
        }

        return CompletableFuture.allOf(output.toArray(CompletableFuture[]::new));
    }
    private void registerItemBlocks(Set<BlockItem> itemBlocks, BiConsumer<ResourceLocation, Supplier<JsonElement>> consumer) {
        itemBlocks.forEach(i -> {
            consumer.accept(ModelLocationUtils.getModelLocation(i), new DelegatedModel(ModelLocationUtils.getModelLocation(i.getBlock())));
        });
    }
    private static void registerItems(Set<Item> items, BiConsumer<ResourceLocation, Supplier<JsonElement>> consumer) {
        takeAll(items, i -> true).forEach(i -> ModelTemplates.FLAT_ITEM.create(ModelLocationUtils.getModelLocation(i), TextureMapping.layer0(i), consumer));
    }

    private static void registerItemOverrides(Set<Item> items, BiConsumer<ResourceLocation, Supplier<JsonElement>> consumer) {
        //Manual items
        items.remove(failnaught);
    }
    @NotNull
    @Override
    public String getName() {
        return "ExtraBotany item models";
    }
}
