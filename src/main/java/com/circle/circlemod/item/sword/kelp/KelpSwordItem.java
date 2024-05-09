package com.circle.circlemod.item.sword.kelp;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.item.sword.SwordAbilities;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 海带剑
 */
public class KelpSwordItem extends SwordItem implements KelpSwordItemInterface {
    public static final int MAX_ABILITY_COUNT = 2;
    public static final String ABILITI_NBT_KEY = "KelpSwordAbilities";

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

//    @Override
//    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
//        super.initializeClient(consumer);
//        consumer.accept(new IItemRenderProperties() {
//            @Override
//            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
//                return IItemRenderProperties.super.getItemStackRenderer();
//            }
//        });
//
//    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    /**
     * 设置指定数量的能力
     * 设置nbt
     *
     * @param count
     */
    public void setRandomAbilities(ItemStack stack, int count) {
        ArrayList<SwordAbilities.CustomAbility> randomAbilities = generateMagics(count);

        setMagicTags(stack, randomAbilities);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        useAbilities(stack, SwordAbilities.UseType.CLICK);
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);

        //父类中逻辑，不可食用则不会触发startingUsingItem，这里手动调用。
        pPlayer.startUsingItem(pUsedHand);
        // 使用技能
        useAbilities(item, SwordAbilities.UseType.USE);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    /**
     * 添加技能的hoverText
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
        SwordAbilities.CustomAbility allAbilities = SwordAbilities.KelpSword.getAbilities();
        ArrayList<Object> abilitiyKeys = new ArrayList<>();//随机到的指定数量的技能的key
        ArrayList<SwordAbilities.CustomAbility> targetAbilities = new ArrayList<>();//根据key，存储的技能对象

        // 取从0到count之间的随机数，随机数用来从allAbilities中取技能
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < count; i++) {
            int randomInt = random.nextInt(allAbilities.getAbilityHashMap().size());
            CircleMod.LOGGER.debug(String.valueOf(randomInt));
            while (abilitiyKeys.contains(randomInt)) {
                randomInt = random.nextInt(allAbilities.getAbilityHashMap().size());
            }
            abilitiyKeys.add(allAbilities.getAbilityHashMap().keySet().toArray()[i]);//对应索引下的CustomAbility对象
        }
        abilitiyKeys.forEach(key -> {
            targetAbilities.add(allAbilities.getAbilityHashMap().get(key));
        });
        return targetAbilities;
    }

    /**
     * 筛选出触发方式和技能使用方式相同的技能key，然后触发技能
     *
     * @param stack
     * @param type
     */
    public void useAbilities(ItemStack stack, SwordAbilities.UseType type) {
        ListTag list = stack.getOrCreateTag().getList(ABILITI_NBT_KEY, Tag.TAG_STRING);
        HashMap<SwordAbilities.KelpSword, SwordAbilities.CustomAbility> allAbilities = SwordAbilities.KelpSword.getAbilities().getAbilityHashMap();
        list.forEach(tag -> {
            SwordAbilities.CustomAbility ability = allAbilities.get(SwordAbilities.KelpSword.valueOf(tag.getAsString()));

            if (ability.getType() == type) {
                SwordAbilities.KelpSword key = ability.getKey();
                switch (key) {
                    case ENTANGLEMENT:
                        entanglement(ability.getName());
                        break;
                    case ABSORPTION:
                        absorption(ability.getName());
                        break;
                    case SHIELD:
                        shield(ability.getName());
                        break;
                    case REGENERATION:
                        regeneration(ability.getName());
                        break;
                    case FORCE_OF_TIDAL:
                        forceOfTidal(ability.getName());
                        break;
                    case WATER_PERCEPTION:
                        waterPerception(ability.getName());
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 缠绕（攻击触发）
     */
    public void entanglement(String name) {
        CircleMod.LOGGER.debug(name);
    }

    /**
     * 水分吸收（攻击触发）
     */
    public void absorption(String name) {
        CircleMod.LOGGER.debug(name);
    }

    /**
     * 护盾（use触发）
     */
    public void shield(String name) {
        CircleMod.LOGGER.debug(name);
    }

    /**
     * 再生（usingTick）
     */
    public void regeneration(String name) {
        CircleMod.LOGGER.debug(name);
    }

    /**
     * 潮汐之力 （攻击触发）
     */
    public void forceOfTidal(String name) {
        CircleMod.LOGGER.debug(name);
    }

    /**
     * 水之感知（use触发）
     */
    public void waterPerception(String name) {
        CircleMod.LOGGER.debug(name);
    }
}
