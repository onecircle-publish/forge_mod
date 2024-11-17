package com.circle.circlemod.enums;

/**
 * Circle Mod 资源
 *
 * @author yuanxin
 * @date 2024/11/17
 */
public enum CircleModResources {

    /**
     * 创意项目
     */
    CREATIVE_ITEM("creative_mod_tab_item", CircleModTypes.FORGE_ITEM),
    /**
     * 鞭炮
     */
    FIRE_CRACKER("fire_cracker", CircleModTypes.FORGE_ITEM),
    /**
     * 鞭炮弹
     */
    FIRE_CRACKER_PROJECTILE("fire_cracker_projectile", CircleModTypes.FORGE_ENTITY),

    /**
     * 安全床
     */
    SAFE_BED("safe_bed", CircleModTypes.FORGE_ITEM);

    public CircleModResource resource = null;

    /**
     * Circle Mod 资源
     *
     * @param id   身份证
     * @param type 类型
     */
    private CircleModResources(String id, CircleModTypes type) {
        this.resource = new CircleModResource(id, type);
    }

    /**
     * 资源
     *
     * @return {@link CircleModResource }
     */
    public CircleModResource resource() {
        return this.resource;
    }

    /**
     * 获取 ID
     *
     * @return {@link String }
     */
    public String getId() {
        return this.resource.id;
    }

    /**
     * 获取类型
     *
     * @return {@link CircleModTypes }
     */
    public CircleModTypes getType() {
        return this.resource.type;
    }

    /**
     * Circle Mod 资源
     *
     * @author yuanxin
     * @date 2024/11/17
     */
    public class CircleModResource {
        public String id;

        public CircleModTypes type;

        private CircleModResource(String id, CircleModTypes type) {
            this.id = id;
            this.type = type;
        }
    }
}
