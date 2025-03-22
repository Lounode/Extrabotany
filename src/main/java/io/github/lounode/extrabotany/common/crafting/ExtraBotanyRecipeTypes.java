package io.github.lounode.extrabotany.common.crafting;

import io.github.lounode.extrabotany.api.recipe.PedestalRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import vazkii.botania.mixin.RecipeManagerAccessor;

import java.util.Map;
import java.util.function.BiConsumer;

public class ExtraBotanyRecipeTypes {
    public static final RecipeType<PedestalRecipe> PEDESTAL_SMASH_TYPE = new ModRecipeType<>();
    public static final RecipeSerializer<PedestalsRecipe> PEDESTAL_SMASH_SERIALIZER = new PedestalsRecipe.Serializer();

    public static void submitRecipeTypes(BiConsumer<RecipeType<?>, ResourceLocation> r) {
        r.accept(PEDESTAL_SMASH_TYPE, PedestalsRecipe.TYPE_ID);
    }
    public static void submitRecipeSerializers(BiConsumer<RecipeSerializer<?>, ResourceLocation> r) {
        r.accept(PEDESTAL_SMASH_SERIALIZER, PedestalsRecipe.TYPE_ID);
    }

    private static class ModRecipeType<T extends Recipe<?>> implements RecipeType<T> {
        @Override
        public String toString() {
            return BuiltInRegistries.RECIPE_TYPE.getKey(this).toString();
        }
    }

    public static <C extends Container, T extends Recipe<C>> Map<ResourceLocation, T> getRecipes(Level world, RecipeType<T> type) {
        return ((RecipeManagerAccessor) world.getRecipeManager()).botania_getAll(type);
    }
}
