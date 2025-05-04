package io.github.lounode.extrabotany.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.helper.ItemNBTHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ElvenTradeProvider extends ExtraBotanyRecipeProvider{
    public ElvenTradeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {

    }

    private static ResourceLocation id(String path) {
        return prefix("elven_trade/" + path);
    }

    @Override
    public String getName() {
        return "ExtraBotany elven trade recipes";
    }

    protected static class FinishedElvenRecipe implements FinishedRecipe {
        private final ResourceLocation id;
        private final List<Ingredient> inputs;
        private final List<ItemStack> outputs;

        public FinishedElvenRecipe(ResourceLocation id, ItemStack output, Ingredient... inputs) {
            this(id, Arrays.asList(inputs), Collections.singletonList(output));
        }

        protected FinishedElvenRecipe(ResourceLocation id, List<Ingredient> inputs, List<ItemStack> outputs) {
            this.id = id;
            this.inputs = inputs;
            this.outputs = outputs;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray in = new JsonArray();
            for (Ingredient ingr : inputs) {
                in.add(ingr.toJson());
            }

            JsonArray out = new JsonArray();
            for (ItemStack s : outputs) {
                out.add(ItemNBTHelper.serializeStack(s));
            }

            json.add("ingredients", in);
            json.add("output", out);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return BotaniaRecipeTypes.ELVEN_TRADE_SERIALIZER;
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
