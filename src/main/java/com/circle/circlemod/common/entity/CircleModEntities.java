package com.circle.circlemod.common.entity;

import com.circle.circlemod.common.CircleMod;
import com.circle.circlemod.common.entity.firecracker.FireCrackereProjectileEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author : yuanxin
 * @date : 2024-06-28 10:44
 **/
public class CircleModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CircleMod.MODID);

    public static final RegistryObject<EntityType<FireCrackereProjectileEntity>> FIRE_CRACKER =
            ENTITY_TYPES.register("fire_cracker_projectile", () -> EntityType.Builder.<FireCrackereProjectileEntity>of(FireCrackereProjectileEntity::new, MobCategory.MISC)
                    .sized(0.2f, 0.6f).build("fire_cracker_projectile"));



    public static void register(IEventBus eventBus) {
        CircleMod.LOGGER.info("register entities...");
        ENTITY_TYPES.register(eventBus);
    }
}
