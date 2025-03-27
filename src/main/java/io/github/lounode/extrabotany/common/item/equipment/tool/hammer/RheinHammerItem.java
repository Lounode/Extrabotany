package io.github.lounode.extrabotany.common.item.equipment.tool.hammer;

import net.minecraft.world.item.Tier;

public class RheinHammerItem extends ManasteelHammerItem {
    public RheinHammerItem(Properties props) {
        this(HammerTiers.RHEIN, 4, -3.0F, props);
    }

    public RheinHammerItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }
}
