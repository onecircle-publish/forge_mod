package com.circle.circlemod.item.staff;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

public class MagicStaffMagics {
    public static void monsters(Monster entity) {
        // 骷髅僵尸特殊逻辑
        if (entity instanceof AbstractSkeleton skeleton) {
            skeleton.reassessWeaponGoal();
            return;
        }

        // 苦力怕 - 丢出TNT 或者爆炸
        if (entity instanceof Creeper creeper) {
            ArrayList<Supplier<?>> functions = new ArrayList<>() {
                {
                    add(() -> {
                        // 丢弃TNT逻辑
                        MagicStaff.dropItem(Items.TNT.getDefaultInstance(), entity);
                        return null;
                    });
                    add(() -> {
                        // 执行爆炸逻辑
                        Method explodeCreeper = ObfuscationReflectionHelper.findMethod(Creeper.class, "explodeCreeper");
                        explodeCreeper.setAccessible(true);
                        try {
                            explodeCreeper.invoke(creeper);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                        return null;
                    });
                }
            };

            int index = new Random().nextInt(functions.size());
            Supplier<?> supplier = functions.get(index);
            supplier.get();
            return;
        }

        // 末影人
        if (entity instanceof EnderMan) {
            EnderMan enderMan = (EnderMan) entity;
            MagicStaff.dropItem(enderMan.getCarriedBlock().getBlock().asItem().getDefaultInstance(), enderMan);
            enderMan.setCarriedBlock(null);
            return;
        }
    }

    public static void animals(LivingEntity entity) {


        // 羊
        if (entity instanceof Sheep sheep) {
            DyeColor color = sheep.getColor();

            Map<DyeColor, ItemLike> itemByDye = ObfuscationReflectionHelper.getPrivateValue(Sheep.class, sheep, "ITEM_BY_DYE");
            ItemLike itemLike = itemByDye.get(color);
            MagicStaff.dropItem(itemLike.asItem().getDefaultInstance(), entity);
            return;
        }

        // 鸡
        if (entity instanceof Chicken) {
            ArrayList<Item> items = new ArrayList<>();
            items.add(Items.CHICKEN);
            items.add(Items.EGG);

            Item item = items.get(new Random().nextInt(items.size()));
            MagicStaff.dropItem(item.getDefaultInstance(), entity);
            return;
        }

        Iterable<SpawnEggItem> eggs = SpawnEggItem.eggs();
        for (SpawnEggItem egg : eggs) {
            EntityType<?> entityType = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, egg, "defaultType");
            if (entityType.equals(entity.getType())) {
                MagicStaff.dropItem(egg.getDefaultInstance(), entity);
            }
        }
    }
}
