package com.circle.circlemod.item.sword.directionsword;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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

            float yRot = attacker.getYRot() % 360;
            Vec3 playerPos = attacker.getPosition(1);

            entities.forEach(entity -> {
                // todo:计算正确的角度逻辑


//                if (!entity.is(attacker) && entity instanceof LivingEntity) {
//                    double rot = Math.abs(yRot + Math.toDegrees((entity.getX() - playerPos.x) / entity.getPosition(1).distanceTo(playerPos)));
//                    if (rot <= 225 && rot >= 135) {
//                        attacker.attack(entity);
//                    }
//
//                    double d0 = (double) (-Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)));
//                    double d1 = (double) Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F));
//
//                    ((ServerLevel) level).sendParticles(ParticleTypes.SWEEP_ATTACK, attacker.getX() + d0, attacker.getY(0.5D), attacker.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
//                }
            });
        } else {
            level.addParticle(ParticleTypes.SWEEP_ATTACK, attacker.getX(), attacker.getEyeY(), attacker.getZ(), 0, 0, 0);
        }
    }
}
