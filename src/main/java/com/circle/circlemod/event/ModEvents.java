package com.circle.circlemod.event;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.paticle.FreezeParticle.FreezeParticle;
import com.circle.circlemod.paticle.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;


@Mod.EventBusSubscriber(modid = CircleMod.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            player.sendMessage(Component.nullToEmpty(player.getName().getString() + " hurt a " + event.getEntity().getName().getString()),
                    UUID.randomUUID());
        }
    }

    @SubscribeEvent
    public static void registerParticleFactories(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.FREEZE_PARTICLES.get(),
                FreezeParticle.Provider::new);
    }
}
