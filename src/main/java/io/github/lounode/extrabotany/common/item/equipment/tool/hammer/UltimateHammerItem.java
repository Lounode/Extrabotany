package io.github.lounode.extrabotany.common.item.equipment.tool.hammer;

import net.minecraft.world.item.Tier;
import vazkii.botania.api.BotaniaAPI;

public class UltimateHammerItem extends ManasteelHammerItem {
    public UltimateHammerItem(Properties props) {
        this(HammerTiers.RHEIN, 4, -3.0F, props);
    }

    public UltimateHammerItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }
}
