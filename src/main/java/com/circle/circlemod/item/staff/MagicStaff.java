package com.circle.circlemod.item.staff;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

/**
 * 水晶权杖
 */
public class MagicStaff extends ProjectileWeaponItem {
    private Entity interactiveTarget = null;
    private final int CD = 10;
    private Player usePlayer = null;

    public MagicStaff(Properties pProperties) {
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
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 20;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack pStack, Level pLevel, @NotNull LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide) {
            Entity entity = pLevel.getEntity((interactiveTarget == null) ? -1 : interactiveTarget.getId());
            if (entity != null) {
                doMagic(entity);
            }
        }
        this.interactiveTarget = null;
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        usePlayer = pPlayer;
        pPlayer.startUsingItem(InteractionHand.MAIN_HAND);
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);
    }

    @Override
    public boolean useOnRelease(@NotNull ItemStack pStack) {
        if (usePlayer != null) {
            usePlayer.getCooldowns().addCooldown(this, CD);
        }
        return super.useOnRelease(pStack);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return super.useOn(pContext);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        pTooltipComponents.add(new TranslatableComponent("item.circlemod.magic_staff.text").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public void doMagic(Entity entity) {
        if (entity instanceof LivingEntity) {
            magicToLivingEntity((LivingEntity) entity);
        }
    }

    public void magicToLivingEntity(LivingEntity entity) {
        try {
            doSpecialLogic(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        doDefaultLogic(entity);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        return InteractionResult.PASS;
    }

    /**
     * 对特殊实体执行特殊逻辑
     *
     * @param entity
     * @return
     */
    public void doSpecialLogic(LivingEntity entity) throws IllegalAccessException {
        // 骷髅僵尸特殊逻辑
        if (entity instanceof AbstractSkeleton skeleton) {
            skeleton.reassessWeaponGoal();
            return;
        }

        // 苦力怕 - 丢出TNT
        if (entity instanceof Creeper) {
            dropItem(Items.TNT.getDefaultInstance(), entity);
            return;
        }

        // 末影人
        if (entity instanceof EnderMan) {
            EnderMan enderMan = (EnderMan) entity;
            dropItem(enderMan.getCarriedBlock().getBlock().asItem().getDefaultInstance(), enderMan);
            enderMan.setCarriedBlock(null);
            return;
        }

        if (entity instanceof Animal) {
            MagicStaffMagics.animals(entity);
        }
    }

    /**
     * 执行常规逻辑，丢弃某一个slot的物品
     */
    public void doDefaultLogic(LivingEntity entity) {
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
                dropItem(randomItem, entity);

                // 丢弃装备栏
                for (int i = LivingEntity.ARMOR_SLOT_OFFSET; i < LivingEntity.ARMOR_SLOT_OFFSET + EquipmentSlot.HEAD.getIndex(); i++) {
                    SlotAccess slot = entity.getSlot(i);
                    if (slot.get().is(randomItem.getItem())) {
                        slot.set(ItemStack.EMPTY);
                    }
                }

                // 丢弃主副手武器
                for (int i = LivingEntity.EQUIPMENT_SLOT_OFFSET; i < LivingEntity.EQUIPMENT_SLOT_OFFSET + EquipmentSlot.OFFHAND.getIndex(); i++) {
                    SlotAccess slot = entity.getSlot(i);
                    if (slot.get().is(randomItem.getItem())) {
                        slot.set(ItemStack.EMPTY);
                        if (i == EquipmentSlot.MAINHAND.getIndex() + LivingEntity.EQUIPMENT_SLOT_OFFSET) {
                            entity.swing(InteractionHand.MAIN_HAND);
                        } else {
                            entity.swing(InteractionHand.OFF_HAND);
                        }
                    }
                }
            }
        }
    }

    /**
     * 丢弃物品
     */
    public static void dropItem(ItemStack item, Entity entity) {
        ItemEntity itemEntity = entity.spawnAtLocation(item);
        Vec3 lookAngle = entity.getLookAngle();
        if (itemEntity != null) {
            itemEntity.setDeltaMovement(new Vec3(lookAngle.x, lookAngle.y, lookAngle.z));
        }
    }

    public void setInteractiveTarget(Entity entity) {
        interactiveTarget = entity;
    }
}
