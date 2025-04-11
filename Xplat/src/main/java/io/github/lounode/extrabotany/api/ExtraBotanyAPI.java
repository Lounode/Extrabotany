package io.github.lounode.extrabotany.api;

import io.github.lounode.extrabotany.api.item.equipment.bauble.CoreOfTheVoidVariant;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vazkii.botania.api.ServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ExtraBotanyAPI {
    String MODID = "extrabotany";
    Logger LOGGER = LoggerFactory.getLogger(MODID);

    ExtraBotanyAPI INSTANCE = ServiceUtil.findService(ExtraBotanyAPI.class, () -> new ExtraBotanyAPI() {});

    ArmorMaterial DUMMY_ARMOR_MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurabilityForType(@NotNull ArmorItem.Type type) {
            return 0;
        }

        @Override
        public int getDefenseForType(@NotNull ArmorItem.Type type) {
            return 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 0;
        }

        @NotNull
        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_LEATHER;
        }

        @NotNull
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }

        @Override
        public @NotNull String getName() {
            return "missingno";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };

    Tier DUMMY_ITEM_TIER = new Tier() {
        @Override
        public int getUses() {
            return 0;
        }

        @Override
        public float getSpeed() {
            return 0;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 0;
        }

        @NotNull
        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }
    };

    static ExtraBotanyAPI instance() {
        return INSTANCE;
    }

    default void registerCOVVariant(CoreOfTheVoidVariant variant) {}

    default Map<String ,CoreOfTheVoidVariant> getCOVVariants() {
        return new HashMap<>();
    }
}
