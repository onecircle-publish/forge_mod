package com.circle.circlemod.entity.sheild;

import com.circle.circlemod.CircleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class ShieldEntityRenderer extends GeoEntityRenderer<ShieldEntity> {
    public ShieldEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ShieldEntityModel());
    }

    @Override
    public ResourceLocation getTextureLocation(ShieldEntity animatable) {
        return new ResourceLocation(CircleMod.MOD_ID, "textures/entity/shield.png");
    }

    @Override
    public RenderType getRenderType(ShieldEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
