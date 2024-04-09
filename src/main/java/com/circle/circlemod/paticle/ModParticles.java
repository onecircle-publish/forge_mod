package com.circle.circlemod.paticle;

import com.circle.circlemod.CircleMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CircleMod.MOD_ID);

    public static final RegistryObject<SimpleParticleType> FREEZE_PARTICLES =
            PARTICLE_TYPES.register("freeze_particles", () -> new SimpleParticleType(true));

    public static void registry(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
