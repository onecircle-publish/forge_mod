package com.circle.circlemod.event;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.item.staff.MagicStaff;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CircleMod.MOD_ID)
public class ModEvents {
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
}
