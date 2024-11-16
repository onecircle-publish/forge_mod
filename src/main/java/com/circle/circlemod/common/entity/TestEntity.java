package com.circle.circlemod.common.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * 测试实体
 *
 * @author yuanxin
 * @date 2024/11/17
 */
public class TestEntity extends LivingEntity {
    /**
     * 测试实体
     *
     * @param p_20966_ 第 20966 页
     * @param p_20967_ 第 20967 页
     */
    protected TestEntity(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    /**
     * 获取装甲槽
     *
     * @return {@link Iterable }<{@link ItemStack }>
     */
    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    /**
     * 按槽位获取物品
     *
     * @param equipmentSlot 设备槽位
     * @return {@link ItemStack }
     */
    @Override
    public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
        return null;
    }

    /**
     * 设置物品槽
     *
     * @param equipmentSlot 设备槽位
     * @param itemStack     项堆栈
     */
    @Override
    public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return null;
    }
}
