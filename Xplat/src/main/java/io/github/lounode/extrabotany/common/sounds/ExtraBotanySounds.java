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
	public static final SoundEvent EXCALIBUR_ATTACK = makeSoundEvent("item.excalibur.attack");
	public static final SoundEvent FAILNAUGHT_SHOOT = makeSoundEvent("item.failnaught.shoot");
	public static final SoundEvent FEATHER_OF_JINGWEI_SHOOT = makeSoundEvent("item.feather_of_jingwei.shoot");
	public static final SoundEvent PLAYER_BACKFIRE = makeSoundEvent("entity.player.hurt_backfire");
	public static final SoundEvent REWARD_BAG_OPEN = makeSoundEvent("item.reward_bag.open");
	public static final SoundEvent PANDORAS_BOX_OPEN = makeSoundEvent("item.pandoras_box.open");
	public static final SoundEvent WALKING_CANE_USE = makeSoundEvent("item.walking_cane.use");

	public static final SoundEvent MUSIC_GAIA3 = makeSoundEvent("music.gaia3");
	public static final SoundEvent ARMOR_EQUIP_MAID = makeSoundEvent("item.armor.equip_maid");
	public static final SoundEvent ARMOR_EQUIP_IDOL = makeSoundEvent("item.armor.equip_idol");;

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
