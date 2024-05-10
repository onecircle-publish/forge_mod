package com.circle.circlemod.item.sword.kelp;

import com.circle.circlemod.CircleMod;
import com.circle.circlemod.item.sword.SwordAbilities;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

/**
 * @author yuanxin
 * @date 2024-05-07 15:12
 **/
public class KelpSwordItem extends SwordItem {
    public final int MAX_ABILITY_COUNT = 2;// 一次性产生的技能数量
    public final int waterPerceptionRadius = 100;//水之感知的半径
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
        useAbilities(SwordAbilities.UseType.CLICK, stack, player, entity);
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack item = pPlayer.getItemInHand(pUsedHand);

        //父类中逻辑，不可食用则不会触发startingUsingItem，这里手动调用。
        pPlayer.startUsingItem(pUsedHand);
        // 使用技能
        useAbilities(SwordAbilities.UseType.USE, item, pPlayer, null);
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
    public void useAbilities(SwordAbilities.UseType type, ItemStack stack, Player player, @Nullable Entity attatchedEntity) {
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
                        if (attatchedEntity == null) return;
                        forceOfTidal(ability.getName(), stack, player, attatchedEntity);
                        break;
                    case WATER_PERCEPTION:
                        waterPerception(ability.getName(), stack, player);
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
    public void forceOfTidal(String name, ItemStack stack, Player pPlayer, Entity attackedEntity) {
        CircleMod.LOGGER.debug(name);
//        Vec3 centerPos = attackedEntity.position().add(0, attackedEntity.getBbHeight() / 2, 0); // 中心位置略高于实体中心
//        double radius = 2; // 流动水区域的半径
//        Level level = pPlayer.level;
//
//        // 使用粒子效果模拟水流
//        for (double angle = 0; angle < Math.PI * 2; angle += Math.PI / 8) { // 分8个方向发射粒子
//            Vec3 direction = new Vec3(Math.cos(angle), 0, Math.sin(angle)).normalize().scale(0.6); // 调整速度
//            level.addParticle(ParticleTypes.BUBBLE, centerPos.x, centerPos.y, centerPos.z, direction.x, 0.1, direction.z);
//        }
    }

    /**
     * 水之感知（use触发）
     */
    public void waterPerception(String name, ItemStack stack, Player pPlayer) {
        Level level = pPlayer.level;
        Vec3 eyePosition = pPlayer.getEyePosition();
        BlockPos searchCenter = new BlockPos(eyePosition);
        AABB searchArea = new AABB(searchCenter).inflate(waterPerceptionRadius);

        Optional<BlockPos> neareastWaterPos = BlockPos.betweenClosedStream(searchArea).filter(pos -> level.getBlockState(pos).getFluidState().getType() == Fluids.WATER).map(pos -> new BlockPos(pos)).min(Comparator.comparingDouble(pos -> pos.distSqr(searchCenter)));

        if (neareastWaterPos.isPresent()) {
            BlockPos targetPos = neareastWaterPos.get();
            CircleMod.LOGGER.debug("最近{}格内水方块位置：{}", waterPerceptionRadius, targetPos);
            waterPerceptionParticles(level, searchCenter, targetPos);
        } else {
            CircleMod.LOGGER.debug("没有在{}格内找到水方块", waterPerceptionRadius);
        }
    }

    public void waterPerceptionParticles(Level level, BlockPos start, BlockPos end) {
        double dist = start.distSqr(end);
        double xDuration = end.getX() - start.getX();
        double zDuration = end.getZ() - start.getZ();

        double step = 0.5;
        double allStep = Math.sqrt(dist) / step;

        for (double i = 0; i < allStep; i += step) {
            double t = i / allStep;
            double nextX = start.getX() + t * xDuration;
            double nextY = start.getZ();
            double nextZ = start.getZ() + t * zDuration;

            level.addParticle(ParticleTypes.COMPOSTER, nextX, nextY, nextZ, 0, 0, 0);
        }

//        for (double i = 0; i < waterPerceptionRadius; i++) {
//            double stepX = (end.getX() + 0.5 - start.getX()) / waterPerceptionRadius;
//            double stepY = (double) (end.getY() + 1 - start.getY()) / waterPerceptionRadius;
//            double stepZ = (end.getZ() + 0.5 - start.getZ()) / waterPerceptionRadius;
//            double nextX = start.getX() + stepX * i;
//            double nextY = start.getY() + stepY * i;
//            double nextZ = start.getZ() + stepZ * i;

//            level.addParticle(ParticleTypes.COMPOSTER, nextX, nextY, nextZ, 0, 0, 0);
//        }
    }
}
