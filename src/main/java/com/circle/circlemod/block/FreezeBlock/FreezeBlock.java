package com.circle.circlemod.block.FreezeBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
import java.util.UUID;

public class FreezeBlock extends Block {
    public FreezeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity entity,
                            ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, entity, itemStack);
        if (entity == null) return;

        AABB aabb = new AABB(blockPos).inflate(5);
        List<Entity> entities = level.getEntities(null, aabb);

        entity.sendMessage(Component.nullToEmpty("test"), UUID.randomUUID());
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
}
