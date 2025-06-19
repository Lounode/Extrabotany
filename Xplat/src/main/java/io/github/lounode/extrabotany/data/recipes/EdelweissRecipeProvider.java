package io.github.lounode.extrabotany.data.recipes;

import com.google.gson.JsonObject;

import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.RecipeSerializer;

import org.jetbrains.annotations.Nullable;

import io.github.lounode.extrabotany.common.crafting.ExtraBotanyRecipeTypes;

import java.util.function.Consumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class EdelweissRecipeProvider extends ExtraBotanyRecipeProvider {

	public EdelweissRecipeProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
		consumer.accept(new Eat(id("snow_golem"), EntityTypePredicate.of(EntityType.SNOW_GOLEM), 3200));
	}

	protected ResourceLocation id(String id) {
		return prefix("edelweiss/" + id);
	}

	protected static class Eat implements net.minecraft.data.recipes.FinishedRecipe {
		private final ResourceLocation id;
		private final EntityTypePredicate input;
		private final int outputMana;

		public Eat(ResourceLocation id, EntityTypePredicate input, int outputMana) {
			this.id = id;
			this.input = input;
			this.outputMana = outputMana;
		}

		@Override
		public void serializeRecipeData(JsonObject json) {
			json.add("input", input.serializeToJson());
			json.addProperty("outputMana", outputMana);
		}

		@Override
		public ResourceLocation getId() {
			return id;
		}

		@Override
		public RecipeSerializer<?> getType() {
			return ExtraBotanyRecipeTypes.EDELWEISS_SERIALIZER;
		}

		@Nullable
		@Override
		public JsonObject serializeAdvancement() {
			return null;
		}

		@Nullable
		@Override
		public ResourceLocation getAdvancementId() {
			return null;
		}
	}

	@Override
	public String getName() {
		return "Extrabotany Edelweiss recipes";
	}
}
