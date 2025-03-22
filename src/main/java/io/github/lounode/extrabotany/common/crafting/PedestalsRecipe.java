package io.github.lounode.extrabotany.common.crafting;

import com.google.gson.JsonObject;
import io.github.lounode.extrabotany.api.recipe.PedestalRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PedestalsRecipe implements PedestalRecipe {
    private final ResourceLocation id;
    private final ItemStack output;

    private final Ingredient smashTools;
    private final Ingredient input;

    public PedestalsRecipe(ResourceLocation id, ItemStack output, Ingredient smashTools, Ingredient input) {
        this.id = id;
        this.output = output;
        this.input = input;
        this.smashTools = smashTools;
    }
    @Override
    public boolean matches(Container container, Level world) {
        ItemStack inputStack = container.getItem(0);
        return input.test(inputStack);
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return getResultItem(registryAccess).copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public Ingredient getSmashTools() {
        return smashTools;
    }

    @Override
    public Ingredient getInput() {
        return input;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    public static class Serializer implements RecipeSerializer<PedestalsRecipe> {
        @NotNull
        @Override
        public PedestalsRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            Ingredient input = Ingredient.fromJson(json.get("input"));
            Ingredient smashTools = Ingredient.fromJson(json.get("smash_tools"));

            return new PedestalsRecipe(id, output, smashTools, input);
        }

        @Override
        public @Nullable PedestalsRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
            Ingredient smashTools = Ingredient.fromNetwork(buf);
            Ingredient input = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            return new PedestalsRecipe(id, output, smashTools, input);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull PedestalsRecipe recipe) {
            recipe.smashTools.toNetwork(buf);
            recipe.input.toNetwork(buf);
            buf.writeItem(recipe.output);
        }
    }
}
