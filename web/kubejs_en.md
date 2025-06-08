# KubeJS Support

Extrabotany native support KubeJS.

Here is an example showing how you can use KubeJS to add a custom recipe.

```js
ServerEvents.recipes(event => {
    const { extrabotany } = event.recipes;

    //Nature Pedestal
    // .pedestal_smash(output, input, [smash_tools="#extrabotany:hammers"], [exp=5], [strike=5])
    extrabotany.pedestal_smash('minecraft:glowstone', 'minecraft:diamond');
    extrabotany.pedestal_smash('minecraft:redstone', 'minecraft:diamond', 'minecraft:stick');
    extrabotany.pedestal_smash('minecraft:emerald', 'minecraft:diamond', '#minecraft:pickaxes',/*exp*/ 100, /*strike*/3);
})
```