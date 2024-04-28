package com.circle.circlemod.entity.block.doom;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.entity.ModEntities;
import com.circle.circlemod.paticle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class DoomMushroomEntity extends Entity {
    private static final EntityDataAccessor<Integer> FUSE = SynchedEntityData.defineId(DoomMushroomEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> EXPLODED = SynchedEntityData.defineId(DoomMushroomEntity.class, EntityDataSerializers.BOOLEAN);

    public DoomMushroomEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.blocksBuilding = true;
    }

    public DoomMushroomEntity(Level pLevel, BlockPos pos, @Nullable LivingEntity pOwner) {
        this(ModEntities.DOOM_MUSHROOM_ENTITY.get(), pLevel);
        this.setPos(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void tick() {
        int fuse = this.getFuse();
        CircleMod.LOGGER.debug("DoomMushroomEntity fuse: {}", fuse);
        int i = fuse - 1;
        this.setFuse(i);
        if (i <= 0) {
            // 服务端销毁后客户端就不会tick了，所以用exploded字段来保证 客户端执行完操作后再在服务端进行销毁
            if (getExploded()) {
                this.discard();
                doServerExplode();
            }
            doParticle();
        }
    }

    public void doServerExplode() {
        if (!this.level.isClientSide) {
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 10, false, Explosion.BlockInteraction.BREAK);
        }
    }

    public void doParticle() {
        if (level.isClientSide) {
            for (int i = -50; i < 50; i++) {
                level.addParticle(ModParticles.FREEZE_PARTICLE.get(), this.getX() + i, this.getY(), this.getZ(), 0, 20, 0);
            }
        }
        this.setExploded(true);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(FUSE, 40);
        this.entityData.define(EXPLODED, false);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setFuse(pCompound.getShort("Fuse"));
        this.setExploded(pCompound.getBoolean("Exploded"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putShort("Fuse", (short) this.getFuse());
        pCompound.putBoolean("Exploded", this.getExploded());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setFuse(int pLife) {
        this.entityData.set(FUSE, pLife);
    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuse() {
        return this.entityData.get(FUSE);
    }

    public boolean getExploded() {
        return this.entityData.get(EXPLODED);
    }

    public void setExploded(boolean exploded) {
        this.entityData.set(EXPLODED, exploded);
    }
}
