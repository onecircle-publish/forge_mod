package com.circle.circlemod.item.sword.kelp;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface KelpSwordItemInterface {
    void entanglement(String name);

    void absorption(String name);

    void shield(String name);

    void regeneration(String name);

    void forceOfTidal(String name);

    void waterPerception(String name, ItemStack stack, Player pPlayer);
}
