package com.circle.circlemod.item.sword;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * 武器的能力
 */
public class SwordAbilities {
    public enum UseType {
        USING_TICK,//按住右键触发
        USE,//右键使用时触发
        CLICK //攻击时触发
    }

    public enum KelpSword {
        ENTANGLEMENT,//缠绕
        ABSORPTION,//水分吸收
        SHIELD,//护盾
        REGENERATION,//再生
        FORCE_OF_TIDAL;//潮汐之力;//水之感知

        public static CustomAbility getAbilities() {
            CustomAbility customAbility = new CustomAbility();
            customAbility.setHashMap(KelpSword.ENTANGLEMENT, UseType.CLICK, "缠绕");
            customAbility.setHashMap(KelpSword.ABSORPTION, UseType.CLICK, "水分吸收");
            customAbility.setHashMap(KelpSword.SHIELD, UseType.USING_TICK, "水之护盾");
            customAbility.setHashMap(KelpSword.FORCE_OF_TIDAL, UseType.CLICK, "潮汐之力");
            customAbility.setHashMap(KelpSword.REGENERATION, UseType.USING_TICK, "水愈");
            return customAbility;
        }
    }

    /**
     *
     */
    public static class CustomAbility {
        private KelpSword key;
        private UseType type;
        private String name;
        public HashMap<KelpSword, CustomAbility> abilityHashMap = new HashMap<>();
        private static final String SPLIT_CHAR = "-";

        public CustomAbility() {

        }

        private CustomAbility(KelpSword key, UseType type, String name) {
            this.key = key;
            this.type = type;
            this.name = name;
        }

        public void setHashMap(KelpSword key, UseType type, @Nullable String name) {
            abilityHashMap.put(key, new CustomAbility(key, type, name == null ? key.toString() : name));
        }

        @Override
        public String toString() {
            return this.key.toString() + SPLIT_CHAR + this.type.toString();
        }

        public static CustomAbility parseAbilityString(String string) {
            String[] split = string.split(SPLIT_CHAR);
            return new CustomAbility(KelpSword.valueOf(split[0]), UseType.valueOf(split[1]), null);
        }

        public KelpSword getKey() {
            return key;
        }

        public void setKey(KelpSword key) {
            this.key = key;
        }

        public UseType getType() {
            return type;
        }

        public void setType(UseType type) {
            this.type = type;
        }

        public HashMap<KelpSword, CustomAbility> getAbilityHashMap() {
            return abilityHashMap;
        }

        public void setAbilityHashMap(HashMap<KelpSword, CustomAbility> abilityHashMap) {
            this.abilityHashMap = abilityHashMap;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
