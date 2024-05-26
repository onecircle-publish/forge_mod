package com.circle.circlemod.entity.projectile.ice;

import com.circle.circlemod.CircleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class IceRenderer extends EntityRenderer<Ice> {
    private EntityModel<Ice> ice;
    private final ItemRenderer itemRenderer;

    public IceRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        itemRenderer = pContext.getItemRenderer();
    }

    @Override
    public ResourceLocation getTextureLocation(Ice pEntity) {
        return new ResourceLocation(CircleMod.MOD_ID, "textures/entity/ice.png");
    }

    @Override
    public void render(Ice pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.itemRenderer.renderStatic(pEntity.getItem(), ItemTransforms.TransformType.GROUND, pPackedLight, OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pEntity.getId());
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
}
