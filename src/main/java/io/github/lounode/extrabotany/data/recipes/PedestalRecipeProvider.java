package io.github.lounode.extrabotany.data.recipes;

import com.google.gson.JsonObject;
import io.github.lounode.extrabotany.common.crafting.ExtraBotanyRecipeTypes;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.common.helper.ItemNBTHelper;

import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class PedestalRecipeProvider extends ExtraBotanyRecipeProvider{
    public PedestalRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }


    @Override
    public String getName() {
        return "ExtraBotany pedestal recipes";
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        consumer.accept(new FinishedPedestalRecipe(id("gilded_potato_mashed"),
                new ItemStack(ExtraBotanyItems.gildedPotatoMashed),
                Ingredient.of(ExtraBotanyItems.gildedPotato),
                5
        ));
        consumer.accept(new FinishedPedestalRecipe(id("spirit_fragment"),
                new ItemStack(ExtraBotanyItems.spiritFragment),
                Ingredient.of(ExtraBotanyItems.spiritFuel),
                5
        ));
    }

    protected ResourceLocation id(String s) {
        return prefix("pedestal_smash/" + s);
    }

    protected static class FinishedPedestalRecipe implements net.minecraft.data.recipes.FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient input;
        private final ItemStack output;
        private final Ingredient hammer;
        private final int experience;
        //private final int strike;

        public FinishedPedestalRecipe(ResourceLocation id, ItemStack output, Ingredient input, @Nullable Ingredient hammer, int experience) {
            this.id = id;
            this.output = output;
            this.input = input;
            //TODO 做出来锤子后改这里
            this.hammer = hammer == null ? Ingredient.of(Items.STICK) : hammer;
            this.experience = experience;
        }

        public FinishedPedestalRecipe(ResourceLocation id, ItemStack output, Ingredient input) {
            this(id, output, input, null, 0);
        }

        public FinishedPedestalRecipe(ResourceLocation id, ItemStack output, Ingredient input, int experience) {
            this(id, output, input, null, experience);
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("input", input.toJson());
            json.add("output", ItemNBTHelper.serializeStack(output));
            json.add("smash_tools", hammer.toJson());
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ExtraBotanyRecipeTypes.PEDESTAL_SMASH_SERIALIZER;
        }

        @Override
        public @Nullable JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public @Nullable ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
