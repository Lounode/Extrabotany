package io.github.lounode.extrabotany.data;

import io.github.lounode.extrabotany.ExtraBotany;
import io.github.lounode.extrabotany.data.recipes.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collections;

@Mod.EventBusSubscriber(modid = ExtraBotany.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper efh = event.getExistingFileHelper();
        var output = gen.getPackOutput();
        var disabledHelper = new ExistingFileHelper(Collections.emptyList(), Collections.emptySet(), false, null, null);

        //Client
        gen.addProvider(event.includeClient(), new ItemModelProvider(output));
        gen.addProvider(event.includeClient(), new BlockstateProvider(output));
        //Server
        var forgeBlockTagProvider = new ForgeBlockTagProvider(output, event.getLookupProvider(), disabledHelper);
        gen.addProvider(event.includeServer(), forgeBlockTagProvider);
        gen.addProvider(event.includeServer(), new ForgeItemTagProvider(output, event.getLookupProvider(),
                forgeBlockTagProvider.contentsGetter(), disabledHelper));
        var blockTagProvider = new BlockTagProvider(output, event.getLookupProvider());
        gen.addProvider(event.includeServer(), blockTagProvider);
        gen.addProvider(event.includeServer(), new ItemTagProvider(output, event.getLookupProvider(), blockTagProvider.contentsGetter()));

        //Recipes
        gen.addProvider(event.includeServer(), new RunicAltarProvider(output));
        gen.addProvider(event.includeServer(), new ManaInfusionProvider(output));
        gen.addProvider(event.includeServer(), new ElvenTradeProvider(output));
        gen.addProvider(event.includeServer(), new TerrestrialAgglomerationProvider(output));
        gen.addProvider(event.includeServer(), new CraftingRecipeProvider(output));

        gen.addProvider(event.includeServer(), new PedestalRecipeProvider(output));

        gen.addProvider(event.includeServer(), AdvancementProvider.create(output, event.getLookupProvider()));
    }
}
