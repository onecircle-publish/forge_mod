package com.circle.circlemod.paticle.shield;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author : yuanxin
 * @date : 2024-05-11 11:49
 **/
public class ShieldParticle extends TextureSheetParticle {
    public BlockPos startPos = null;

    protected ShieldParticle(ClientLevel p_108328_, double xCoord, double yCoord, double zCoord, SpriteSet spriteSet, double xd, double yd, double zd) {
        super(p_108328_, xCoord, yCoord, zCoord, xd, yd, zd);
        this.hasPhysics = false;
        this.gravity = 0f;
        startPos = new BlockPos(xCoord, yCoord, zCoord);//存储初始位置

        this.move(startPos.getX() + 2, startPos.getY(), startPos.getZ() + 2);
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
            return new ShieldParticle(level, x, y, z, this.spriteSet, dx, dy, dz);
        }
    }
}
