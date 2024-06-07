package com.circle.circlemod.item.sword.directionsword;

import com.circle.circlemod.utils.CircleUtils;
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

import java.util.List;

/**
 * @author : yuanxin
 * @date : 2024-06-03 17:02
 **/
public class DirectionSword extends SwordItem {
    private Direction direction = null;

    protected DirectionSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public DirectionSword(Direction direction, Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        this(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.direction = direction;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return false;
    }

    /**
     * 攻击后方
     */
    public void useDirectionHurt(Level level, ItemStack useItem, Player attacker) {
        if (!level.isClientSide) {
            double attackRange = attacker.getAttackRange();
            List<Entity> entities = level.getEntities(attacker, new AABB(attacker.getOnPos()).inflate(attackRange));

            entities.forEach(entity -> {
                double angle = CircleUtils.angleBetweenPlayerAndEntity(attacker, entity);

                if (!(entity instanceof Player) && entity instanceof LivingEntity) {
                    if (isInAttackRange(angle)) {
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

    public boolean isInAttackRange(double angle) {
        if (this.direction == Direction.REAR) {
            return angle <= 225 && angle >= 135;
        } else if (this.direction == Direction.LEFT) {
            return angle <= 135 && angle >= 45;
        } else if (this.direction == Direction.RIGHT) {
            return angle <= 315 && angle >= 225;
        }
        return false;
    }

    public enum Direction {
        LEFT,
        RIGHT,
        REAR
    }
}
