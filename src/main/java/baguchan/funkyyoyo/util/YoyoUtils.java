package baguchan.funkyyoyo.util;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.yoyocore.YoyoCore;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class YoyoUtils {
    public static final String TAG_YOYO_SIDE = "YoyoSide";
    public static final String TAG_YOYO_CORE = "YoyoCore";

    /*
     * Set Modifier Scale when take the item with has more material
     */
    public static ItemStack makeYoyo(YoyoCore yoyoCore, YoyoSide yoyoSide, ItemStack stack) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        ResourceLocation resourceLocation = FunkyYoyo.registryAccess().registryOrThrow(YoyoCore.REGISTRY_KEY).getKey(yoyoCore);
        ResourceLocation resourceLocation2 = FunkyYoyo.registryAccess().registryOrThrow(YoyoSide.REGISTRY_KEY).getKey(yoyoSide);
        compoundTag.putString(TAG_YOYO_CORE, resourceLocation.toString());
        compoundTag.putString(TAG_YOYO_SIDE, resourceLocation2.toString());
        return stack;
    }

    @Nullable
    public static YoyoSide getYoyoSide(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        if(compoundTag != null) {
            return FunkyYoyo.registryAccess().registryOrThrow(YoyoSide.REGISTRY_KEY).get(ResourceLocation.tryParse(compoundTag.getString(TAG_YOYO_SIDE)));
        }

        return null;
    }
    @Nullable
    public static YoyoCore getYoyoCore(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        if(compoundTag != null) {
            return FunkyYoyo.registryAccess().registryOrThrow(YoyoCore.REGISTRY_KEY).get(ResourceLocation.tryParse(compoundTag.getString(TAG_YOYO_CORE)));
        }

        return null;
    }


}