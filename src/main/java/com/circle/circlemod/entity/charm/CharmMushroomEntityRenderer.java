package com.circle.circlemod.entity.charm;

import com.circle.circlemod.CircleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class CharmMushroomEntityRenderer extends EntityRenderer<CharmMushroomEntity> {
    private EntityModel<CharmMushroomEntity> charmMushroomModelModel;

    public CharmMushroomEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.charmMushroomModelModel = new CharmMushroomModel<>(pContext.bakeLayer(CharmMushroomModel.LAYER_LOCATION));
    }

    @Override
    public void render(CharmMushroomEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.mulPose(Vector3f.XN.rotationDegrees(-180));
        this.charmMushroomModelModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
    }

    @Override
    public ResourceLocation getTextureLocation(CharmMushroomEntity pEntity) {
        return new ResourceLocation(CircleMod.MOD_ID, "textures/entity/charm_mushroom.png");
    }
}
