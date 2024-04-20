package com.circle.circlemod.effect.freezeEffect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;

import java.util.UUID;

public class    FreezeEffect extends MobEffect {
    public static final UUID freezeUUID = UUID.fromString("D41C3A45-9CB2-392E-F75A-8ADD94C21DFF");

    public FreezeEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }


    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        AttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);

        AttributeModifier modifier = attribute.getModifier(freezeUUID);
        if (modifier == null) {
            attribute.addPermanentModifier(new AttributeModifier(freezeUUID, "freeze", -1,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));
        }

        super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int p_19471_) {
        AttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        attribute.removeModifier(freezeUUID);
        super.removeAttributeModifiers(livingEntity, attributeMap, p_19471_);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return true;
    }


    @Override
    public void addAttributeModifiers(LivingEntity livingEntity, AttributeMap attributeMap, int p_19480_) {
        super.addAttributeModifiers(livingEntity, attributeMap, p_19480_);
    }


}
