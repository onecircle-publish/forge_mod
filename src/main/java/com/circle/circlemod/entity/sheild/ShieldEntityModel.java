package com.circle.circlemod.entity.sheild;

import com.circle.circlemod.CircleMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShieldEntityModel extends AnimatedGeoModel {
    @Override
    public ResourceLocation getModelLocation(Object o) {
        return new ResourceLocation(CircleMod.MOD_ID, "geo/shield.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object o) {
        return new ResourceLocation(CircleMod.MOD_ID, "textures/entity/shield.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object o) {
        return new ResourceLocation(CircleMod.MOD_ID, "animations/shield.animation.json");
    }
}
