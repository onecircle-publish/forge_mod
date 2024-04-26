package com.circle.circlemod.block.doom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class DoomMushroom extends Block {
    public DoomMushroom() {
        this(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops().noOcclusion());

    }

    public DoomMushroom(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Explosion explosion = new Explosion(pLevel, null, pPos.getX(), pPos.getY(), pPos.getZ(), 10f);
                explosion.explode();
            }
        }, 1000);

    }
}
