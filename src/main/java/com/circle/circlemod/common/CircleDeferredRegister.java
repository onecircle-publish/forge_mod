package com.circle.circlemod.common;

import com.circle.circlemod.common.entity.firecracker.FireCrackereProjectileEntity;
import com.circle.circlemod.enums.CircleModResources;
import com.circle.circlemod.enums.CircleModTypes;
import com.circle.circlemod.factory.BuildCreativeModTab;
import com.circle.circlemod.factory.BuildObject;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


/**
 * 用来处理CircleModRegister中所有的内容，通过type分辨类型并进行不同的注册逻辑
 */
public class CircleDeferredRegister {
    /**
     * 创造模式标签
     */
    public static final String CircleModTabId = "circlemod";

    /**
     * 注册到Forge的所有物品
     */
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CircleMod.MODID);
    /**
     * 注册到Forge的所有实体
     */
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CircleMod.MODID);


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
     */
    public static void createItem(ArrayList<BuildObject> buildObjects) {

        buildObjects.forEach(buildObject -> {
            CircleModResources resource = buildObject.getResource();
            Supplier supplier = buildObject.getSupplier();

            String id = resource.getId();
            CircleModTypes type = resource.getType();


            switch (type) {
                case FORGE_ITEM -> {
                    RegistryObject<Item> reItem = ITEMS.register(id, supplier);
                    itemObjects.put(id, reItem);
                    CircleMod.LOGGER.debug("注册了物品（item)：{}", id);

                    BuildCreativeModTab.addToCreativeMod(resource);
                }
                case FORGE_ENTITY -> {
                    RegistryObject<EntityType<?>> reEntity = ENTITIES.register(id, supplier);
                    entityObjects.put(id, reEntity);
                    CircleMod.LOGGER.debug("注册了实体（entity)：{}", id);
                }
            }
        });


    }


    /**
     * 注册到forge
     */
    public static void register(IEventBus iEventBus) {
        ITEMS.register(iEventBus);
        ENTITIES.register(iEventBus);
        BuildCreativeModTab.setup(iEventBus);
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
     * uangz@date 2024/11/17
     */
    @Mod.EventBusSubscriber(modid = CircleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register((EntityType<FireCrackereProjectileEntity>) getEntityInstanceByResource(CircleModResources.FIRE_CRACKER_PROJECTILE).get(), ThrownItemRenderer::new);
        }
    }

}
