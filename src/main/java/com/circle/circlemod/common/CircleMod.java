package com.circle.circlemod.common;

import com.mojang.logging.LogUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


/**
 * ForgeMod入口类
 *
 * @author yuanxin
 * @date 2024/11/17
 */
@Mod(CircleMod.MODID)
public class CircleMod {
    public static final String MODID = "circle";
    public static final Logger LOGGER = LogUtils.getLogger();


    /**
     * Circle Mod
     */
    public CircleMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();
        modEventBus.addListener(this::commonSetup);

        CircleModRegister.register(modEventBus);
    }

    /**
     * 通用设置
     *
     * @param event 事件
     */
    public void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.debug("FML common setup...");
    }
}
