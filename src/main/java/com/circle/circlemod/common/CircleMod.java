package com.circle.circlemod.common;

import com.circle.circlemod.common.item.CircleModCreativeTabs;
import com.circle.circlemod.common.item.CircleModItems;
import com.mojang.logging.LogUtils;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CircleMod.MODID)
public class CircleMod {
    public static final String MODID = "circle";
    public static final Logger LOGGER = LogUtils.getLogger();


    public CircleMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();
        modEventBus.addListener(this::commonSetup);
        CircleModCreativeTabs.register(modEventBus); // 创造模式类目
        CircleModItems.register(modEventBus); // 物品
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.debug("FML common setup...");
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }
}
