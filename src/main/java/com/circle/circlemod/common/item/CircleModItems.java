package com.circle.circlemod.common.item;

import com.circle.circlemod.common.CircleMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CircleModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CircleMod.MODID);
    public static final RegistryObject<Item> CREATIVE_MOD_TAB_ITEM = registerItem("creative_mod_tab_item", () -> new Item(new Item.Properties()));

    public static RegistryObject<Item> registerItem(String name, final Supplier sup) {
        CircleMod.LOGGER.debug("register item -> {}", name);
        return ITEMS.register(name, sup);
    }

    public static void register(IEventBus iEventBus) {
        CircleMod.LOGGER.debug("register items...");
        ITEMS.register(iEventBus);
    }
}
