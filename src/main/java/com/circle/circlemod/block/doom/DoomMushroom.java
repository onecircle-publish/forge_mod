package com.circle.circlemod.block.doom;

import com.circle.circlemod.entity.doom.DoomMushroomEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

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
        pLevel.removeBlock(pPos, false);
        this.explode(pLevel, pPos, pPlacer);
    }

    public void explode(Level pLevel, BlockPos pPos, LivingEntity pPlacer) {
        DoomMushroomEntity doomMushroomEntity = new DoomMushroomEntity(pLevel, pPos, pPlacer);
        pLevel.addFreshEntity(doomMushroomEntity);
    }
}
