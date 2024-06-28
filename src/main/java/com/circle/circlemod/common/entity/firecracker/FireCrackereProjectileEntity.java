package com.circle.circlemod.common.entity.firecracker;

import com.circle.circlemod.common.item.CircleModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

/**
 * @author : yuanxin
 * @date : 2024-06-28 10:25
 **/
public class FireCrackereProjectileEntity extends ThrowableItemProjectile {
    public FireCrackereProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public FireCrackereProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public FireCrackereProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return CircleModItems.FIRE_CRACKER.get();
    }
}
