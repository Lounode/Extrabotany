# Important
Current version `1.9.0` is the `Final` update before the `Herrscher of the Void` is released.

After this version released, updates will no longer follow the current pace and will be `paused` until all related content is completed and released together.

# Current Version: 1.20.1-1.9.0 Release（2025.7.27）
## Hotfix1.9.1: fixed gaia 3 music that can be heard faraway
### New
- Try support EMI
- Stonesia, Edelweiss, Omniviolet now can use kubejs to add new recipes
- See more on [Here](kubejs_en.md)
### Change&Optimize
- Updated Gaia III record texture
- Gaia III now can configurable banning item list
### Bugfix
- Fixed bug that some item do not have tag on fabric
- Fixed bug that Sunshine Lily has the recipe conflict with sunflower
- Fixed bug that gaia boss bar continue alive after disconnect to server and re-login
- Removed Herobrine

## 1.20.1-1.8.0 Release (2025.7.20)
### New
#### New Item `Manasteel Shield`
Manasteel can be inlaid on Shield to improve its properties, and can also consume mana to self repair when damaged.
#### New Item `Elementium Shield`
A shield inlaid with Elementium has higher durability, and will Ignite the attacker after a successful block, and has a chance to summon Pixie to counterattack.
#### New Item `Terrasteel Shield`
A shield inlaid with Terrasteel will return some of the blocked damage to the attacker, and also provide Regeneration effect to the defender.
#### New Item `Achilles Shield`
Achille Shield can block the erosion from Honkai Energy. Also can return All the blocked damage to the enemy.
#### New Item `Scathed Music Disc`
A rare drop from Guardian of Gaia III, recording the melody of fighting him.
#### New Item `Push Lens`
Push Lens causes the Mana Burst to push all mobs it touches, forcing them to move with the Mana Burst.
#### New Item `Smelt Lens`
Smelt Lens will destroy blocks collided by the Mana Burst and drop the smelted items.
#### New Item `Mana Lens"`
Mana Lens is like a Mana Pool, it will infuse the item with mana. If you place a Alchemy Catalyst or a Conjuration Catalyst under the item, it will do the corresponding work.
#### New Item `Potion Lens`
Potion Lens will add potion effects to the first entity collided. Craft the lens with the brew flask to decide its effect.
#### New Item `Trace Lens`
Trace Lens causes Mana Burst to lock onto nearby mobs and continue tracking them until one of them disappears.
### Change&Optimize
- Updated `Event Wrapper` require to 1.1.3
### Bugfix
- Fixed bug that some item do not have tag that it should have
- Fixed bug that caused Void Archives have multiply damage attribute
- Removed Herobrine
## 1.20.1-1.7.0 Release (2025.7.13)
### New
#### New Functional Flower: `Mirrowtunia`
Mirrowtunia can continuously provide `Thirror` effect to surrounding players like a beacon.

Players with the `Thirror` effect can return some of the damage they take to the entity who caused it, just like the `Thorns` enchantment.
#### New Functional Flower: `Necrofleur`
The creed of the Necrofleur is to harvest life. It damages entities whose health falls below a certain percentage of their maximum health, let them in `heal reverse` for the next few seconds.
#### New Functional Flower: `Manalink`
Manalink can easily transfer mana to a bound Mana Pool over a long distance.
#### New Functional Flower: `Enchanter`
By consuming a large amount of mana and a long conversion time, Enchanter can convert the Grass Block into Enchanted Soil.
### Change&Optimize
- Updated `Twinstar`, `Omniviolet`, `Tinkle` and `Blood Enchantress`'s texture
### Bugfix
- Fixed bug that caused some floating flower Patchouli entry to have incorrect indexes
- Fixed bug that the `Pure Daisy Necklace` would drop logs abnormally when converting living wood
- Fixed some text errors in `zh_cn`
- Removed Herobrine
## 1.20.1-1.6.0 Release (2025.7.6)
### New
#### New Generating Flower: `Sunshine Lily`
Sunshine Lily have the same ability as Daybloom, converting sunlight into mana.

Like Hydroangeas, they will decay after about three days.

#### New Generating Flower:  `Moonlight Lily`
Moonlight Lily has the same ability as Nightshade, generating mana at night.

Like Hydroangeas, they will decay after about three days.

#### New Functional Flower: `Serenitian`
Serenitian could prevent passive flowers nearby at the same floor from decay, and set the decay progress to zero.

#### New Generating Flower: `Twinstar`
Twinstar like a thermoelectric generator, If you pour lava in one block and water in one block around it, it will start producing mana.

The greater the temperature difference of the surrounding liquid, the faster the mana is produced.

Time goes by, the temperature difference will cause it decay and turn into a Dead Bush.

#### New Generating Flower: `Omniviolet`
Knowledge is power, Omniviolet converts Books or Written Books into Mana.

Placing Bookshelves around it can increase its mana production efficiency.

#### New Generating Flower: `Tinkle`
Tinkle converts kinetic energy of nearby moving players into Mana. It will also increase players' energy consumption.

#### New Generating Flower: `Blood Enchantress`
Blood Enchantress creates Mana by consuming nearby entity's life.

Each time it consumes, this entity is marked. Marks reduce its mana generation, and it will stop working when the total number of marks on nearby creatures exceeds a certain threshold.

- Added `ru_ru` translate (by CurseForge: yogino)

### Bugfix
- Fixed bug that `Mana Reader` could not read mana form Functional Flower
- Fixed bug that cause server crash on server when players equipped `Jackie Chan Ring` or `Parkour Ring`
- Removed Herobrine

## 1.20.1-1.5.0 Release (2025.6.29)
### New
#### New Generating Flower: `Resoncund`
Sounds comes from the resonance of mana. Resoncund can reverse this process and produce a certain amount of mana.

But too many of the same sounds will cause Resoncund to go on strike against this sound unless it is allowed to rest for a while.
#### New Bauble: `Pure Daisy Pendant`
Pure Daisy Pendant gives the wearer abilities similar to the Pure Daisy. 

`Right Click` a block using empty hand and consuming a small amount of mana, it can turn `Logs` into `Livingwood Logs`, `Stones` into `Livingrocks`, and so on...
#### New Bauble: `Dispersive Ring`
Dispersive Ring can export the mana from your Mana Tablet, Mana Ring or other mana storage items, and supply it to nearby Functional Flowers for use.
#### New Bauble: `Frost Ring`
Wearing a Frost Ring allows you to freeze surrounding Water and solidify surrounding Lava without a Frost Walker. Looking at mobs will also slow them down slightly.
#### New Bauble: `Curse Ring`
With the power of Wither Skeleton Skull, Cursed Ring can consume a small amount of Mana to make entities in a small area gain Wither and Bad Luck effects.
#### New Bauble: `Spider Ring`
Spider Ring allows the wearer to have the ability to climbing the walls like a Spider, without the need for Ladders.
#### New Bauble: `Jackie Chan Ring`
When equipped the Jackie Chan Ring, wearer can jump along the wall.
#### New Bauble: `Parkour Ring`
Combining the advantages of Spider Ring and Jackie Chan Ring, this is Parkour Ring. 

Immune to fall damage while wearing it.

### Change&Optimize
- Updated `Event Wrapper` require to 1.1.2
- Removed mana require when using Walking Cane
- Moved some Patchouli page to other category
- Changed sorting of some items in creative mode
### Bugfix
- Fixed some wrong text in Patchouli book
- Removed Herobrine
## 1.20.1-1.4.0 Release (2025.6.22)
### New
#### New Generating Flower: `Stonesia`
Place Stonesia near your mine or cobblestone generator! 

It will eat these Stone or Ore blocks to produce a certain amount of mana.
#### New Generating Flower: `Edelweiss`
While working, Edelweiss will eat Snow Golem within range to generate mana.

### Change&Optimize
- added some `ja_jp` translate

### Bugfix
- upd eventwrapper to 1.1.1 fix some bug that cause crash when item was in cooldown.
- Removed Herobrine

## 1.20.1-1.3.0 Release (2025.6.15)
### New
#### New Generating Flower: `Bellflower`
Bellflower converts wind power into Mana.

When there's Enough Space nearby, it slowly generates a bit of Mana and the wind blew away one of its leaves.

Over time, it will eventually decay into a Dead Bush.

#### New Functional Flower: `Annoyingflower`
The Annoyingflower can fish in nearby Nature Pedestal filled with Water Bucket. 

For that process it will use mana. Feeding Fried Chicken to it will significantly increase the efficiency and the chance of getting treasure.
#### New Food: `Fried Chicken`
Fried Chicken is a delicious food that can be eaten even when you're full.

- added KubeJS support for Nature Pedestal
- added Patchouli Book entry for these new Brews
### Bugfix
- fixed bug that item in Nature Pedestal could not rotate
- Removed Herobrine
## 1.20.1-1.2.0 Release (2025.6.8)
### New 
#### New Generating Flower: `Reikarlily`
Reikarlily turns the tremendous energy of lightning into Mana. 

When a lightning bolt hits nearby, it will generate a great amount of Mana immediately and continue to produce Mana for a long time.
#### New Functional Flower: `Woodienia`
Just need a little bit of Mana, Woodienia can help you harvest the Logs within a certain range.

Built your cabin near the Woodienia might not a good idea.

Any Redstone Signal can suppression it's activity.
- add client configuration item: `otakuMode` some text only available when it was set to true
- add Naming Easter egg for Excalibur
- add some configuration items for flower
### Change&Optimize
- changed some armor model that solved clip bug
- changed some text in Patchouli book
- Tradeorchid now can be suppressed by any Redstone Signal
- changed ManaCocktail texture
- changed some `en_us` translations
- changed some `ja_jp` translate.
- GaiaIII now can't be summoned when it has GaiaI GaiaII in arena
- GaiaIII now can't be the Missile Rod's Target
### Bugfix
- fix clip bug when equip armor and installed `3D Skin Layers`
- fix bug that Mana Charger and Pedestals can't be speedup by Torcherino
- fix bug that some Brew doesn't have a recipe
- fix bug that Infinite Wine gives a wrong effect duration
- Fixed Failnaught tracking the wrong enemy when held in the off-hand
- Removed Herobrine
## 1.20.1-1.1.0 (2025.6.1)
### New
#### New Armorset: Starry Idol
From the Isekai beyond this world, a princess who reigns as the greatest of all. She once wore this armor set.

When equip the full armor set, grants an Super Mana Affinity

#### New Armorset: Pleiades Combat Maid
Uniform that worn by the Pleiades Combat Maids in Isekai 「Yggdrasil」.

When equip the full armor set, will increase the damage cursed by Empty Handed, and increase Mana Affinity and Regeneration Ability.

~~Rumor has it that the Suit has another form, But maybe it just a rumor.~~
#### New Armorset: Shadow Warrior
Equip any armor in this armor set will grant a Night Vision when night.

When equip the full armor set, will immune Falling Damage and Explosion Damage.
#### New Armorset: Goblin Slayer
When equip the full armor set, made some extra damage for Undead Mob. 

For Goblins, can kill with one attack.

- Added advancements for these armor sets.
- Added some `ja_jp` translate.

### Change&Optimize
- changed some `en_us` translations

### Bugfix
- Fix crash when Fabric Server boot up.
- Fix console spam on Forge Server boot up.
- Fix when installed Apotheosis, some Affixes didn't work.
- Fix discount mob effect has no icon.
- Removed Herobrine

## 1.20.1-1.0.1 Release（2025.5.26）
### Bugfix
- Fixed the purple-and-black texture issue that occurred when certain other mods were installed simultaneously.
- Removed Herobrine