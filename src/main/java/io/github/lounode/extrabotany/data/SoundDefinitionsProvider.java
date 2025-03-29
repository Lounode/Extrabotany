package io.github.lounode.extrabotany.data;

import io.github.lounode.extrabotany.ExtraBotany;
import io.github.lounode.extrabotany.common.sounds.ExtrabotanySounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SoundDefinitionsProvider extends net.minecraftforge.common.data.SoundDefinitionsProvider {
    protected SoundDefinitionsProvider(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    @Override
    public void registerSounds() {
        Set<SoundEvent> soundEvents = BuiltInRegistries.SOUND_EVENT.stream().filter(i -> ExtraBotany.MODID.equals(BuiltInRegistries.SOUND_EVENT.getKey(i).getNamespace()))
                .collect(Collectors.toSet());

        for (SoundEvent soundEvent : soundEvents) {
            defaultSound(soundEvent);
        }
    }

    protected SoundDefinition def(SoundEvent soundEvent) {
        return definition()
                .subtitle("subtitles." + soundEvent.getLocation())
                .with(
                        sound(relocateOggPath(soundEvent.getLocation()))
                );
    }
    protected void defaultSound(SoundEvent soundEvent) {
        this.add(soundEvent, def(soundEvent));
    }

    protected ResourceLocation relocateOggPath(ResourceLocation location) {
        String namespace = location.getNamespace();
        String originPath = location.getPath();

        String path = originPath.replace('.', '/');

        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }
}
