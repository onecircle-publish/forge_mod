package com.circle.circlemod.entity.block.doom;// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.circle.circlemod.CircleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class DoomMushroomModel extends EntityModel<DoomMushroomEntity> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CircleMod.MOD_ID, "doom_mushroom"), "main");

    private final ModelPart bone;

    public DoomMushroomModel(ModelPart root) {
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -8.0F, 8.0F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 9).addBox(-7.0F, -2.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 9).addBox(-9.0F, -2.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(13, 1).addBox(-11.0F, -4.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(13, 1).addBox(-11.0F, -6.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(13, 1).addBox(-10.0F, -7.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(13, 1).addBox(-6.0F, -7.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(13, 1).addBox(-5.0F, -6.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(13, 1).addBox(-5.0F, -4.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(16F, 0F, -16F));
        PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 9).addBox(0.0F, -9.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 4).addBox(5.0F, -12.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 1).addBox(7.0F, -8.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 1).addBox(2.0F, -11.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 1).addBox(2.0F, -7.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(2, 9).addBox(3.0F, -6.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 4).addBox(5.0F, -6.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 1).addBox(6.0F, -7.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 1).addBox(6.0F, -11.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(4, 1).addBox(7.0F, -10.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(2, 9).addBox(3.0F, -6.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(2, 9).addBox(3.0F, -12.0F, 8.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));
        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(DoomMushroomEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
    }
}