# KubeJS支持

额外植物学原生支持KubeJS，以下是您使用KubeJS修改的一个示例：

```js
ServerEvents.recipes(event => {
    const { extrabotany } = event.recipes;

    //Nature Pedestal
    // .pedestal_smash(output, input, [smash_tools="#extrabotany:hammers"], [exp=5], [strike=5])
    extrabotany.pedestal_smash('minecraft:glowstone', 'minecraft:diamond');
    extrabotany.pedestal_smash('minecraft:redstone', 'minecraft:diamond', 'minecraft:stick');
    extrabotany.pedestal_smash('minecraft:emerald', 'minecraft:diamond', '#minecraft:pickaxes',/*exp*/ 100, /*strike*/3);

    //Stonesia
    // .stonesia(outputMana, inputBlockOrBlockTag)
    extrabotany.stonesia(100, 'minecraft:obsidian');
    extrabotany.stonesia(10, '#minecraft:dirt')

    //Edelweiss
    // .edelweiss(outputMana, inputEntityTypeKey)
    extrabotany.edelweiss(100, 'minecraft:cow');
    extrabotany.edelweiss(200, '#minecraft:skeletons');

    //OmniViolet
    // .omniviolet(inputItem, burnTime)
    extrabotany.omniviolet('minecraft:apple', 100);
})
```