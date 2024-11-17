package com.circle.circlemod.factory;

import com.circle.circlemod.common.CircleMod;
import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.*;

import static com.circle.circlemod.common.CircleDeferredRegister.*;

/**
 * 创造模式选项卡
 *
 * @author yuanxin
 * @date 2024/11/17
 */
public class BuildCreativeModTab {
    public static final Collection<CircleModResources> modResourcesList = new HashSet<>();
    /**
     * 注册到Forge的创造模式栏
     */
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CircleMod.MODID);


    public static void addToCreativeMod(CircleModResources resources) {
        modResourcesList.add(resources);
    }


    public static void setup(IEventBus iEventBus) {
        CreativeModeTab build = CreativeModeTab
                .builder()
                .title(Component.translatable(CircleModTabId))
                .displayItems(((pParameters, pOutput) -> {
                    modResourcesList.forEach(resources -> {
                        pOutput.accept(getItemInstanceByResource(resources).get());
                    });
                }))
                .icon(() -> getItemInstanceByResource(CircleModResources.CREATIVE_ITEM)
                        .get()
                        .getDefaultInstance())
                .build();

        CREATIVE_MODE_TABS.register("circlemod", () -> build);
        CREATIVE_MODE_TABS.register(iEventBus);
    }
}
