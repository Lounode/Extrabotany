package io.github.lounode.extrabotany.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import vazkii.botania.common.block.block_entity.mana.ManaPoolBlockEntity;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.helper.ItemNBTHelper;
import vazkii.botania.common.item.BotaniaItems;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class TerrestrialAgglomerationProvider extends ExtraBotanyRecipeProvider{
    public TerrestrialAgglomerationProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "ExtraBotany Terra Plate recipes";
    }

    @Override
    public void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
        consumer.accept(new FinishedRecipe(idFor("aerialite_ingot"), ManaPoolBlockEntity.MAX_MANA / 2,
                new ItemStack(ExtraBotanyItems.aerialite),
                Ingredient.of(BotaniaItems.enderAirBottle),
                Ingredient.of(BotaniaItems.dragonstone),
                Ingredient.of(Items.PHANTOM_MEMBRANE)));
        consumer.accept(new FinishedRecipe(idFor("the_universe"), ManaPoolBlockEntity.MAX_MANA,
                new ItemStack(ExtraBotanyItems.theUniverse),
                Ingredient.of(ExtraBotanyItems.theChaos),
                Ingredient.of(ExtraBotanyItems.theOrigin),
                Ingredient.of(ExtraBotanyItems.theEnd)
                ));
    }


    private static ResourceLocation idFor(String s) {
        return prefix("terra_plate/" + s);
    }

    protected static class FinishedRecipe implements net.minecraft.data.recipes.FinishedRecipe {
        private final ResourceLocation id;
        private final int mana;
        private final ItemStack output;
        private final Ingredient[] inputs;

        public FinishedRecipe(ResourceLocation id, int mana, ItemStack output, Ingredient... inputs) {
            this.id = id;
            this.mana = mana;
            this.output = output;
            this.inputs = inputs;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("mana", mana);
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingr : inputs) {
                ingredients.add(ingr.toJson());
            }
            json.add("ingredients", ingredients);
            json.add("result", ItemNBTHelper.serializeStack(output));
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return BotaniaRecipeTypes.TERRA_PLATE_SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
