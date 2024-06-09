package com.circle.circlemod.item.sword.directionsword;

import com.circle.circlemod.utils.CircleUtils;
import com.circle.circlemod.utils.direction.DirectionPlayerAndEntityModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

/**
 * @author : yuanxin
 * @date : 2024-06-03 17:02
 **/
public class DirectionSword extends SwordItem {
    public static final String PLAYER_INTERACT_EVENT_TAG = "direction_sword_tag";//非实体攻击时，为player对象添加该tag，完成攻击时删除tag
    private Direction direction = null;

    protected DirectionSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public DirectionSword(Direction direction, Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        this(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        this.direction = direction;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return super.onLeftClickEntity(stack, player, entity);
    }

    /**
     * 攻击后方
     */
    public void useDirectionHurt(Level level, ItemStack useItem, Player attacker) {
        if (!level.isClientSide) {
            double attackRange = attacker.getAttackRange();
            List<Entity> entities = level.getEntities(attacker, new AABB(attacker.getOnPos()).inflate(attackRange));

            entities.forEach(entity -> {
                DirectionPlayerAndEntityModel directionModel = CircleUtils.angleBetweenPlayerAndEntity(attacker, entity);

                if (!(entity instanceof Player) && entity instanceof LivingEntity) {
                    if (isInAttackRange(directionModel.isLeft(), directionModel.getAngle())) {

                        AttributeModifier modifier = (AttributeModifier) useItem.getItem().getAttributeModifiers(EquipmentSlot.MAINHAND, useItem).get(Attributes.ATTACK_DAMAGE).stream().toArray()[0];
                        if (modifier != null) {
                            attacker.attack(entity);
                        }
                    }
                }
            });
        }
    }

    /**
     * 处理左键事件函数
     *
     * @param event
     */
    public static void callDirectionLeftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Player serverPlayer = CircleUtils.getServerSideWorld().getPlayerByUUID(player.getUUID());
        serverPlayer.addTag(PLAYER_INTERACT_EVENT_TAG);
        InteractionHand usedItemHand = player.getUsedItemHand();
        Item itemInHand = player.getItemInHand(usedItemHand).getItem();
        if (itemInHand instanceof DirectionSword) {
            if (serverPlayer != null) {
                ((DirectionSword) itemInHand).useDirectionHurt(serverPlayer.level, player.getItemInHand(player.getUsedItemHand()), serverPlayer);
            }
        }
        serverPlayer.removeTag(PLAYER_INTERACT_EVENT_TAG);
    }

    public boolean isInAttackRange(boolean isLeft, double angle) {
        if (this.direction == Direction.ALL) return true;

        if (this.direction == Direction.REAR) {
            return angle <= 180 && angle >= 135;
        } else if (this.direction == Direction.LEFT && isLeft) {
            return angle <= 135 && angle >= 45;
        } else if (this.direction == Direction.RIGHT && !isLeft) {
            return angle <= 135 && angle >= 45;
        }
        return false;
    }

    public enum Direction {
        LEFT,
        RIGHT,
        REAR,
        ALL
    }
}
