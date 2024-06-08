package com.circle.circlemod.event;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.item.staff.MagicStaff;
import com.circle.circlemod.item.sword.directionsword.DirectionSword;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CircleMod.MOD_ID)
public class ModEvents {
    // 右键对着实体使用
    @SubscribeEvent
    public static void playerInteractive(PlayerInteractEvent.EntityInteractSpecific event) {
        InteractionHand usedItemHand = event.getPlayer().getUsedItemHand();
        Item itemInHand = event.getPlayer().getItemInHand(usedItemHand).getItem();
        Entity target = event.getTarget();

        // 交互时，使用的是权杖
        if (itemInHand instanceof MagicStaff) {
            ((MagicStaff) itemInHand).setInteractiveTarget(target);
        }
    }

    // 左键点击空气
    @SubscribeEvent
    public static void playerInteractiveLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack itemStack = event.getItemStack();

        Item useItem = itemStack.getItem();
        if (useItem instanceof DirectionSword) {
            DirectionSword.callDirectionLeftClick(event);
        }
    }

    @SubscribeEvent
    public static void playerInteractiveLeftClickEmpty(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack itemStack = event.getItemStack();
        Item useItem = itemStack.getItem();
        if (useItem instanceof DirectionSword) {
            DirectionSword.callDirectionLeftClick(event);
        }
    }
}
