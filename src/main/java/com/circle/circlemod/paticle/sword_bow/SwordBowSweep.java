package com.circle.circlemod.paticle.sword_bow;

import com.circle.circlemod.utils.CircleUtils;
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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class SwordBowSweep extends TextureSheetParticle {
    private final SpriteSet sprites;
    private Entity onwer;
    private double startXD;// 速度分量
    private double startYD;
    private double startZD;
    private double yRot; // 相对于计算后的初始角度位置的偏航角
    private double defaultYRot;
    private double startOwnerYRot;
    private double startOwnerXRot;
    private final int maxTicks = this.maxUseTicks + 40; // 最大飞行时间
    private int directionValue = 1;//左边发射为-1，右边1
    private int useTicks = 0; // 拉弓持续ticks
    private final int maxUseTicks = 100; // 拉弓效果增加的最大ticks
    private int updateTicks = 10; // 触发改变的tick数量
    private int encPower = 0;

    public SwordBowSweep(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet spriteSet, SwordBowSweepParticleType particleType) {
        this(pLevel, pX, pY, pZ, spriteSet, pXSpeed, pYSpeed, pZSpeed);

        this.useTicks = particleType.getUseTicks();
        this.defaultYRot = particleType.getStartYRot();
        this.onwer = particleType.getOnwer();
        this.encPower = particleType.getEncPower();

        boolean isLeftDirection = particleType.isLeftDirection();
        //是否粒子从左侧发射
        if (isLeftDirection) {
            directionValue = -1;
        }

        this.startOwnerYRot = 360 - this.onwer.getYRot() % 360;
        this.startOwnerXRot = 360 - this.onwer.getXRot() % 360;
        this.startXD = Math.sin(Math.toRadians(startOwnerYRot));
        this.startYD = Math.sin(Math.toRadians(startOwnerXRot));
        this.startZD = Math.cos(Math.toRadians(startOwnerYRot));

        this.yRot = defaultYRot;
        this.updateScale();
    }

    public SwordBowSweep(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet spriteSet, double pXSpeed, double pYSpeed, double pZSpeed) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
        this.friction = 0;
        this.gravity = 0;
        this.hasPhysics = false;
        this.lifetime = maxTicks;
        this.sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.quadSize = 1;
        this.alpha = 0.85F;
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        Vec3 vec3 = pRenderInfo.getPosition();
        float f = (float) (Mth.lerp((double) pPartialTicks, this.xo, this.x) - vec3.x());
        float f1 = (float) (Mth.lerp((double) pPartialTicks, this.yo, this.y) - vec3.y());
        float f2 = (float) (Mth.lerp((double) pPartialTicks, this.zo, this.z) - vec3.z());

        Quaternion quaternion = new Quaternion(Vector3f.YP, (float) (startOwnerYRot + yRot * directionValue), true);
        Quaternion quaternion2 = new Quaternion(Vector3f.XP, (float) (75 + startOwnerXRot), true);
        quaternion.mul(quaternion2);

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
        checkAttack();
        super.tick();

        double percent = (double) this.age / ((double) this.maxTicks / 2); // 0 - 1
        double power = (float) (Math.pow(getPowerModifier() * percent, 2) + 1);
        // 设置移动
        this.xd = startXD * power;
        this.yd = startYD * power;
        this.zd = startZD * power;

        // 旋转动画
        this.yRot = defaultYRot - defaultYRot * percent;

        // 计算下一步移动矢量
        this.startXD = Math.sin(Math.toRadians(startOwnerYRot + yRot * directionValue));
        this.startZD = Math.cos(Math.toRadians(startOwnerYRot + yRot * directionValue));
    }

    public void checkAttack() {
        ServerLevel serverWorld = CircleUtils.getServerSideWorld();
        List<LivingEntity> nearbyEntities = serverWorld.getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, null, this.getBoundingBox());
        if (!nearbyEntities.isEmpty()) {
            nearbyEntities.forEach(entity -> {
                entity.hurt(DamageSource.GENERIC, 8 + 2 * getPowerModifier());
            });
        }
    }

    /**
     * 设置力量属性改变的效果
     *
     * @return
     */
    public float getPowerModifier() {
        return (1 + ((float) this.encPower / Enchantments.POWER_ARROWS.getMaxLevel()) * 5);
    }

    /**
     * 设置不同拉取时间下的大小
     */
    public void updateScale() {
        int baseScale = 1;
        if (this.useTicks >= this.maxUseTicks) {
            baseScale = 20;
        }
        float scaleValue = baseScale * ((float) (((this.useTicks % maxUseTicks) / updateTicks) | 1)) * getPowerModifier();
        this.scale(scaleValue);
        this.setSize(2 * scaleValue, scaleValue);
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
            return new SwordBowSweep(level, x, y, z, dx, dy, dz, this.spriteSet, particleType);
        }
    }
}
