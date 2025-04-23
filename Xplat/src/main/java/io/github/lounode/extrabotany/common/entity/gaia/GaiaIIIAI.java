package io.github.lounode.extrabotany.common.entity.gaia;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Dynamic;
import io.github.lounode.extrabotany.common.entity.gaia.behavior.GaiaTeleport;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.schedule.Activity;

public class GaiaIIIAI extends GaiaAI{
    public static final int EMERGE_TIME = 200;
    protected static final int TELEPORT_DELAY_INITIAL = 25;
    protected static final int TELEPORT_DELAY = 30;
    protected static final float TELEPORT_RANGE = 12;

    public GaiaIIIAI() {}
    public static void updateActivity(Gaia gaia) {
        gaia.getBrain().setActiveActivityToFirstValid(ImmutableList.of(
                Activity.EMERGE,
                Activity.FIGHT
        ));
    }

    protected static void initMemories(Gaia gaia, ServerLevel level, BlockPos pos) {
        GaiaTeleport.initMemories(gaia.getBrain(), TELEPORT_RANGE, TELEPORT_DELAY, TELEPORT_DELAY_INITIAL);

    }

    protected static Brain<?> makeBrain(Gaia gaia, Dynamic<?> ops) {
        Brain.Provider<GaiaIII> provider = Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
        Brain<GaiaIII> brain = provider.makeBrain(ops);
        initCoreActivity(brain);
        initSpawnActivity(brain, EMERGE_TIME);
        initFightActivity(brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.EMERGE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initFightActivity(Brain<? extends Gaia> brain) {
        brain.addActivity(Activity.FIGHT, 10, ImmutableList.of(
                new GaiaTeleport<>()
        ));
    }
}
