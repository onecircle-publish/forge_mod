package com.circle.circlemod.entity.funnel;

import com.circle.circlemod.entity.sheild.ShieldEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Optional;
import java.util.Random;

public class FunnelEntity extends LivingEntity implements IAnimatable {
    public static final AnimationBuilder BATTLE_ANI = new AnimationBuilder().addAnimation("battle", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    public static final AnimationBuilder BATTLE_HOLD_ANI = new AnimationBuilder().addAnimation("battle_hold", ILoopType.EDefaultLoopTypes.LOOP);
    public static final AnimationBuilder NORMAL_ANI = new AnimationBuilder().addAnimation("normal", ILoopType.EDefaultLoopTypes.LOOP);
    public static final AnimationBuilder BATTLE_TO_NORMAL = new AnimationBuilder().addAnimation("battle_to_normal", ILoopType.EDefaultLoopTypes.PLAY_ONCE);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    protected static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(FunnelEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> IS_BATTLE = SynchedEntityData.defineId(FunnelEntity.class, EntityDataSerializers.BOOLEAN);
    private final int changeRandomTick = 40;
    private final Random random = new Random();
    public Double randomValue = random.nextDouble();
    private int battleTargetId = 0;

    public FunnelEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
    }

    public static AttributeSupplier setAttributes() {

        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 4).build();
    }

    public Optional<Entity> getOnwer() {
        return Optional.ofNullable(this.level.getEntity(this.getEntityData().get(OWNER_ID)));
    }

    /**
     * 跟随Onwer，如果Owner存在
     */
    public void followOwner() {
        if (this.getEntityData().get(OWNER_ID) == 0) {
            Player player = this.level.getNearestPlayer(this, 10);
            if (player != null) {
                this.entityData.set(OWNER_ID, player.getId());
            }
        } else {
            Entity entity = this.level.getEntity(this.entityData.get(OWNER_ID));
            if (entity != null) {
                Vec3 pos = entity.getEyePosition();

                this.teleportTo(pos.x + randomValue, pos.y + 0.5, pos.z + randomValue);
                this.setYRot((float) (entity.getYRot() + randomValue));
            }
        }
    }

    public void changeRandom(int tickCount) {
        if (this.tickCount % changeRandomTick == 0) {
            this.randomValue = random.nextDouble();
        }
    }

    public LivingEntity getBattleTarget() {
        Entity entity = this.level.getEntity(this.battleTargetId);
        if (entity instanceof LivingEntity) {
            return (LivingEntity) this.level.getEntity(this.battleTargetId);
        } else {
            return null;
        }
    }

    /**
     * 判断目前处于的状态
     */
    public void checkMode() {
        Optional<Entity> onwer = getOnwer();
        if (onwer.isEmpty()) return;

        // battle mode
        if (onwer.get() instanceof LivingEntity livingEntity) {
            LivingEntity lastHurtMob = livingEntity.getLastHurtMob();

            if (lastHurtMob != null) {
                this.battleTargetId = lastHurtMob.getId();
            }
        }

        if (getBattleTarget() != null && getBattleTarget().isAlive()) {
            setIsBattle(true);
        } else {
            setIsBattle(false);
        }

        if (getIsBattle()) {
            doAttack();
        }
    }

    /**
     * 执行
     */
    public void doAttack() {
        LivingEntity battleTarget = getBattleTarget();
        if (battleTarget != null) {
            battleTarget.setLastHurtByMob(this);
            battleTarget.hurt(DamageSource.GENERIC, 2);
        }
    }

    /**
     * tickFollow
     */
    public void tickFollow() {
        changeRandom(this.tickCount);
        followOwner();
    }

    @Override
    public boolean canBeAffected(MobEffectInstance pEffectInstance) {
        return false;
    }

    @Override
    public boolean attackable() {
        return false;
    }

    @Override
    public void tick() {
        this.setDeltaMovement(new Vec3(0, 0, 0));
        super.tick();
        tickFollow();

        checkMode();
        // 如果拿起武器，进入战斗状态
    }

    public void setIsBattle(Boolean isBattle) {
        this.getEntityData().set(IS_BATTLE, isBattle);
    }

    public boolean getIsBattle() {
        return this.getEntityData().get(IS_BATTLE);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return NonNullList.withSize(4, ItemStack.EMPTY);
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
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "Mode", 0, this::modeController));
    }

    public <E extends ShieldEntity> PlayState modeController(final AnimationEvent<E> event) {
        Animation currentAnimation = event.getController().getCurrentAnimation();
        AnimationState animationState = event.getController().getAnimationState();

        //判断当前动画是否是BATTLE_ANI对应的动画
        if (currentAnimation == null) {
            event.getController().setAnimation(NORMAL_ANI);
            return PlayState.CONTINUE;
        }
        switch (currentAnimation.animationName) {
            case "normal":
                if (getIsBattle()) {
                    event.getController().setAnimation(BATTLE_ANI);
                }
                break;
            case "battle":
                if ((animationState == AnimationState.Stopped)) {
                    event.getController().setAnimation(BATTLE_HOLD_ANI);
                }
                break;
            case "battle_hold":
                if (!getIsBattle()) {
                    event.getController().setAnimation(BATTLE_TO_NORMAL);
                }
                break;
            case "battle_to_normal":
                if (animationState == AnimationState.Stopped) {
                    event.getController().setAnimation(NORMAL_ANI);
                }
                break;
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER_ID, 0);
        this.entityData.define(IS_BATTLE, false);
    }
}
