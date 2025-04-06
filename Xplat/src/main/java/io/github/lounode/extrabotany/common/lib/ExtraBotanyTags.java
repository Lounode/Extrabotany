package io.github.lounode.extrabotany.common.lib;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ExtraBotanyTags {
    public static class Items {
        public static final TagKey<Item> INGOTS_ORICHALCOS = tag("orichalcos_ingots");
        public static final TagKey<Item> INGOTS_PHOTONIUM = tag("photonium_ingots");
        public static final TagKey<Item> INGOTS_SHADOWIUM = tag("shadowium_ingots");
        public static final TagKey<Item> INGOTS_AERIALITE = tag("aerialite_ingots");

        public static final TagKey<Item> NUGGETS_ORICHALCOS = tag("orichalcos_nuggets");
        public static final TagKey<Item> NUGGETS_PHOTONIUM = tag("photonium_nuggets");
        public static final TagKey<Item> NUGGETS_SHADOWIUM = tag("shadowium_nuggets");
        public static final TagKey<Item> NUGGETS_AERIALITE = tag("aerialite_nuggets");

        public static final TagKey<Item> BLOCKS_ORICHALCOS = tag("orichalcos_blocks");
        public static final TagKey<Item> BLOCKS_PHOTONIUM = tag("photonium_blocks");
        public static final TagKey<Item> BLOCKS_SHADOWIUM = tag("shadowium_blocks");
        public static final TagKey<Item> BLOCKS_AERIALITE = tag("aerialite_blocks");

        public static final TagKey<Item> HAMMERS = tag("hammers");
        public static final TagKey<Item> PEDESTALS = tag("pedestals");



        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, prefix(name));
        }
    }

    public static class Blocks {
        public static final TagKey<Block> BLOCKS_ORICHALCOS = tag("orichalcos_blocks");
        public static final TagKey<Block> BLOCKS_PHOTONIUM = tag("photonium_blocks");
        public static final TagKey<Block> BLOCKS_SHADOWIUM = tag("shadowium_blocks");
        public static final TagKey<Block> BLOCKS_AERIALITE = tag("aerialite_blocks");
        public static final TagKey<Block> PEDESTALS = tag("pedestals");

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, prefix(name));
        }
    }

    public static class DamageTypes {
        public static final TagKey<DamageType> PEACE_AMULET_AVAILABLE = tag("peace_amulet_available");

        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, prefix(name));
        }
    }
}
