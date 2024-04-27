package com.circle.circlemod.entity;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.entity.block.doom.DoomMushroomEntity;
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

    public static final RegistryObject<EntityType<DoomMushroomEntity>> DOOM_MUSHROOM_ENTITY = ENTITIES.register("doom_mushroom", () -> EntityType.Builder.<DoomMushroomEntity>of(DoomMushroomEntity::new, MobCategory.MISC).sized(1f, 2f).build("doom_mushroom"));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
        ENTITIES.register(eventBus);
    }
}
