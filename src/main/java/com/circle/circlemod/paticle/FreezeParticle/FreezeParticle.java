package com.circle.circlemod.paticle.FreezeParticle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FreezeParticle extends TextureSheetParticle {
    private int startFadeTime;

    protected FreezeParticle(ClientLevel p_108328_, double xCoord, double yCoord, double zCoord,
                             SpriteSet spriteSet, double xd, double yd, double zd) {
        super(p_108328_, xCoord, yCoord, zCoord, xd, yd, zd);
        this.gravity = 0.4f;
        this.quadSize *= 0.85f;
        this.setSpriteFromAge(spriteSet);

        this.lifetime = 100;
        this.rCol = 1f;
        this.gCol = 1f;
        this.bCol = 1f;
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
    }


    public void fadeOut() {
        if (this.age >= lifetime - lifetime * 0.5) {
            if (startFadeTime == 0) {
                startFadeTime = this.age;
            }
            this.alpha =
                    (-((1 / (float) startFadeTime) * (float) (age - (float) lifetime * 0.5)) + 1);

        }
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
            return new FreezeParticle(level, x, y, z, this.spriteSet, dx, dy, dz);
        }

    }
}
