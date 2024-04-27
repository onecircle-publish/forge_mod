package com.circle.circlemod.network.packs;

import com.circle.circlemod.CircleMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

//    通过 CircleModNetworking.INSTANCE.send(PacketDistributor.ALL.noArg(), new BaseSendPack("test"));使用通信
public class CircleModNetworking {
    public static SimpleChannel INSTANCE;

    public static final String VERSION = "1.0";

    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(CircleMod.MOD_ID, "circle_networking"), () -> VERSION, (version) -> version.equals(VERSION), (version) -> version.equals(VERSION));
        INSTANCE.messageBuilder(BaseSendPack.class, nextID()).encoder(BaseSendPack::toBytes).decoder(BaseSendPack::new).consumer(BaseSendPack::handler).add();
    }
}
