package com.circle.circlemod.common;

import com.circle.circlemod.common.item.firecracker.FireCracker;
import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.HashMap;
import java.util.function.Supplier;


/**
 * 用于添加所有自定义的物品、实体等
 *
 * @author yuanxin
 * @date 2024/11/17
 */
public class CircleModRegister {
    public static void register(IEventBus iEventBus) {
        CircleMod.LOGGER.debug("register items...");

        CircleDeferredRegister.createItem(new HashMap<CircleModResources.CircleModResource, Supplier>() {
            {
                put(CircleModResources.CREATIVE_ITEM.resource, () -> new Item(new Item.Properties()));
                put(CircleModResources.FIRE_CRACKER.resource, () -> new FireCracker(new Item.Properties()));
            }
        });
        CircleDeferredRegister.register(iEventBus);
    }
}
