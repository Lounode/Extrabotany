package io.github.lounode.extrabotany.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.lounode.extrabotany.common.entity.gaia.Gaia;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import vazkii.botania.client.core.helper.CoreShaders;
import vazkii.botania.client.core.helper.RenderHelper;
import vazkii.botania.client.model.armor.ArmorModels;

import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class GaiaRender extends HumanoidMobRenderer<Gaia, HumanoidModel<Gaia>> {
    public static final float DEFAULT_GRAIN_INTENSITY = 0.05F;
    public static final float DEFAULT_DISFIGURATION = 0.025F;

    private final Model normalModel;
    private final Model slimModel;

    public GaiaRender(EntityRendererProvider.Context ctx) {
        super(ctx, new Model(ctx.bakeLayer(ModelLayers.PLAYER)), 0F);
        this.normalModel = (Model) this.getModel();
        this.slimModel = new Model(ctx.bakeLayer(ModelLayers.PLAYER_SLIM));
        // Call this here bc no other place with access to Context
        ArmorModels.init(ctx);
    }

    @Override
    public void render(@NotNull Gaia dopple, float yaw, float partialTicks, PoseStack ms, MultiBufferSource buffers, int light) {
        int invulTime = dopple.getInvulTime();
        ShaderInstance shader = CoreShaders.doppleganger();
        if (shader != null) {
            float grainIntensity, disfiguration;
            if (invulTime > 0) {
                grainIntensity = invulTime > 20 ? 1F : invulTime * 0.05F;
                disfiguration = grainIntensity * 0.3F;
            } else {
                disfiguration = (DEFAULT_DISFIGURATION + dopple.hurtTime * ((1F - 0.15F) / 20F)) / 2F;
                grainIntensity = DEFAULT_GRAIN_INTENSITY + dopple.hurtTime * ((1F - 0.15F) / 10F);
            }


            shader.safeGetUniform("BotaniaGrainIntensity").set(grainIntensity);
            shader.safeGetUniform("BotaniaDisfiguration").set(disfiguration);
        }

        var view = Minecraft.getInstance().getCameraEntity();
        if (view instanceof AbstractClientPlayer && DefaultPlayerSkin.getSkinModelName(view.getUUID()).equals("slim")) {
            this.model = slimModel;
        } else {
            this.model = normalModel;
        }

        super.render(dopple, yaw, partialTicks, ms, buffers, light);

        //TODO BUG 多个盖亚会出现问题
        if (shader != null) {
            //shader.safeGetUniform("BotaniaGrainIntensity").set(DEFAULT_GRAIN_INTENSITY);
            //shader.safeGetUniform("BotaniaDisfiguration").set(DEFAULT_DISFIGURATION);
        }
    }

    @NotNull
    @Override
    public ResourceLocation getTextureLocation(@NotNull Gaia entity) {
        Minecraft mc = Minecraft.getInstance();

        if (!(mc.getCameraEntity() instanceof AbstractClientPlayer clientPlayer)) {
            return DefaultPlayerSkin.getDefaultSkin(entity.getUUID());
        }

        return clientPlayer.getSkinTextureLocation();
    }

    @Override
    protected boolean isBodyVisible(Gaia dopple) {
        return true;
    }

    private static class Model extends HumanoidModel<Gaia> {
        Model(ModelPart root) {
            super(root, RenderHelper::getDopplegangerLayer);
        }
    }

}
