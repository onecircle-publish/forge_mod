package com.circle.circlemod.item.sword.directionsword;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/**
 * @author : yuanxin
 * @date : 2024-06-03 17:02
 **/
public class DirectionSword extends SwordItem {
    public DirectionSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    /**
     * 攻击后方
     */
    public void useDirectionHurt(ItemStack useItem, Player attacker, LivingEntity target) {
        float yRot = attacker.getYRot();
        attacker.level.addParticle(ParticleTypes.SWEEP_ATTACK, attacker.getX(), attacker.getY(), attacker.getZ(), 0.0D, 0.0D, 0.0D);
        this.hurtEnemy(useItem, target, attacker);
    }
}
