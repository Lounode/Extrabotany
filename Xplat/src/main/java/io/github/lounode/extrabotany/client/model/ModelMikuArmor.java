package io.github.lounode.extrabotany.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class ModelMikuArmor<T extends LivingEntity> extends HumanoidModel<T> {

	public ModelMikuArmor(ModelPart root) {
		super(root);
	}

	public static MeshDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		// Head
		root.addOrReplaceChild("head", CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		// Body
		root.addOrReplaceChild("body", CubeListBuilder.create()
				.texOffs(16, 16)
				.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4),
				PartPose.offset(0.0F, 0.0F, 0.0F));

		// Right Arm
		root.addOrReplaceChild("right_arm", CubeListBuilder.create()
				.texOffs(40, 16)
				.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4),
				PartPose.offset(-5.0F, 2.0F, 0.0F));

		// Left Arm
		root.addOrReplaceChild("left_arm", CubeListBuilder.create()
				.texOffs(40, 16)
				.mirror()
				.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4),
				PartPose.offset(5.0F, 2.0F, 0.0F));

		// Right Leg
		root.addOrReplaceChild("right_leg", CubeListBuilder.create()
				.texOffs(0, 16)
				.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4),
				PartPose.offset(-1.9F, 12.0F, 0.0F));

		// Left Leg
		root.addOrReplaceChild("left_leg", CubeListBuilder.create()
				.texOffs(0, 16)
				.mirror()
				.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4),
				PartPose.offset(1.9F, 12.0F, 0.0F));

		return mesh;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch) {
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
			int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		this.head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}
