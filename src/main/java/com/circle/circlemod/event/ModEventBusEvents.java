package com.circle.circlemod.event;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.entity.ModEntities;
import com.circle.circlemod.entity.block.charm.CharmMushroomEntityRenderer;
import com.circle.circlemod.entity.block.charm.CharmMushroomModel;
import com.circle.circlemod.entity.block.doom.DoomMushroomEntity;
import com.circle.circlemod.entity.block.doom.DoomMushroomEntityRenderer;
import com.circle.circlemod.entity.block.doom.DoomMushroomModel;
import com.circle.circlemod.paticle.charm.CharmParticle;
import com.circle.circlemod.paticle.freeze.FreezeParticle;
import com.circle.circlemod.paticle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CircleMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerParticleFactories(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.FREEZE_PARTICLE.get(), FreezeParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.CHARM_PARTICLE.get(), CharmParticle.Provider::new);
    }

    //注册模型渲染器
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.DOOM_MUSHROOM_ENTITY.get(), DoomMushroomEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.CHARM_MUSHROOM_ENTITY.get(), CharmMushroomEntityRenderer::new);
    }

    //注册模型贴图
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.<DoomMushroomEntity>registerLayerDefinition(DoomMushroomModel.LAYER_LOCATION, DoomMushroomModel::createBodyLayer);
        event.<DoomMushroomEntity>registerLayerDefinition(CharmMushroomModel.LAYER_LOCATION, CharmMushroomModel::createBodyLayer);
    }
}
