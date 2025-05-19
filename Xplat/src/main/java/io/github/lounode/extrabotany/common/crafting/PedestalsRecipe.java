package io.github.lounode.extrabotany.common.crafting;

import com.google.gson.JsonObject;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.github.lounode.extrabotany.api.recipe.PedestalRecipe;

public class PedestalsRecipe implements PedestalRecipe {
	private final ResourceLocation id;
	private final ItemStack output;

	private final Ingredient smashTools;
	private final Ingredient input;
	private final int strike;
	private final int exp;

	public PedestalsRecipe(ResourceLocation id, ItemStack output, Ingredient smashTools, Ingredient input, int strike, int exp) {
		this.id = id;
		this.output = output;
		this.input = input;
		this.smashTools = smashTools;
		this.strike = strike;
		this.exp = exp;
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

	@Override
	public int getStrike() {
		return strike;
	}

	@Override
	public int getExp() {
		return exp;
	}

	@NotNull
	@Override
	public RecipeType<? extends PedestalRecipe> getType() {
		return ExtraBotanyRecipeTypes.PEDESTAL_SMASH_TYPE;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ExtraBotanyRecipeTypes.PEDESTAL_SMASH_SERIALIZER;
	}

	public static class Serializer implements RecipeSerializer<PedestalsRecipe> {
		@NotNull
		@Override
		public PedestalsRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
			ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
			Ingredient input = Ingredient.fromJson(json.get("input"));
			Ingredient smashTools = Ingredient.fromJson(json.get("smash_tools"));
			int strike = json.get("strike").getAsInt();
			int exp = json.get("exp").getAsInt();

			return new PedestalsRecipe(id, output, smashTools, input, strike, exp);
		}

		@Override
		public @Nullable PedestalsRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buf) {
			Ingredient smashTools = Ingredient.fromNetwork(buf);
			Ingredient input = Ingredient.fromNetwork(buf);
			ItemStack output = buf.readItem();
			int strike = buf.readInt();
			int exp = buf.readInt();
			return new PedestalsRecipe(id, output, smashTools, input, strike, exp);
		}

		@Override
		public void toNetwork(@NotNull FriendlyByteBuf buf, @NotNull PedestalsRecipe recipe) {
			recipe.smashTools.toNetwork(buf);
			recipe.input.toNetwork(buf);
			buf.writeItem(recipe.output);
			buf.writeInt(recipe.strike);
			buf.writeInt(recipe.exp);
		}
	}
}
