package com.circle.circlemod.block.freezeBlock;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.block.ModBlocks;
import com.circle.circlemod.item.ModCreativeModeTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.RegistryObject;

public class RegisterFreezeBlock {
    private static final CreativeModeTab tab = ModCreativeModeTab.TEST_TAB;

    public static RegistryObject<FreezeBlock> FREEZE_BLOCK = ModBlocks.registerBlock("freeze_mushroom",
            () -> new FreezeBlock(), tab);

    public static RegistryObject<FreezeBlock> FREEZE_BLOCK_BIG = ModBlocks.registerBlock("freeze_mushroom_big",
            () -> new FreezeMushroomBig(30), tab);

    public static void register() {
        CircleMod.LOGGER.debug("loaded freeze blocks");
    }
}
