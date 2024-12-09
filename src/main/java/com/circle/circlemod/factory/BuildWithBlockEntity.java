package com.circle.circlemod.factory;

import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class BuildWithBlockEntity<T extends BlockEntity> extends BuildObject<T> {
    BlockEntityType.Builder<T> builder = null;

    public BuildWithBlockEntity() {
    }

    public BuildWithBlockEntity(CircleModResources resource) {
        super(resource);
    }


    /**
     * 绑定供应商
     *
     * @param supplier 供应商
     * @return {@link BuildWithBlockEntity }<{@link T }>
     */
    public BuildWithBlockEntity<T> bindSupplier(Supplier<?> supplier) {
        super.supplier = supplier;
        return this;
    }
}
