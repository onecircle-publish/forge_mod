package com.circle.circlemod.entity.funnel;

import com.circle.circlemod.CircleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FunnelEntityRenderer extends GeoEntityRenderer<FunnelEntity> {
    public FunnelEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FunnelEntityModel());
    }

    @Override
    public ResourceLocation getTextureLocation(FunnelEntity animatable) {
        return new ResourceLocation(CircleMod.MOD_ID, "textures/entity/funnel.png");
    }

    @Override
    public RenderType getRenderType(FunnelEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
