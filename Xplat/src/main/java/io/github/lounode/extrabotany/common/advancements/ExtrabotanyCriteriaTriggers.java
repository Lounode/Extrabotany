package io.github.lounode.extrabotany.common.advancements;

import vazkii.botania.mixin.CriteriaTriggersAccessor;

public class ExtrabotanyCriteriaTriggers {
	public static void init() {
		CriteriaTriggersAccessor.botania_register(ItemUsedTrigger.INSTANCE);
		CriteriaTriggersAccessor.botania_register(ManaChargeTrigger.INSTANCE);
		CriteriaTriggersAccessor.botania_register(HasArmorSetTrigger.INSTANCE);
	}
}
