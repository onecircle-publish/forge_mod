package com.circle.circlemod.item.staff;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Supplier;

public class MagicStaffMagics {
    public static void monsters(Monster entity) {
        // 骷髅僵尸特殊逻辑 - 丢弃弓，取消射击AI
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
                        Method explodeCreeper = ObfuscationReflectionHelper.findMethod(Creeper.class, "m_32315_"); //explodeCreeper
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

        // 末影人 - 丢弃手中的方块
        if (entity instanceof EnderMan) {
            EnderMan enderMan = (EnderMan) entity;
            MagicStaff.dropItem(enderMan.getCarriedBlock().getBlock().asItem().getDefaultInstance(), enderMan);
            enderMan.setCarriedBlock(null);
            return;
        }

        // 蜘蛛 - 丢弃蜘蛛丝
        if (entity instanceof Spider) {
            MagicStaff.dropItem(Items.STRING.getDefaultInstance(), entity);
        }

        // 女巫 - 随机丢弃药水
        if (entity instanceof Witch) {
            List<Map.Entry<ResourceKey<Potion>, Potion>> entries = ForgeRegistries.POTIONS.getEntries().stream().toList();
            int index = new Random().nextInt(entries.size());
            Potion potion = entries.get(index).getValue();
            ItemStack itemStack = Items.POTION.getDefaultInstance();
            ItemStack potionItem = PotionUtils.setPotion(itemStack, potion);
            MagicStaff.dropItem(potionItem, entity);
        }
    }

    public static void animals(LivingEntity entity) {
        // 羊 - 丢出羊毛
        if (entity instanceof Sheep sheep) {
            DyeColor color = sheep.getColor();

            Map<DyeColor, ItemLike> itemByDye = ObfuscationReflectionHelper.getPrivateValue(Sheep.class, sheep, "f_29800_");//ITEM_BY_DYE
            ItemLike itemLike = itemByDye.get(color);
            MagicStaff.dropItem(itemLike.asItem().getDefaultInstance(), entity);
            return;
        }

        // 鸡 - 丢出鸡蛋、鸡腿...
        if (entity instanceof Chicken) {
            ArrayList<Item> items = new ArrayList<>();
            items.add(Items.CHICKEN);
            items.add(Items.EGG);

            Item item = items.get(new Random().nextInt(items.size()));
            MagicStaff.dropItem(item.getDefaultInstance(), entity);
            return;
        }

        if (entity instanceof Pig) {
            ArrayList<Item> items = new ArrayList<>();
            if (entity.isOnFire()) {
                items.add(Items.COOKED_PORKCHOP);
            } else {
                items.add(Items.PORKCHOP);
            }

            Item item = items.get(new Random().nextInt(items.size()));
            MagicStaff.dropItem(item.getDefaultInstance(), entity);
            return;
        }

        // 如果没有特殊动物逻辑，则丢弃动物的生成蛋
        Iterable<SpawnEggItem> eggs = SpawnEggItem.eggs();
        for (SpawnEggItem egg : eggs) {
            EntityType<?> entityType = egg.getType(null);
            if (entityType.equals(entity.getType())) {
                MagicStaff.dropItem(egg.getDefaultInstance(), entity);
            }
        }
    }

    /**
     * 铁傀儡
     * 丢出铁锭
     */
    public static void ironGolem(IronGolem ironGolem) {
        MagicStaff.dropItem(Items.IRON_INGOT.getDefaultInstance(), ironGolem);
    }
}
