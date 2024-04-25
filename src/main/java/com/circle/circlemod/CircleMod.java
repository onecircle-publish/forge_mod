package com.circle.circlemod;

import com.circle.circlemod.block.CircleBlocks;
import com.circle.circlemod.block.ModBlocks;
import com.circle.circlemod.effect.ModEffects;
import com.circle.circlemod.item.ModItems;
import com.circle.circlemod.paticle.ModParticles;
import com.google.gson.JsonDeserializationContext;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperty;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.client.model.generators.loaders.OBJLoaderBuilder;
import net.minecraftforge.client.model.obj.OBJLoader;
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
        ModBlocks.register(modEventBus);
        ModParticles.register(modEventBus);
        ModEffects.register(modEventBus);
        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(CircleBlocks.CHARM_BLOCK.get(), RenderType.cutout());
    }
}
