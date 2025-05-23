package io.github.lounode.extrabotany.common.crafting.recipe;

import com.google.gson.JsonObject;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import vazkii.botania.common.helper.ItemNBTHelper;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;

import java.util.UUID;

public final class DasRheingoldSoulbindRecipe extends ShapelessRecipe {

	private static final String TAG_SOULBIND_UUID = "soulbindUUID";
	public static final RecipeSerializer<DasRheingoldSoulbindRecipe> SERIALIZER = new DasRheingoldSoulbindRecipe.Serializer();

	public DasRheingoldSoulbindRecipe(ShapelessRecipe compose) {
		super(
				compose.getId(),
				compose.getGroup(),
				CraftingBookCategory.EQUIPMENT,
				compose.getResultItem(RegistryAccess.EMPTY),
				compose.getIngredients()
		);
	}

	@Override
	public boolean matches(CraftingContainer inv, Level level) {
		ItemStack rheingoldInput = ItemStack.EMPTY;
		ItemStack relic = ItemStack.EMPTY;

		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == ExtraBotanyItems.dasRheingold) {
					rheingoldInput = stack;
				} else {
					relic = stack;
				}
			}
		}

		if (rheingoldInput.isEmpty() || relic.isEmpty()) {
			return false;
		}

		return EXplatAbstractions.INSTANCE.findRelic(relic) != null;
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
		ItemStack rheingoldInput = ItemStack.EMPTY;
		ItemStack relic = ItemStack.EMPTY;

		for (int i = 0; i < container.getContainerSize(); i++) {
			ItemStack stack = container.getItem(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == ExtraBotanyItems.dasRheingold) {
					rheingoldInput = stack;
				} else {
					relic = stack;
				}
			}
		}

		if (rheingoldInput.isEmpty() || relic.isEmpty()) {
			return ItemStack.EMPTY;
		}

		if (EXplatAbstractions.INSTANCE.findRelic(relic) == null) {
			return ItemStack.EMPTY;
		}

		ItemStack relicOutput = relic.copy();
		UUID rheingoldUUID = getSoulbindUUID(rheingoldInput);

		if (rheingoldUUID == null) {
			ItemNBTHelper.removeEntry(relicOutput, TAG_SOULBIND_UUID);
		} else {
			bindToUUID(relicOutput, rheingoldUUID);
		}

		return relicOutput;
	}

	public void bindToUUID(ItemStack stack, UUID uuid) {
		ItemNBTHelper.setString(stack, TAG_SOULBIND_UUID, uuid.toString());
	}

	@Nullable
	public UUID getSoulbindUUID(ItemStack stack) {
		if (ItemNBTHelper.verifyExistance(stack, TAG_SOULBIND_UUID)) {
			try {
				return UUID.fromString(ItemNBTHelper.getString(stack, TAG_SOULBIND_UUID, ""));
			} catch (IllegalArgumentException ex) { // Bad UUID in tag
				ItemNBTHelper.removeEntry(stack, TAG_SOULBIND_UUID);
			}
		}

		return null;
	}

	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	private static class Serializer implements RecipeSerializer<DasRheingoldSoulbindRecipe> {
		@NotNull
		@Override
		public DasRheingoldSoulbindRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
			return new DasRheingoldSoulbindRecipe(SHAPELESS_RECIPE.fromJson(recipeId, json));
		}

		@NotNull
		@Override
		public DasRheingoldSoulbindRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {
			return new DasRheingoldSoulbindRecipe(SHAPELESS_RECIPE.fromNetwork(recipeId, buffer));
		}

		@Override
		public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull DasRheingoldSoulbindRecipe recipe) {
			SHAPELESS_RECIPE.toNetwork(buffer, recipe);
		}
	}
}
