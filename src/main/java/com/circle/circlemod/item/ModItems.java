package com.circle.circlemod.item;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.item.breathmask.BreathMask;
import com.circle.circlemod.item.kelp_soap.KelpSoap;
import com.circle.circlemod.item.staff.MagicStaff;
import com.circle.circlemod.item.sword.directionsword.DirectionSword;
import com.circle.circlemod.item.sword.kelp.KelpSwordItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CircleMod.MOD_ID);
    public static RegistryObject<Item> KELP_SWORD = ITEMS.register("kelp_sword", () -> new KelpSwordItem(ModTiers.KELP_SWORD, 4, 2, new Item.Properties().tab(ModCreativeModeTab.TEST_TAB)));
    public static RegistryObject<Item> KELP_SOAP = ITEMS.register("kelp_soap", () -> new KelpSoap(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.TEST_TAB)));
    public static RegistryObject<Item> MAGIC_STAFF = ITEMS.register("magic_staff", () -> new MagicStaff(new Item.Properties().stacksTo(1).tab(ModCreativeModeTab.TEST_TAB)));
    // 不同方向攻击的剑
    public static RegistryObject<Item> DIRECTION_REAR_SWORD = ITEMS.register("direction_rear_sword", () -> new DirectionSword(DirectionSword.Direction.REAR, Tiers.IRON, 3, -2.4F, new Item.Properties().tab(ModCreativeModeTab.TEST_TAB)));
    public static RegistryObject<Item> DIRECTION_LEFT_SWORD = ITEMS.register("direction_left_sword", () -> new DirectionSword(DirectionSword.Direction.LEFT, Tiers.IRON, 3, -2.4F, new Item.Properties().tab(ModCreativeModeTab.TEST_TAB)));
    public static RegistryObject<Item> DIRECTION_RIGHT_SWORD = ITEMS.register("direction_right_sword", () -> new DirectionSword(DirectionSword.Direction.RIGHT, Tiers.IRON, 3, -2.4F, new Item.Properties().tab(ModCreativeModeTab.TEST_TAB)));
    public static RegistryObject<Item> DIRECTION_SWORD = ITEMS.register("direction_sword", () -> new DirectionSword(DirectionSword.Direction.ALL, Tiers.IRON, 3, -2.4F, new Item.Properties().tab(ModCreativeModeTab.TEST_TAB)));
    // 念气罩
    public static RegistryObject<Item> BREATH_MASK = ITEMS.register("breath_mask", () -> new BreathMask(new Item.Properties().tab(ModCreativeModeTab.TEST_TAB)));

    public static RegistryObject<Item> registerItem(String name, final Supplier sup) {
        return ITEMS.register(name, sup);
    }

    public static void register(IEventBus iEventBus) {
        ITEMS.register(iEventBus);
    }
}
