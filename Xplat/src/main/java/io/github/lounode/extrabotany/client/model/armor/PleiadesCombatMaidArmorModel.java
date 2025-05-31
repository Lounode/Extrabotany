package io.github.lounode.extrabotany.client.model.armor;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class PleiadesCombatMaidArmorModel {

	public static MeshDefinition createNormalMesh() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		PartDefinition Head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(1, 61).addBox(-5.0F, -9.0F, -1.0F, 10.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Ear2_r1 = Head.addOrReplaceChild("Ear2_r1", CubeListBuilder.create().texOffs(12, 55).addBox(-2.0F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -9.0F, -2.0F, 0.0F, 0.0F, -0.8727F));
		PartDefinition Ear1_r1 = Head.addOrReplaceChild("Ear1_r1", CubeListBuilder.create().texOffs(1, 55).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -9.0F, -2.0F, 0.0F, 0.0F, 0.8727F));

		PartDefinition Body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition Chest_r1 = Body.addOrReplaceChild("Chest_r1", CubeListBuilder.create().texOffs(1, 67).addBox(-1.0F, -4.5F, -2.0F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 5.0F, -1.0F, 0.192F, 0.0F, 0.0F));

		PartDefinition Right_Arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(24, 75).addBox(-4.0F, -2.0F, -3.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition Left_Arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(1, 75).addBox(-1.0F, -2.0F, -3.0F, 5.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition Right_Leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition Left_Leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 12.0F, 0.0F));

		return meshdefinition;
	}

	public static MeshDefinition createDressMesh() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);

		var body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition Dress = body.addOrReplaceChild("Dress", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition DressB_r1 = Dress.addOrReplaceChild("DressB_r1", CubeListBuilder.create().texOffs(1, 38).addBox(-0.25F, -2.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -10.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
		PartDefinition DressA_r1 = Dress.addOrReplaceChild("DressA_r1", CubeListBuilder.create().texOffs(1, 38).addBox(-0.75F, -2.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
		PartDefinition DressBackPlus_r1 = Dress.addOrReplaceChild("DressBackPlus_r1", CubeListBuilder.create().texOffs(16, 32).addBox(-3.0F, -2.0522F, 0.0685F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.7F, 2.0F, 0.3054F, 0.0F, 0.0F));
		PartDefinition DressFrontPlus_r1 = Dress.addOrReplaceChild("DressFrontPlus_r1", CubeListBuilder.create().texOffs(1, 32).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, -3.0F, -0.3054F, 0.0F, 0.0F));
		PartDefinition DressBack_r1 = Dress.addOrReplaceChild("DressBack_r1", CubeListBuilder.create().texOffs(1, 48).addBox(-4.0F, -2.0076F, -0.1743F, 8.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, 2.0F, 0.1745F, 0.0F, 0.0F));
		PartDefinition DressFront_r1 = Dress.addOrReplaceChild("DressFront_r1", CubeListBuilder.create().texOffs(20, 48).addBox(-4.0F, -2.0076F, 0.1743F, 8.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, -3.0F, -0.1745F, 0.0F, 0.0F));

		return meshdefinition;
	}
}
