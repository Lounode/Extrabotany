package io.github.lounode.extrabotany.common.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public final class ItemStackDataHelper {
	private ItemStackDataHelper() {}

	public static void set(ItemStack stack, String key, Tag value) {
		update(stack, tag -> tag.put(key, value));
	}

	public static Tag get(ItemStack stack, String key) {
		return data(stack).get(key);
	}

	public static void setString(ItemStack stack, String key, String value) {
		update(stack, tag -> tag.putString(key, value));
	}

	public static String getString(ItemStack stack, String key, String fallback) {
		CompoundTag tag = data(stack);
		return tag.contains(key) ? tag.getString(key) : fallback;
	}

	public static void setInt(ItemStack stack, String key, int value) {
		update(stack, tag -> tag.putInt(key, value));
	}

	public static int getInt(ItemStack stack, String key, int fallback) {
		CompoundTag tag = data(stack);
		return tag.contains(key) ? tag.getInt(key) : fallback;
	}

	public static void setLong(ItemStack stack, String key, long value) {
		update(stack, tag -> tag.putLong(key, value));
	}

	public static long getLong(ItemStack stack, String key, long fallback) {
		CompoundTag tag = data(stack);
		return tag.contains(key) ? tag.getLong(key) : fallback;
	}

	public static void setBoolean(ItemStack stack, String key, boolean value) {
		update(stack, tag -> tag.putBoolean(key, value));
	}

	public static boolean getBoolean(ItemStack stack, String key, boolean fallback) {
		CompoundTag tag = data(stack);
		return tag.contains(key) ? tag.getBoolean(key) : fallback;
	}

	public static void setList(ItemStack stack, String key, ListTag value) {
		update(stack, tag -> tag.put(key, value));
	}

	public static ListTag getList(ItemStack stack, String key, int elementType, boolean nullSafe) {
		CompoundTag tag = data(stack);
		return tag.contains(key) ? tag.getList(key, elementType) : new ListTag();
	}

	public static void removeEntry(ItemStack stack, String key) {
		CompoundTag tag = data(stack);
		tag.remove(key);
		write(stack, tag);
	}

	public static boolean verifyExistance(ItemStack stack, String key) {
		return data(stack).contains(key);
	}

	public static boolean hasCustomData(ItemStack stack) {
		return !data(stack).isEmpty();
	}

	public static JsonElement serializeStack(ItemStack stack) {
		return ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, stack).result().orElseGet(() -> {
			JsonObject fallback = new JsonObject();
			fallback.addProperty("id", stack.getItem().builtInRegistryHolder().key().location().toString());
			fallback.addProperty("count", stack.getCount());
			return fallback;
		});
	}

	private static CompoundTag data(ItemStack stack) {
		return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
	}

	private static void update(ItemStack stack, java.util.function.Consumer<CompoundTag> updater) {
		CompoundTag tag = data(stack);
		updater.accept(tag);
		write(stack, tag);
	}

	private static void write(ItemStack stack, CompoundTag tag) {
		if (tag.isEmpty()) {
			stack.remove(DataComponents.CUSTOM_DATA);
		} else {
			stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
		}
	}
}
