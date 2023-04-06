package baguchan.funkyyoyo.item;

import baguchan.funkyyoyo.client.render.item.YoyoCorePartItemBWLR;
import baguchan.funkyyoyo.util.YoyoUtils;
import baguchan.funkyyoyo.yoyocore.YoyoCore;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class YoyoCoreItem extends Item {

    private final ResourceLocation partId;

    public YoyoCoreItem(Properties properties, ResourceLocation partId) {
        super(properties);
        this.partId = partId;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        YoyoCore yoyoCore = YoyoUtils.getYoyoCore(stack);
        if (yoyoCore != null) {
            return yoyoCore.getDurability();
        }
        return super.getMaxDamage(stack);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new YoyoCorePartItemBWLR(partId);
            }
        });
    }

}
