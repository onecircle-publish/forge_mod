package com.circle.circlemod.common.item.firecracker;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

/**
 * 鞭炮
 *
 * @author : yuanxin
 * @date 2024/11/17
 */
public class FireCracker extends Item implements GeoItem {

    public final AnimatableInstanceCache instanceCache = GeckoLibUtil.createInstanceCache(this);

    public FireCracker(Properties pProperties) {
        super(pProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    /**
     * 寄存器控制器
     *
     * @param controllerRegistrar 控制者注册商
     */
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    /**
     * 获取可动画实例缓存
     *
     * @return {@link AnimatableInstanceCache }
     */
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return instanceCache;
    }

    /**
     * 初始化客户端
     *
     * @param consumer 消费者
     */
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private FireCrackerModelRenderer renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (this.renderer == null) {
                    this.renderer = new FireCrackerModelRenderer();
                }
                return renderer;
            }
        });
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            Explosion explosion = new Explosion(pLevel, pPlayer, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), 1, false, Explosion.BlockInteraction.DESTROY);
            explosion.explode();
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
