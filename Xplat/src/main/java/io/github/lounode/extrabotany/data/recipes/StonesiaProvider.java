package io.github.lounode.extrabotany.data.recipes;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import vazkii.botania.api.recipe.StateIngredient;
import vazkii.botania.common.crafting.StateIngredients;

import io.github.lounode.extrabotany.common.crafting.StonesiasRecipe;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.location;
import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class StonesiaProvider extends ExtraBotanyRecipeProvider {
	public StonesiaProvider(PackOutput packOutput) {
		super(packOutput);
	}

	@Override
	protected void buildRecipes(RecipeOutput recipeOutput) {
		//Stone
		save(recipeOutput, id("stone"), forTag(BlockTags.BASE_STONE_OVERWORLD), 20);
		save(recipeOutput, id("cobblestone"), StateIngredients.anyOf(
				forTag(location("c", "cobblestones"))
		), 10);
		//Minecraft Ores
		save(recipeOutput, id("coal_ore"), vanillaOre(BlockTags.COAL_ORES), 305);
		save(recipeOutput, id("iron_ore"), vanillaOre(BlockTags.IRON_ORES), 370);
		save(recipeOutput, id("diamond_ore"), vanillaOre(BlockTags.DIAMOND_ORES), 750);
		save(recipeOutput, id("redstone_ore"), vanillaOre(BlockTags.REDSTONE_ORES), 330);
		save(recipeOutput, id("lapis_ore"), vanillaOre(BlockTags.LAPIS_ORES), 290);
		save(recipeOutput, id("gold_ore"), vanillaOre(BlockTags.GOLD_ORES), 580);
		save(recipeOutput, id("emerald_ore"), vanillaOre(BlockTags.EMERALD_ORES), 690);
		save(recipeOutput, id("copper_ore"), vanillaOre(BlockTags.COPPER_ORES), 320);
		save(recipeOutput, id("quartz_ore"), ore("quartz"), 340);

		//Mod ores
		save(recipeOutput, id("tin_ore"), ore("tin"), 320);//锡
		save(recipeOutput, id("aluminum_ore"), ore("aluminum"), 390);//铝
		save(recipeOutput, id("lead_ore"), ore("lead"), 390);//铅
		save(recipeOutput, id("nickel_ore"), ore("nickel"), 480);//镍
		save(recipeOutput, id("silver_ore"), ore("silver"), 580);//银
		save(recipeOutput, id("zinc_ore"), ore("zinc"), 340);//锌
		save(recipeOutput, id("platinum_ore"), ore("platinum"), 800);//铂
		save(recipeOutput, id("uranium_ore"), ore("uranium"), 580);//铀
		save(recipeOutput, id("osmium_ore"), ore("osmium"), 450);//锇
		save(recipeOutput, id("cinnabar_ore"), ore("cinnabar"), 470);//朱砂
		save(recipeOutput, id("sulfur_ore"), ore("sulfur"), 290);//硫
		save(recipeOutput, id("apatite_ore"), ore("apatite"), 100);//磷灰石
		save(recipeOutput, id("niter_ore"), ore("niter"), 470);//硝石
		save(recipeOutput, id("tungsten_ore"), ore("tungsten"), 580);//钨
		save(recipeOutput, id("ruby_ore"), ore("ruby"), 800);//红宝石
		save(recipeOutput, id("sapphire_ore"), ore("sapphire"), 800);//蓝宝石
		save(recipeOutput, id("iridium_ore"), ore("iridium"), 800);//铱

		//consumer.accept(new Result(id("amber_ore"), ore("amber"), 470));//琥珀
		//consumer.accept(new Result(id("cobalt_ore"), ore("cobalt"), 540));//钴
		//consumer.accept(new Result(id("ardite_ore"), ore("ardite"), 540));//阿迪特
		//consumer.accept(new Result(id("mithril_ore"), ore("mithril"), 1300));//秘银
	}

	protected ResourceLocation id(String id) {
		return prefix("stonesia/" + id);
	}

	protected static StateIngredient forBlock(Block block) {
		return StateIngredients.of(block);
	}

	protected static StateIngredient forTag(ResourceLocation tag) {
		return StateIngredients.of(TagKey.create(net.minecraft.core.registries.Registries.BLOCK, tag));
	}

	protected static StateIngredient forTag(TagKey<Block> tag) {
		return StateIngredients.of(tag);
	}

	protected static StateIngredient ore(String id) {
		return forTag(location("c", "ores/" + id));
	}

	protected static StateIngredient vanillaOre(TagKey<Block> tag) {
		StateIngredient modTagIngredient = ore(tag.location().getPath().replace("_ores", ""));

		return StateIngredients.anyOf(
				forTag(tag),
				modTagIngredient
		);
	}

	private static void save(RecipeOutput recipeOutput, ResourceLocation id, StateIngredient input, int outputMana) {
		recipeOutput.accept(id, new StonesiasRecipe(id, input, outputMana), null);
	}

	@Override
	public String getName() {
		return "Extrabotany Stonesia recipes";
	}
}
