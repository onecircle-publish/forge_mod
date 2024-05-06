package com.circle.circlemod.item;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.item.sword.kelp.KelpSword;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CircleMod.MOD_ID);
    public static RegistryObject<Item> KELP_SWORD = ITEMS.register("kelp_sword", () -> new SwordItem(ModTiers.KELP_SWORD, 4, 2, new Item.Properties().tab(ModCreativeModeTab.TEST_TAB)));

    public static RegistryObject<Item> registerItem(String name, final Supplier sup) {
        return ITEMS.register(name, sup);
    }

    public static void register(IEventBus iEventBus) {
        ITEMS.register(iEventBus);
    }
}
