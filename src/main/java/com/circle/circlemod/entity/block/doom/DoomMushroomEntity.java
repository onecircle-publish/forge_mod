package com.circle.circlemod.entity.block.doom;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.entity.ModEntities;
import com.circle.circlemod.paticle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class DoomMushroomEntity extends Entity {
    private static final int EXPLODE_TIME = 100;

    private static final EntityDataAccessor<Integer> FUSE = SynchedEntityData.defineId(DoomMushroomEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Boolean> EXPLODED = SynchedEntityData.defineId(DoomMushroomEntity.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> EXPLODE_NEXT_TICK = SynchedEntityData.defineId(DoomMushroomEntity.class, EntityDataSerializers.BOOLEAN);

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
        if (getExplodedNextTick()) {
            doDoom();
            return;
        }
        int fuse = this.getFuse();
        CircleMod.LOGGER.debug("DoomMushroomEntity fuse: {}", fuse);
        int i = fuse - 1;
        this.setFuse(i);
        if (i <= 0) {
            doDoom();
        }
    }

    public void doDoom() {
        if (!this.level.isClientSide) {
            this.discard();
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 20, false, Explosion.BlockInteraction.BREAK);
            doParticle();
        }
    }

    public void doParticle() {
        if (!level.isClientSide) {
            ((ServerLevel) level).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), 3000, 10, 0, 10, 0.5);
        }
    }

    /**
     * 标记在下一个tick开始触发爆炸
     */
    public void markExplodeNextTick() {
        this.setExplodeNextTick(true);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(FUSE, EXPLODE_TIME);
        this.entityData.define(EXPLODED, false);
        this.entityData.define(EXPLODE_NEXT_TICK, false);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setFuse(pCompound.getShort("Fuse"));
        this.setExploded(pCompound.getBoolean("Exploded"));
        this.setExploded(pCompound.getBoolean("ExplodedNextTick"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putShort("Fuse", (short) this.getFuse());
        pCompound.putBoolean("Exploded", this.getExploded());
        pCompound.putBoolean("ExplodedNextTick", this.getExplodedNextTick());
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

    public boolean getExplodedNextTick() {
        return this.entityData.get(EXPLODE_NEXT_TICK);
    }

    public void setExplodeNextTick(boolean exploded) {
        this.entityData.set(EXPLODE_NEXT_TICK, exploded);
    }
}
