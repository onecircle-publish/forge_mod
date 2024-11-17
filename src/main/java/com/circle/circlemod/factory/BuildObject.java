package com.circle.circlemod.factory;

import com.circle.circlemod.enums.CircleModResources;

import java.util.function.Supplier;

public class BuildObject {
    public CircleModResources resource;
    public Supplier<?> supplier;

    /**
     * build 对象
     */
    public BuildObject() {
    }

    /**
     * build 对象
     *
     * @param resource 资源
     */
    public BuildObject(CircleModResources resource) {
        this.resource = resource;
    }

    /**
     * 获取资源
     *
     * @return {@link CircleModResources }
     */
    public CircleModResources getResource() {
        return resource;
    }

    /**
     * 设置资源
     *
     * @param resource 资源
     */
    public void setResource(CircleModResources resource) {
        this.resource = resource;
    }

    /**
     * 获取供应商
     *
     * @return {@link Supplier }
     */
    public Supplier<?> getSupplier() {
        return supplier;
    }

    /**
     * 设置供应商
     *
     * @param supplier 供应商
     */
    public void setSupplier(Supplier<?> supplier) {
        this.supplier = supplier;
    }
}
