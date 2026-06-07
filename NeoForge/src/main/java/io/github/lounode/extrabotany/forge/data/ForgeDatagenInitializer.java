package io.github.lounode.extrabotany.forge.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import io.github.lounode.extrabotany.data.AdvancementProvider;
import io.github.lounode.extrabotany.data.BlockstateProvider;
import io.github.lounode.extrabotany.data.FloatingFlowerModelProvider;
import io.github.lounode.extrabotany.data.ItemModelProvider;
import io.github.lounode.extrabotany.data.PatchouliBookProvider;
import io.github.lounode.extrabotany.data.PottedPlantModelProvider;
import io.github.lounode.extrabotany.data.SoundProvider;
import io.github.lounode.extrabotany.data.loot.BlockLootProvider;
import io.github.lounode.extrabotany.data.loot.EntityLootProvider;
import io.github.lounode.extrabotany.data.loot.RewardBagLootProvider;
import io.github.lounode.extrabotany.data.recipes.BrewProvider;
import io.github.lounode.extrabotany.data.recipes.CraftingRecipeProvider;
import io.github.lounode.extrabotany.data.recipes.EdelweissRecipeProvider;
import io.github.lounode.extrabotany.data.recipes.ElvenTradeProvider;
import io.github.lounode.extrabotany.data.recipes.ManaInfusionProvider;
import io.github.lounode.extrabotany.data.recipes.OmnivioletProvider;
import io.github.lounode.extrabotany.data.recipes.PedestalRecipeProvider;
import io.github.lounode.extrabotany.data.recipes.PetalApothecaryProvider;
import io.github.lounode.extrabotany.data.recipes.RunicAltarProvider;
import io.github.lounode.extrabotany.data.recipes.SmeltingProvider;
import io.github.lounode.extrabotany.data.recipes.SmithingRecipeProvider;
import io.github.lounode.extrabotany.data.recipes.StonesiaProvider;
import io.github.lounode.extrabotany.data.recipes.TerrestrialAgglomerationProvider;
import io.github.lounode.extrabotany.data.tags.BlockTagProvider;
import io.github.lounode.extrabotany.data.tags.DamageTypeTagProvider;
import io.github.lounode.extrabotany.data.tags.EntityTypeTagProvider;
import io.github.lounode.extrabotany.data.tags.ItemTagProvider;
import io.github.lounode.extrabotany.common.lib.LibMisc;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes.BACKFIRE;
import static io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes.BACKFIRE_DAMAGE;
import static io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes.EXCALIBUR;
import static io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes.EXCALIBUR_BEAM_DAMAGE;
import static io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes.JINGWEI;
import static io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes.JINGWEI_PUNCH_DAMAGE;
import static io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes.LINK;
import static io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes.LINK_DAMAGE;
import static io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes.REVERSE_HEAL;
import static io.github.lounode.extrabotany.common.ExtraBotanyDamageTypes.REVERSE_HEAL_DAMAGE;

@EventBusSubscriber(modid = LibMisc.MOD_ID)
public class ForgeDatagenInitializer {
	@SubscribeEvent
	public static void configureForgeDatagen(GatherDataEvent evt) {
		configureXplatDatagen(evt);
		configureNeoForgeDatagen(evt);
	}

	private static void configureXplatDatagen(GatherDataEvent evt) {
		var generator = evt.getGenerator();
		var output = new PackOutput(generator.getPackOutput().getOutputFolder().resolve("../../../../Xplat/src/generated/resources").normalize());

		generator.addProvider(evt.includeServer(), new BlockLootProvider(output));
		generator.addProvider(evt.includeServer(), new LootTableProvider(output, Set.of(), List.of(
				new LootTableProvider.SubProviderEntry(lookup -> new EntityLootProvider(), LootContextParamSets.ENTITY),
				new LootTableProvider.SubProviderEntry(lookup -> new RewardBagLootProvider(), LootContextParamSets.EMPTY)
		), evt.getLookupProvider()));

		var blockTagProvider = new BlockTagProvider(output, evt.getLookupProvider());
		generator.addProvider(evt.includeServer(), blockTagProvider);
		generator.addProvider(evt.includeServer(), new ItemTagProvider(output, evt.getLookupProvider(),
				blockTagProvider.contentsGetter()));
		generator.addProvider(evt.includeServer(), new EntityTypeTagProvider(output, evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new DamageTypeTagProvider(output, evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new DatapackBuiltinEntriesProvider(output, evt.getLookupProvider(), addDamageTypes(), Set.of(LibMisc.MOD_ID)));

		generator.addProvider(evt.includeServer(), new PedestalRecipeProvider(output).withLookupProvider(evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new CraftingRecipeProvider(output).withLookupProvider(evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new SmeltingProvider(output).withLookupProvider(evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new SmithingRecipeProvider(output).withLookupProvider(evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new ElvenTradeProvider(output).withLookupProvider(evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new ManaInfusionProvider(output).withLookupProvider(evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new BrewProvider(output).withLookupProvider(evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new PetalApothecaryProvider(output).withLookupProvider(evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new RunicAltarProvider(output).withLookupProvider(evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new TerrestrialAgglomerationProvider(output).withLookupProvider(evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new StonesiaProvider(output).withLookupProvider(evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new EdelweissRecipeProvider(output).withLookupProvider(evt.getLookupProvider()));
		generator.addProvider(evt.includeServer(), new OmnivioletProvider(output).withLookupProvider(evt.getLookupProvider()));

		generator.addProvider(evt.includeClient(), new BlockstateProvider(output));
		generator.addProvider(evt.includeClient(), new FloatingFlowerModelProvider(output));
		generator.addProvider(evt.includeClient(), new ItemModelProvider(output));
		generator.addProvider(evt.includeClient(), new PottedPlantModelProvider(output));
		generator.addProvider(evt.includeClient(), AdvancementProvider.create(output, evt.getLookupProvider()));
		generator.addProvider(evt.includeClient(), new SoundProvider(output, LibMisc.MOD_ID));
		generator.addProvider(evt.includeClient(), new PatchouliBookProvider(output));
	}

	private static void configureNeoForgeDatagen(GatherDataEvent evt) {
		var generator = evt.getGenerator();
		var output = generator.getPackOutput();
		var disabledHelper = new ExistingFileHelper(Collections.emptyList(), Collections.emptySet(), false, null, null);
		var blockTagProvider = new ForgeBlockTagProvider(output, evt.getLookupProvider(), disabledHelper);

		generator.addProvider(evt.includeServer(), blockTagProvider);
		generator.addProvider(evt.includeServer(), new ForgeItemTagProvider(output, evt.getLookupProvider(),
				blockTagProvider.contentsGetter(), disabledHelper));
		generator.addProvider(evt.includeServer(), new ForgeBlockLootProvider(generator));
	}

	private static RegistrySetBuilder addDamageTypes() {
		return new RegistrySetBuilder().add(Registries.DAMAGE_TYPE, ForgeDatagenInitializer::damageTypeBC);
	}

	private static void damageTypeBC(BootstrapContext<DamageType> context) {
		context.register(LINK_DAMAGE, LINK);
		context.register(EXCALIBUR_BEAM_DAMAGE, EXCALIBUR);
		context.register(JINGWEI_PUNCH_DAMAGE, JINGWEI);
		context.register(REVERSE_HEAL_DAMAGE, REVERSE_HEAL);
		context.register(BACKFIRE_DAMAGE, BACKFIRE);
	}
}
