package io.github.lounode.extrabotany.common.advancements;

import net.minecraft.advancements.CriteriaTriggers;
import vazkii.botania.mixin.CriteriaTriggersAccessor;

public class ExtrabotanyCriteriaTriggers {
    public static void init() {
        CriteriaTriggers.register(ItemUsedTrigger.INSTANCE);
        CriteriaTriggers.register(ManaChargeTrigger.INSTANCE);
    }
}
