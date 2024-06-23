package com.circle.circlemod.common.datagen;

import com.circle.circlemod.common.CircleMod;
import com.circle.circlemod.common.item.CircleModCreativeTabs;
import com.circle.circlemod.common.item.CircleModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class Language extends LanguageProvider {
    public Language(PackOutput output, String locale) {
        super(output, CircleMod.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add(CircleModItems.CREATIVE_MOD_TAB_ITEM.get(), "创作模式标签图标物品");
        add(CircleModCreativeTabs.CircleModTabId, "Circle Mod");
    }
}
