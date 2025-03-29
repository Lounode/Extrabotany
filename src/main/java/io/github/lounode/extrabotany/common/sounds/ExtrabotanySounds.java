package io.github.lounode.extrabotany.common.sounds;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ExtrabotanySounds {
    public static final List<SoundEvent> EVENTS = new ArrayList<>();
    public static final SoundEvent CAMERA_USE = makeSoundEvent("item.camera.use");
    public static final SoundEvent CAMERA_CHARGE = makeSoundEvent("item.camera.charge");
    public static final SoundEvent CAMERA_FOCUS = makeSoundEvent("item.camera.focus");

    private static SoundEvent makeSoundEvent(String name) {
        SoundEvent event = SoundEvent.createVariableRangeEvent(prefix(name));
        EVENTS.add(event);
        return event;
    }

    public static void init(BiConsumer<SoundEvent, ResourceLocation> r) {
        for (SoundEvent event : EVENTS) {
            r.accept(event, event.getLocation());
        }
    }
}
