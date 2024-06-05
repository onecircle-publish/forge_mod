package com.circle.circlemod;

import com.circle.circlemod.block.CircleBlocks;
import com.circle.circlemod.block.ModBlocks;
import com.circle.circlemod.effect.ModEffects;
import com.circle.circlemod.entity.ModEntities;
import com.circle.circlemod.event.ModEvents;
import com.circle.circlemod.item.ModItems;
import com.circle.circlemod.network.packs.CircleModNetworking;
import com.circle.circlemod.paticle.ModParticles;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(CircleMod.MOD_ID)
public class CircleMod {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "circlemod";

    public CircleMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModParticles.register(modEventBus);
        ModEffects.register(modEventBus);
        ModEntities.register(modEventBus);
        modEventBus.addListener(this::setup);

        GeckoLib.initialize();
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(CircleBlocks.CHARM_BLOCK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(CircleBlocks.DOOM_MUSHROOM.get(), RenderType.cutout());
        // 注册网络
        event.enqueueWork(()->{
            CircleModNetworking.registerMessage();
            MinecraftForge.EVENT_BUS.addListener(ModEvents::playerInteractiveLeftClickEmpty);
        });

    }
}
