package io.github.lounode.extrabotany.common.loot;

import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import vazkii.botania.common.loot.BindUuid;

public class ExtendBindUUID extends BindUuid {
    public ExtendBindUUID(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    public static LootItemConditionalFunction.Builder<?> bindUUID() {
        return simpleBuilder(ExtendBindUUID::new);
    }
}
