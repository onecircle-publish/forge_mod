package com.circle.circlemod.factory;

import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * 建造实体
 *
 * @author yuanxin
 * @date 2024/11/17
 */
public class BuildWithBlock<T extends Block> extends BuildObject<T> {

    Supplier<BlockItem> blockItemSupplier;
    Item.Properties itemProperties;


    public BuildWithBlock(CircleModResources resources) {
        super(resources);
    }

    /**
     * 获取物品属性
     *
     * @return {@link Item.Properties }
     */
    public Item.Properties getItemProperties() {
        return this.itemProperties;
    }

    /**
     * 获取块物品供应商
     *
     * @return {@link Supplier }<{@link BlockItem }>
     */
    public Supplier<BlockItem> getBlockItemSupplier() {
        return blockItemSupplier;
    }

    /**
     * 绑定供应商
     *
     * @param supplier 供应商
     * @return {@link BuildWithItem }
     */
    public BuildWithBlock<T> bindSupplier(Supplier<T> supplier, Item.Properties properties) {
        super.supplier = supplier;
        this.itemProperties = properties;
        return this;
    }

}
