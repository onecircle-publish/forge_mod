package com.circle.circlemod.event;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.ai.ZombieAttachSameTypeGoal;
import com.circle.circlemod.effect.ModEffects;
import com.circle.circlemod.paticle.ModParticles;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.ZombieEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;


@Mod.EventBusSubscriber(modid = CircleMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            player.sendMessage(Component.nullToEmpty(player.getName().getString() + " hurt a " + event.getEntity().getName().getString()),
                    UUID.randomUUID());
        }
    }


    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent e) {
//        判断效果存在期间执行逻辑的样例，可以判断是否存在魅惑菇的魅惑效果，然后给实体添加爱心粒子
//        LivingEntity entityLiving = e.getEntityLiving();
//        if (entityLiving instanceof Zombie) {
//            MobEffectInstance effect = entityLiving.getEffect(ModEffects.freezeEffect.get());
//            if ((effect != null) && effect.getDuration() != 0) {
//                Vec3 pos = entityLiving.position();
//                // 产生10个粒子，粒子移动会从脚部位移动到头部
//                for (int i = 0; i < 10; i++) {
//                    entityLiving.level.addParticle(ModParticles.FREEZE_PARTICLES.get(), pos.x, pos.y, pos.z, 0, 10,
//                    0);
//                }
//            }
//        }
    }

    @SubscribeEvent
    public static void onZombieEvent(ZombieEvent e) {
        Zombie summoner = e.getSummoner();
        summoner.targetSelector.addGoal(1, new ZombieAttachSameTypeGoal(summoner, Zombie.class, false));
    }
}
