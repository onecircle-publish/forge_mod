package com.circle.circlemod.capability.entity;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerBreathMaskStatusProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerBreathMaskStatus> PLAYER_BREATH_MASK_STATUS = CapabilityManager.get(new CapabilityToken<PlayerBreathMaskStatus>() {
    });
    private PlayerBreathMaskStatus instance = null;
    private final LazyOptional<PlayerBreathMaskStatus> optional = LazyOptional.of(this::createPlayerBreathMask);

    private PlayerBreathMaskStatus createPlayerBreathMask() {
        if (this.instance == null) {
            this.instance = new PlayerBreathMaskStatus();
        }
        return this.instance;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_BREATH_MASK_STATUS) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return ICapabilityProvider.super.getCapability(cap);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerBreathMask().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerBreathMask().loadNBTData(nbt);
    }
}
