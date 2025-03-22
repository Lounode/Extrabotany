package io.github.lounode.extrabotany.client.integration.jei;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.lounode.extrabotany.ExtraBotany;
import io.github.lounode.extrabotany.api.recipe.PedestalRecipe;
import io.github.lounode.extrabotany.common.block.ExtraBotanyBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PedestalRecipeCategory implements IRecipeCategory<PedestalRecipe> {
    public static final RecipeType<PedestalRecipe> TYPE =
            RecipeType.create(ExtraBotany.MODID, "pedestal_smash", PedestalRecipe.class);
    private final Component localizedName;
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;
    private final ItemStack renderStack = new ItemStack(ExtraBotanyBlocks.livingrockPedestal);

    public PedestalRecipeCategory(IGuiHelper guiHelper) {
        this.localizedName = Component.translatable("extrabotany.jei.pedestal");
        this.background = guiHelper.createBlankDrawable(142, 60);
        this.overlay = guiHelper.createDrawable(ResourceLocation.fromNamespaceAndPath("botania","textures/gui/pure_daisy_overlay.png"),
                0, 0, 64, 46);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, renderStack.copy());
    }
    @Override
    public RecipeType<PedestalRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return localizedName;
    }

    @Override
    public int getWidth() {
        return 142;
    }

    @Override
    public int getHeight() {
        return 60;
    }


    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(PedestalRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics gui, double mouseX, double mouseY) {
        background.draw(gui, 0, 0);
        RenderSystem.enableBlend();


        overlay.draw(gui, 40, 0);

        RenderSystem.disableBlend();
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PedestalRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 32, 12)
                .addIngredients(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 93, 12)
                .addItemStack(recipe.getResultItem(RegistryAccess.EMPTY));
        builder.addSlot(RecipeIngredientRole.CATALYST, 62, 12)
                .addItemStack(renderStack);
        builder.addSlot(RecipeIngredientRole.CATALYST, 62, 45)
                .addIngredients(recipe.getSmashTools());

    }
}
