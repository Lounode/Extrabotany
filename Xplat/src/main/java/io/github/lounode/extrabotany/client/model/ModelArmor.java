package io.github.lounode.extrabotany.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;

public class ModelArmor extends HumanoidModel<LivingEntity> {

	private final EquipmentSlot slot;

	public static final ModelLayerLocation LAYER_LOCATION =
			new ModelLayerLocation(new ResourceLocation("extrabotany", "textures/model"), "armor_starry_idol.png");

	public ModelArmor(ModelPart root, EquipmentSlot slot) {
		super(root);
		this.slot = slot;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition part = mesh.getRoot();
		// 添加自定义模型层级结构，如果需要修改几何体，参考下方

		return LayerDefinition.create(mesh, 64, 32); // 分辨率可根据你的贴图决定
	}

	@Override
	public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
			float netHeadYaw, float headPitch) {
		if (!(entity instanceof ArmorStand armorStand)) {
			super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			return;
		}

		this.head.xRot = (float) Math.toRadians(armorStand.getHeadPose().getX());
		this.head.yRot = (float) Math.toRadians(armorStand.getHeadPose().getY());
		this.head.zRot = (float) Math.toRadians(armorStand.getHeadPose().getZ());
		this.head.setPos(0.0F, 1.0F, 0.0F);

		this.body.xRot = (float) Math.toRadians(armorStand.getBodyPose().getX());
		this.body.yRot = (float) Math.toRadians(armorStand.getBodyPose().getY());
		this.body.zRot = (float) Math.toRadians(armorStand.getBodyPose().getZ());

		this.leftArm.xRot = (float) Math.toRadians(armorStand.getLeftArmPose().getX());
		this.leftArm.yRot = (float) Math.toRadians(armorStand.getLeftArmPose().getY());
		this.leftArm.zRot = (float) Math.toRadians(armorStand.getLeftArmPose().getZ());

		this.rightArm.xRot = (float) Math.toRadians(armorStand.getRightArmPose().getX());
		this.rightArm.yRot = (float) Math.toRadians(armorStand.getRightArmPose().getY());
		this.rightArm.zRot = (float) Math.toRadians(armorStand.getRightArmPose().getZ());

		this.leftLeg.xRot = (float) Math.toRadians(armorStand.getLeftLegPose().getX());
		this.leftLeg.yRot = (float) Math.toRadians(armorStand.getLeftLegPose().getY());
		this.leftLeg.zRot = (float) Math.toRadians(armorStand.getLeftLegPose().getZ());
		this.leftLeg.setPos(1.9F, 11.0F, 0.0F);

		this.rightLeg.xRot = (float) Math.toRadians(armorStand.getRightLegPose().getX());
		this.rightLeg.yRot = (float) Math.toRadians(armorStand.getRightLegPose().getY());
		this.rightLeg.zRot = (float) Math.toRadians(armorStand.getRightLegPose().getZ());
		this.rightLeg.setPos(-1.9F, 11.0F, 0.0F);
	}
}
