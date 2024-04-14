package com.circle.circlemod;

import com.circle.circlemod.block.ModBlocks;
import com.circle.circlemod.effect.ModEffects;
import com.circle.circlemod.item.ModItems;
import com.circle.circlemod.paticle.ModParticles;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CircleMod.MOD_ID)
public class CircleMod {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "circlemod";

    public CircleMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.registry(modEventBus);
        ModParticles.registry(modEventBus);
        ModEffects.register(modEventBus);

        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {

    }
}
