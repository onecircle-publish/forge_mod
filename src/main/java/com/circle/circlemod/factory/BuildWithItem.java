package com.circle.circlemod.factory;

import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

/**
 * 建造物品
 *
 * @author yuanxin
 * @date 2024/11/17
 */
public class BuildWithItem<T extends Item> extends BuildObject {

    public BuildWithItem(CircleModResources resources) {
        super(resources);
    }

    /**
     * 绑定供应商
     *
     * @param supplier 供应商
     * @return {@link BuildWithItem }
     */
    public BuildWithItem<T> bindSupplier(Supplier<T> supplier) {
        super.supplier = supplier;
        return this;
    }
}
