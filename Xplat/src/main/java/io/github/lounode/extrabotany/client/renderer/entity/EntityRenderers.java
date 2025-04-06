package io.github.lounode.extrabotany.client.renderer.entity;

import io.github.lounode.extrabotany.client.renderer.blockentity.PedestalRenderer;
import io.github.lounode.extrabotany.common.block.block_entity.ExtraBotanyBlockEntities;
import io.github.lounode.extrabotany.common.entity.ExtraBotanyEntityType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class EntityRenderers {
    public interface EntityRendererConsumer {
        <E extends Entity> void accept(EntityType<? extends E> entityType,
                                       EntityRendererProvider<E> entityRendererFactory);
    }

    public interface BERConsumer {
        <E extends BlockEntity> void register(BlockEntityType<E> type, BlockEntityRendererProvider<? super E> factory);
    }
    public static void registerEntityRenderers(EntityRendererConsumer consumer) {
        consumer.accept(ExtraBotanyEntityType.AURA_FIRE, NoopRenderer::new);
    }
    public static void registerBlockEntityRenderers(BERConsumer consumer) {
        consumer.register(ExtraBotanyBlockEntities.PEDESTAL, PedestalRenderer::new);
    }
}
