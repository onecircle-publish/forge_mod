package com.circle.circlemod.block.FreezeBlock;

import com.circle.circlemod.paticle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FreezeBlock extends Block {
    public FreezeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity entity,
                            ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, entity, itemStack);
        freezeBlockPlacedParticles(level, blockPos);

        if (entity == null) return;
        AABB aabb = new AABB(blockPos).inflate(5);
        List<Entity> entities = level.getEntities(null, aabb);


        for (Entity e : entities) {
            Vec3 position = e.position();
            var z = blockPos.getZ();
            var x = blockPos.getX();
            var y = blockPos.getY();
            if (Math.abs(y - position.y) <= 2 && Math.abs(x - position.x) <= 2 && Math.abs(z - position.z) <= 2) {
                e.hurt(DamageSource.STARVE, 5);
            }
        }
    }

    public void freezeBlockPlacedParticles(Level level, BlockPos pos) {
        level.addParticle(ModParticles.FREEZE_PARTICLES.get(), pos.getX() + 1d, pos.getY(), pos.getZ() + 1d, 0.25d,
                0.15d, 0.25d);
    }


}
