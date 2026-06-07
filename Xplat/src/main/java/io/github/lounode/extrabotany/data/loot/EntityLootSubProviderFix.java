package io.github.lounode.extrabotany.data.loot;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityPredicate.Builder;
import net.minecraft.advancements.critereon.EntitySubPredicates;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class EntityLootSubProviderFix implements LootTableSubProvider {
	protected static final EntityPredicate.Builder ENTITY_ON_FIRE = Builder.entity().flags(EntityFlagsPredicate.Builder.flags().setOnFire(true));
	private static final Set<EntityType<?>> SPECIAL_LOOT_TABLE_TYPES;
	private final FeatureFlagSet allowed;
	private final FeatureFlagSet required;
	private final Map<EntityType<?>, Map<ResourceKey<LootTable>, LootTable.Builder>> map;

	protected EntityLootSubProviderFix(FeatureFlagSet enabledFeatures) {
		this(enabledFeatures, enabledFeatures);
	}

	protected EntityLootSubProviderFix(FeatureFlagSet allowed, FeatureFlagSet required) {
		this.map = Maps.newHashMap();
		this.allowed = allowed;
		this.required = required;
	}

	protected static LootTable.Builder createSheepTable(ItemLike woolItem) {
		return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(woolItem))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(NestedLootTable.lootTableReference(EntityType.SHEEP.getDefaultLootTable())));
	}

	protected abstract Stream<EntityType<?>> getKnownEntityTypes();

	public abstract void generate();

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
		this.generate();
		Set<ResourceKey<LootTable>> set = Sets.newHashSet();
		getKnownEntityTypes().forEach((entitytype) -> {
			ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(entitytype);
			if (entitytype.isEnabled(this.allowed)) {
				if (canHaveLootTable(entitytype)) {
					Map<ResourceKey<LootTable>, LootTable.Builder> map = this.map.remove(entitytype);
					ResourceKey<LootTable> resourcekey = entitytype.getDefaultLootTable();
					if (resourcekey != BuiltInLootTables.EMPTY && entitytype.isEnabled(this.required) && (map == null || !map.containsKey(resourcekey))) {
						throw new IllegalStateException(String.format(Locale.ROOT, "Missing loottable '%s' for '%s'", resourcekey, key));
					}

					if (map != null) {
						map.forEach((p_250376_, p_250972_) -> {
							if (!set.add(p_250376_)) {
								throw new IllegalStateException(String.format(Locale.ROOT, "Duplicate loottable '%s' for '%s'", p_250376_, key));
							} else {
								output.accept(p_250376_, p_250972_);
							}
						});
					}
				} else {
					Map<ResourceKey<LootTable>, LootTable.Builder> map1 = this.map.remove(entitytype);
					if (map1 != null) {
						throw new IllegalStateException(String.format(Locale.ROOT, "Weird loottables '%s' for '%s', not a LivingEntity so should not have loot", map1.keySet().stream().map(lootTable -> lootTable.location().toString()).collect(Collectors.joining(",")), key));
					}
				}
			}

		});
		if (!this.map.isEmpty()) {
			throw new IllegalStateException("Created loot tables for entities not supported by datapack: " + this.map.keySet());
		}
	}

	private static boolean canHaveLootTable(EntityType<?> entityType) {
		return SPECIAL_LOOT_TABLE_TYPES.contains(entityType) || entityType.getCategory() != MobCategory.MISC;
	}

	protected LootItemCondition.Builder killedByFrog() {
		return DamageSourceCondition.hasDamageSource(net.minecraft.advancements.critereon.DamageSourcePredicate.Builder.damageType().source(Builder.entity().of(EntityType.FROG)));
	}

	protected LootItemCondition.Builder killedByFrogVariant(FrogVariant frogVariant) {
		return DamageSourceCondition.hasDamageSource(net.minecraft.advancements.critereon.DamageSourcePredicate.Builder.damageType().source(Builder.entity().of(EntityType.FROG).subPredicate(EntitySubPredicates.frogVariant(BuiltInRegistries.FROG_VARIANT.wrapAsHolder(frogVariant)))));
	}

	protected void add(EntityType<?> entityType, LootTable.Builder builder) {
		this.add(entityType, entityType.getDefaultLootTable(), builder);
	}

	protected void add(EntityType<?> entityType, ResourceKey<LootTable> lootTableLocation, LootTable.Builder builder) {
		this.map.computeIfAbsent(entityType, (p_251466_) -> new HashMap<>()).put(lootTableLocation, builder);
	}

	protected static ResourceKey<LootTable> lootTable(ResourceLocation location) {
		return ResourceKey.create(Registries.LOOT_TABLE, location);
	}

	static {
		SPECIAL_LOOT_TABLE_TYPES = ImmutableSet.of(EntityType.PLAYER, EntityType.ARMOR_STAND, EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM, EntityType.VILLAGER);
	}
}
