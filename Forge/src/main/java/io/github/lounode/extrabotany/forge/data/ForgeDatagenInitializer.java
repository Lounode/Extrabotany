package io.github.lounode.extrabotany.forge.data;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;

import io.github.lounode.extrabotany.common.lib.LibMisc;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeDatagenInitializer {
	@SubscribeEvent
	public static void configureForgeDatagen(GatherDataEvent evt) {
		var generator = evt.getGenerator();
		var output = generator.getPackOutput();
		var disabledHelper = new ExistingFileHelper(Collections.emptyList(), Collections.emptySet(), false, null, null);
		var blockTagProvider = new ForgeBlockTagProvider(output, evt.getLookupProvider(), disabledHelper);

		generator.addProvider(evt.includeServer(), blockTagProvider);
		generator.addProvider(evt.includeServer(), new ForgeItemTagProvider(output, evt.getLookupProvider(),
				blockTagProvider.contentsGetter(), disabledHelper));
		generator.addProvider(evt.includeServer(), new ForgeBlockLootProvider(generator));
	}
}
