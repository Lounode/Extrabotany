package io.github.lounode.datagenlib.patchouli;

import com.google.gson.JsonObject;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;


//
public class PatchouliProvider implements DataProvider {
    protected final PackOutput packOutput;
    public List<PatchouliPage> pages = new ArrayList<>();

    private ResourceLocation TEST = prefix("testcategory");


    public PatchouliProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        /*
        addPage(new PatchouliBuilder(TEST, "testentry")

        );

         */
        return null;
    }

    public String getLangPath(String name, int count) {
        return "ars_nouveau.page" + count + "." + name;
    }

    public String getLangPath(String name) {
        return "ars_nouveau.page." + name;
    }

    public PatchouliPage addPage(PatchouliBuilder builder, Path path) {
        return addPage(new PatchouliPage(builder, path));
    }

    public PatchouliPage addPage(PatchouliPage patchouliPage){
        this.pages.add(patchouliPage);
        return patchouliPage;
    }
    /*
    public PatchouliBuilder buildBasicItem(ItemLike item, ResourceLocation category, IPatchouliPage recipePage) {
        PatchouliBuilder builder = new PatchouliBuilder(category, item.asItem().getDescriptionId())
                //.withIcon(item.asItem())
                //.withPage(new TextPage("ars_nouveau.page." + getRegistryName(item.asItem()).getPath()));
        if (recipePage != null) {
            builder.withPage(recipePage);
        }
        return builder;
    }

    public PatchouliPage addBasicItem(ItemLike item, ResourceLocation category, IPatchouliPage recipePage) {
        PatchouliBuilder builder = buildBasicItem(item, category, recipePage);
        return addPage(new PatchouliPage(builder, getPath(category, getRegistryName(item.asItem()))));
    }
    */
    /*
    public Path getPath(ResourceLocation category, ResourceLocation fileName) {
        return this.packOutput.resolve("assets/ars_nouveau/patchouli_books/worn_notebook/en_us/entries/" + category.getPath() + "/" + fileName.getPath() + ".json");
    }

    public Path getPath(ResourceLocation category, String fileName) {
        return this.output.resolve("assets/ars_nouveau/patchouli_books/worn_notebook/en_us/entries/" + category.getPath() + "/" + fileName + ".json");
    }

    @Override
    public void collectJsons(CachedOutput pOutput) {
        addEntries();
        for (PatchouliPage patchouliPage : pages) {
            saveStable(pOutput, patchouliPage.build(), patchouliPage.path);
        }
    }
    */
    public record PatchouliPage(PatchouliBuilder builder, Path path) {
        @Override
        public Path path() {
            return path;
        }

        public JsonObject build() {
            return builder.build();
        }

        public String relationPath(){
            String fileName = path.getFileName().toString();
            fileName = FilenameUtils.removeExtension(fileName);
            return builder.category.toString() + "/" + fileName;
        }
    }




    @Override
    public String getName() {
        return "Patchouli";
    }
}
