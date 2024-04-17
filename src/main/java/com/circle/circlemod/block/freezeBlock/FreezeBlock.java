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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FreezeBlock extends Block {
    private int freezeSize = 5 + 1; // 冰冻范围
    private boolean excludeSelf = true; //是否使用者不受影响

    public FreezeBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops().noOcclusion());
    }

    public FreezeBlock(int freezeSize) {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(9f).requiresCorrectToolForDrops().noOcclusion());
        setFreezeSize(freezeSize);
    }

    /**
     * 设置冰冻范围
     *
     * @param size
     */
    public void setFreezeSize(int size) {
        this.freezeSize = size + 1;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity entity,
                            ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, entity, itemStack);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                freezeBlockPlacedParticles(level, blockPos); // 触发粒子
                level.destroyBlock(blockPos, true); //销毁方块

                if (entity == null) return;
                AABB aabb = new AABB(blockPos).inflate(freezeSize);
                List<Entity> entities = level.getEntities(null, aabb);

                for (Entity e : entities) {
                    Vec3 position = e.position();
                    var z = blockPos.getZ();
                    var x = blockPos.getX();
                    var y = blockPos.getY();
                    if (((Math.abs(y - position.y) < freezeSize) &&
                            (Math.sqrt(Math.pow(z - position.z, 2) + Math.pow(x - position.x, 2)) <= freezeSize))) {

                        //设置冰冻效果
                        if (e instanceof LivingEntity && (!excludeSelf || (excludeSelf && !e.equals(entity)))) {
                            ((LivingEntity) e).addEffect(new MobEffectInstance(ModEffects.freezeEffect.get(),
                                    20 * 3));
                        }
                    }
                }
            }
        }, 500L);
    }

    /**
     * 冰冻粒子
     *
     * @param level
     * @param pos
     */
    public void freezeBlockPlacedParticles(Level level, BlockPos pos) {
        List<Point2D.Double> points = new ArrayList<Point2D.Double>();

        double d = 2 * Math.PI * freezeSize;


        for (int r = 0; r <= freezeSize; r++) {
            for (double i = 0; i <= d; i += 2) {
                double randomOffset = Math.random() * 2;
                double x = Math.cos(Math.toDegrees(i / freezeSize) + randomOffset) * r;
                double y = Math.sin(Math.toDegrees(i / freezeSize) + randomOffset) * r;
                points.add(new Point2D.Double(x, y));
            }
        }

        for (Point2D.Double p : points) {
            level.addParticle(ModParticles.FREEZE_PARTICLES.get(),
                    pos.getX() + p.x,
                    pos.getY(),
                    pos.getZ() + p.y,
                    0.5d, 0d, 0.5d);
        }


    }
}
