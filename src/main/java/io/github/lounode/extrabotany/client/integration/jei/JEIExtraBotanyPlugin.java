package io.github.lounode.extrabotany.client.integration.jei;

import io.github.lounode.extrabotany.api.recipe.PedestalRecipe;
import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import io.github.lounode.extrabotany.common.crafting.ExtraBotanyRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.recipe.OrechidRecipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

@JeiPlugin
public class JEIExtraBotanyPlugin implements IModPlugin {
    private static final ResourceLocation ID = prefix("main");


    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(
                new PedestalRecipeCategory(registry.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        for(Block pedestal : ExtraBotanyBlocks.ALL_PEDESTALS) {
            registry.addRecipeCatalyst(new ItemStack(pedestal), PedestalRecipeCategory.TYPE);
        }
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registry) {
        registry.addRecipes(PedestalRecipeCategory.TYPE, sortRecipes(ExtraBotanyRecipeTypes.PEDESTAL_SMASH_TYPE, BY_SMASH_TOOLS.thenComparing(BY_GROUP).thenComparing(BY_ID)));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }

    private static final Comparator<Recipe<?>> BY_ID = Comparator.comparing(Recipe::getId);
    private static final Comparator<Recipe<?>> BY_GROUP = Comparator.comparing(Recipe::getGroup);
    private static final Comparator<OrechidRecipe> BY_WEIGHT = Comparator.<OrechidRecipe, Integer>comparing(OrechidRecipe::getWeight).reversed();
    private static final Comparator<PedestalRecipe> BY_SMASH_TOOLS = (l, r) -> {
        Ingredient left = l.getSmashTools();
        Ingredient right = r.getSmashTools();
        if (left == null) {
            return right == null ? 0 : -1;
        } else if (right == null) {
            return 1;
        } else {
            return left.toString().compareTo(right.toString());
        }
    };
    private static <T extends Recipe<C>, C extends Container> List<T> sortRecipes(RecipeType<T> type, Comparator<? super T> comparator) {
        Collection<T> recipes = ExtraBotanyRecipeTypes.getRecipes(Minecraft.getInstance().level, type).values();
        List<T> list = new ArrayList<>(recipes);
        list.sort(comparator);
        return list;
    }
}
