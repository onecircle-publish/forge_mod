package com.circle.circlemod.entity.sheild;

import com.circle.circlemod.CircleMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Team;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ShieldEntity extends LivingEntity implements IAnimatable {
    private static final EntityDataAccessor<Boolean> IS_SHOW = SynchedEntityData.defineId(ShieldEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public ShieldEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
    }

    public static AttributeSupplier setAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 4).build();
    }

    public void moveToBindLivingEntity(LivingEntity entity) {
        Vec3 position = entity.getPosition(1);
        CircleMod.LOGGER.debug("positon x:{},y:{},z{}", position.x, position.y, position.z);
        this.move(MoverType.SELF, new Vec3(position.x - this.getX(), position.y - this.getY(), position.z - this.getZ()));
    }

    @Override
    protected void doPush(Entity pEntity) {

    }

    @Override
    public void tick() {
        super.tick();
    }

    public void setIsShieldShow(boolean value) {
        this.entityData.set(IS_SHOW, value);
    }

    public boolean getIsShieldShow() {
        return this.entityData.get(IS_SHOW);
    }

    @Override
    public boolean isAlwaysTicking() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean skipAttackInteraction(Entity pEntity) {
        return true;
    }

    @Override
    public boolean attackable() {
        return false;
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        return false;
    }

    @Override
    public boolean canAttack(LivingEntity pLivingentity, TargetingConditions pCondition) {
        return false;
    }

    @Nullable
    @Override
    public Team getTeam() {
        PlayerTeam playersTeam = this.level.getScoreboard().getPlayersTeam(this.getScoreboardName());
        if (playersTeam != null) {
            playersTeam.setCollisionRule(Team.CollisionRule.PUSH_OTHER_TEAMS);
        }
        return playersTeam;
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_SHOW, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return null;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {

    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "rotate_controller", 0, this::rotateAnimation));
        data.addAnimationController(new AnimationController(this, "visible_controller", 0, this::visibleAnimation));
    }

    public <E extends ShieldEntity> PlayState rotateAnimation(final AnimationEvent<E> event) {
        AnimationController<E> controller = event.getController();

        if (controller.getCurrentAnimation() == null) {
            controller.setAnimation(new AnimationBuilder().addAnimation("rotate", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    /**
     * 动画文档 https://github.com/bernie-g/geckolib/wiki/Defining-Animations-in-Code-(Geckolib3)
     * 每次调用set，会重新刷新动画。所以要充分的判断，防止重复调用set
     *
     * @param event
     * @param <E>
     * @return
     */
    public <E extends ShieldEntity> PlayState visibleAnimation(final AnimationEvent<E> event) {
        AnimationController<E> controller = event.getController();

        if (controller.getCurrentAnimation() == null) {
            controller.markNeedsReload();
            controller.setAnimation(new AnimationBuilder().addAnimation("show", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }
        if (!getIsShieldShow()) {
            if (!(controller.getAnimationState() == AnimationState.Running)) {
                controller.markNeedsReload();
                return PlayState.STOP;
            }

            if (!controller.getCurrentAnimation().animationName.equals("disappear")) {
                controller.setAnimation(new AnimationBuilder().addAnimation("disappear", ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME));
            }
        } else {
            if (!controller.getCurrentAnimation().animationName.equals("show")) {
                controller.setAnimation(new AnimationBuilder().addAnimation("show", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            }
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
