package io.github.lounode.extrabotany.data;


import io.github.lounode.extrabotany.common.lib.LibMisc;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.Set;
import java.util.stream.Collectors;

public class ExtraBotanySoundDefinitionsProvider extends SoundDefinitionsProvider{

    public ExtraBotanySoundDefinitionsProvider(PackOutput output, String modId) {
        super(output, modId);
    }

    @Override
    public void registerSounds() {
        Set<SoundEvent> soundEvents = BuiltInRegistries.SOUND_EVENT.stream().filter(i -> LibMisc.MOD_ID.equals(BuiltInRegistries.SOUND_EVENT.getKey(i).getNamespace()))
                .collect(Collectors.toSet());

        for (SoundEvent soundEvent : soundEvents) {
            defaultSound(soundEvent);
        }
    }

    protected SoundDefinition def(SoundEvent soundEvent) {
        return SoundDefinitionsProvider.definition()
                .subtitle("subtitles." + soundEvent.getLocation())
                .with(
                        SoundDefinitionsProvider.sound(relocateOggPath(soundEvent.getLocation()))
                );
    }
    protected void defaultSound(SoundEvent soundEvent) {
        this.add(soundEvent, def(soundEvent));
    }

    protected ResourceLocation relocateOggPath(ResourceLocation location) {
        String namespace = location.getNamespace();
        String originPath = location.getPath();

        String path = originPath.replace('.', '/');

        return new ResourceLocation(namespace, path);
    }
}
