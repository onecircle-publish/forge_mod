package com.circle.circlemod.common;

import com.circle.circlemod.common.entity.CircleModEntities;
import com.circle.circlemod.enums.CircleModResources;
import com.circle.circlemod.enums.CircleModTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 用来处理CircleModRegister中所有的内容，通过type分辨类型并进行不同的注册逻辑
 */
public class CircleDeferredRegister {
    /** 创造模式标签 */
    public static final String CircleModTabId = "circlemod";

    /** 注册到Forge的所有物品 */
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CircleMod.MODID);
    /** 注册到Forge的所有实体 */
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CircleMod.MODID);
    /** 注册到Forge的创造模式栏 */
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CircleMod.MODID);

    /**
     * 注册后的物品对象，CircleModObject中实现的getId作为map的key
     */
    public static Map<String, RegistryObject<Item>> itemObjects = new HashMap<>();
    /**
     * 注册后的实体对象，CircleModObject中实现的getId作为map的key
     */
    public static Map<String, RegistryObject<EntityType<?>>> entityObjects = new HashMap<>();


    /**
     * 创建所有项
     *
     * @param supplierArrayList sup数组列表
     */
    public static void createItem(HashMap<CircleModResources.CircleModResource, Supplier> supplierArrayList) {
        supplierArrayList.entrySet().forEach(item -> {
            CircleModResources.CircleModResource resource = item.getKey();// objId
            Supplier value = item.getValue();


            String itemId = resource.id;
            CircleModTypes itemType = resource.type;

            switch (itemType) {
                case FORGE_ITEM -> {
                    RegistryObject<Item> reItem = ITEMS.register(itemId, value);
                    itemObjects.put(itemId, reItem);
                    CircleMod.LOGGER.debug("加载了物品（item)：{}", itemId);
                }
                case FORGE_ENTITY -> {
                    RegistryObject<EntityType<?>> reEntity = ENTITIES.register(itemId, value);
                    entityObjects.put(itemId, reEntity);
//                    CircleMod.LOGGER.debug("加载了实体（entity)：{}", objId);
                }
            }
        });


        CREATIVE_MODE_TABS.register("circlemod", () -> CreativeModeTab.builder()
                .withTabsBefore(CreativeModeTabs.COMBAT)
                .title(Component.translatable(CircleModTabId))
                .displayItems((parameters, output) -> {
                    output.accept(getItemInstanceByResource(CircleModResources.CREATIVE_ITEM).get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
                    output.accept(getItemInstanceByResource(CircleModResources.FIRE_CRACKER).get());
                })
                .icon(() -> getItemInstanceByResource(CircleModResources.CREATIVE_ITEM).get()
                        .getDefaultInstance())
                .build());
    }


    /** 注册到forge */
    public static void register(IEventBus iEventBus) {
        ITEMS.register(iEventBus);
        ENTITIES.register(iEventBus);

        CREATIVE_MODE_TABS.register(iEventBus);

    }


    /**
     * 按资源获取项目实例
     *
     * @param resource 资源
     * @return {@link RegistryObject }<{@link Item }>
     */
    public static RegistryObject<Item> getItemInstanceByResource(CircleModResources resource) {
        return itemObjects.get(resource.getId());
    }


    /**
     * 按资源获取实体实例
     *
     * @param resource 资源
     * @return {@link RegistryObject }<{@link EntityType }<{@link ? }>>
     */
    public static RegistryObject<EntityType<?>> getEntityInstanceByResource(CircleModResources resource) {
        return entityObjects.get(resource.getId());
    }


    /**
     * 客户端 Mod 事件
     *
     * @author yuanxin
     * @date 2024/11/17
     */
    @Mod.EventBusSubscriber(modid = CircleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(CircleModEntities.FIRE_CRACKER.get(), ThrownItemRenderer::new);

        }
    }

}
