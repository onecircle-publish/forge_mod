package com.circle.circlemod.effect.charm;

import com.circle.circlemod.ai.ZombieAttachSameTypeGoal;
import com.circle.circlemod.effect.ModEffects;
import com.circle.circlemod.paticle.ModParticles;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.phys.Vec3;

public class CharmEffect extends MobEffect {
    private TargetGoal attachSameTypeGoal;
    private LivingEntity livingEntity;
    private int currentDuration = 0;

    public CharmEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        //  判断效果存在期间执行逻辑的样例，可以判断是否存在魅惑菇的魅惑效果，然后给实体添加爱心粒子
        if (pLivingEntity instanceof Zombie) {
            handleZombieEntity(pLivingEntity, pAmplifier);
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        this.currentDuration = pDuration;
        return true;
    }


    /**
     * 作用于Zombie
     *
     * @param pLivingEntity
     * @param pAmplifier
     */
    public void handleZombieEntity(LivingEntity pLivingEntity, int pAmplifier) {
        // 效果即将消失，清除goal
        if (currentDuration == 1) {
            ((Zombie) pLivingEntity).targetSelector.removeGoal(this.attachSameTypeGoal);
            return;
        }

        if (livingEntity == null) {
            this.livingEntity = pLivingEntity;
        }

        if (attachSameTypeGoal == null) {
            //避免重复添加
            this.attachSameTypeGoal = new ZombieAttachSameTypeGoal<Zombie>((Zombie) pLivingEntity, Zombie.class,
                    false);
        }

        ((Zombie) pLivingEntity).targetSelector.removeGoal(this.attachSameTypeGoal);
        ((Zombie) pLivingEntity).targetSelector.addGoal(1,
                new ZombieAttachSameTypeGoal((Zombie) pLivingEntity,
                        Zombie.class, false));


        MobEffectInstance effect = pLivingEntity.getEffect(ModEffects.FREEZE_EFFECT.get());
        if ((effect != null) && effect.getDuration() != 0) {
            Vec3 pos = pLivingEntity.position();
            // 产生10个粒子，粒子移动会从脚部位移动到头部
            for (int i = 0; i < 10; i++) {
                pLivingEntity.level.addParticle(ModParticles.CHARM_PARTICLE.get(), pos.x, pos.y, pos.z, 0, 10,
                        0);
            }
        }
    }
}
