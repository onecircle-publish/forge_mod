package com.circle.circlemod.paticle.sword_bow;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SwordBowSweep extends TextureSheetParticle {
    private final SpriteSet sprites;
    private Entity onwer;
    private double startXD;
    private double startYD;
    private double startZD;
    private final double defaultYRot = 20;
    private double yRot;
    private double startOwnerYRot;
    private double startOwnerXRot;
    private int maxTicks;
    private int startFadeTime;

    public SwordBowSweep(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet spriteSet, Entity onwer) {
        this(pLevel, pX, pY, pZ, spriteSet, pXSpeed, pYSpeed, pZSpeed);
        this.onwer = onwer;
        this.startOwnerYRot = 360 - this.onwer.getYRot() % 360;
        this.startOwnerXRot = 360 - this.onwer.getXRot() % 360;
        this.startXD = Math.sin(Math.toRadians(startOwnerYRot));
        this.startYD = Math.sin(Math.toRadians(startOwnerXRot));
        this.startZD = Math.cos(Math.toRadians(startOwnerYRot));
        this.yRot = defaultYRot;
    }

    public SwordBowSweep(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.maxTicks = 80;
        this.friction = 0;
        this.gravity = 0;
        this.hasPhysics = false;
        this.lifetime = maxTicks;
        this.sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.setSize(5, 5);
        this.quadSize = 1;
        this.scale(5);
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        Vec3 vec3 = pRenderInfo.getPosition();
        float f = (float) (Mth.lerp((double) pPartialTicks, this.xo, this.x) - vec3.x());
        float f1 = (float) (Mth.lerp((double) pPartialTicks, this.yo, this.y) - vec3.y());
        float f2 = (float) (Mth.lerp((double) pPartialTicks, this.zo, this.z) - vec3.z());

        Quaternion quaternion = new Quaternion(Vector3f.ZP, (float) (startOwnerXRot), true);
        Quaternion quaternion2 = new Quaternion(Vector3f.YP, (float) (startOwnerYRot + yRot), true);
        Quaternion quaternion3 = new Quaternion(Vector3f.XP, (float) 75, true);
        quaternion.mul(quaternion2);
        quaternion.mul(quaternion3);

//        if (this.roll == 0.0F) {
//            quaternion = pRenderInfo.rotation();
//        } else {
//            quaternion = new Quaternion(pRenderInfo.rotation());
//            float f3 = Mth.lerp(pPartialTicks, this.oRoll, this.roll);
//            quaternion.mul(Vector3f.ZP.rotation(f3));
//        }
//
//        Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
//        vector3f1.transform(quaternion);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        float f4 = this.getQuadSize(pPartialTicks);

        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.transform(quaternion);
            vector3f.mul(f4);
            vector3f.add(f, f1, f2);
        }

        float f7 = this.getU0();
        float f8 = this.getU1();
        float f5 = this.getV0();
        float f6 = this.getV1();
        int j = this.getLightColor(pPartialTicks);
        pBuffer.vertex((double) avector3f[0].x(), (double) avector3f[0].y(), (double) avector3f[0].z()).uv(f8, f6).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        pBuffer.vertex((double) avector3f[1].x(), (double) avector3f[1].y(), (double) avector3f[1].z()).uv(f8, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        pBuffer.vertex((double) avector3f[2].x(), (double) avector3f[2].y(), (double) avector3f[2].z()).uv(f7, f5).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
        pBuffer.vertex((double) avector3f[3].x(), (double) avector3f[3].y(), (double) avector3f[3].z()).uv(f7, f6).color(this.rCol, this.gCol, this.bCol, this.alpha).uv2(j).endVertex();
    }

    @Override
    public void tick() {
        super.tick();
        double x = (double) this.age / this.maxTicks; // 0 - 1
        double rot = x - Math.sin(20); //旋转角度 20度 - 90度
        double power = (float) (Math.pow(x, 2) * 5 + 1);
        // 设置移动
        this.xd = startXD * power;
        this.yd = startYD * power;
        this.zd = startZD * power;

        if (this.age <= this.maxTicks - 40) {
            // 旋转动画
            this.yRot = Math.toDegrees(Math.asin(rot));

            // 计算下一步移动矢量
            this.startXD = Math.sin(Math.toRadians(startOwnerYRot + yRot));
            this.startZD = Math.cos(Math.toRadians(startOwnerYRot + yRot));
        } else {
            // 计算下一步移动矢量
            this.startXD = Math.sin(Math.toRadians(startOwnerYRot + Math.toDegrees(Math.asin(rot))));
            this.startZD = Math.cos(Math.toRadians(startOwnerYRot + Math.toDegrees(Math.asin(rot))));
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        ParticleRenderType PARTICLE_SHEET_TRANSLUCENT = new ParticleRenderType() {
            public void begin(BufferBuilder p_107455_, TextureManager p_107456_) {
                RenderSystem.depthMask(true);
                RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                RenderSystem.disableCull();//禁用背面剔除
                p_107455_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
            }

            public void end(Tesselator p_107458_) {
                p_107458_.end();
            }

            public String toString() {
                return "PARTICLE_SHEET_TRANSLUCENT";
            }
        };
        return PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SwordBowSweepParticleType> {
        public final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SwordBowSweepParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            return new SwordBowSweep(level, x, y, z, dx, dy, dz, this.spriteSet, particleType.getOnwer());
        }
    }
}
