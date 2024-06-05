package com.circle.circlemod.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class MinecraftUtils {
    public static ServerLevel getServerSideWorld(){
        return Minecraft.getInstance().getSingleplayerServer().getLevel(Level.OVERWORLD).getLevel();
    }
}
