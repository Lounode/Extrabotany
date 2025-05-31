package io.github.lounode.extrabotany.client.model.armor;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class GoblinSlayerArmorModel {

	public static MeshDefinition createNormalMesh() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition Head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -8.0F, -4.0F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(0, 18).addBox(-2.25F, -1.0F, -5.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(50, 0).addBox(-1.5F, -5.0F, -5.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(50, 0).addBox(0.5F, -5.0F, -5.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Hair2_r1 = Head.addOrReplaceChild("Hair2_r1", CubeListBuilder.create().texOffs(34, 4).addBox(-1.0F, -2.0F, 3.0F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -8.0F, -1.0F, -0.1745F, 0.2182F, 0.0F));

		PartDefinition Hair1_r1 = Head.addOrReplaceChild("Hair1_r1", CubeListBuilder.create().texOffs(35, 0).addBox(-0.5F, -2.0F, -1.0F, 3.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -8.0F, 3.0F, 0.1745F, -0.3927F, 0.0F));

		PartDefinition Bar4_r1 = Head.addOrReplaceChild("Bar4_r1", CubeListBuilder.create().texOffs(59, 0).addBox(0.0F, -3.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -3.0F, -5.0F, 0.0F, 0.0F, 0.1745F));

		PartDefinition Bar3_r1 = Head.addOrReplaceChild("Bar3_r1", CubeListBuilder.create().texOffs(59, 0).addBox(0.3F, -2.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -4.0F, -5.0F, 0.0F, 0.0F, -0.1396F));

		PartDefinition Hat9_r1 = Head.addOrReplaceChild("Hat9_r1", CubeListBuilder.create().texOffs(0, 74).addBox(-7.0F, -3.5F, -2.091F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -5.0F, -1.5F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Hat8_r1 = Head.addOrReplaceChild("Hat8_r1", CubeListBuilder.create().texOffs(0, 69).addBox(3.1F, -1.0F, -3.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.4F, -7.5F, 4.0178F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Hat7_r1 = Head.addOrReplaceChild("Hat7_r1", CubeListBuilder.create().texOffs(0, 65).mirror().addBox(-7.0F, -1.0F, 5.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.92F, -9.5F, -0.2329F, 0.0F, -0.7854F, 0.0F));

		PartDefinition Hat6_r1 = Head.addOrReplaceChild("Hat6_r1", CubeListBuilder.create().texOffs(0, 62).mirror().addBox(-2.5F, -0.5F, -1.44F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.6F, -10.0F, -0.2753F, 0.0F, -0.7854F, 0.0F));

		PartDefinition Hat5_r1 = Head.addOrReplaceChild("Hat5_r1", CubeListBuilder.create().texOffs(0, 56).addBox(-6.0F, -1.5F, -5.01F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.1F, -8.0F, -1.64F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Hat4_r1 = Head.addOrReplaceChild("Hat4_r1", CubeListBuilder.create().texOffs(0, 49).addBox(0.0F, -0.5F, -5.5F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -8.0F, 2.25F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Hat3_r1 = Head.addOrReplaceChild("Hat3_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, -1.0F, -5.1F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.15F, -6.5F, 0.7F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Hat2_r1 = Head.addOrReplaceChild("Hat2_r1", CubeListBuilder.create().texOffs(0, 35).addBox(-1.0F, -1.0F, -5.5F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, -5.5F, 0.5429F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Hat1_r1 = Head.addOrReplaceChild("Hat1_r1", CubeListBuilder.create().texOffs(0, 29).addBox(-0.98F, -0.5F, -4.015F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.22F, -5.5F, -0.785F, 0.0F, 0.7854F, 0.0F));

		PartDefinition Ear2_r1 = Head.addOrReplaceChild("Ear2_r1", CubeListBuilder.create().texOffs(16, 18).addBox(0.5F, -2.0F, -2.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(16, 24).addBox(-9.5F, -2.0F, -2.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -3.0F, 1.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition Body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(100, 0).addBox(-5.0F, -1.0F, -1.0F, 9.0F, 13.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(50, 8).addBox(-5.5F, 5.0F, -1.5F, 10.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(68, 0).addBox(-5.5F, 11.0F, -1.5F, 10.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, -1.0F));

		PartDefinition RobeBack2_r1 = Body.addOrReplaceChild("RobeBack2_r1", CubeListBuilder.create().texOffs(78, 18).addBox(0.0F, 0.0F, -1.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(78, 18).addBox(0.0F, 0.0F, 5.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2815F, 11.1381F, -1.0F, 0.0F, 0.0F, 0.2967F));

		PartDefinition RobeBack3_r1 = Body.addOrReplaceChild("RobeBack3_r1", CubeListBuilder.create().texOffs(66, 18).addBox(-5.5F, -0.5F, -1.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(66, 18).addBox(-5.5F, -0.5F, 6.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1042F, 11.423F, -1.5F, 0.0F, 0.0F, -0.3054F));

		PartDefinition RobeBack4_r1 = Body.addOrReplaceChild("RobeBack4_r1", CubeListBuilder.create().texOffs(66, 15).addBox(-5.5F, 0.0F, 0.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(66, 15).addBox(-5.5F, 0.0F, 7.5F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.3302F, 10.4917F, -3.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition RobeBack1_r1 = Body.addOrReplaceChild("RobeBack1_r1", CubeListBuilder.create().texOffs(66, 15).mirror().addBox(0.5F, -1.0F, -1.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(66, 15).mirror().addBox(0.5F, -1.0F, 6.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0714F, 11.4576F, -1.5F, 0.0F, 0.0F, -0.2618F));

		PartDefinition ArmorPieceBack_r1 = Body.addOrReplaceChild("ArmorPieceBack_r1", CubeListBuilder.create().texOffs(54, 0).addBox(-1.0F, -3.0F, 0.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 8.0F, 4.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition ArmorPiece7_r1 = Body.addOrReplaceChild("ArmorPiece7_r1", CubeListBuilder.create().texOffs(56, 0).addBox(0.31F, -2.295F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.05F, 1.125F, -2.0F, 0.0F, 0.0F, -2.3562F));

		PartDefinition ArmorPiece6_r1 = Body.addOrReplaceChild("ArmorPiece6_r1", CubeListBuilder.create().texOffs(56, 0).addBox(0.2F, -2.2929F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.6F, 3.66F, -2.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition ArmorPiece5_r1 = Body.addOrReplaceChild("ArmorPiece5_r1", CubeListBuilder.create().texOffs(54, 0).addBox(-1.0F, -3.0F, 0.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9778F, 4.3636F, -3.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition ArmorPiece4_r1 = Body.addOrReplaceChild("ArmorPiece4_r1", CubeListBuilder.create().texOffs(82, 8).addBox(-1.1F, -2.0F, 0.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2F, 6.0F, -2.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition ArmorPiece3_r1 = Body.addOrReplaceChild("ArmorPiece3_r1", CubeListBuilder.create().texOffs(60, 15).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2707F, 3.8822F, -4.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition ArmorPiece2_r1 = Body.addOrReplaceChild("ArmorPiece2_r1", CubeListBuilder.create().texOffs(50, 15).mirror().addBox(-1.1F, -2.0F, 0.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.2F, 2.5387F, -4.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition ArmorPiece1_r1 = Body.addOrReplaceChild("ArmorPiece1_r1", CubeListBuilder.create().texOffs(92, 8).addBox(-3.0F, 0.62F, -1.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 9.0F, -1.5F, 0.0F, 0.0F, -0.7854F));

		PartDefinition Robe6_r1 = Body.addOrReplaceChild("Robe6_r1", CubeListBuilder.create().texOffs(80, 21).addBox(-6.0F, 0.0F, 0.5F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.2378F, 14.3542F, -2.0F, 0.0F, 0.0F, 0.2967F));

		PartDefinition Robe6_r2 = Body.addOrReplaceChild("Robe6_r2", CubeListBuilder.create().texOffs(66, 21).addBox(-3.0F, 1.0F, -5.5F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.7917F, 8.6199F, 4.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition Robe4_r1 = Body.addOrReplaceChild("Robe4_r1", CubeListBuilder.create().texOffs(80, 21).addBox(-6.0F, 0.0F, 0.5F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.7314F, 11.0965F, -2.0F, 0.0F, 0.0F, -0.3054F));

		PartDefinition Robe3_r1 = Body.addOrReplaceChild("Robe3_r1", CubeListBuilder.create().texOffs(66, 21).addBox(-3.0F, 1.0F, -5.5F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.6213F, 7.3258F, 4.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition RightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(66, 41).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F))
				.texOffs(98, 48).addBox(-5.0F, 4.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(108, 48).addBox(-4.5F, 4.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition RightArm2_r1 = RightArm.addOrReplaceChild("RightArm2_r1", CubeListBuilder.create().texOffs(104, 37).addBox(-6.0F, -3.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition RightArm_r1 = RightArm.addOrReplaceChild("RightArm_r1", CubeListBuilder.create().texOffs(100, 25).addBox(-11.5F, -24.0F, -5.5F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 22.0F, 2.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition LeftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 48).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false)
				.texOffs(108, 48).mirror().addBox(0.5F, 4.0F, -2.5F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(98, 48).mirror().addBox(5.0F, 4.0F, -2.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 2.0F, 0.0F));

		PartDefinition LeftArm2_r1 = LeftArm.addOrReplaceChild("LeftArm2_r1", CubeListBuilder.create().texOffs(104, 37).mirror().addBox(-6.0F, -3.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.8F, 3.4771F, 0.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition LeftArm_r1 = LeftArm.addOrReplaceChild("LeftArm_r1", CubeListBuilder.create().texOffs(100, 25).mirror().addBox(-11.44F, -23.96F, -5.5F, 7.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(16.0F, 18.5F, 2.0F, 0.0F, 0.0F, -0.2182F));

		var left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.ZERO);
		PartDefinition LeftBoot = left_leg.addOrReplaceChild("_left_leg", CubeListBuilder.create().texOffs(24, 27).addBox(0.5F, -2.0F, -3.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 42).addBox(-0.5F, -6.0F, 2.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(29, 45).addBox(4.5F, -5.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 45).addBox(4.5F, -6.0F, -0.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(24, 31).addBox(-0.5F, -4.0F, -2.5F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(21, 33).addBox(1.5F, -3.0F, -3.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(24, 45).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(29, 45).addBox(-0.5F, -5.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		var right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.ZERO);
		PartDefinition RightBoot = right_leg.addOrReplaceChild("_right_leg", CubeListBuilder.create().texOffs(24, 27).mirror().addBox(0.5F, -2.0F, -3.5F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 42).mirror().addBox(-0.5F, -6.0F, 2.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(29, 45).mirror().addBox(4.5F, -5.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 45).mirror().addBox(4.5F, -6.0F, -0.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 31).mirror().addBox(-0.5F, -4.0F, -2.5F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(21, 33).mirror().addBox(1.5F, -3.0F, -3.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 45).mirror().addBox(-0.5F, -6.0F, -0.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(29, 45).mirror().addBox(-0.5F, -5.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.9F, 12.0F, 0.0F));

		return meshdefinition;
	}

	public static MeshDefinition createLeggingsMesh() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.ZERO);
		partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition RightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(98, 61).addBox(-2.1F, 1.0F, -3.0F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(84, 70).addBox(-3.6F, 2.0F, -2.5F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(108, 61).addBox(-3.1F, -1.0F, -2.0F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(98, 61).mirror().addBox(-1.9F, 1.0F, -3.0F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(84, 70).addBox(-2.4F, 2.0F, -2.5F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(108, 61).addBox(-1.9F, -1.0F, -2.0F, 5.0F, 13.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 12.0F, 0.0F));

		return meshdefinition;
	}
}
