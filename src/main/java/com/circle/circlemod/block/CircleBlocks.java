package com.circle.circlemod.block;

import com.circle.circlemod.block.charm.CharmBlock;
import com.circle.circlemod.block.freeze.FreezeBlock;
import com.circle.circlemod.block.freeze.FreezeMushroomBig;
import net.minecraftforge.registries.RegistryObject;

public class CircleBlocks {
    /**
     * 冰川菇
     */
    public static RegistryObject<FreezeBlock> FREEZE_BLOCK = ModBlocks.registerBlock("freeze_mushroom", () -> new FreezeBlock(), ModBlocks.tab);

    /**
     * 大冰川菇
     */
    public static RegistryObject<FreezeBlock> FREEZE_BLOCK_BIG = ModBlocks.registerBlock("freeze_mushroom_big", () -> new FreezeMushroomBig(30, 5), ModBlocks.tab);

    /**
     * 魅惑菇
     */
    public static RegistryObject<CharmBlock> CHARM_BLOCK = ModBlocks.registerBlock("charm_mushroom", () -> new CharmBlock(), ModBlocks.tab);

    public static void register() {
    }
}
