package com.circle.circlemod.common.item.firecracker;

import com.circle.circlemod.common.CircleMod;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

/**
 * @author : yuanxin
 * @date : 2024-06-27 14:25
 **/
public class FireCrackerModelRenderer extends GeoItemRenderer<FireCracker> {
    public FireCrackerModelRenderer() {
        super(new DefaultedItemGeoModel<>(new ResourceLocation(CircleMod.MODID, "fire_cracker")));
    }
}
