package com.circle.circlemod.event;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.paticle.charm.CharmParticle;
import com.circle.circlemod.paticle.freeze.FreezeParticle;
import com.circle.circlemod.paticle.ModParticles;
import net.minecraft.client.Minecraft;
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
}
