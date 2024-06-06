package com.circle.circlemod.item.sword.directionsword;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;

import java.util.List;

/**
 * @author : yuanxin
 * @date : 2024-06-03 17:02
 **/
public class DirectionSword extends SwordItem {
    public DirectionSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return super.onLeftClickEntity(stack, player, entity);
    }

    /**
     * 攻击后方
     */
    public void useDirectionHurt(Level level, ItemStack useItem, Player attacker) {
        if (!level.isClientSide) {
            double attackRange = attacker.getAttackRange();
            List<Entity> entities = level.getEntities(attacker, new AABB(attacker.getOnPos()).inflate(attackRange));

            // 计算玩家朝向和实体之间的夹角
            // yRot为2PI - 视角角度（0-2PI），这里需要游戏中视角向左的时候为逆时针，实际上靠左的坐标才是正坐标，所以用2PI去减
            // vecAttacker 是玩家 到 玩家朝向的一个点 的向量，也就是玩家指向 玩家坐标向玩家朝向延长一个单位的地方 的向量。
            float yRot = (float) (Math.toDegrees(Math.PI * 2) - (float) (Math.toDegrees(Math.PI * 2) + attacker.getYRot() % 360));
            Vec2 vecAttacker = new Vec2(((float) Math.sin(Math.toRadians(yRot))), (float) Math.cos(Math.toRadians(yRot)));
            entities.forEach(entity -> {

                // 这里求出玩家指向实体的向量
                Vec2 vecTargetToAttacker = new Vec2((float) (entity.getX() - attacker.getX()), (float) (entity.getZ() - attacker.getZ()));

                // vecAttacker 和 vecTargetToAttacker的夹角。点积除以长度之和的反余弦，然后转角度
                double angle = Math.toDegrees(Math.acos(vecAttacker.dot(vecTargetToAttacker) / (vecAttacker.length() * vecTargetToAttacker.length())));

                if (!(entity instanceof Player) && entity instanceof LivingEntity) {
                    if (angle <= 225 && angle >= 135) {
                        attacker.attack(entity);
                    }

                    double d0 = (double) (-Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)));
                    double d1 = (double) Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F));
                    ((ServerLevel) level).sendParticles(ParticleTypes.SWEEP_ATTACK, attacker.getX() + d0, attacker.getY(0.5D), attacker.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
                }
            });
        } else {
            level.addParticle(ParticleTypes.SWEEP_ATTACK, attacker.getX(), attacker.getEyeY(), attacker.getZ(), 0, 0, 0);
        }
    }
}
