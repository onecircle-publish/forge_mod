package com.circle.circlemod.event;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.entity.ModEntities;
import com.circle.circlemod.entity.charm.CharmMushroomEntity;
import com.circle.circlemod.entity.charm.CharmMushroomEntityRenderer;
import com.circle.circlemod.entity.charm.CharmMushroomModel;
import com.circle.circlemod.entity.doom.DoomMushroomEntity;
import com.circle.circlemod.entity.doom.DoomMushroomEntityRenderer;
import com.circle.circlemod.entity.doom.DoomMushroomModel;
import com.circle.circlemod.entity.funnel.FunnelEntity;
import com.circle.circlemod.entity.funnel.FunnelEntityRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CircleMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    //注册模型渲染器
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.DOOM_MUSHROOM_ENTITY.get(), DoomMushroomEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.CHARM_MUSHROOM_ENTITY.get(), CharmMushroomEntityRenderer::new);
        event.registerEntityRenderer(ModEntities.FUNNEL_ENTITY.get(), FunnelEntityRenderer::new);
//        event.registerEntityRenderer(ModEntities.SHIELD_ENTITY.get(), ShieldEntityRenderer::new);
    }

    //注册模型贴图
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.<DoomMushroomEntity>registerLayerDefinition(DoomMushroomModel.LAYER_LOCATION, DoomMushroomModel::createBodyLayer);
        event.<CharmMushroomEntity>registerLayerDefinition(CharmMushroomModel.LAYER_LOCATION, CharmMushroomModel::createBodyLayer);
    }

    // 加载实体attributes
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
//        event.put(ModEntities.SHIELD_ENTITY.get(), ShieldEntity.setAttributes());
        event.put(ModEntities.FUNNEL_ENTITY.get(), FunnelEntity.setAttributes());
    }
}
