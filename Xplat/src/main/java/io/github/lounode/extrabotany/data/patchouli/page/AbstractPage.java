package io.github.lounode.extrabotany.data.patchouli.page;

import com.google.gson.JsonObject;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractPage<T extends AbstractPage<T>> implements IPatchouliPage {
	protected JsonObject object = new JsonObject();

	@SuppressWarnings("unchecked")
	public T withAdvancement(ResourceLocation advancement) {
		object.addProperty("advancement", advancement.toString());
		return (T) this;
	}

	public T withAdvancement(Advancement advancement) {
		return withAdvancement(advancement.getId());
	}

	@Override
	public JsonObject build() {
		object.addProperty("type", getType().toString());
		return object;
	}
}
