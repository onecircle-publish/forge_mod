package com.circle.circlemod.common.entity.safebed;

import com.circle.circlemod.common.CircleMod;
import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class SafeBedEntityRenderer extends GeoBlockRenderer<SafeBedEntity> {

    public SafeBedEntityRenderer(BlockEntityRendererProvider.Context pContext) {
        super(new DefaultedEntityGeoModel<>(new ResourceLocation(CircleMod.MODID, CircleModResources.SAFE_BED_ENTITY.getId())));
    }
}
