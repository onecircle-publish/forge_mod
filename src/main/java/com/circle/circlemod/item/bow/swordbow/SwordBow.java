package com.circle.circlemod.item.bow.swordbow;

import com.circle.circlemod.paticle.ModParticles;
import com.circle.circlemod.paticle.sword_bow.SwordBowSweep;
import com.circle.circlemod.paticle.sword_bow.SwordBowSweepParticleType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SwordBow extends BowItem {
    public SwordBow(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        super.releaseUsing(pStack, pLevel, pEntityLiving, pTimeLeft);

        if (!pLevel.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) pLevel;
            SwordBowSweepParticleType swordBowSweepParticleType = ModParticles.SWORD_BOW_SWEEP.get();
            swordBowSweepParticleType.setOwner(pEntityLiving);
            serverLevel.sendParticles(swordBowSweepParticleType, pEntityLiving.getX(), pEntityLiving.getY(0.5D), pEntityLiving.getZ(), 0, 0, 0.0D, 0, 0.0D);
        }
    }
}
