package com.circle.circlemod.item.staff;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class MagicStaffMagics {
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
