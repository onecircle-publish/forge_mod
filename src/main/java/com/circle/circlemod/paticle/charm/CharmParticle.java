package com.circle.circlemod.paticle.charm;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CharmParticle extends TextureSheetParticle {
    public CharmParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet set, double pXSpeed,
                         double pYSpeed,
                         double pZSpeed) {
        this(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.setSpriteFromAge(set);
        this.gravity = 0.8f;
        this.lifetime = 40;
    }


    public CharmParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed,
                         double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
    }

    public CharmParticle(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
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

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z
                , double dx, double dy, double dz) {
            return new CharmParticle(level, x, y, z, this.spriteSet, dx, dy, dz);
        }

    }
}
