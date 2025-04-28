package io.github.lounode.extrabotany.data.loot;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public abstract class RewardBagLootSubProvider implements LootTableSubProvider {
    private final Map<ResourceLocation, LootTable.Builder> map;

    protected RewardBagLootSubProvider() {
        this.map = Maps.newHashMap();
    }

    public abstract void generate();
    public abstract String getNameSpace();

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> output) {
        this.generate();
        Set<ResourceLocation> duplicates = Sets.newHashSet();
        Set<ResourceLocation> processed = Sets.newHashSet();

        for (var entry : this.map.entrySet()) {
            ResourceLocation location = entry.getKey();
            if (!processed.add(location)) {
                duplicates.add(location);
            }
        }

        if (!duplicates.isEmpty()) {
            throw new IllegalStateException("Duplicate loot tables: " + duplicates);
        }

        this.map.forEach((location, builder) -> {
            try {
                output.accept(location, builder);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to create loot table '" + location + "'", e);
            }
        });

    }

    protected void add(ResourceLocation lootTableLocation, LootTable.Builder builder) {
        this.map.put(lootTableLocation, builder);
    }

    public void add(String key, LootTable.Builder builder) {
        ResourceLocation lootTableLocation = new ResourceLocation(getNameSpace(), key).withPrefix("reward_bags/");
        this.add(lootTableLocation, builder);
    }
}
