package com.circle.circlemod.item;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.item.kelp_soap.KelpSoap;
import com.circle.circlemod.item.staff.IceStaff;
import com.circle.circlemod.item.sword.kelp.KelpSwordItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CircleMod.MOD_ID);
    public static RegistryObject<Item> KELP_SWORD = ITEMS.register("kelp_sword", () -> new KelpSwordItem(ModTiers.KELP_SWORD, 4, 2, new Item.Properties().tab(ModCreativeModeTab.TEST_TAB)));
    public static RegistryObject<Item> KELP_SOAP = ITEMS.register("kelp_soap", () -> new KelpSoap(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.TEST_TAB)));
    public static RegistryObject<Item> ICE_STAFF = ITEMS.register("ice_staff", () -> new IceStaff(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.TEST_TAB)));

    public static RegistryObject<Item> registerItem(String name, final Supplier sup) {
        return ITEMS.register(name, sup);
    }

    public static void register(IEventBus iEventBus) {
        ITEMS.register(iEventBus);
    }
}
