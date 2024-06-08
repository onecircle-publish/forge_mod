package com.circle.circlemod.utils;

import com.circle.circlemod.utils.direction.DirectionPlayerAndEntityModel;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;

public class CircleUtils {
    /**
     * 在任何地方获取ServerLevel的普通维度
     *
     * @return
     */
    public static ServerLevel getServerSideWorld() {
        return Minecraft.getInstance().getSingleplayerServer().getLevel(Level.OVERWORLD).getLevel();
    }

    /**
     * 获取实体视角 和 实体位置之间的夹角
     * 例如：玩家视角朝向 和 某个实体之间的夹角，用来求是否实体在玩家准心的某个角度范围内等等。
     *
     * @return angle 角度（0 - PI）
     */
    public static DirectionPlayerAndEntityModel angleBetweenPlayerAndEntity(Entity player, Entity entity) {
        // 计算玩家朝向和实体之间的夹角
        // yRot为2PI - 视角角度（0-2PI），这里需要游戏中视角向左的时候为逆时针，实际上靠左的坐标才是正坐标，所以用2PI去减
        // vecAttacker 是玩家 到 玩家朝向的一个点 的向量，也就是玩家指向 玩家坐标向玩家朝向延长一个单位的地方 的向量。
        float yRot = (float) (Math.toDegrees(Math.PI * 2) - (float) (Math.toDegrees(Math.PI * 2) + player.getYRot() % 360));
        Vec2 vecAttacker = new Vec2(((float) Math.sin(Math.toRadians(yRot))), (float) Math.cos(Math.toRadians(yRot)));

        // 这里求出玩家指向实体的向量
        Vec2 vecTargetToAttacker = new Vec2((float) (entity.getX() - player.getX()), (float) (entity.getZ() - player.getZ()));

        // vecAttacker 和 vecTargetToAttacker的夹角。点积除以长度之和的反余弦，然后转角度
        double angle = Math.toDegrees(Math.acos(vecAttacker.dot(vecTargetToAttacker) / (vecAttacker.length() * vecTargetToAttacker.length())));
        //
        boolean isLeft = (vecAttacker.x * vecTargetToAttacker.y - vecAttacker.y * vecTargetToAttacker.x) < 0;

        return new DirectionPlayerAndEntityModel(isLeft, angle);
    }

    /**
     * 获取实体在左侧还是右侧
     *
     * @param player
     * @param entity
     * @return
     */
    public static double relativePositionBetweenPlayerAndEntity(Entity player, Entity entity) {
        // 计算玩家朝向和实体之间的夹角
        // yRot为2PI - 视角角度（0-2PI），这里需要游戏中视角向左的时候为逆时针，实际上靠左的坐标才是正坐标，所以用2PI去减
        // vecAttacker 是玩家 到 玩家朝向的一个点 的向量，也就是玩家指向 玩家坐标向玩家朝向延长一个单位的地方 的向量。
        float yRot = (float) (Math.toDegrees(Math.PI * 2) - (float) (Math.toDegrees(Math.PI * 2) + player.getYRot() % 360));
        Vec2 vecAttacker = new Vec2(((float) Math.sin(Math.toRadians(yRot))), (float) Math.cos(Math.toRadians(yRot)));

        // 这里求出玩家指向实体的向量
        Vec2 vecTargetToAttacker = new Vec2((float) (entity.getX() - player.getX()), (float) (entity.getZ() - player.getZ()));

        // vecAttacker 和 vecTargetToAttacker的夹角。点积除以长度之和的反余弦，然后转角度
        return Math.toDegrees(Math.acos(vecAttacker.dot(vecTargetToAttacker) / (vecAttacker.length() * vecTargetToAttacker.length())));
    }
}
