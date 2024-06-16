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

    public void setOwner(Entity entity) {
        this.onwer = entity;
    }

    public Entity getOnwer() {
        return onwer;
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

    public SwordBowSweepParticleType(boolean pOverrideLimiter) {
        super(pOverrideLimiter, DESERIALIZER);
    }

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
