package com.circle.circlemod.item;

import com.circle.circlemod.block.CircleBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * 标签组
 */
public class ModCreativeModeTab {
    public static final CreativeModeTab TEST_TAB = new CreativeModeTab("tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(CircleBlocks.FREEZE_BLOCK.get());
        }
    };
}
