package com.circle.circlemod.item.staff;

import com.circle.circlemod.entity.ModEntities;
import com.circle.circlemod.entity.projectile.ice.Ice;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * 水晶权杖
 */
public class IceStaff extends ProjectileWeaponItem {
    public static final Vec3 LEFT = new Vec3(0, 0, 0);
    public static final Vec3 RIGHT = new Vec3(0, 0, 0);
    public static final Vec3 TOP = new Vec3(0, 0, 0);
    public static final Vec3 BOTTOM = new Vec3(0, 0, 0);

    public static final int LEVEL_1_TICK = 0;
    public static final int LEVEL_2_TICK = 40;
    public static final int LEVEL_3_TICK = 100;

    public SHOOT_LEVEL currentShootLevel = null;

    public enum SHOOT_LEVEL {
        LEVEL_1, LEVEL_2, LEVEL_3
    }


    public IceStaff(Properties pProperties) {
        super(pProperties);
        resetShootLevel();
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);
        setShootLevel(count);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        Player player = (Player) pLivingEntity;
        player.stopUsingItem();

        if (!pLevel.isClientSide) {
            shootByLevel(pLevel, pStack, player);
            resetShootLevel();
        }

        super.releaseUsing(pStack, pLevel, pLivingEntity, pTimeCharged);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.startUsingItem(InteractionHand.MAIN_HAND);
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }

    public ArrayList<Ice> createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        ArrayList<Ice> ices = new ArrayList<>();
        ices.add(new Ice(ModEntities.ICE.get(), shooter, level, LEFT));
        ices.add(new Ice(ModEntities.ICE.get(), shooter, level, RIGHT));
        ices.add(new Ice(ModEntities.ICE.get(), shooter, level, TOP));
        ices.add(new Ice(ModEntities.ICE.get(), shooter, level, BOTTOM));
        return ices;
    }

    public LivingEntity getEntityPlayerFacing() {
        Entity entity = Minecraft.getInstance().crosshairPickEntity;
        if (entity instanceof LivingEntity) {
            return (LivingEntity) entity;
        }
        return null;
    }

    /**
     * 根据不同发射等级选择不同的释放方式
     *
     * @param pLevel
     * @param pStack
     * @param player
     */
    public void shootByLevel(Level pLevel, ItemStack pStack, Player player) {
        if (this.currentShootLevel == SHOOT_LEVEL.LEVEL_1) {
            ArrayList<Ice> ices = createArrow(pLevel, pStack, player);
            LivingEntity entityPlayerFacing = getEntityPlayerFacing();
            shootIce(ices, pLevel, player, entityPlayerFacing);
        } else if (this.currentShootLevel == SHOOT_LEVEL.LEVEL_2) {

        } else if (this.currentShootLevel == SHOOT_LEVEL.LEVEL_3) {
            
        }


    }

    /**
     * LEVEL_1 发射子弹
     *
     * @param ices
     * @param pLevel
     * @param player
     * @param targetEntity
     */
    public void shootIce(ArrayList<Ice> ices, Level pLevel, Player player, @Nullable LivingEntity targetEntity) {
        if (targetEntity == null) {
            ices.forEach((ice) -> {
                ice.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.8F, 1.0F);
                pLevel.addFreshEntity(ice);
            });
        } else {
            // 目标不为null的情况
//            ices.forEach((ice) -> {
//                ice.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.8F, 1.0F);
//                pLevel.addFreshEntity(ice);
//            });
        }
    }

    public void setShootLevel(int ticks) {
        if (ticks > LEVEL_3_TICK) {
            currentShootLevel = SHOOT_LEVEL.LEVEL_3;
        } else if (ticks > LEVEL_2_TICK) {
            currentShootLevel = SHOOT_LEVEL.LEVEL_2;
        } else if (ticks > LEVEL_1_TICK) {
            currentShootLevel = SHOOT_LEVEL.LEVEL_1;
        }
    }

    public void resetShootLevel() {
        currentShootLevel = SHOOT_LEVEL.LEVEL_1;
    }

}
