package com.circle.circlemod.common.item;

import com.circle.circlemod.common.CircleMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CircleModCreativeTabs {
    public static final String CircleModTabId = "circlemod";

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CircleMod.MODID);

    public static final RegistryObject<CreativeModeTab> CIRCLEMOD_CREATIVE_MOD_TAB = CREATIVE_MODE_TABS.register("circlemod", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable(CircleModTabId))
            .displayItems((parameters, output) -> {
                output.accept(CircleModItems.CREATIVE_MOD_TAB_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            })
            .icon(() -> CircleModItems.CREATIVE_MOD_TAB_ITEM.get()
                    .getDefaultInstance())
            .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
