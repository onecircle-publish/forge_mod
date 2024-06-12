package com.circle.circlemod.item.breathmask;

import com.circle.circlemod.paticle.ModParticles;
import com.circle.circlemod.paticle.breathmask.BreathMaskParticle;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * 念气罩
 */
public class BreathMask extends Item {
    public BreathMask(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            ServerLevel serverLevel = (ServerLevel) pLevel;

            double x = pPlayer.getX();
            double y = pPlayer.getY();
            double z = pPlayer.getZ();
            int radius = 10;

            for (int i = 0; i < 360; i += 1) {
                for (int j = 0; j < 180; j += 1) {
                    double xPos = x + radius * Math.cos(i) * Math.sin(j);
                    double yPos = y + radius * Math.sin(i) * Math.sin(j);
                    double zPos = z + radius * Math.cos(j);

                    double motionX = 0; // Set the motion for each particle
                    double motionY = 0;
                    double motionZ = 0;
                    pLevel.addParticle(ModParticles.BREATH_MASK_PARTICLE.get(), xPos, yPos, zPos, motionX, motionY, motionZ);
                    serverLevel.sendParticles(ModParticles.BREATH_MASK_PARTICLE.get(), xPos, yPos, zPos, 0, 0, 0, 0, 0);
                }
            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
