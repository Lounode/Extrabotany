package io.github.lounode.extrabotany.data.recipes;

import com.google.gson.JsonObject;
import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.crafting.BotaniaRecipeTypes;
import vazkii.botania.common.crafting.StateIngredientHelper;
import vazkii.botania.common.helper.ItemNBTHelper;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ManaInfusionProvider extends ExtraBotanyRecipeProvider{
    public ManaInfusionProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "ExtraBotany mana pool recipes";
    }

    @Override
    public void buildRecipes(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer) {
        consumer.accept(new FinishedRecipe(id("nightmare_fuel"), new ItemStack(ExtraBotanyItems.nightmareFuel), Ingredient.of(Items.COAL), 2000));
    }

    protected void cycle(Consumer<net.minecraft.data.recipes.FinishedRecipe> consumer, int cost, String group, ItemLike... items) {
        for (int i = 0; i < items.length; i++) {
            Ingredient in = ingr(items[i]);
            ItemStack out = new ItemStack(i == items.length - 1 ? items[0] : items[i + 1]);
            String id = String.format("%s_to_%s", BuiltInRegistries.ITEM.getKey(items[i].asItem()).getPath(), BuiltInRegistries.ITEM.getKey(out.getItem()).getPath());
            consumer.accept(FinishedRecipe.alchemy(id(id), out, in, cost, group));
        }
    }

    protected FinishedRecipe mini(ItemLike mini, ItemLike full) {
        return FinishedRecipe.alchemy(id(BuiltInRegistries.ITEM.getKey(mini.asItem()).getPath()), new ItemStack(mini), ingr(full), 2500, "botania:flower_shrinking");
    }

    protected FinishedRecipe deconstruct(String id, ItemLike items, ItemLike block) {
        return FinishedRecipe.alchemy(id(id), new ItemStack(items, 4), ingr(block), 25, "botania:block_deconstruction");
    }

    protected ResourceLocation id(String s) {
        return prefix("mana_infusion/" + s);
    }

    protected static Ingredient ingr(ItemLike i) {
        return Ingredient.of(i);
    }

    protected static class FinishedRecipe implements net.minecraft.data.recipes.FinishedRecipe {
        private static final StateIngredient CONJURATION = StateIngredientHelper.of(BotaniaBlocks.conjurationCatalyst);
        private static final StateIngredient ALCHEMY = StateIngredientHelper.of(BotaniaBlocks.alchemyCatalyst);
        //TODO 次元催化器
        //private static final StateIngredient DIMENSION = StateIngredientHelper.of(com.meteor.extrabotany.common.blocks.ModBlocks.dimensioncatalyst);

        private final ResourceLocation id;
        private final Ingredient input;
        private final ItemStack output;
        private final int mana;
        private final String group;
        @Nullable
        private final StateIngredient catalyst;

        public static FinishedRecipe conjuration(ResourceLocation id, ItemStack output, Ingredient input, int mana) {
            return new FinishedRecipe(id, output, input, mana, "", CONJURATION);
        }
        /* TODO 次元催化器
        public static FinishedRecipe dimension(ResourceLocation id, ItemStack output, Ingredient input, int mana) {
            return new FinishedRecipe(id, output, input, mana, "", DIMENSION);
        }
        */
        public static FinishedRecipe alchemy(ResourceLocation id, ItemStack output, Ingredient input, int mana) {
            return alchemy(id, output, input, mana, "");
        }

        public static FinishedRecipe alchemy(ResourceLocation id, ItemStack output, Ingredient input, int mana, String group) {
            return new FinishedRecipe(id, output, input, mana, group, ALCHEMY);
        }

        public FinishedRecipe(ResourceLocation id, ItemStack output, Ingredient input, int mana) {
            this(id, output, input, mana, "");
        }

        public FinishedRecipe(ResourceLocation id, ItemStack output, Ingredient input, int mana, String group) {
            this(id, output, input, mana, group, null);
        }

        public FinishedRecipe(ResourceLocation id, ItemStack output, Ingredient input, int mana, String group, @Nullable StateIngredient catalyst) {
            this.id = id;
            this.input = input;
            this.output = output;
            this.mana = mana;
            this.group = group;
            this.catalyst = catalyst;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("input", input.toJson());
            json.add("output", ItemNBTHelper.serializeStack(output));
            json.addProperty("mana", mana);
            if (!group.isEmpty()) {
                json.addProperty("group", group);
            }
            if (catalyst != null) {
                json.add("catalyst", catalyst.serialize());
            }
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return BotaniaRecipeTypes.MANA_INFUSION_SERIALIZER;
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
