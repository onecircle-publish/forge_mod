package com.circle.circlemod.factory;

import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Supplier;

public class BuildWithEntity<T extends Entity> extends BuildObject {

    EntityType.Builder<T> builder = null;

    public BuildWithEntity() {
    }

    public BuildWithEntity(CircleModResources resource) {
        super(resource);
    }

    /**
     * 绑定供应商
     *
     * @param supplier 供应商
     * @return {@link BuildWithEntity }<{@link T }>
     */
    public BuildWithEntity<T> bindSupplier(Supplier<?> supplier) {
        super.supplier = supplier;
        return this;
    }

}
