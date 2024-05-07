package com.circle.circlemod.item.sword.kelp;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class KelpSword extends SwordItem {
    public KelpSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }


    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return super.onLeftClickEntity(stack, player, entity);
    }

    /**
     * 缠绕
     */
    public void entanglement() {
//        判断NBT标签是否包含该属性，则在左键的时候调用
    }

    /**
     * 水分吸收
     */
    public void absorption() {

    }

    /**
     * 护盾
     */
    public void shield() {

    }

    /**
     * 再生
     */
    public void regeneration() {

    }

    /**
     * 潮汐之力
     */
    public void forceOfTidal() {

    }

    /**
     * 水之感知
     */
    public void waterPerception(){

    }
}
