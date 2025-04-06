package io.github.lounode.extrabotany.data.recipes;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import vazkii.botania.common.item.BotaniaItems;

import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class SmithingRecipeProvider extends ExtraBotanyRecipeProvider{
    public SmithingRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    private static InventoryChangeTrigger.TriggerInstance conditionsFromItem(ItemLike item) {
        return CraftingRecipeProvider.conditionsFromItem(item);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        SmithingTransformRecipeBuilder.smithing(
                Ingredient.of(BotaniaItems.dreamwoodTwig),
                Ingredient.of(BotaniaItems.terraSword),
                Ingredient.of(ExtraBotanyItems.heroMedal),
                RecipeCategory.COMBAT,
                ExtraBotanyItems.excalibur
                )
                .unlocks("has_item", conditionsFromItem(ExtraBotanyItems.heroMedal))
                .save(consumer, id(ExtraBotanyItems.excalibur));
    }

    protected ResourceLocation id(ItemLike itemLike) {
        return prefix("smithing/" + getItemName(itemLike));
    }

    protected static String getItemName(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
    }
    @Override
    public String getName() {
        return "Extrabotany smithing recipes";
    }
}
