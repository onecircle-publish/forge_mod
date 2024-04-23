package com.circle.circlemod.block.charm;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CharmBlock extends Block {
    public CharmBlock() {
        this(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops().noOcclusion());
    }

    public CharmBlock(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer,
                            ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);

        List<LivingEntity> nearbyEntities = pLevel.getNearbyEntities(LivingEntity.class,
                TargetingConditions.DEFAULT,
                pPlacer,
                new AABB(pPos).inflate(5));
        for (LivingEntity nearbyEntity : nearbyEntities) {
            if (nearbyEntity instanceof Zombie) {
                nearbyEntity.addEffect(new MobEffectInstance(ModEffects.CHARM_EFFECT.get(), 60));
            }
        }
    }
}
