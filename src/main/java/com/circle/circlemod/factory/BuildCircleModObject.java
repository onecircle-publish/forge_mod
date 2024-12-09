package com.circle.circlemod.factory;

import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;


/**
 * 构建Circle Mod 物品
 *
 * @author yuanxin
 * @date 2024/11/17
 */
public class BuildCircleModObject {


    public <T extends Item>BuildWithItem<T> withItem(CircleModResources resources) {
        return new BuildWithItem<>(resources);
    }

    public <T extends Entity>BuildWithEntity<T> withEntity(CircleModResources resources) {
        return new BuildWithEntity<>(resources);
    }

    public <T extends BlockEntity>BuildWithBlockEntity<T> withBlockEntity(CircleModResources resources) {
        return new BuildWithBlockEntity<>(resources);
    }

    public <T extends Block>BuildWithBlock<T> withBlock(CircleModResources resources) {
        return new BuildWithBlock<>(resources);
    }
}
