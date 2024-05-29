package com.circle.circlemod.item.staff;

import com.circle.circlemod.CircleMod;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

/**
 * 水晶权杖
 */
public class IceStaff extends ProjectileWeaponItem {
    private Entity interactiveTarget = null;

    public IceStaff(Properties pProperties) {
        super(pProperties);
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
        return 20;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        doMagic();
        this.interactiveTarget = null;
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.startUsingItem(InteractionHand.MAIN_HAND);
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public boolean useOnRelease(ItemStack pStack) {

        return super.useOnRelease(pStack);
    }

    /**
     * 获取准心所指实体
     *
     * @return
     */
    public Entity getEntityCrosshairPick() {
        return Minecraft.getInstance().crosshairPickEntity;
    }

    public void doMagic() {
        Entity entityCrosshairPick = getEntityCrosshairPick();

        if (entityCrosshairPick instanceof LivingEntity) {
            magicToLivingEntity((LivingEntity) entityCrosshairPick);
        }
    }

    public void magicToLivingEntity(LivingEntity entity) {
        List<ItemStack> allItems = new ArrayList<>();

        // 加入丢弃列表
        Iterable<ItemStack> allSlots = entity.getAllSlots();
        allSlots.forEach(slot -> {
            ItemStack item = slot.getItem().getDefaultInstance();
            if (!item.isEmpty()) {
                allItems.add(item);
            }
        });
        // 随机丢弃一个物品
        if (!allItems.isEmpty()) {
            ItemStack randomItem = allItems.get(new Random().nextInt(allItems.size()));

            if (this.interactiveTarget != null) {
                ItemEntity itemEntity = interactiveTarget.spawnAtLocation(randomItem, 0);
                Vec3 lookAngle = entity.getLookAngle();
                itemEntity.setDeltaMovement(new Vec3(lookAngle.x, lookAngle.y, lookAngle.z));

//                if (entity instanceof Mob) {
//                    ((Mob) entity).goalSelector.getAvailableGoals().forEach(goal -> {
//                        Goal currentGoal = goal.getGoal();
//                        if (currentGoal instanceof RangedBowAttackGoal<?>) {
//                            ((Mob) entity).goalSelector.removeGoal(goal);
//                        }
//                    });
//                }

                SlotAccess mainHand = entity.getSlot(EquipmentSlot.MAINHAND.getIndex() + LivingEntity.EQUIPMENT_SLOT_OFFSET);
                SlotAccess offHand = entity.getSlot(EquipmentSlot.OFFHAND.getIndex() + LivingEntity.EQUIPMENT_SLOT_OFFSET);
                if (mainHand.get().is(randomItem.getItem())) {
                    entity.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    entity.swing(InteractionHand.MAIN_HAND);
                }
                if (offHand.get().is(randomItem.getItem())) {
                    entity.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                    entity.swing(InteractionHand.OFF_HAND);
                }
            }
        }
    }

    public void setInteractiveTarget(Entity entity) {
        interactiveTarget = entity;
    }
}
