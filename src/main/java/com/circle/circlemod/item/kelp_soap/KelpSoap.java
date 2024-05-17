package com.circle.circlemod.item.kelp_soap;

import com.circle.circlemod.CircleMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeTier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * 海带香皂
 */
public class KelpSoap extends TieredItem {
    public static final int MAX_DAMAGE = 6; // 最大耐久度
    private SoapStatus status; //当前状态

    public enum SoapStatus {
        STATUS_DEFAULT,
        STATUS_2,
        STATUS_3
    }

    public KelpSoap(Properties pProperties) {
        this(new ForgeTier(1, MAX_DAMAGE, 2.0F, 1.0F, 1, null, () -> null), pProperties);
    }

    public KelpSoap(Tier pTier, Properties pProperties) {
        super(pTier, pProperties);
    }

    public void updateDamage(Player player, ItemStack stack) {
        int damage = this.getDamage(stack);//当前损耗
        this.setDamage(stack, damage + 1);

        attamptToDestoryItem(stack);
        updateStatus(stack);
    }

    public void updateStatus(ItemStack stack) {
        this.status = getStatus(this.getDamage(stack));
    }

    public void attamptToDestoryItem(ItemStack stack) {
        if (this.getDamage(stack) >= MAX_DAMAGE) {
            stack.shrink(1);  //销毁物品
        }
    }

    /**
     * 设置武器状态。DEFAULT -> 2 -> 3 消耗逐级递增
     *
     * @param damage
     * @return
     */
    public SoapStatus getStatus(int damage) {
        if (damage < 2) return SoapStatus.STATUS_DEFAULT;
        if (damage < 4) return SoapStatus.STATUS_2;
        if (damage < 6) return SoapStatus.STATUS_3;

        return SoapStatus.STATUS_DEFAULT;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level
            pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        pTooltipComponents.add(new TranslatableComponent("item.circlemod.kelp_soap.text").withStyle(ChatFormatting.YELLOW));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return super.getUseDuration(pStack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) {
            for (double y = pPlayer.getY(); y < pPlayer.getY() + 2; y += 0.5) {  // 从玩家脚下到头部，每次增加 0.5 的高度
                ((ServerLevel) pLevel).sendParticles(ParticleTypes.BUBBLE, pPlayer.getX(), y, pPlayer.getZ(), 10, 0.2, 0.2, 0.2, 0.1);
            }
        }
        Collection<MobEffectInstance> activeEffects = pPlayer.getActiveEffects();
        // 如果存在效果，随机移除一个
        if (!activeEffects.isEmpty()) {
            int indexToRemove = new Random().nextInt(activeEffects.size());
            MobEffectInstance effectToRemove = activeEffects.stream().skip(indexToRemove).findFirst().orElse(null);
            if (effectToRemove != null) {
                pPlayer.removeEffect(effectToRemove.getEffect());
            }
        }
        pPlayer.playSound(SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS, 1F, 1F);
        updateDamage(pPlayer, pPlayer.getItemInHand(pUsedHand));
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.EAT;
    }
}