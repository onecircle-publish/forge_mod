package com.circle.circlemod.paticle.sword_bow;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

public class SwordBowSweepParticleType extends ParticleType<SwordBowSweepParticleType> implements ParticleOptions {
    private Entity onwer;
    private boolean isLeftDirection = false;//粒子从左边发射
    private int useTicks = 0;
    private int startYRot = 20; // 贴图适配的默认角度
    private int encPower = 0;//力量附魔

    public SwordBowSweepParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter, DESERIALIZER);
    }

    public SwordBowSweepParticleType(boolean pOverrideLimiter, Entity onwer, boolean isLeftDirection, int useTicks, int startYRot, int encPower) {
        this(pOverrideLimiter);
        this.onwer = onwer;
        this.isLeftDirection = isLeftDirection;
        this.useTicks = useTicks;
        this.startYRot = startYRot;
        this.encPower = encPower;
    }

    @Override
    public boolean getOverrideLimiter() {
        return super.getOverrideLimiter();
    }

    public void setOwner(Entity entity) {
        this.onwer = entity;
    }

    public Entity getOnwer() {
        return onwer;
    }

    public void setUseTicks(int useTicks) {
        this.useTicks = useTicks;
    }

    public int getUseTicks() {
        return useTicks;
    }

    public void setLeftDirection(boolean leftDirection) {
        isLeftDirection = leftDirection;
    }

    public boolean isLeftDirection() {
        return isLeftDirection;
    }

    public void setStartYRot(int startYRot) {
        this.startYRot = startYRot;
    }

    public int getStartYRot() {
        return startYRot;
    }

    public void setEncPower(int encPower) {
        this.encPower = encPower;
    }

    public int getEncPower() {
        return encPower;
    }

    private static final ParticleOptions.Deserializer<SwordBowSweepParticleType> DESERIALIZER = new ParticleOptions.Deserializer<SwordBowSweepParticleType>() {
        public SwordBowSweepParticleType fromCommand(ParticleType<SwordBowSweepParticleType> p_123846_, StringReader p_123847_) {
            return (SwordBowSweepParticleType) p_123846_;
        }

        public SwordBowSweepParticleType fromNetwork(ParticleType<SwordBowSweepParticleType> p_123849_, FriendlyByteBuf p_123850_) {
            return (SwordBowSweepParticleType) p_123849_;
        }
    };
    private final Codec<SwordBowSweepParticleType> codec = Codec.unit(this::getType);

    public SwordBowSweepParticleType getType() {
        return this;
    }

    public Codec<SwordBowSweepParticleType> codec() {
        return this.codec;
    }

    public void writeToNetwork(FriendlyByteBuf pBuffer) {
    }

    public String writeToString() {
        return Registry.PARTICLE_TYPE.getKey(this).toString();
    }
}
