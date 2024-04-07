package com.circle.circlemod.block;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.items.ModCreativeModeTab;
import com.circle.circlemod.items.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CircleMod.MOD_ID);

    public static RegistryObject<Block> TEST_BLOCK = registryBlock("test_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.TEST_TAB);

    private static <T extends Block> RegistryObject<T> registryBlock(String name, Supplier<T> block,
                                                                     CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registryBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> void registryBlockItem(String name, RegistryObject<T> block,
                                                            CreativeModeTab tab) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void registry(IEventBus eventBus) {
        BLOCKS.register(eventBus);

    }
}
