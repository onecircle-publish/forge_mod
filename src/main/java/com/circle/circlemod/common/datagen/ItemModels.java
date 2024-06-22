package com.circle.circlemod.common.datagen;

import com.circle.circlemod.common.CircleMod;
import com.circle.circlemod.common.item.CircleModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.stream.Collectors;

public class ItemModels extends ItemModelProvider {
    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";

    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CircleMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<Item> items = ForgeRegistries.ITEMS.getValues()
                .stream()
                .filter(i -> CircleMod.MODID.equals(ForgeRegistries.ITEMS.getKey(i)
                        .getNamespace()))
                .collect(Collectors.toSet());

        itemGeneratedModel(CircleModItems.CREATIVE_MOD_TAB_ITEM.get(), resourceItem(itemName(CircleModItems.CREATIVE_MOD_TAB_ITEM.get())));
        items.remove(CircleModItems.CREATIVE_MOD_TAB_ITEM.get());
    }

    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
    }

    private String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item)
                .getPath();
    }

    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(CircleMod.MODID, "block/" + path);
    }

    public ResourceLocation resourceItem(String path) {
        return new ResourceLocation(CircleMod.MODID, "item/" + path);
    }


}
