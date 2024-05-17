package com.circle.circlemod.item.sword.kelp;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.effect.ModEffects;
import com.circle.circlemod.entity.ModEntities;
import com.circle.circlemod.entity.sheild.ShieldEntity;
import com.circle.circlemod.item.sword.SwordAbilities;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

/**
 * @author yuanxin
 * @date 2024-05-07 15:12
 **/
public class KelpSwordItem extends SwordItem {
    public final int MAX_ABILITY_COUNT = 1;// 一次性产生的技能数量
    public final int ENTANGLEMENT_TICKS = 40;
    public final int ABSORPTION_TICKS = 30 * 2;
    public static final String ABILITI_NBT_KEY = "KelpSwordAbilities";
    public ArrayList<SwordAbilities.CustomAbility> currentAbilities = null;
    public ShieldEntity shieldEntity = null;

    public KelpSwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        if (!pLevel.isClientSide) {
            setRandomAbilities(pStack, MAX_ABILITY_COUNT);
        }
        super.onCraftedBy(pStack, pLevel, pPlayer);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CROSSBOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    /**
     * 设置指定数量的能力
     * 设置tag
     *
     * @param count
     */
    public void setRandomAbilities(ItemStack stack, int count) {
        ArrayList<SwordAbilities.CustomAbility> randomAbilities = generateMagics(count);
        setMagicTags(stack, randomAbilities);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        useAbilities(SwordAbilities.UseType.CLICK, stack, player, entity);
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);

        //父类中逻辑，不可食用则不会触发startingUsingItem，这里手动调用。
//         使用技能
//        useAbilities(SwordAbilities.UseType.USE, item, pPlayer, null);
        if (hasShield(item)) {
            shield(item, pPlayer);
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(item);
        } else {
            return InteractionResultHolder.pass(item);
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if (this.shieldEntity != null) {
            Vec3 position = player.getPosition(1);
            this.shieldEntity.setIsShieldShow(true);
            this.shieldEntity.setPos(position.x, position.y, position.z);
        }
        super.onUsingTick(stack, player, count);
    }

    @Override
    public boolean useOnRelease(ItemStack pStack) {
        if (this.shieldEntity != null) {
            this.shieldEntity.setIsShieldShow(false);
        }
        return super.useOnRelease(pStack);
    }

    /**
     * 添加技能tag
     *
     * @param stack
     * @param abilities
     */
    public void setMagicTags(ItemStack stack, ArrayList<SwordAbilities.CustomAbility> abilities) {
        //存储nbt
        CompoundTag tag = stack.getOrCreateTag();
        ListTag abilityTags = new ListTag();
        CompoundTag compoundTag = new CompoundTag();
        ListTag lore = new ListTag();

        abilities.forEach(ability -> {
            abilityTags.add(StringTag.valueOf(ability.getKey().toString()));
            lore.add(StringTag.valueOf(new Gson().toJson(new TextComponent(ability.getName()))));
        });

        tag.put(ABILITI_NBT_KEY, abilityTags);

        compoundTag.put("Lore", lore);
        tag.put("display", compoundTag);
        stack.setTag(tag);
    }

    /**
     * 随机获取指定数量的技能
     *
     * @param count
     */
    public ArrayList<SwordAbilities.CustomAbility> generateMagics(int count) {
        Random random = new Random();
        SwordAbilities.CustomAbility allAbilities = SwordAbilities.KelpSword.getAbilities();
        ArrayList<SwordAbilities.CustomAbility> targetAbilities = new ArrayList<>();//根据key，存储的技能对象

        // 取从0到count之间的随机数，随机数用来从allAbilities中取技能
        HashMap<SwordAbilities.KelpSword, SwordAbilities.CustomAbility> abilityHashMap = allAbilities.getAbilityHashMap();
        HashSet<Integer> keys = new HashSet<>();

        while (keys.size() < count) {
            int randomInt = random.nextInt(abilityHashMap.size());

            if (!keys.contains(randomInt)) {
                keys.add(randomInt);
            }
        }

        Object[] keyArray = allAbilities.abilityHashMap.keySet().toArray();
        keys.forEach(key -> {
            targetAbilities.add(abilityHashMap.get(keyArray[key]));
        });
        return targetAbilities;
    }

    /**
     * 筛选出触发方式和技能使用方式相同的技能key，然后触发技能
     *
     * @param stack
     * @param type
     */
    public void useAbilities(SwordAbilities.UseType type, ItemStack stack, Player player, @Nullable Entity
            attatchedEntity) {
        ListTag list = stack.getOrCreateTag().getList(ABILITI_NBT_KEY, Tag.TAG_STRING);
        HashMap<SwordAbilities.KelpSword, SwordAbilities.CustomAbility> allAbilities = SwordAbilities.KelpSword.getAbilities().getAbilityHashMap();
        list.forEach(tag -> {
            SwordAbilities.CustomAbility ability = allAbilities.get(SwordAbilities.KelpSword.valueOf(tag.getAsString()));

            if (ability.getType() == type) {
                SwordAbilities.KelpSword key = ability.getKey();
                switch (key) {
                    case ENTANGLEMENT:
                        entanglement(ability.getName(), stack, player, attatchedEntity);
                        break;
                    case ABSORPTION:
                        absorption(ability.getName(), stack, player, attatchedEntity);
                        break;
                    case FORCE_OF_TIDAL:
                        if (attatchedEntity == null) return;
                        forceOfTidal(ability.getName(), stack, player, attatchedEntity);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 缠绕（攻击触发）
     */
    public void entanglement(String name, ItemStack stack, Player pPlayer, Entity attackedEntity) {
        CircleMod.LOGGER.debug(name);
        if ((attackedEntity instanceof LivingEntity)) {
            ((LivingEntity) attackedEntity).addEffect(new MobEffectInstance(ModEffects.ENTANGLE_EFFECT.get(), ENTANGLEMENT_TICKS), pPlayer);
        }
    }

    /**
     * 水分吸收（攻击触发）
     */
    public void absorption(String name, ItemStack stack, Player pPlayer, Entity attackedEntity) {
        CircleMod.LOGGER.debug(name);
        if (attackedEntity instanceof LivingEntity) {
            MobEffectInstance effect = ((LivingEntity) attackedEntity).getEffect(MobEffects.WEAKNESS);
            if (effect == null) {
                ((LivingEntity) attackedEntity).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, ABSORPTION_TICKS), pPlayer);
            } else {
                int amplifier = effect.getAmplifier() + 1;
                ((LivingEntity) attackedEntity).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, ABSORPTION_TICKS, amplifier), pPlayer);
            }
        }
    }

    /**
     * 护盾（use触发）
     */
    public void shield(ItemStack stack, Player pPlayer) {
        this.shieldEntity = new ShieldEntity(ModEntities.SHIELD_ENTITY.get(), pPlayer.level);
        this.shieldEntity.moveTo(pPlayer.position());
        pPlayer.level.addFreshEntity(this.shieldEntity);
    }

    /**
     * 潮汐之力 （攻击触发）
     */
    public void forceOfTidal(String name, ItemStack stack, Player pPlayer, Entity attackedEntity) {
        CircleMod.LOGGER.debug(name);
        Level level = attackedEntity.level;
        BlockPos pos = attackedEntity.blockPosition();
        level.setBlock(pos, Blocks.WATER.defaultBlockState(), 3);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }, 1500);
    }

    public boolean hasShield(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        ListTag list = tag.getList(ABILITI_NBT_KEY, Tag.TAG_STRING);
        return list.contains(StringTag.valueOf(SwordAbilities.KelpSword.SHIELD.toString()));
    }
}
