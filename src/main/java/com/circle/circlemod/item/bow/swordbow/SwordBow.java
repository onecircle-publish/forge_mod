package com.circle.circlemod.item.bow.swordbow;

import com.circle.circlemod.paticle.ModParticles;
import com.circle.circlemod.paticle.sword_bow.SwordBowSweepParticleType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.LinkedList;

public class SwordBow extends BowItem {
    public SwordBow(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        super.releaseUsing(pStack, pLevel, pEntityLiving, pTimeLeft);
        int useTicks = getUseDuration(this.getDefaultInstance()) - pTimeLeft;
        boolean multiShot = getMultiShot(pStack);
        int power = getPower(pStack);

        LinkedList<SwordBowSweepParticleType> swordBowSweepParticleTypes = new LinkedList<>() {
            {
                add(new SwordBowSweepParticleType(true, pEntityLiving, false, useTicks, 20, power));
                add(new SwordBowSweepParticleType(true, pEntityLiving, true, useTicks, 20, power));
            }
        };

        if (multiShot) {
            swordBowSweepParticleTypes.add(new SwordBowSweepParticleType(true, pEntityLiving, true, useTicks, 60, power));
            swordBowSweepParticleTypes.add(new SwordBowSweepParticleType(true, pEntityLiving, false, useTicks, 60, power));
            swordBowSweepParticleTypes.add(new SwordBowSweepParticleType(true, pEntityLiving, true, useTicks, 90, power));
            swordBowSweepParticleTypes.add(new SwordBowSweepParticleType(true, pEntityLiving, false, useTicks, 90, power));
        }

        swordBowSweepParticleTypes.forEach(swordBowSweepParticleType -> {
            SwordBowSweepParticleType swordBowSweepParticle = ModParticles.SWORD_BOW_SWEEP.get();
            swordBowSweepParticle.setLeftDirection(swordBowSweepParticleType.isLeftDirection());
            swordBowSweepParticle.setOwner(swordBowSweepParticleType.getOnwer());
            swordBowSweepParticle.setUseTicks(swordBowSweepParticleType.getUseTicks());
            swordBowSweepParticle.setStartYRot(swordBowSweepParticleType.getStartYRot());
            swordBowSweepParticle.setEncPower(swordBowSweepParticleType.getEncPower());
            pLevel.addParticle(swordBowSweepParticle, pEntityLiving.getX(), pEntityLiving.getY(0.5D), pEntityLiving.getZ(), 0, 0, 0.0D);
        });
    }

    public boolean getMultiShot(ItemStack stack) {
        int itemEnchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, stack);
        return itemEnchantmentLevel != 0;
    }

    public int getPower(ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
    }
}
