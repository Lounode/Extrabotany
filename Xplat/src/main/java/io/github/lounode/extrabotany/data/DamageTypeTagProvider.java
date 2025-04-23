package io.github.lounode.extrabotany.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes.*;
import static io.github.lounode.extrabotany.common.lib.ExtraBotanyTags.DamageTypes.PEACE_AMULET_AVAILABLE;
import static net.minecraft.tags.DamageTypeTags.*;

public class DamageTypeTagProvider extends TagsProvider<DamageType> {
    Map<ResourceKey<DamageType>, List<TagKey<DamageType>>> map = new HashMap<>();
    public DamageTypeTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Registries.DAMAGE_TYPE, lookupProvider);
        map.put(EXCALIBUR_BEAM_DAMAGE, List.of(
                PEACE_AMULET_AVAILABLE, BYPASSES_ARMOR, BYPASSES_SHIELD
        ));
        map.put(LINK_DAMAGE, List.of(
                NO_IMPACT, WITCH_RESISTANT_TO
        ));
        map.put(JINGWEI_PUNCH_DAMAGE, List.of(
                PEACE_AMULET_AVAILABLE, IS_FIRE
        ));
        map.put(REVERSE_HEAL_DAMAGE, List.of(
                BYPASSES_ARMOR, BYPASSES_SHIELD, BYPASSES_RESISTANCE, BYPASSES_EFFECTS, BYPASSES_ENCHANTMENTS,
                NO_IMPACT, WITCH_RESISTANT_TO
        ));
        map.put(BACKFIRE_DAMAGE, List.of(
                BYPASSES_ARMOR, BYPASSES_SHIELD, BYPASSES_RESISTANCE, BYPASSES_EFFECTS, BYPASSES_ENCHANTMENTS,
                NO_IMPACT, WITCH_RESISTANT_TO
        ));
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        for (var entry : map.entrySet()) {
            ResourceKey<DamageType> damageType = entry.getKey();
            for (TagKey<DamageType> tag : entry.getValue()) {
                this.tag(tag).add(damageType);
            }
        }
    }
}
