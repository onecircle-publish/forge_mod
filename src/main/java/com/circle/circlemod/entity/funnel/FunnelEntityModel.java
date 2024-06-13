package com.circle.circlemod.entity.funnel;

import com.circle.circlemod.CircleMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FunnelEntityModel extends AnimatedGeoModel {
    @Override
    public ResourceLocation getModelLocation(Object object) {
        return new ResourceLocation(CircleMod.MOD_ID, "geo/funnel.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object object) {
        return new ResourceLocation(CircleMod.MOD_ID, "textures/entity/funnel.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object animatable) {
        return new ResourceLocation(CircleMod.MOD_ID, "animations/funnel.animation.json");
    }
}
