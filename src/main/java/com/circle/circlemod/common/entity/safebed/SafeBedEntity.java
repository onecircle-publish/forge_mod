package com.circle.circlemod.common.entity.safebed;

import com.circle.circlemod.common.CircleDeferredRegister;
import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SafeBedEntity extends BlockEntity implements GeoBlockEntity {
    public final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    public SafeBedEntity(BlockPos pPos, BlockState pBlockState) {
        super(CircleDeferredRegister
                .getBlockEntityInstanceByResource(CircleModResources.SAFE_BED_ENTITY)
                .get(), pPos, pBlockState);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
