package com.circle.circlemod.network.packs;

import com.circle.circlemod.CircleMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * 使用
 * HashMap<String, Object> map = new HashMap<>();
 * map.put("level", this);
 * CircleModNetworking.INSTANCE.send(PacketDistributor.ALL.noArg(), new BaseSendPack(map));
 * <p>
 * FriendlyByteBuf支持buf.readBlockPos()等跟forge相关的内容
 */
public class BaseSendPack {
    private HashMap<String, String> dataMap;

    public BaseSendPack(FriendlyByteBuf buf) {
        this.dataMap = (HashMap) buf.readMap(FriendlyByteBuf::readUtf, FriendlyByteBuf::readUtf);
    }

    public BaseSendPack(HashMap dataMap) {
        this.dataMap = dataMap;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeMap(this.dataMap, FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeUtf);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            CircleMod.LOGGER.debug(String.valueOf(this.dataMap.get("test")));
        });
        ctx.get().setPacketHandled(true);
    }
}