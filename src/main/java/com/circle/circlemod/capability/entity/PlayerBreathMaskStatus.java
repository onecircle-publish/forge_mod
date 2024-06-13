package com.circle.circlemod.capability.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerBreathMaskStatus {
    private int age = 0;

    public void playerTick(Player player) {
        if (this.age > 0) {
            this.age--;
        }
    }

    public void startBreathMask(int age) {
        this.age = age;
    }

    public void copyFrom(PlayerBreathMaskStatus source) {
        this.age = source.age;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("age", this.age);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.age = nbt.getInt("age");
    }
}

