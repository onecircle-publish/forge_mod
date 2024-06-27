package com.circle.circlemod.common.item.firecracker;

import com.circle.circlemod.common.CircleMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

/**
 * @author : yuanxin
 * @date : 2024-06-27 14:28
 **/
public class FireCrackerModel extends GeoModel {
    @Override
    public ResourceLocation getModelResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(CircleMod.MODID,"geo/small-tnt.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoAnimatable geoAnimatable) {
        return new ResourceLocation(CircleMod.MODID,"textures/item/");
    }

    @Override
    public ResourceLocation getAnimationResource(GeoAnimatable geoAnimatable) {
        return null;
    }
}
