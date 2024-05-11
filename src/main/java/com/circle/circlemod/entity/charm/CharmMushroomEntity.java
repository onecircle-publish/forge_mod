package com.circle.circlemod.entity.charm;

import com.circle.circlemod.effect.ModEffects;
import com.circle.circlemod.entity.ModEntities;
import com.circle.circlemod.entity.doom.DoomMushroomEntity;
import com.circle.circlemod.paticle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CharmMushroomEntity extends Entity {
    private static final EntityDataAccessor<Integer> TICK = SynchedEntityData.defineId(CharmMushroomEntity.class, EntityDataSerializers.INT);
    private final int charmDuration = 100;//增加的效果持续时间
    private final int mushroomLifetime = 120;//魅惑菇存在时间
    private LivingEntity onwer;

    public CharmMushroomEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public CharmMushroomEntity(EntityType<? extends ItemFrame> pEntityType, Level pLevel, BlockPos pPos, Direction pDirection) {
        this(pEntityType, pLevel);
        this.setPos(pPos.getX(), pPos.getY(), pPos.getZ());
    }

    public CharmMushroomEntity(Level pLevel, BlockPos pos, @Nullable LivingEntity pOwner) {
        this(ModEntities.CHARM_MUSHROOM_ENTITY.get(), pLevel);
        this.setPos(pos.getX(), pos.getY(), pos.getZ());
        this.onwer = pOwner;
    }

    @Override
    public void tick() {
        if (this.level.isEmptyBlock(this.blockPosition().below())) {
            this.remove(RemovalReason.DISCARDED);
        }
        int tick = this.getTick();
        if (tick < 0) {
            this.remove(RemovalReason.KILLED);
        }
        if (tick % 40 == 0) {
            doCharm(this.level, this.onwer, this.blockPosition());
        }
        setTick(tick - 1);
        super.tick();
    }

    /**
     * 魅惑姑功能
     *
     * @param pLevel
     * @param pPlacer
     * @param pPos
     */
    public void doCharm(Level pLevel, LivingEntity pPlacer, BlockPos pPos) {
        List<Entity> entities = pLevel.getEntitiesOfClass(Entity.class, new AABB(pPos).inflate(10));
        entities.forEach(entity -> {
            if (entity instanceof DoomMushroomEntity) {
                ((DoomMushroomEntity) entity).markExplodeNextTick();
            }
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addEffect(new MobEffectInstance(ModEffects.CHARM_EFFECT.get(), this.charmDuration));
            }
        });
        doParticles(entities, pLevel, pPos);
    }

    public void doParticles(List<Entity> entities, Level level, BlockPos pos) {
        ArrayList<Vec3> vec3s = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity) {
                Vec3 position = entity.getPosition(1);
                vec3s.add(new Vec3(position.x - pos.getX(), position.y - pos.getY(), position.z - pos.getZ()));
            }
        }
        vec3s.forEach(vec3 -> {
            level.addParticle(ModParticles.CHARM_PARTICLE.get(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, vec3.x, vec3.y, vec3.z);
        });
    }

    protected void defineSynchedData() {
        this.entityData.define(TICK, mushroomLifetime);
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
