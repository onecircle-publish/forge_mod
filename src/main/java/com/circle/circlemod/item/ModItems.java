package com.circle.circlemod.item;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.item.ironFlower.IronFlower;
import com.circle.circlemod.item.ironFlower.IronFlowerRegister;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CircleMod.MOD_ID);

    public static final RegistryObject<Item> TEST = ITEMS.register("test",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.TEST_TAB)));

    public static RegistryObject<Item> registerItem(String name, final Supplier sup) {
        return ITEMS.register(name, sup);
    }

    public static void register(IEventBus iEventBus) {
        ITEMS.register(iEventBus);
    }
}
