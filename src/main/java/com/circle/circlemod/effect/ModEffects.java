package com.circle.circlemod.effect;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.effect.freezeEffect.FreezeEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,
            CircleMod.MOD_ID);

    public static final RegistryObject<FreezeEffect> freezeEffect = EFFECTS.register("freeze",
            () -> new FreezeEffect(MobEffectCategory.HARMFUL, 3124687));

    public static void register(IEventBus iEventBus) {
        EFFECTS.register(iEventBus);
    }
}
