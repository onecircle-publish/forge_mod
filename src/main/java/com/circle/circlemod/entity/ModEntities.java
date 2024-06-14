package com.circle.circlemod.entity;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.entity.charm.CharmMushroomEntity;
import com.circle.circlemod.entity.doom.DoomMushroomEntity;
import com.circle.circlemod.entity.funnel.FunnelEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, CircleMod.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, CircleMod.MOD_ID);
    public static final RegistryObject<EntityType<CharmMushroomEntity>> CHARM_MUSHROOM_ENTITY = ENTITIES.register("charm_mushroom", () -> EntityType.Builder.<CharmMushroomEntity>of(CharmMushroomEntity::new, MobCategory.MISC).sized(1f, 1f).build(new ResourceLocation(CircleMod.MOD_ID, "charm_mushroom").toString()));
    public static final RegistryObject<EntityType<DoomMushroomEntity>> DOOM_MUSHROOM_ENTITY = ENTITIES.register("doom_mushroom", () -> EntityType.Builder.<DoomMushroomEntity>of(DoomMushroomEntity::new, MobCategory.MISC).sized(1f, 2f).build(new ResourceLocation(CircleMod.MOD_ID, "doom_mushroom").toString()));
    // 浮游宠物
    public static final RegistryObject<EntityType<FunnelEntity>> FUNNEL_ENTITY = ENTITIES.register("funnel", () -> EntityType.Builder.<FunnelEntity>of(FunnelEntity::new, MobCategory.MISC).sized(0.2f, 0.2f).build(new ResourceLocation(CircleMod.MOD_ID, "funnel").toString()));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
        ENTITIES.register(eventBus);
    }
}
