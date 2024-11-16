package com.circle.circlemod.common.datagen;

import com.circle.circlemod.common.CircleDeferredRegister;
import com.circle.circlemod.common.CircleMod;
import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 项目模型
 *
 * @author yuanxin
 * @date 2024/11/17
 */
public class ItemModels extends ItemModelProvider {
    public static final String GENERATED = "item/generated";
    public static final String HANDHELD = "item/handheld";

    /**
     * 项目模型
     *
     * @param output             输出
     * @param existingFileHelper 现有文件帮助程序
     */
    public ItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CircleMod.MODID, existingFileHelper);
    }

    /**
     * 获取注册表 OBJ
     *
     * @return {@link RegistryObject }<{@link Item }>
     */
    public RegistryObject<Item> getRegistryObj() {
        return CircleDeferredRegister.getItemInstanceByResource(CircleModResources.CREATIVE_ITEM);
    }

    /**
     * 套准模型
     */
    @Override
    protected void registerModels() {
        Set<Item> items = ForgeRegistries.ITEMS.getValues()
                .stream()
                .filter(i -> CircleMod.MODID.equals(ForgeRegistries.ITEMS.getKey(i)
                        .getNamespace()))
                .collect(Collectors.toSet());

        itemGeneratedModel(getRegistryObj().get(), resourceItem(itemName(getRegistryObj().get())));
        items.remove(getRegistryObj().get());
    }

    /**
     * 项目生成模型
     *
     * @param item    项目
     * @param texture 质地
     */
    public void itemGeneratedModel(Item item, ResourceLocation texture) {
        withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
    }

    /**
     * 项目名称
     *
     * @param item 项目
     * @return {@link String }
     */
    private String itemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item)
                .getPath();
    }

    /**
     * 资源块
     *
     * @param path 路径
     * @return {@link ResourceLocation }
     */
    public ResourceLocation resourceBlock(String path) {
        return new ResourceLocation(CircleMod.MODID, "block/" + path);
    }

    /**
     * 资源项
     *
     * @param path 路径
     * @return {@link ResourceLocation }
     */
    public ResourceLocation resourceItem(String path) {
        return new ResourceLocation(CircleMod.MODID, "item/" + path);
    }


}
