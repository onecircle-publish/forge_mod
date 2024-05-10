package com.circle.circlemod.effect.entangle;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * 缠绕效果
 * 让实体移动速度变成0（和FREZZE相同）
 * @author : yuanxin
 * @date : 2024-05-10 15:42
 **/
public class EntangleEffect extends MobEffect {
    public static final UUID entangleUUID = UUID.fromString("232BE823-1A65-4AB2-A7EE-DD50D593557E");


    public EntangleEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        AttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier modifier = attribute.getModifier(entangleUUID);
        if (modifier == null) {
            attribute.addPermanentModifier(new AttributeModifier(entangleUUID, "entangle", -1, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity livingEntity, AttributeMap attributeMap, int p_19471_) {
        AttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        attribute.removeModifier(entangleUUID);
        super.removeAttributeModifiers(livingEntity, attributeMap, p_19471_);
    }

}
