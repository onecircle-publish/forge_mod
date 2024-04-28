package com.circle.circlemod.entity.block.charm;

import com.circle.circlemod.effect.ModEffects;
import com.circle.circlemod.entity.ModEntities;
import com.circle.circlemod.entity.block.doom.DoomMushroomEntity;
import com.circle.circlemod.paticle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CharmMushroomEntity extends Entity {
    private static final EntityDataAccessor<Integer> TICK = SynchedEntityData.defineId(DoomMushroomEntity.class, EntityDataSerializers.INT);

    private final int charmDuration = 60;

    private LivingEntity onwer;

    public CharmMushroomEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public CharmMushroomEntity(Level pLevel, BlockPos pos, @Nullable LivingEntity pOwner) {
        this(ModEntities.CHARM_MUSHROOM_ENTITY.get(), pLevel);
        this.setPos(pos.getX(), pos.getY(), pos.getZ());
        this.onwer = pOwner;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isEmptyBlock(this.blockPosition().below())) {
            this.remove(RemovalReason.DISCARDED);
        }
        int tick = this.getTick();
        if (tick < 0) {
            this.remove(RemovalReason.KILLED);
        }
        if (tick % 20 == 0) {
            doCharm(this.level, this.onwer, this.blockPosition());
        }
        setTick(tick - 1);
    }

    /**
     * 魅惑姑功能
     *
     * @param pLevel
     * @param pPlacer
     * @param pPos
     */
    public void doCharm(Level pLevel, LivingEntity pPlacer, BlockPos pPos) {
        List<LivingEntity> nearbyEntities = pLevel.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, pPlacer, new AABB(pPos).inflate(5));
        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (nearbyEntity instanceof Zombie) {
                nearbyEntity.addEffect(new MobEffectInstance(ModEffects.CHARM_EFFECT.get(), this.charmDuration));
            }
        }
        doParticles(nearbyEntities, pLevel, pPos);
    }

    public void doParticles(List<LivingEntity> entities, Level level, BlockPos pos) {
        ArrayList<Vec3> vec3s = new ArrayList<>();
        for (Entity entity : entities) {
            Vec3 position = entity.getPosition(1);
            vec3s.add(new Vec3(position.x - pos.getX(), position.y - pos.getY(), position.z - pos.getZ()));
        }
        vec3s.forEach(vec3 -> {
            level.addParticle(ModParticles.CHARM_PARTICLE.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, vec3.x, vec3.y, vec3.z);
        });
    }

    protected void defineSynchedData() {
        this.entityData.define(TICK, 80);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.setTick(pCompound.getShort("Tick"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putShort("Tick", (short) this.getTick());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setTick(int pLife) {
        this.entityData.set(TICK, pLife);
    }

    public int getTick() {
        return this.entityData.get(TICK);
    }
}
