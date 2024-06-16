package com.circle.circlemod.paticle;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.paticle.charm.CharmParticle;
import com.circle.circlemod.paticle.freeze.FreezeParticle;
import com.circle.circlemod.paticle.sword_bow.SwordBowSweep;
import com.circle.circlemod.paticle.sword_bow.SwordBowSweepParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = CircleMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, CircleMod.MOD_ID);

    @SubscribeEvent
    public static void registerParticle(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.FREEZE_PARTICLE.get(), FreezeParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.CHARM_PARTICLE.get(), CharmParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.DOOM_PARTICLE.get(), CharmParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.SWORD_BOW_SWEEP.get(), SwordBowSweep.Provider::new);
    }

    /**
     * 冰冻粒子（雪花）
     */
    public static final RegistryObject<SimpleParticleType> FREEZE_PARTICLE = PARTICLE_TYPES.register("freeze_particle", () -> new SimpleParticleType(true));
    /**
     * 魅惑粒子（爱心）
     */
    public static final RegistryObject<SimpleParticleType> CHARM_PARTICLE = PARTICLE_TYPES.register("loving_particle", () -> new SimpleParticleType(true));
    /**
     * 毁灭菇粒子
     */
    public static final RegistryObject<SimpleParticleType> DOOM_PARTICLE = PARTICLE_TYPES.register("doom_particle", () -> new SimpleParticleType(true));
    public static final RegistryObject<SwordBowSweepParticleType> SWORD_BOW_SWEEP = PARTICLE_TYPES.register("sword_bow_sweep", () -> new SwordBowSweepParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
