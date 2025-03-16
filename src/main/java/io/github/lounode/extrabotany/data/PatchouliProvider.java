package io.github.lounode.extrabotany.data;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.data.patchouli.CraftingPage;
import io.github.lounode.extrabotany.data.patchouli.PatchouliEntry;
import io.github.lounode.extrabotany.data.patchouli.TextPage;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PatchouliProvider implements DataProvider {
    private final PackOutput packOutput;
    private static final String NAMESPACE = "botania";
    private static final String BOOK = "lexicon";
    private static final ResourceLocation EXTRA_BOTANY_CATEGORY = ResourceLocation.fromNamespaceAndPath("botania","extrabotanies");


    private List<PatchouliEntry> entries = new ArrayList<>();

    public PatchouliProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }
    @NotNull
    @Override
    public String getName() {
        return "ExtraBotany Patchouli";
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        PackOutput.PathProvider pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "patchouli_books");
        List<CompletableFuture<?>> output = new ArrayList<>();

        String nameKey = "extrabotany_nametest";
        TextPage page = new TextPage("Hello World");

        PatchouliEntry entry = new PatchouliEntry(
                EXTRA_BOTANY_CATEGORY,
                ResourceLocation.fromNamespaceAndPath("extrabotany", nameKey))
                .withIcon(ExtraBotanyItems.orichalcos)
                .withPage(page)
                ;

        entries.add(entry);

        entries.add(new PatchouliEntry(EXTRA_BOTANY_CATEGORY, ExtraBotanyItems.orichalcos)
                .withLocalizedText()
                .withPage(new CraftingPage(Items.IRON_BLOCK))
                .withPage(new CraftingPage(Items.IRON_INGOT))
        );

        for (PatchouliEntry e : entries) {
            output.add(DataProvider.saveStable(cache, e.build(), pathProvider.json(
                getPath(NAMESPACE, BOOK, e.getCategory().getPath(), e.getName().getPath())
            )));
        }

        return CompletableFuture.allOf(output.toArray(CompletableFuture[]::new));
    }


    private ResourceLocation getPath(String namespace,String book, String category, String fileName) {
        return ResourceLocation.fromNamespaceAndPath(namespace, Path.of(book, "en_us/entries", category, fileName).toString().replace("\\","/"));
    }

}
