package com.circle.circlemod.effect;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.effect.charm.CharmEffect;
import com.circle.circlemod.effect.entangle.EntangleEffect;
import com.circle.circlemod.effect.freeze.FreezeEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CircleMod.MOD_ID);

    /**
     * 冰冻效果
     */
    public static final RegistryObject<FreezeEffect> FREEZE_EFFECT = EFFECTS.register("freeze", () -> new FreezeEffect(MobEffectCategory.HARMFUL, 3124687));

    /**
     * 魅惑效果
     */
    public static final RegistryObject<CharmEffect> CHARM_EFFECT = EFFECTS.register("charm", () -> new CharmEffect(MobEffectCategory.HARMFUL, Color.pink.getRGB()));

    /**
     * 缠绕效果
     */
    public static final RegistryObject<EntangleEffect> ENTANGLE_EFFECT = EFFECTS.register("charm", () -> new EntangleEffect(MobEffectCategory.HARMFUL, Color.green.getRGB()));

    public static void register(IEventBus iEventBus) {
        EFFECTS.register(iEventBus);
    }
}
