package com.circle.circlemod.common;

import com.circle.circlemod.common.entity.firecracker.FireCrackereProjectileEntity;
import com.circle.circlemod.common.entity.safebed.SafeBedEntityRenderer;
import com.circle.circlemod.enums.CircleModResources;
import com.circle.circlemod.enums.CircleModTypes;
import com.circle.circlemod.factory.BuildCreativeModTab;
import com.circle.circlemod.factory.BuildObject;
import com.circle.circlemod.factory.BuildWithBlock;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
     * 注册到Forge的所有物品
     */
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CircleMod.MODID);
    /**
     * 注册到Forge的所有实体
     */
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CircleMod.MODID);
    /**
     * 注册到Forge的所有实体
     */
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CircleMod.MODID);


    /**
     * 注册后的物品对象，CircleModObject中实现的getId作为map的key
     */
    public static Map<String, RegistryObject<Item>> itemObjects = new HashMap<>();
    /**
     * 注册后的方块，CircleModObject中实现的getId作为map的key
     */
    public static Map<String, RegistryObject<Block>> blockObjects = new HashMap<>();
    /**
     * 注册后的实体对象，CircleModObject中实现的getId作为map的key
     */
    public static Map<String, RegistryObject<EntityType<? extends Entity>>> entityObjects = new HashMap<>();
    /**
     * 注册后的实体对象，CircleModObject中实现的getId作为map的key
     */
    public static Map<String, RegistryObject<BlockEntityType<? extends BlockEntity>>> blockEntityObjects = new HashMap<>();


    /**
     * 加物品
     *
     * @param resource 资源
     * @param supplier 供应商
     */
    public static void addItem(CircleModResources resource, Supplier supplier) {
        RegistryObject<Item> reItem = ITEMS.register(resource.getId(), supplier);
        itemObjects.put(resource.getId(), reItem);
        CircleMod.LOGGER.debug("注册了物品（item)：{}", resource.getId());
        BuildCreativeModTab.addToCreativeMod(resource);
    }

    public static RegistryObject<Block> addBlock(CircleModResources resource, Supplier supplier) {
        RegistryObject<Block> reBlock = BLOCKS.register(resource.getId(), supplier);
        blockObjects.put(resource.getId(), reBlock);
        CircleMod.LOGGER.debug("注册了方块（block)：{}", resource.getId());
        return reBlock;
    }


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
                    addItem(resource, supplier);
                }
                case FORGE_BLOCK -> {
                        // 注册对应方块
                        BuildWithBlock buildWithBlock = (BuildWithBlock) buildObject;
                        RegistryObject<Block> blockRegistryObject = addBlock(resource, supplier);

                        // 注册对应物品。此注册方式不支持修改blockItem
                        addItem(resource, () -> new BlockItem(blockRegistryObject.get(), buildWithBlock.getItemProperties()));
                }
                case FORGE_ENTITY -> {
                    RegistryObject reEntity = ENTITIES.register(id, supplier);
                    entityObjects.put(id, reEntity);
                    CircleMod.LOGGER.debug("注册了实体（entity)：{}", id);
                }
                case FORGE_BLOCK_ENTITY -> {
                    RegistryObject reBlockEntity = BLOCK_ENTITIES.register(id, supplier);
                    blockEntityObjects.put(id, reBlockEntity);
                    CircleMod.LOGGER.debug("注册了方块实体（block_entity)：{}", id);
                }
            }
        });


    }


    /**
     * 注册到forge
     */
    public static void register(IEventBus iEventBus) {
        ITEMS.register(iEventBus);
        BLOCKS.register(iEventBus);
        BLOCK_ENTITIES.register(iEventBus);
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
     * 按资源获取方块实例
     *
     * @param resource 资源
     * @return {@link RegistryObject }<{@link Item }>
     */
    public static RegistryObject<Block> getBlockInstanceByResource(CircleModResources resource) {
        return blockObjects.get(resource.getId());
    }



    /**
     * 按资源获取实体实例
     *
     * @param resource 资源
     * @return {@link RegistryObject }<{@link EntityType }<{@link ? }>>
     */
    public static RegistryObject<EntityType<? extends Entity>> getEntityInstanceByResource(CircleModResources resource) {
        return entityObjects.get(resource.getId());
    }


    /**
     * 按资源获取方块实体实例
     *
     * @param resource 资源
     * @return {@link RegistryObject }<{@link BlockEntityType }<{@link ? }>>
     */
    public static RegistryObject<BlockEntityType<? extends BlockEntity>> getBlockEntityInstanceByResource(CircleModResources resource) {
        return blockEntityObjects.get(resource.getId());
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


        /**
         * 寄存器渲染器
         * Geckolib模型渲染器注册
         *
         * @param event 事件
         */
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            BlockEntityType blockEntityType = CircleDeferredRegister
                    .getBlockEntityInstanceByResource(CircleModResources.SAFE_BED_ENTITY)
                    .get();


            event.registerBlockEntityRenderer(blockEntityType, SafeBedEntityRenderer::new);
        }
    }

}
