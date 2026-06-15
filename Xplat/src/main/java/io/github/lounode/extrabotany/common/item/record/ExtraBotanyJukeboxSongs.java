package io.github.lounode.extrabotany.common.item.record;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

import static io.github.lounode.extrabotany.common.lib.ResourceLocationHelper.prefix;

public final class ExtraBotanyJukeboxSongs {
	public static final ResourceKey<JukeboxSong> GAIA_3 = ResourceKey.create(Registries.JUKEBOX_SONG, prefix("gaia_3"));

	private ExtraBotanyJukeboxSongs() {}
}
