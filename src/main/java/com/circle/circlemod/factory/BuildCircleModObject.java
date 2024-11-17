package com.circle.circlemod.factory;

import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.world.entity.Entity;


public class BuildCircleModObject<T> {


    public BuildWithItem withItem(CircleModResources resources) {
        return new BuildWithItem(resources);
    }

    public BuildWithEntity<? extends Entity> withEntity(CircleModResources resources) {
        return new BuildWithEntity<>(resources);
    }
}
