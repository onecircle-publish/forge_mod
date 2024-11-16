package com.circle.circlemod.interfaces;

import com.circle.circlemod.enums.CircleModTypes;

/**
 * Circle Mod 对象
 *
 * @author yuanxin
 * @date 2024/11/17
 */
public interface CircleModObject {
    /**
     * 物品id
     *
     * @return
     */
    public String getId();

    /**
     * 物品类型
     * @return
     */
    public CircleModTypes getType();
}
