package io.github.lounode.extrabotany.common.crafting.recipe;

import io.github.lounode.extrabotany.common.brew.BrewUtil;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.api.brew.Brew;
import vazkii.botania.common.brew.BotaniaBrews;

public abstract class CopyBrewRecipe extends ShapelessRecipe {

    public CopyBrewRecipe(ShapelessRecipe compose) {
        super(compose.getId(), compose.getGroup(), compose.category(), compose.getResultItem(RegistryAccess.EMPTY), compose.getIngredients());
    }

    public abstract Item getBrewSource();
    @Override
    public abstract @NotNull RecipeSerializer<?> getSerializer();

    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer inv, @NotNull RegistryAccess registries) {
        ItemStack out = super.assemble(inv, registries);
        Brew brew = null;

        for(int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack input = inv.getItem(i);
            if (!input.is(getBrewSource())) {
                continue;
            }
            if (BrewUtil.getBrew(input) == BotaniaBrews.fallbackBrew) {
                continue;
            }
            brew = BrewUtil.getBrew(input);
        }

        if (brew == null) {
            return ItemStack.EMPTY;
        }

        BrewUtil.setBrew(out, brew);

        return out;
    }
}
