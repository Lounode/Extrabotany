package io.github.lounode.extrabotany.common.crafting.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.common.crafting.recipe.NoOpRecipeSerializer;
import vazkii.botania.common.helper.ItemNBTHelper;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.xplat.EXplatAbstractions;

import java.util.UUID;

public final class DasRheingoldChangeSoulBoundRecipe extends CustomRecipe {

	private static final String TAG_SOULBIND_UUID = "soulbindUUID";
	public static final NoOpRecipeSerializer<DasRheingoldChangeSoulBoundRecipe> SERIALIZER = new NoOpRecipeSerializer<>(DasRheingoldChangeSoulBoundRecipe::new);

	public DasRheingoldChangeSoulBoundRecipe(ResourceLocation id) {
		super(id, CraftingBookCategory.EQUIPMENT);
	}

	@Override
	public boolean matches(CraftingContainer inv, Level level) {
		boolean findRheinGold = false;
		boolean findRelic = false;

		for (int i = 0; i < inv.getContainerSize(); i++) {
			ItemStack stack = inv.getItem(i);
			if (stack.isEmpty()) {
				continue;
			}
			if (stack.is(ExtraBotanyItems.dasRheingold)) {
				if (findRheinGold) {
					return false;
				}
				findRheinGold = true;
			} else if (EXplatAbstractions.INSTANCE.findRelic(stack) != null) {
				if (findRelic) {
					return false;
				}
				findRelic = true;
			}
		}

		return findRheinGold && findRelic;
	}

	@Override
	public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
		ItemStack rheingold = ItemStack.EMPTY;
		ItemStack relic = ItemStack.EMPTY;

		for (int i = 0; i < container.getContainerSize(); i++) {
			ItemStack stack = container.getItem(i);
			if (stack.isEmpty()) {
				continue;
			}

			if (stack.getItem() == ExtraBotanyItems.dasRheingold) {
				rheingold = stack;
			} else {
				relic = stack;
			}
		}

		if (rheingold.isEmpty() || relic.isEmpty()) {
			return ItemStack.EMPTY;
		}

		if (EXplatAbstractions.INSTANCE.findRelic(relic) == null) {
			return ItemStack.EMPTY;
		}

		ItemStack relicOutput = relic.copy();
		UUID rheingoldUUID = getSoulbindUUID(rheingold);

		if (rheingoldUUID == null) {
			ItemNBTHelper.removeEntry(relicOutput, TAG_SOULBIND_UUID);
		} else {
			bindToUUID(relicOutput, rheingoldUUID);
		}

		return relicOutput;
	}

	public static void bindToUUID(ItemStack stack, UUID uuid) {
		ItemNBTHelper.setString(stack, TAG_SOULBIND_UUID, uuid.toString());
	}

	@Nullable
	public static UUID getSoulbindUUID(ItemStack stack) {
		if (ItemNBTHelper.verifyExistance(stack, TAG_SOULBIND_UUID)) {
			try {
				return UUID.fromString(ItemNBTHelper.getString(stack, TAG_SOULBIND_UUID, ""));
			} catch (IllegalArgumentException ex) { // Bad UUID in tag
				ItemNBTHelper.removeEntry(stack, TAG_SOULBIND_UUID);
			}
		}

		return null;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width > 1 || height > 1;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
}
