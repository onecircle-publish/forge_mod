package com.circle.circlemod.item;

import com.circle.circlemod.CircleMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModTiers {
    public static final Tier KELP_SWORD = TierSortingRegistry.registerTier(
            new ForgeTier(5, 1500, 5f, 4f, 25, null, () -> Ingredient.of(ModItems.KELP_SWORD.get())),
            new ResourceLocation(CircleMod.MOD_ID, "kelp_sword"), List.of(Tiers.STONE), List.of());
}
