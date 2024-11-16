package com.circle.circlemod.common.datagen;

import com.circle.circlemod.common.CircleDeferredRegister;
import com.circle.circlemod.common.CircleMod;
import com.circle.circlemod.enums.CircleModResources;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * 语言
 *
 * @author yuanxin
 * @date 2024/11/17
 */
public class Language extends LanguageProvider {
    public Language(PackOutput output, String locale) {
        super(output, CircleMod.MODID, locale);
    }

    /**
     * 添加翻译
     */
    @Override
    protected void addTranslations() {
        add(CircleDeferredRegister.getEntityInstanceByResource(CircleModResources.CREATIVE_ITEM).get(), "创作模式标签图标物品");
        add(CircleDeferredRegister.CircleModTabId, "Circle Mod");
    }
}
