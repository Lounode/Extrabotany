package io.github.lounode.extrabotany.client;

import io.github.lounode.extrabotany.common.item.ExtraBotanyItems;
import io.github.lounode.extrabotany.common.item.relic.FailnaughtItem;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import vazkii.botania.common.item.equipment.tool.bow.LivingwoodBowItem;
import vazkii.botania.network.TriConsumer;

public class ExtraBotanyItemProperties {
    public static void init(TriConsumer<ItemLike, ResourceLocation, ClampedItemPropertyFunction> consumer) {
        // [BotaniaCopy] ItemProperties.BOW's minecraft:pulling property
        ClampedItemPropertyFunction pulling = (stack, worldIn, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
        ClampedItemPropertyFunction pull = (stack, worldIn, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                FailnaughtItem item = ((FailnaughtItem) stack.getItem());
                return entity.getUseItem() != stack
                        ? 0.0F
                        : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) * item.chargeVelocityMultiplier(stack, entity) / 20.0F;
            }
        };
        consumer.accept(ExtraBotanyItems.failnaught, ResourceLocation.parse("pulling"), pulling);
        consumer.accept(ExtraBotanyItems.failnaught, ResourceLocation.parse("pull"), pull);
    }
}
