package com.circle.circlemod.block.charm;

import com.circle.circlemod.entity.block.charm.CharmMushroomEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CharmBlock extends Block {
    public CharmBlock() {
        this(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops().noOcclusion());
    }

    public CharmBlock(int CharmDuration) {
        this();
    }

    public CharmBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        pLevel.removeBlock(pPos, false);
        pLevel.addFreshEntity(new CharmMushroomEntity(pLevel, pPos, pPlacer));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Block.box(4D, 0.0D, 4D, 12D, 7D, 12.0D);
    }
}
