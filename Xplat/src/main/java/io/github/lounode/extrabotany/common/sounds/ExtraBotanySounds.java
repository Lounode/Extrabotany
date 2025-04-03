package io.github.lounode.extrabotany.common.sounds;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public class ExtraBotanySounds {
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
