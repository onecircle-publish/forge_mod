package com.circle.circlemod.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * 标签组
 */
public class ModCreativeModeTab {
    public static final CreativeModeTab TEST_TAB = new CreativeModeTab("test_tab") {

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.TEST.get());
        }
    };
}
