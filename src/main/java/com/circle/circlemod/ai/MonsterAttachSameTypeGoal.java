package com.circle.circlemod.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class MonsterAttachSameTypeGoal<T extends Monster> extends TargetGoal {
    private Mob mob;

    private Class<T> targetType;

    @Nullable
    protected Monster target;

    protected TargetingConditions targetConditions;

    public MonsterAttachSameTypeGoal(Mob pMob, Class<T> pTargetType, boolean pMustSee) {
        this(pMob, pTargetType, pMustSee, false, (Predicate<LivingEntity>) null);
    }

    public MonsterAttachSameTypeGoal(Mob pMob, Class<T> pTargetType, boolean pMustSee, boolean pMustReach) {
        this(pMob, pTargetType, pMustSee, pMustReach, (Predicate<LivingEntity>) null);
    }

    public MonsterAttachSameTypeGoal(Mob pMob, Class<T> pTargetType, boolean pMustSee, boolean pMustReach, @javax.annotation.Nullable Predicate<LivingEntity> pTargetPredicate) {
        super(pMob, pMustSee, pMustReach);
        this.targetType = pTargetType;
        this.mob = pMob;
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        this.targetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(pTargetPredicate);
    }

    public AABB getTargetSearchArea(double pTargetDistance) {
        return this.mob.getBoundingBox().inflate(pTargetDistance, 4.0D, pTargetDistance);
    }

    public double getFollowDistance() {
        return this.mob.getAttributeValue(Attributes.FOLLOW_RANGE);
    }

    @Override
    public boolean canUse() {
        return findTarget();
    }

    public boolean findTarget() {
        this.target = mob.level.getNearestEntity(this.mob.level.getEntitiesOfClass(this.targetType, this.getTargetSearchArea(this.getFollowDistance()), (v) -> {
            return true;
        }), this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        if (this.target != null) {
            return true;
        } else {
            this.setFlags(EnumSet.of(Flag.LOOK));
            return false;
        }
    }

    @Override
    protected boolean canAttack(@Nullable LivingEntity pPotentialTarget, TargetingConditions pTargetPredicate) {
        return super.canAttack(pPotentialTarget, pTargetPredicate);
    }

    @Override
    public void start() {
        this.mob.setTarget(this.target);
        super.start();
    }
}
