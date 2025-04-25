package io.github.lounode.extrabotany.common.bossevents;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

public class ComponentCodec {
    public static final Codec<Component> CODEC = Codec.PASSTHROUGH.comapFlatMap(
            json -> {
                try {
                    JsonElement element = json.convert(JsonOps.INSTANCE).getValue();
                    Component component = Component.Serializer.fromJson(element);
                    return DataResult.success(component);
                } catch (Exception e) {
                    return DataResult.error(() -> "Failed to parse component: " + e.getMessage());
                }
            },
            component -> {
                JsonElement element = Component.Serializer.toJsonTree(component);
                return new Dynamic<>(JsonOps.INSTANCE, element);
            }
    );

    public static Component fromNetwork(FriendlyByteBuf buf) {
        return buf.readComponent();
    }

    public static void toNetwork(FriendlyByteBuf buf, Component component) {
        buf.writeComponent(component);
    }
}
