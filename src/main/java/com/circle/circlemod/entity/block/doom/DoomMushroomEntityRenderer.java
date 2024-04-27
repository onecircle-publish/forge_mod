package com.circle.circlemod.entity.block.doom;

import com.circle.circlemod.CircleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DoomMushroomEntityRenderer extends EntityRenderer<DoomMushroomEntity> {
    private EntityModel<DoomMushroomEntity> doomMushroomModel;

    public DoomMushroomEntityRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        doomMushroomModel = new DoomMushroomModel(pContext.bakeLayer(DoomMushroomModel.LAYER_LOCATION));
    }

    @Override
    public void render(DoomMushroomEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
        this.doomMushroomModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutout(getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public ResourceLocation getTextureLocation(DoomMushroomEntity pEntity) {
        return new ResourceLocation(CircleMod.MOD_ID, "textures/entity/doom_mushroom.png");
    }
}
