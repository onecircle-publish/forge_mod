package com.circle.circlemod.block.freezeBlock;

import com.circle.circlemod.effect.ModEffects;
import com.circle.circlemod.paticle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
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
    private final int freezeSize = 5 + 1;

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
            if (((Math.abs(y - position.y) < freezeSize) &&
                    (Math.sqrt(Math.pow(z - position.z, 2) + Math.pow(x - position.x, 2)) <= freezeSize))) {

                //设置冰冻效果
                if (e instanceof LivingEntity) {
                    ((LivingEntity) e).addEffect(new MobEffectInstance(ModEffects.freezeEffect.get(), 20 * 3));
                }
            }
        }
    }

    public void freezeBlockPlacedParticles(Level level, BlockPos pos) {
        for (int j = 0; j < freezeSize; j++) {
            for (int i = 0; i < 360; i++) {
                if (i % freezeSize == 0) {
                    level.addParticle(ModParticles.FREEZE_PARTICLES.get(),
                            pos.getX() + Math.cos(Math.toRadians(i)) * j,
                            pos.getY(),
                            pos.getZ() + Math.sin(Math.toRadians(i)) * j,
                            0.5d, 0d, 0.5d);

                }
            }
        }

    }
}
