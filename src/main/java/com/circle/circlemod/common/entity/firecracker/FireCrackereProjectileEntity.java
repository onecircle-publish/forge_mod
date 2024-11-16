package com.circle.circlemod.common.entity.firecracker;

import com.circle.circlemod.common.CircleDeferredRegister;
import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

/**
 * 打火机-实体-投掷物
 *
 * @author : yuanxin
 * @date 2024/11/17
 */
public class FireCrackereProjectileEntity extends ThrowableItemProjectile {
    /**
     *
     * @param pEntityType
     * @param pLevel
     */
    public FireCrackereProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /**
     *
     * @param pEntityType
     * @param pX
     * @param pY
     * @param pZ
     * @param pLevel
     */
    public FireCrackereProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    /**
     *
     * @param pEntityType
     * @param pShooter
     * @param pLevel
     */
    public FireCrackereProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }


    /**
     * 获取默认物品
     *
     * @return {@link Item }
     */
    @Override
    protected Item getDefaultItem() {
        return CircleDeferredRegister.getItemInstanceByResource(CircleModResources.FIRE_CRACKER).get();
    }
}
