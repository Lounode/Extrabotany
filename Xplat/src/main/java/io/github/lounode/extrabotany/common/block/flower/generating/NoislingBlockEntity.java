package io.github.lounode.extrabotany.common.block.flower.generating;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import org.jetbrains.annotations.Nullable;

import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.api.block_entity.RadiusDescriptor;

import io.github.lounode.eventwrapper.EventsWrapper;
import io.github.lounode.eventwrapper.event.PlayLevelSoundEventWrapper;
import io.github.lounode.eventwrapper.eventbus.api.SubscribeEventWrapper;
import io.github.lounode.extrabotany.common.block.flower.ExtrabotanyFlowerBlocks;
import io.github.lounode.extrabotany.xplat.ExtraBotanyConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class NoislingBlockEntity extends GeneratingFlowerBlockEntity {

	public static final String TAG_SOUND_HEARD = "soundHeard";

	private static final int RANGE = 4;
	private static final int CACHE_SIZE = 32;
	private static final int MAX_PRODUCE = 500;
	private static final int MIN_PRODUCE = 0;

	public static final int MAX_MANA = 1200;
	public static final int MANA_LOSS_PER_HEARD = 50;

	private final LoadingCache<SoundEvent, Integer> SOUND_HEARD = CacheBuilder.newBuilder()
			.maximumSize(getCacheSize())
			.expireAfterAccess(30, TimeUnit.SECONDS)
			.build(CacheLoader.from(() -> 0));

	public NoislingBlockEntity(BlockPos pos, BlockState blockState) {
		super(ExtrabotanyFlowerBlocks.NOISLING, pos, blockState);
		EventsWrapper.register(this);
	}

	public void onSoundHeard(SoundEvent soundEvent) {
		int count = SOUND_HEARD.getUnchecked(soundEvent);
		if (count > Integer.MAX_VALUE - 1) {
			count = 0;
		}

		int produce = getMaxProduce();
		produce = produce - (getSoundHeard().get(soundEvent) * getLossPerHeard());
		produce = Math.max(getMinProduce(), produce);

		addMana(produce);
		sync();

		SOUND_HEARD.put(soundEvent, count + 1);
	}

	public int getLossPerHeard() {
		return ExtraBotanyConfig.common().noislingLossPerHeard();
	}

	public int getMinProduce() {
		return MIN_PRODUCE;
	}

	public int getMaxProduce() {
		return MAX_PRODUCE;
	}

	public int getCacheSize() {
		return CACHE_SIZE;
	}

	@Override
	public int getMaxMana() {
		return ExtraBotanyConfig.common().noislingMaxMana();
	}

	@Override
	public int getColor() {
		return 0xF54DAF;
	}

	@Override
	public @Nullable RadiusDescriptor getRadius() {
		return RadiusDescriptor.Rectangle.square(getEffectivePos(), RANGE);
	}

	public Map<SoundEvent, Integer> getSoundHeard() {
		return SOUND_HEARD.asMap();
	}

	@Override
	public void writeToPacketNBT(CompoundTag cmp) {
		super.writeToPacketNBT(cmp);

		CompoundTag sounds = new CompoundTag();

		for (var entry : getSoundHeard().entrySet()) {
			SoundEvent sound = entry.getKey();
			int heards = entry.getValue();

			sounds.putInt(sound.getLocation().toString(), heards);
		}

		cmp.put(TAG_SOUND_HEARD, sounds);
	}

	@Override
	public void readFromPacketNBT(CompoundTag cmp) {
		super.readFromPacketNBT(cmp);

		CompoundTag sounds = cmp.getCompound(TAG_SOUND_HEARD);

		Map<SoundEvent, Integer> soundHeard = new ConcurrentHashMap<>();
		for (var key : sounds.getAllKeys()) {
			if (soundHeard.size() >= getCacheSize()) {
				break;
			}
			ResourceLocation res = ResourceLocation.tryParse(key);
			int heards = sounds.getInt(key);
			if (heards < 0) {
				continue;
			}
			BuiltInRegistries.SOUND_EVENT.getOptional(res).ifPresent(soundEvent -> {
				soundHeard.put(soundEvent, heards);
			});
		}

		SOUND_HEARD.invalidateAll();
		SOUND_HEARD.putAll(soundHeard);
	}

	@SubscribeEventWrapper
	public void onPlayLevelSound(PlayLevelSoundEventWrapper.AtPosition event) {
		if (getLevel().isClientSide()) {
			EventsWrapper.unregister(this);
			return;
		}
		if (this.isRemoved()) {
			EventsWrapper.unregister(this);
			return;
		}
		if (event.getSound() == null) {
			return;
		}

		var aabb = new AABB(getEffectivePos()).inflate(RANGE);
		if (aabb.contains(event.getPosition())) {
			onSoundHeard(event.getSound().value());
		}
	}

	@SubscribeEventWrapper
	public void onPlayLevelSound(PlayLevelSoundEventWrapper.AtEntity event) {
		if (getLevel().isClientSide()) {
			EventsWrapper.unregister(this);
			return;
		}
		if (this.isRemoved()) {
			EventsWrapper.unregister(this);
			return;
		}
		if (event.getSound() == null) {
			return;
		}
		var aabb = new AABB(getEffectivePos()).inflate(RANGE);
		if (aabb.contains(event.getEntity().position())) {
			onSoundHeard(event.getSound().value());
		}
	}
}
