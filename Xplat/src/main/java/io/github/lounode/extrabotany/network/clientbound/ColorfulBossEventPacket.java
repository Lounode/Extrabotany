package io.github.lounode.extrabotany.network.clientbound;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

import io.github.lounode.extrabotany.common.bossevents.ComponentCodec;
import io.github.lounode.extrabotany.network.ExtrabotanyPacket;

public record ColorfulBossEventPacket(UUID id, Operation operation) implements ExtrabotanyPacket {

	public static final Codec<BossEvent.BossBarColor> BOSSBAR_COLOR_CODEC = Codec.STRING.xmap(
			BossEvent.BossBarColor::valueOf,
			BossEvent.BossBarColor::name
	);

	public static final Codec<BossEvent.BossBarOverlay> BOSSBAR_OVERLAY_CODEC = Codec.STRING.xmap(
			BossEvent.BossBarOverlay::valueOf,
			BossEvent.BossBarOverlay::name
	);

	public static final ResourceLocation ID = prefix("cbp");

	@Override
	public ResourceLocation getFabricId() {
		return ID;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void encode(FriendlyByteBuf buf) {
		buf.writeUUID(this.id);
		buf.writeJsonWithCodec((Codec<Operation>) operation().getCodec(), operation());
	}

	public static ColorfulBossEventPacket decode(FriendlyByteBuf buf) {
		return new ColorfulBossEventPacket(buf.readUUID(), buf.readJsonWithCodec(Operation.CODEC));
	}

	public static ColorfulBossEventPacket createAddPacket(BossEvent event) {
		return new ColorfulBossEventPacket(event.getId(), ColorfulBossEventPacket.AddOperation.form(event));
	}

	public static ColorfulBossEventPacket createRemovePacket(UUID id) {
		return new ColorfulBossEventPacket(id, new RemoveOperation(id));
	}

	public static ColorfulBossEventPacket createUpdateProgressPacket(BossEvent event) {
		return new ColorfulBossEventPacket(event.getId(), new UpdateProgressOperation(event.getProgress()));
	}

	public static ColorfulBossEventPacket createUpdateNamePacket(BossEvent event) {
		return new ColorfulBossEventPacket(event.getId(), new UpdateNameOperation(event.getName()));
	}

	public static ColorfulBossEventPacket createUpdateStylePacket(BossEvent event) {
		return new ColorfulBossEventPacket(event.getId(), new UpdateStyleOperation(event.getColor(), event.getOverlay()));
	}

	public static ColorfulBossEventPacket createUpdatePropertiesPacket(BossEvent event) {
		return new ColorfulBossEventPacket(event.getId(), new UpdatePropertiesOperation(event.shouldDarkenScreen(), event.shouldPlayBossMusic(), event.shouldCreateWorldFog()));
	}

	public interface Handler {
		default void add(UUID uuid, Component name, float progress, BossEvent.BossBarColor color, BossEvent.BossBarOverlay overlay, boolean darkenScreen, boolean playMusic, boolean createWorldFog) {}

		default void remove(UUID uuid) {}

		default void updateProgress(UUID uuid, float progress) {}

		default void updateName(UUID uuid, Component name) {}

		default void updateStyle(UUID uuid, BossEvent.BossBarColor color, BossEvent.BossBarOverlay overlay) {}

		default void updateProperties(UUID uuid, boolean darkenScreen, boolean playMusic, boolean createWorldFog) {}
	}

	public void dispatch(Handler handler) {
		this.operation.dispatch(this.id, handler);
	}

	public record AddOperation(Component name, float progress, BossEvent.BossBarColor color, BossEvent.BossBarOverlay overlay, boolean darkenScreen, boolean playMusic, boolean createWorldFog) implements Operation {

		public static final Codec<AddOperation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.STRING.fieldOf("type").forGetter(AddOperation::getType),
				ComponentCodec.CODEC.fieldOf("name").forGetter(AddOperation::name),
				Codec.FLOAT.fieldOf("progress").forGetter(AddOperation::progress),
				BOSSBAR_COLOR_CODEC.fieldOf("color").forGetter(AddOperation::color),
				BOSSBAR_OVERLAY_CODEC.fieldOf("overlay").forGetter(AddOperation::overlay),
				Codec.BOOL.fieldOf("darkenScreen").forGetter(AddOperation::darkenScreen),
				Codec.BOOL.fieldOf("playMusic").forGetter(AddOperation::playMusic),
				Codec.BOOL.fieldOf("createWorldFog").forGetter(AddOperation::createWorldFog)
		).apply(instance, (type, name, progress, color, overlay, darkenScreen, playMusic, createWorldFog) -> new AddOperation(name, progress, color, overlay, darkenScreen, playMusic, createWorldFog))
		);

		public static AddOperation form(BossEvent event) {
			return new AddOperation(
					event.getName(),
					event.getProgress(),
					event.getColor(),
					event.getOverlay(),
					event.shouldDarkenScreen(),
					event.shouldPlayBossMusic(),
					event.shouldCreateWorldFog()
			);
		}

		@Override
		public String getType() {
			return "add";
		}

		@Override
		public Codec<? extends Operation> getCodec() {
			return CODEC;
		}

		@Override
		public void dispatch(UUID uuid, Handler handler) {
			handler.add(uuid, this.name, this.progress, this.color, this.overlay, this.darkenScreen, this.playMusic, this.createWorldFog);
		}

	}

	public record RemoveOperation(UUID uuid) implements Operation {
		public static final Codec<RemoveOperation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.STRING.fieldOf("type").forGetter(RemoveOperation::getType),
				Codec.STRING.xmap(UUID::fromString, UUID::toString)
						.fieldOf("uuid")
						.forGetter(RemoveOperation::uuid)
		).apply(instance, (type, uuid) -> new RemoveOperation(uuid))
		);

		@Override
		public String getType() {
			return "remove";
		}

		@Override
		public Codec<? extends Operation> getCodec() {
			return CODEC;
		}

		@Override
		public void dispatch(UUID uuid, ColorfulBossEventPacket.Handler handler) {
			handler.remove(uuid);
		}
	}

	public record UpdateProgressOperation(float progress) implements ColorfulBossEventPacket.Operation {
		public static final Codec<UpdateProgressOperation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.STRING.fieldOf("type").forGetter(UpdateProgressOperation::getType),
				Codec.FLOAT.fieldOf("progress").forGetter(UpdateProgressOperation::progress))
				.apply(instance, (type, progress) -> new UpdateProgressOperation(progress))
		);

		@Override
		public String getType() {
			return "update_progress";
		}

		@Override
		public Codec<? extends ColorfulBossEventPacket.Operation> getCodec() {
			return CODEC;
		}

		@Override
		public void dispatch(UUID uuid, ColorfulBossEventPacket.Handler handler) {
			handler.updateProgress(uuid, progress());
		}
	}

	public record UpdateNameOperation(Component name) implements ColorfulBossEventPacket.Operation {
		public static final Codec<UpdateNameOperation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.STRING.fieldOf("type").forGetter(UpdateNameOperation::getType),
				ComponentCodec.CODEC.fieldOf("name").forGetter(UpdateNameOperation::name)
		).apply(instance, (type, name) -> new UpdateNameOperation(name))
		);

		@Override
		public Codec<? extends Operation> getCodec() {
			return CODEC;
		}

		@Override
		public void dispatch(UUID uuid, Handler handler) {
			handler.updateName(uuid, name());
		}

		@Override
		public String getType() {
			return "update_name";
		}
	}

	public record UpdateStyleOperation(BossEvent.BossBarColor color, BossEvent.BossBarOverlay overlay) implements ColorfulBossEventPacket.Operation {
		public static final Codec<UpdateStyleOperation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.STRING.fieldOf("type").forGetter(UpdateStyleOperation::getType),
				BOSSBAR_COLOR_CODEC.fieldOf("color").forGetter(UpdateStyleOperation::color),
				BOSSBAR_OVERLAY_CODEC.fieldOf("overlay").forGetter(UpdateStyleOperation::overlay)
		).apply(instance, (type, color, overlay) -> new UpdateStyleOperation(color, overlay))
		);

		@Override
		public Codec<? extends Operation> getCodec() {
			return CODEC;
		}

		@Override
		public void dispatch(UUID uuid, Handler handler) {
			handler.updateStyle(uuid, color(), overlay());
		}

		@Override
		public String getType() {
			return "update_style";
		}
	}

	public record UpdatePropertiesOperation(boolean darkenScreen, boolean playMusic, boolean createWorldFog) implements ColorfulBossEventPacket.Operation {

		public static final Codec<UpdatePropertiesOperation> CODEC = RecordCodecBuilder.create(instance -> instance.group(
				Codec.STRING.fieldOf("type").forGetter(UpdatePropertiesOperation::getType),
				Codec.BOOL.fieldOf("darkenScreen").forGetter(UpdatePropertiesOperation::darkenScreen),
				Codec.BOOL.fieldOf("playMusic").forGetter(UpdatePropertiesOperation::playMusic),
				Codec.BOOL.fieldOf("createWorldFog").forGetter(UpdatePropertiesOperation::createWorldFog)
		).apply(instance, (type, darkenScreen, playMusic, createWorldFog) -> new UpdatePropertiesOperation(darkenScreen, playMusic, createWorldFog)
		)
		);

		@Override
		public Codec<? extends Operation> getCodec() {
			return CODEC;
		}

		@Override
		public void dispatch(UUID uuid, Handler handler) {
			handler.updateProperties(uuid, darkenScreen(), playMusic(), createWorldFog());
		}

		@Override
		public String getType() {
			return "update_properties";
		}
	}

	public interface Operation {
		Map<String, Supplier<Codec<? extends Operation>>> REGISTRY = new HashMap<>();

		Codec<Operation> CODEC = Codec.STRING.dispatch(
				Operation::getType,
				type -> REGISTRY.getOrDefault(type, () -> {
					throw new IllegalArgumentException("Unknown operation type: " + type);
				}).get()
		);

		Codec<? extends Operation> getCodec();

		void dispatch(UUID uuid, Handler handler);
		String getType();

		static void register(String type, Supplier<Codec<? extends Operation>> codecSupplier) {
			REGISTRY.put(type, codecSupplier);
		}
	}
}
