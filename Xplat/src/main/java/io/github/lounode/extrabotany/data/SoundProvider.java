package io.github.lounode.extrabotany.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import vazkii.botania.common.handler.BotaniaSounds;

import io.github.lounode.extrabotany.common.lib.LibMisc;
import io.github.lounode.extrabotany.data.sound.SoundDefinition;
import io.github.lounode.extrabotany.data.sound.SoundDefinitionsProvider;

import java.util.Set;
import java.util.stream.Collectors;

import static io.github.lounode.extrabotany.common.sounds.ExtraBotanySounds.*;

public class SoundProvider extends SoundDefinitionsProvider {

	public SoundProvider(PackOutput output, String modId) {
		super(output, modId);
	}

	@Override
	public void registerSounds() {
		Set<SoundEvent> soundEvents = BuiltInRegistries.SOUND_EVENT.stream().filter(i -> LibMisc.MOD_ID.equals(BuiltInRegistries.SOUND_EVENT.getKey(i).getNamespace()))
				.collect(Collectors.toSet());

		take(soundEvents, EXCALIBUR_ATTACK);
		take(soundEvents, FAILNAUGHT_SHOOT);
		take(soundEvents, FEATHER_OF_JINGWEI_SHOOT);
		take(soundEvents, PLAYER_BACKFIRE);
		take(soundEvents, MUSIC_GAIA3);
		take(soundEvents, REWARD_BAG_OPEN);
		take(soundEvents, PANDORAS_BOX_OPEN);

		this.add(PANDORAS_BOX_OPEN, SoundDefinitionsProvider.definition()
				.subtitle(title(PANDORAS_BOX_OPEN))
				.with(
						SoundDefinitionsProvider.sound(SoundEvents.ARMOR_EQUIP_CHAIN.getLocation(), SoundDefinition.SoundType.EVENT)
				)
		);

		this.add(REWARD_BAG_OPEN, SoundDefinitionsProvider.definition()
				.subtitle(title(REWARD_BAG_OPEN))
				.with(
						SoundDefinitionsProvider.sound(SoundEvents.ARMOR_EQUIP_LEATHER.getLocation(), SoundDefinition.SoundType.EVENT)
				)
		);

		this.add(EXCALIBUR_ATTACK, SoundDefinitionsProvider.definition()
				.subtitle(title(EXCALIBUR_ATTACK))
				.with(
						SoundDefinitionsProvider.sound(BotaniaSounds.terraBlade.getLocation())
								.volume(0.4f)
								.pitch(1.4f)
				)
		);

		this.add(FAILNAUGHT_SHOOT, SoundDefinitionsProvider.definition()
				.subtitle(title(FAILNAUGHT_SHOOT))
				.with(
						SoundDefinitionsProvider.sound(BotaniaSounds.terraBlade.getLocation())
								.volume(0.4f)
								.pitch(1.4f)
				)
		);

		this.add(FEATHER_OF_JINGWEI_SHOOT, SoundDefinitionsProvider.definition()
				.subtitle(title(FEATHER_OF_JINGWEI_SHOOT))
				.with(
						SoundDefinitionsProvider.sound(SoundEvents.FIRE_EXTINGUISH.getLocation(), SoundDefinition.SoundType.EVENT)
								.volume(0.2f)
				)
		);

		this.add(PLAYER_BACKFIRE, SoundDefinitionsProvider.definition()
				.subtitle(title(PLAYER_BACKFIRE))
				.with(
						SoundDefinitionsProvider.sound(SoundEvents.PLAYER_HURT_FREEZE.getLocation(), SoundDefinition.SoundType.EVENT)
				)
		);

		this.add(MUSIC_GAIA3, SoundDefinitionsProvider.definition()
				.with(
						SoundDefinitionsProvider.sound(relocateOggPath(MUSIC_GAIA3.getLocation()))
								.stream()
				)
		);

		take(soundEvents, WALKING_CANE_USE);
		this.add(WALKING_CANE_USE, SoundDefinitionsProvider.definition()
				.subtitle(title(WALKING_CANE_USE))
				.with(
						SoundDefinitionsProvider.sound(SoundEvents.FISHING_BOBBER_RETRIEVE.getLocation(), SoundDefinition.SoundType.EVENT)
				)
		);

		for (SoundEvent soundEvent : soundEvents) {
			this.add(soundEvent, defaultDefinition(soundEvent));
		}
	}

	public String title(SoundEvent soundEvent) {
		return "subtitles." + soundEvent.getLocation();
	}

	protected void take(Set<SoundEvent> form, SoundEvent soundEvent) {
		//this.add(soundEvent, definition);
		form.remove(soundEvent);
	}

	protected SoundDefinition copyDefinition(SoundEvent soundEvent, SoundEvent... copyFrom) {
		SoundDefinition definition = SoundDefinitionsProvider.definition()
				.subtitle("subtitles." + soundEvent.getLocation());

		for (SoundEvent sound : copyFrom) {
			definition.with(SoundDefinitionsProvider.sound(sound.getLocation()));
		}

		return definition;

	}

	protected SoundDefinition defaultDefinition(SoundEvent soundEvent) {
		return SoundDefinitionsProvider.definition()
				.subtitle("subtitles." + soundEvent.getLocation())
				.with(
						SoundDefinitionsProvider.sound(relocateOggPath(soundEvent.getLocation()))
				);
	}

	protected ResourceLocation relocateOggPath(ResourceLocation location) {
		String namespace = location.getNamespace();
		String originPath = location.getPath();

		String path = originPath.replace('.', '/');

		return ResourceLocation.tryBuild(namespace, path);
	}
}
