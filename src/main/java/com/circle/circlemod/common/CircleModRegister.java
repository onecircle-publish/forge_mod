package com.circle.circlemod.common;

import com.circle.circlemod.common.block.safebed.SafeBedBlock;
import com.circle.circlemod.common.entity.firecracker.FireCrackereProjectileEntity;
import com.circle.circlemod.common.entity.safebed.SafeBedEntity;
import com.circle.circlemod.common.item.firecracker.FireCracker;
import com.circle.circlemod.enums.CircleModResources;
import com.circle.circlemod.factory.BuildCircleModObject;
import com.circle.circlemod.factory.BuildObject;
import com.mojang.datafixers.DSL;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;


/**
 * 用于添加所有自定义的物品、实体等
 *
 * @author yuanxin
 * @date 2024/11/17
 */
public class CircleModRegister {
    public static void register(IEventBus iEventBus) {
        CircleMod.LOGGER.debug("register items...");
        CircleDeferredRegister.createItem(new ArrayList<BuildObject>() {
            {
                add(new BuildCircleModObject()
                        .withItem(CircleModResources.CREATIVE_ITEM)
                        .bindSupplier(() -> new Item(new Item.Properties())));
                add(new BuildCircleModObject()
                        .<FireCracker>withItem(CircleModResources.FIRE_CRACKER)
                        .bindSupplier(() -> new FireCracker(new Item.Properties())));
                add(new BuildCircleModObject()
                        .<FireCrackereProjectileEntity>withEntity(CircleModResources.FIRE_CRACKER_PROJECTILE)
                        .bindSupplier(() -> EntityType.Builder
                                .<FireCrackereProjectileEntity>of(FireCrackereProjectileEntity::new, MobCategory.MISC)
                                .sized(0.2f, 0.6f)
                                .build(CircleModResources.FIRE_CRACKER_PROJECTILE.getId())));
                add(new BuildCircleModObject()
                        .<SafeBedBlock>withBlock(CircleModResources.SAFE_BED)
                        .bindSupplier(() -> new SafeBedBlock(DyeColor.BLACK, BlockBehaviour.Properties.of()), new Item.Properties().stacksTo(4)));
                add(new BuildCircleModObject()
                        .<SafeBedEntity>withBlockEntity(CircleModResources.SAFE_BED_ENTITY)
                        .bindSupplier(() -> BlockEntityType.Builder
                                .<SafeBedEntity>of(SafeBedEntity::new, CircleDeferredRegister
                                        .getBlockInstanceByResource(CircleModResources.SAFE_BED)
                                        .get())
                                .build(DSL.remainderType())));
            }
        });
        CircleDeferredRegister.register(iEventBus);
    }
}
