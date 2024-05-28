package com.circle.circlemod.entity.projectile.ice;

import com.circle.circlemod.item.ModItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public class Ice extends ThrowableItemProjectile {
    public Ice(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Ice(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    public Ice(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel, Vec3 offset) {
        this(pEntityType, pShooter, pLevel);
        this.setPos(pShooter.getX() + offset.x, pShooter.getEyeY() + 0.1d + offset.y, pShooter.getZ() + offset.z);
    }


    @Override
    protected Item getDefaultItem() {
        return ModItems.ICE_SPIKE.get();
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        Level level = pResult.getEntity().level;
        pResult.getEntity().hurt(DamageSource.FREEZE, 4);
        this.remove(RemovalReason.KILLED);
        //播放破碎粒子
        super.onHitEntity(pResult);
    }
}
