package io.github.lounode.extrabotany.data;

import io.github.lounode.extrabotany.ExtraBotany;
import io.github.lounode.extrabotany.data.recipes.RunicAltarProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExtraBotany.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper efh = event.getExistingFileHelper();

        gen.addProvider(
                event.includeClient(),
                (DataProvider.Factory<ItemModelProvider>) output -> new ItemModelProvider(gen.getPackOutput())
        );
        gen.addProvider(
                event.includeClient(),
                (DataProvider.Factory<BlockstateProvider>) output -> new BlockstateProvider(gen.getPackOutput())
        );

        gen.addProvider(
                event.includeServer(),
                (DataProvider.Factory<RunicAltarProvider>) output -> new RunicAltarProvider(gen.getPackOutput())
        );

    }
}
