package io.github.lounode.extrabotany.client.model.armor;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ShadowWarriorArmorModel {

	public static MeshDefinition createNormalMesh() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		//PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
		PartDefinition _head = head.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 33).addBox(-9.0F, 0.0F, 0.0F, 9.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -11.5F, -4.5F));

		PartDefinition back = _head.addOrReplaceChild("back", CubeListBuilder.create().texOffs(37, 33).addBox(-9.0F, 0.0F, 0.0F, 9.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5F, 7.5F, 0.2618F, 0.0F, 0.0F));

		PartDefinition front1 = _head.addOrReplaceChild("front1", CubeListBuilder.create().texOffs(58, 33).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2F, 2.5F, 2.6F, 0.0F, -0.9948F, 0.0F));

		PartDefinition front2 = _head.addOrReplaceChild("front2", CubeListBuilder.create().texOffs(67, 33).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.2F, 2.5F, 0.0F, 0.0F, 0.9948F, 0.0F));

		PartDefinition right2 = _head.addOrReplaceChild("right2", CubeListBuilder.create().texOffs(76, 33).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.5F, 0.0F, 0.0F, 0.0F, 0.3316F));

		PartDefinition front = _head.addOrReplaceChild("front", CubeListBuilder.create().texOffs(97, 33).addBox(-6.0F, 0.0F, 0.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 0.5F, -0.6F));

		PartDefinition right1 = _head.addOrReplaceChild("right1", CubeListBuilder.create().texOffs(0, 50).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -4.5F, -0.4F, 0.0873F, -0.0873F, -0.2618F));

		PartDefinition medal = _head.addOrReplaceChild("medal", CubeListBuilder.create().texOffs(7, 50).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -3.0F, -0.5F));

		PartDefinition right3 = _head.addOrReplaceChild("right3", CubeListBuilder.create().texOffs(14, 50).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 1.3F, 0.2F, 0.0F, 0.0F, 0.3316F));

		PartDefinition left2 = _head.addOrReplaceChild("left2", CubeListBuilder.create().texOffs(23, 50).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, 0.5F, 0.0F, 0.0F, 0.0F, -0.3316F));

		PartDefinition left3 = _head.addOrReplaceChild("left3", CubeListBuilder.create().texOffs(44, 50).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.2F, 2.0F, 0.2F, 0.0F, 0.0F, -0.3316F));

		PartDefinition left1 = _head.addOrReplaceChild("left1", CubeListBuilder.create().texOffs(53, 50).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.5F, -5.0F, -0.5F, 0.0873F, 0.0873F, 0.2618F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
		PartDefinition _body = body.addOrReplaceChild("_body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0, 0.0F));

		PartDefinition fronter = _body.addOrReplaceChild("fronter", CubeListBuilder.create().texOffs(0, 84).addBox(-6.0F, 0.0F, 0.0F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.0F, -3.0F));

		PartDefinition backer = _body.addOrReplaceChild("backer", CubeListBuilder.create().texOffs(0, 102).addBox(-6.0F, 0.0F, 0.0F, 6.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 1.0F, 1.7F));

		PartDefinition book = _body.addOrReplaceChild("book", CubeListBuilder.create().texOffs(0, 93).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.2F, 2.7F, 0.0F, 0.0F, 0.7854F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
		PartDefinition _left_arm = left_arm.addOrReplaceChild("_left_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rarm1 = _left_arm.addOrReplaceChild("rarm1", CubeListBuilder.create().texOffs(15, 96).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -4.0F, -2.5F, 0.0F, 0.0F, 0.1745F));

		PartDefinition rarm2 = _left_arm.addOrReplaceChild("rarm2", CubeListBuilder.create().texOffs(36, 96).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.7F, -2.0F, -2.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition rarm3 = _left_arm.addOrReplaceChild("rarm3", CubeListBuilder.create().texOffs(47, 96).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.7F, -1.0F, -2.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition rarm4 = _left_arm.addOrReplaceChild("rarm4", CubeListBuilder.create().texOffs(58, 96).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.3F, 3.2F, -2.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition _right_arm = right_arm.addOrReplaceChild("_right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0F, 0.0F, 0.0F));

		PartDefinition larm1 = _right_arm.addOrReplaceChild("larm1", CubeListBuilder.create().texOffs(15, 84).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -3.0F, -2.5F, 0.0F, 0.0F, -0.1745F));

		PartDefinition larm4 = _right_arm.addOrReplaceChild("larm4", CubeListBuilder.create().texOffs(36, 84).addBox(-2.0F, 0.0F, 0.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.3F, 3.2F, -2.0F));

		PartDefinition larm3 = _right_arm.addOrReplaceChild("larm3", CubeListBuilder.create().texOffs(49, 84).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.7F, -1.0F, -2.0F, 0.0F, 0.0F, -0.1745F));

		PartDefinition larm2 = _right_arm.addOrReplaceChild("larm2", CubeListBuilder.create().texOffs(60, 84).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.7F, -2.0F, -2.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition _left_leg = left_leg.addOrReplaceChild("_left_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition boot1 = _left_leg.addOrReplaceChild("boot1", CubeListBuilder.create().texOffs(0, 114).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 8.0F, -2.5F));

		PartDefinition fb1 = boot1.addOrReplaceChild("fb1", CubeListBuilder.create().texOffs(19, 114).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 1.0F, -0.5F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition _right_leg = right_leg.addOrReplaceChild("_right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0F, 0.0F, 0.0F));

		PartDefinition boot2 = _right_leg.addOrReplaceChild("boot2", CubeListBuilder.create().texOffs(0, 114).addBox(-4.0F, 0.0F, 0.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 8.0F, -2.5F));

		PartDefinition fb2 = boot2.addOrReplaceChild("fb2", CubeListBuilder.create().texOffs(19, 114).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -0.5F));

		return meshdefinition;
	}

	public static MeshDefinition createLeggingsMesh() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition root = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition leggings = root.addOrReplaceChild("leggings", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition a2 = leggings.addOrReplaceChild("a2", CubeListBuilder.create().texOffs(0, 67).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -12.04F, -3.0F, -0.2793F, 0.0F, 0.0F));

		PartDefinition a1 = leggings.addOrReplaceChild("a1", CubeListBuilder.create().texOffs(0, 67).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -14.0F, -3.0F, -0.2793F, 0.0F, 0.0F));

		PartDefinition a3 = leggings.addOrReplaceChild("a3", CubeListBuilder.create().texOffs(0, 67).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, -10.0F, -3.0F, -0.2793F, 0.0F, 0.0F));

		PartDefinition b1 = leggings.addOrReplaceChild("b1", CubeListBuilder.create().texOffs(13, 67).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -14.0F, -3.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition b2 = leggings.addOrReplaceChild("b2", CubeListBuilder.create().texOffs(13, 67).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -12.0F, -3.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition b3 = leggings.addOrReplaceChild("b3", CubeListBuilder.create().texOffs(13, 67).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -10.0F, -3.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition c1 = leggings.addOrReplaceChild("c1", CubeListBuilder.create().texOffs(22, 67).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -14.0F, -3.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition c2 = leggings.addOrReplaceChild("c2", CubeListBuilder.create().texOffs(22, 67).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -12.0F, -3.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition c3 = leggings.addOrReplaceChild("c3", CubeListBuilder.create().texOffs(22, 67).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -10.0F, -3.0F, -0.2618F, 0.0F, 0.0F));

		PartDefinition d1 = leggings.addOrReplaceChild("d1", CubeListBuilder.create().texOffs(0, 74).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -14.0F, -2.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition d2 = leggings.addOrReplaceChild("d2", CubeListBuilder.create().texOffs(0, 74).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -12.0F, -2.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition d3 = leggings.addOrReplaceChild("d3", CubeListBuilder.create().texOffs(0, 74).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -10.0F, -2.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition e1 = leggings.addOrReplaceChild("e1", CubeListBuilder.create().texOffs(0, 74).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.2F, -14.2F, -2.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition e2 = leggings.addOrReplaceChild("e2", CubeListBuilder.create().texOffs(0, 74).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.2F, -12.2F, -2.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition e3 = leggings.addOrReplaceChild("e3", CubeListBuilder.create().texOffs(0, 74).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.2F, -10.2F, -2.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition f1 = leggings.addOrReplaceChild("f1", CubeListBuilder.create().texOffs(11, 74).addBox(-9.0F, 0.0F, 0.0F, 9.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -14.0F, 1.8F, 0.2618F, 0.0F, 0.0F));

		PartDefinition f2 = leggings.addOrReplaceChild("f2", CubeListBuilder.create().texOffs(11, 74).addBox(-9.0F, 0.0F, 0.0F, 9.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -12.0F, 1.8F, 0.2618F, 0.0F, 0.0F));

		PartDefinition f3 = leggings.addOrReplaceChild("f3", CubeListBuilder.create().texOffs(11, 74).addBox(-9.0F, 0.0F, 0.0F, 9.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -10.0F, 1.8F, 0.2618F, 0.0F, 0.0F));

		return meshdefinition;
	}
}
