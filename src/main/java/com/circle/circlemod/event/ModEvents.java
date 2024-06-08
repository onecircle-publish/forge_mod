package com.circle.circlemod.event;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.item.staff.MagicStaff;
import com.circle.circlemod.item.sword.directionsword.DirectionSword;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
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

    /**
     * DirectionSword
     * 左键点击空气 触发方向攻击
     *
     * @param event
     */
    @SubscribeEvent
    public static void playerInteractiveLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        ItemStack itemStack = event.getItemStack();

        Item useItem = itemStack.getItem();
        if (useItem instanceof DirectionSword) {
            DirectionSword.callDirectionLeftClick(event);
        }
    }

    /**
     * DirectionSword
     * 点击方块时触发方向攻击
     *
     * @param event
     */
    @SubscribeEvent
    public static void playerInteractiveLeftClickEmpty(PlayerInteractEvent.LeftClickBlock event) {
        ItemStack itemStack = event.getItemStack();
        Item useItem = itemStack.getItem();
        if (useItem instanceof DirectionSword) {
            DirectionSword.callDirectionLeftClick(event);
        }
    }

    /**
     * DirectionSword
     * 阻止默认攻击行为
     *
     * @param event
     */
    @SubscribeEvent
    public static void playerInteractiveLeftClickDefault(AttackEntityEvent event) {
        Player player = event.getPlayer();
        if (!player.getTags().contains(DirectionSword.PLAYER_INTERACT_EVENT_TAG) && player.getItemInHand(player.getUsedItemHand()).getItem() instanceof DirectionSword) {
            event.setCanceled(true);
        }
    }
}
