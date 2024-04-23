package com.circle.circlemod.item;

import com.circle.circlemod.CircleMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CircleMod.MOD_ID);

    public static RegistryObject<Item> registerItem(String name, final Supplier sup) {
        return ITEMS.register(name, sup);
    }

    public static void register(IEventBus iEventBus) {
        ITEMS.register(iEventBus);
    }
}
