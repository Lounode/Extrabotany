package io.github.lounode.extrabotany.data.recipes;

import com.google.gson.JsonObject;
import io.github.lounode.extrabotany.common.item.ManaReaderItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.common.item.WandOfTheForestItem;

public class WandOfTheForestExtendRecipe extends ShapelessRecipe {
    public static final RecipeSerializer<WandOfTheForestExtendRecipe> SERIALIZER = new Serializer();

    public WandOfTheForestExtendRecipe(ShapelessRecipe compose) {
        super(
                compose.getId(),
                compose.getGroup(),
                CraftingBookCategory.EQUIPMENT,
                compose.getResultItem(RegistryAccess.EMPTY),
                compose.getIngredients()
        );
    }

    @NotNull
    @Override
    public ItemStack assemble(CraftingContainer inv, @NotNull RegistryAccess registries) {
        ItemStack wandInput = null;

        for(int i = 0; i < inv.getContainerSize(); i++) {
            if (inv.getItem(i).getItem() instanceof WandOfTheForestItem) {
                wandInput = inv.getItem(i);
            }
        }
        assert wandInput != null;

        ItemStack wandOutput = wandInput.copy();

        CompoundTag tag = wandOutput.getOrCreateTag();
        CompoundTag extraBotanyTag = new CompoundTag();
        CompoundTag manaReaderTag = new CompoundTag();
        manaReaderTag.putBoolean("enable", true);
        extraBotanyTag.put("mana_reader", manaReaderTag);
        tag.put("extrabotany", extraBotanyTag);

        return wandOutput;
    }
    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    private static class Serializer implements RecipeSerializer<WandOfTheForestExtendRecipe> {
        @NotNull
        @Override
        public WandOfTheForestExtendRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
            return new WandOfTheForestExtendRecipe(SHAPELESS_RECIPE.fromJson(recipeId, json));
        }

        @NotNull
        @Override
        public WandOfTheForestExtendRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
            return new WandOfTheForestExtendRecipe(SHAPELESS_RECIPE.fromNetwork(recipeId, buffer));
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull WandOfTheForestExtendRecipe recipe) {
            SHAPELESS_RECIPE.toNetwork(buffer, recipe);
        }
    }
}
