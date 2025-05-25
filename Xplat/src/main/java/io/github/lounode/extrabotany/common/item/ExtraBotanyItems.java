package io.github.lounode.extrabotany.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.RecipeSerializer;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.xplat.XplatAbstractions;

import io.github.lounode.extrabotany.common.crafting.recipe.CopyBrewFormFlaskRecipe;
import io.github.lounode.extrabotany.common.crafting.recipe.CopyBrewFromManaCocktailRecipe;
import io.github.lounode.extrabotany.common.crafting.recipe.DasRheingoldSoulbindRecipe;
import io.github.lounode.extrabotany.common.crafting.recipe.WandOfTheForestExtendRecipe;
import io.github.lounode.extrabotany.common.item.brew.HolyWaterGrenadeItem;
import io.github.lounode.extrabotany.common.item.brew.InfiniteWineItem;
import io.github.lounode.extrabotany.common.item.brew.ManaCocktailItem;
import io.github.lounode.extrabotany.common.item.brew.ManaGlassBottleItem;
import io.github.lounode.extrabotany.common.item.equipment.armor.pleiades_combat_maid.PleiadesCombatMaidArmorItem;
import io.github.lounode.extrabotany.common.item.equipment.armor.pleiades_combat_maid.PleiadesCombatMaidSuitDarkenedItem;
import io.github.lounode.extrabotany.common.item.equipment.armor.pleiades_combat_maid.PleiadesCombatMaidSuitItem;
import io.github.lounode.extrabotany.common.item.equipment.armor.starry_idol.StarryIdolArmorItem;
import io.github.lounode.extrabotany.common.item.equipment.armor.starry_idol.StarryIdolHeadgearItem;
import io.github.lounode.extrabotany.common.item.equipment.bauble.FeatherOfJingweiItem;
import io.github.lounode.extrabotany.common.item.equipment.bauble.NatureOrbItem;
import io.github.lounode.extrabotany.common.item.equipment.shield.ManasteelShieldItem;
import io.github.lounode.extrabotany.common.item.equipment.tool.MagicFingerItem;
import io.github.lounode.extrabotany.common.item.equipment.tool.hammer.*;
import io.github.lounode.extrabotany.common.item.material.ChallangeTicketItem;
import io.github.lounode.extrabotany.common.item.material.GildedPotatoItem;
import io.github.lounode.extrabotany.common.item.material.HammerTiers;
import io.github.lounode.extrabotany.common.item.relic.*;
import io.github.lounode.extrabotany.common.item.relic.void_archives.VoidArchivesItem;
import io.github.lounode.extrabotany.common.item.relic.voidcore.CoreOfTheVoidItem;
import io.github.lounode.extrabotany.common.lib.LibItemNames;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public final class ExtraBotanyItems {
	public static final Map<ResourceLocation, Item> ALL = new LinkedHashMap<>();

	//Mana Item
	public static final Item zadkiel = make(prefix(LibItemNames.ZADKIEL), new ZadkielItem(unstackable()));
	public static final Item manaReader = make(prefix(LibItemNames.MANA_READER), new ManaReaderItem(unstackable()));
	public static final Item natureOrb = make(prefix(LibItemNames.NATURE_ORB), new NatureOrbItem(unstackable().rarity(Rarity.UNCOMMON)));
	public static final Item featherOfJingwei = make(prefix(LibItemNames.FEATHER_OF_JINGWEI), new FeatherOfJingweiItem(unstackable()));
	public static final Item magicFinger = make(prefix(LibItemNames.MAGIC_FINGER), new MagicFingerItem(unstackable()));
	public static final Item walkingCane = make(prefix(LibItemNames.WALKING_CANE), new WalkingCaneItem(unstackable()));
	public static final Item coreOfTheVoid = make(prefix(LibItemNames.CORE_OF_THE_VOID), new CoreOfTheVoidItem(unstackable().rarity(Rarity.UNCOMMON)));
	public static final Item voidArchives = make(prefix(LibItemNames.VOID_ARCHIVES), new VoidArchivesItem(unstackable().rarity(Rarity.EPIC)));

	//Hammer
	public static final Item manasteelHammer = make(prefix(LibItemNames.MANASTEEL_HAMMER),
			new ManasteelHammerItem(HammerTiers.MANASTEEL, 6, -3.1F, unstackableCustomDamage()));
	public static final Item elementiumHammer = make(prefix(LibItemNames.ELEMENTIUM_HAMMER),
			new ElementiumHammerItem(HammerTiers.ELEMENTIUM, 6, -3.1F, unstackableCustomDamage()));
	public static final Item terrasteelHammer = make(prefix(LibItemNames.TERRASTEEL_HAMMER),
			new TerrasteelHammerItem(HammerTiers.TERRASTEEL, 5, -3.0F, unstackableCustomDamage().rarity(Rarity.UNCOMMON)));
	public static final Item gaiaHammer = make(prefix(LibItemNames.GAIA_HAMMER),
			new GaiaHammerItem(HammerTiers.GAIA, 5, -3.0F, unstackableCustomDamage().rarity(Rarity.UNCOMMON).fireResistant()));
	public static final Item photoniumHammer = make(prefix(LibItemNames.PHOTONIUM_HAMMER),
			new PhotoniumHammerItem(HammerTiers.PHOTONIUM, 6, -3.1F, unstackableCustomDamage()));
	public static final Item shadowiumHammer = make(prefix(LibItemNames.SHADOWIUM_HAMMER),
			new ShadowiumHammerItem(HammerTiers.SHADOWIUM, 6, -3.1F, unstackableCustomDamage()));
	public static final Item aerialiteHammer = make(prefix(LibItemNames.AERIALITE_HAMMER),
			new AerialiteHammerItem(HammerTiers.AERIALITE, 5, -3.0F, unstackableCustomDamage().rarity(Rarity.UNCOMMON)));
	public static final Item orichalcosHammer = make(prefix(LibItemNames.ORICHALCOS_HAMMER),
			new OrichalcosHammer(HammerTiers.ORICHALCOS, 5, -3.0F, unstackableCustomDamage().rarity(Rarity.EPIC).fireResistant()));
	public static final Item rheinHammer = make(prefix(LibItemNames.RHEIN_HAMMER),
			new RheinHammerItem(unstackableCustomDamage().rarity(Rarity.EPIC).fireResistant()));
	//Shield
	public static final Item manasteelShield = make(prefix(LibItemNames.MANASTEEL_SHIELD),
			new ManasteelShieldItem(unstackable().durability(BotaniaAPI.instance().getManasteelItemTier().getUses())));
	//Armor
	public static final Item starryIdolHeadgear = make(prefix(LibItemNames.STARRY_IDOL_HEADGEAR),
			new StarryIdolHeadgearItem(unstackableCustomDamage()));
	public static final Item starryIdolSuit = make(prefix(LibItemNames.STARRY_IDOL_SUIT),
			new StarryIdolArmorItem(ArmorItem.Type.CHESTPLATE, unstackableCustomDamage()));
	public static final Item starryIdolSkirt = make(prefix(LibItemNames.STARRY_IDOL_SKIRT),
			new StarryIdolArmorItem(ArmorItem.Type.LEGGINGS, unstackableCustomDamage()));
	public static final Item starryIdolBoots = make(prefix(LibItemNames.STARRY_IDOL_BOOTS),
			new StarryIdolArmorItem(ArmorItem.Type.BOOTS, unstackableCustomDamage()));

	public static final Item pleiadesCombatMaidHeadgear = make(prefix(LibItemNames.PLEIADES_COMBAT_MAID_HEADGEAR),
			new PleiadesCombatMaidArmorItem(ArmorItem.Type.HELMET, unstackableCustomDamage()));
	public static final Item pleiadesCombatMaidSuit = make(prefix(LibItemNames.PLEIADES_COMBAT_MAID_SUIT),
			new PleiadesCombatMaidSuitItem(unstackableCustomDamage()));
	public static final Item pleiadesCombatMaidSkirt = make(prefix(LibItemNames.PLEIADES_COMBAT_MAID_SKIRT),
			new PleiadesCombatMaidArmorItem(ArmorItem.Type.LEGGINGS, unstackableCustomDamage()));
	public static final Item pleiadesCombatMaidBoots = make(prefix(LibItemNames.PLEIADES_COMBAT_MAID_BOOTS),
			new PleiadesCombatMaidArmorItem(ArmorItem.Type.BOOTS, unstackableCustomDamage()));
	public static final Item pleiadesCombatMaidSuitDarkened = make(prefix(LibItemNames.PLEIADES_COMBAT_MAID_SUIT_DARKENED),
			new PleiadesCombatMaidSuitDarkenedItem(unstackableCustomDamage()));

	//Relic
	public static final Item manaRingMaster = make(prefix(LibItemNames.MANA_RING_MASTER), new MasterBandOfManaItem(unstackable().rarity(Rarity.UNCOMMON).fireResistant()));
	public static final Item camera = make(prefix(LibItemNames.CAMERA), new CameraItem(unstackable().rarity(Rarity.UNCOMMON).fireResistant()));
	public static final Item failnaught = make(prefix(LibItemNames.FAILNAUGHT), new FailnaughtItem(unstackable().rarity(Rarity.UNCOMMON).fireResistant()));
	public static final Item excalibur = make(prefix(LibItemNames.EXCALIBUR), new ExcaliburItem(unstackable().rarity(Rarity.UNCOMMON).fireResistant()));
	//Armor

	//Material
	public static final Item orichalcos = make(prefix(LibItemNames.ORICHALCOS), new Item(defaultBuilder().rarity(Rarity.EPIC).fireResistant()));
	public static final Item photonium = make(prefix(LibItemNames.PHOTONIUM), new Item(defaultBuilder()));
	public static final Item shadowium = make(prefix(LibItemNames.SHADOWIUM), new Item(defaultBuilder()));
	public static final Item aerialite = make(prefix(LibItemNames.AERIALITE_INGOT), new Item(defaultBuilder()));
	public static final Item orichalcosNugget = make(prefix(LibItemNames.ORICHALCOS_NUGGET), new Item(defaultBuilder().fireResistant()));
	public static final Item photoniumNugget = make(prefix(LibItemNames.PHOTONIUM_NUGGET), new Item(defaultBuilder()));
	public static final Item shadowiumNugget = make(prefix(LibItemNames.SHADOWIUM_NUGGET), new Item(defaultBuilder()));
	public static final Item aerialiteNugget = make(prefix(LibItemNames.AERIALITE_NUGGET), new Item(defaultBuilder()));
	public static final Item gaiaQuartz = make(prefix(LibItemNames.GAIA_QUARTZ), new Item(defaultBuilder()));
	public static final Item elementiumQuartz = make(prefix(LibItemNames.ELEMENTIUM_QUARTZ), new Item(defaultBuilder()));

	public static final Item dasRheingold = make(prefix(LibItemNames.DAS_RHEINGOLD), new DasRheingoldItem(defaultBuilder()));
	public static final Item gildedPotato = make(prefix(LibItemNames.GILDED_POTATO), new GildedPotatoItem(defaultBuilder()));
	public static final Item gildedPotatoMashed = make(prefix(LibItemNames.GILDED_POTATO_MASHED), new Item(defaultBuilder()));
	public static final Item heroMedal = make(prefix(LibItemNames.HERO_MEDAL), new Item(defaultBuilder().rarity(Rarity.UNCOMMON)));//OT
	public static final Item challengeTicket = make(prefix(LibItemNames.CHALLENGE_TICKET), new ChallangeTicketItem(defaultBuilder()));
	public static final Item nightmareFuel = make(prefix(LibItemNames.NIGHTMARE_FUEL), new NightmareFuelItem(defaultBuilder()
			.food(new FoodProperties.Builder().nutrition(0).saturationMod(0.3F).alwaysEat()
					.effect(new MobEffectInstance(MobEffects.HARM, 1, 1), 1.0F)
					.effect(new MobEffectInstance(MobEffects.BLINDNESS, 500, 1), 1.0F)
					.effect(new MobEffectInstance(MobEffects.WEAKNESS, 500, 1), 1.0F)
					.effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 500, 1), 1.0F)
					.effect(new MobEffectInstance(MobEffects.UNLUCK, 500, 1), 1.0F)
					.build()
			)
	));
	public static final Item spiritFuel = make(prefix(LibItemNames.SPIRIT_FUEL), new SpiritFuelItem(defaultBuilder()
			.food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3F).alwaysEat()
					.effect(new MobEffectInstance(MobEffects.HEAL, 1, 1), 1.0F)
					.effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 500, 1), 1.0F)
					.effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 500, 1), 1.0F)
					.effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 500, 1), 1.0F)
					.effect(new MobEffectInstance(MobEffects.LUCK, 500, 1), 1.0F)
					.build()
			)
	));
	public static final Item spiritFragment = make(prefix(LibItemNames.SPIRIT_FRAGMENT), new Item(defaultBuilder()));
	public static final Item manaGlassBottle = make(prefix(LibItemNames.MANA_GLASS_BOTTLE), new ManaGlassBottleItem(defaultBuilder()));

	public static final Item theChaos = make(prefix(LibItemNames.THE_CHAOS), new Item(defaultBuilder()));
	public static final Item theOrigin = make(prefix(LibItemNames.THE_ORIGIN), new Item(defaultBuilder()));
	public static final Item theEnd = make(prefix(LibItemNames.THE_END), new Item(defaultBuilder()));
	public static final Item theUniverse = make(prefix(LibItemNames.THE_UNIVERSE), new Item(defaultBuilder()));

	public static final Item einsRewardBag = make(prefix(LibItemNames.EINS_REWARD_BAG), new RewardBagItem(defaultBuilder(), prefix("eins")));
	public static final Item zweiRewardBag = make(prefix(LibItemNames.ZWEI_REWARD_BAG), new RewardBagItem(defaultBuilder(), prefix("zwei")));
	public static final Item dreiRewardBag = make(prefix(LibItemNames.DREI_REWARD_BAG), new RewardBagItem(defaultBuilder(), prefix("drei")));
	public static final Item vierRewardBag = make(prefix(LibItemNames.VIER_REWARD_BAG), new RewardBagItem(defaultBuilder(), prefix("vier")));
	public static final Item nineAndThreeQuartersRewardBag = make(prefix(LibItemNames.NINE_AND_THREE_QUARTERS_REWARD_BAG), new RewardBagItem(defaultBuilder(), prefix("nine_and_three_quarters")));
	public static final Item pandorasBox = make(prefix(LibItemNames.PANDORAS_BOX), new PandorasBoxItem(unstackable().rarity(Rarity.UNCOMMON), prefix("pandoras_box")));

	//Brews
	public static final Item manaCocktail = make(prefix(LibItemNames.MANA_COCKTAIL), new ManaCocktailItem(unstackable().craftRemainder(manaGlassBottle), 8, 32, () -> manaGlassBottle));
	public static final Item infiniteWine = make(prefix(LibItemNames.INFINITE_WINE), new InfiniteWineItem(unstackable().rarity(Rarity.RARE).craftRemainder(manaGlassBottle), 12, 18, () -> manaGlassBottle));
	public static final Item holyWaterGrenade = make(prefix(LibItemNames.HOLY_WATER_GRENADE), new HolyWaterGrenadeItem(stackTo4()));

	//For tags
	public static final Item[] REWARD_BAGS = {
			einsRewardBag, zweiRewardBag, dreiRewardBag, vierRewardBag,
			nineAndThreeQuartersRewardBag, pandorasBox,
	};
	public static final Item[] HAMMERS = {
			manasteelHammer, elementiumHammer, terrasteelHammer, gaiaHammer,
			photoniumHammer, shadowiumHammer, aerialiteHammer, orichalcosHammer,
			rheinHammer
	};
	public static final Item[] PICKAXES = {

	};

	public static final Item[] SWORDS = {
			excalibur,
	};
	public static final Item[] BOWS = {
			failnaught
	};
	public static final Item[] MANA_USING_ITEM = {
			camera, failnaught, excalibur, featherOfJingwei, magicFinger, coreOfTheVoid, voidArchives, walkingCane,
			starryIdolHeadgear, starryIdolSuit, starryIdolSkirt, starryIdolBoots
	};

	public static final Item[] REPLICATOR_BLACKLIST = {
			failnaught, manaRingMaster, excalibur
	};
	//Bauble
	public static final Item[] RINGS = {
			manaRingMaster,
	};

	public static final Item[] BODY = {
			coreOfTheVoid
	};

	public static final Item[] ALL_SLOT = {
			featherOfJingwei, natureOrb
	};

	private static <T extends Item> T make(ResourceLocation id, T item) {
		var old = ALL.put(id, item);
		if (old != null) {
			throw new IllegalArgumentException("Typo? Duplicate id " + id);
		}
		return item;
	}

	public static Item.Properties defaultBuilderCustomDamage() {
		return XplatAbstractions.INSTANCE.defaultItemBuilderWithCustomDamageOnFabric();
	}

	public static Item.Properties unstackableCustomDamage() {
		return defaultBuilderCustomDamage().stacksTo(1);
	}

	public static Item.Properties defaultBuilder() {
		return new Item.Properties();
	}

	private static Item.Properties stackTo16() {
		return defaultBuilder().stacksTo(16);
	}
	private static Item.Properties stackTo4() {
		return defaultBuilder().stacksTo(16);
	}

	private static Item.Properties unstackable() {
		return defaultBuilder().stacksTo(1);
	}

	public static void registerRecipeSerializers(BiConsumer<RecipeSerializer<?>, ResourceLocation> r) {
		r.accept(WandOfTheForestExtendRecipe.SERIALIZER, prefix("wand_of_the_forest_extension"));
		r.accept(CopyBrewFormFlaskRecipe.SERIALIZER, prefix("copy_brew_from_flask"));
		r.accept(CopyBrewFromManaCocktailRecipe.SERIALIZER, prefix("copy_brew_from_mana_cocktail"));
		r.accept(DasRheingoldSoulbindRecipe.SERIALIZER, prefix("das_rheingold_change_bind"));
	}

	public static void registerItems(BiConsumer<Item, ResourceLocation> r) {
		for (var e : ALL.entrySet()) {
			r.accept(e.getValue(), e.getKey());
		}
	}
}
