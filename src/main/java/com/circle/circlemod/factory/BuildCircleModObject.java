package com.circle.circlemod.factory;

import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.world.entity.Entity;


/**
 * 构建Circle Mod 物品
 *
 * @author yuanxin
 * @date 2024/11/17
 */
public class BuildCircleModObject<T> {


    public BuildWithItem withItem(CircleModResources resources) {
        return new BuildWithItem(resources);
    }

    public BuildWithEntity<? extends Entity> withEntity(CircleModResources resources) {
        return new BuildWithEntity<>(resources);
    }
}
