package com.circle.circlemod.paticle.doom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DoomParticle extends TextureSheetParticle {
    public DoomParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet set, double pXSpeed, double pYSpeed, double pZSpeed) {
        this(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.setSpriteFromAge(set);
        this.gravity = 0f;
        this.lifetime = 60;
        this.scale(1.5F);
        this.setParticleSpeed(pXSpeed, pYSpeed, pZSpeed);
        this.setPower(0.3F);
    }

    public DoomParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
    }

    public DoomParticle(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
    }

    public void fadeOut() {
        this.alpha = 1 - (age / lifetime);
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        public final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            return new DoomParticle(level, x, y, z, this.spriteSet, dx, dy, dz);
        }
    }
}
