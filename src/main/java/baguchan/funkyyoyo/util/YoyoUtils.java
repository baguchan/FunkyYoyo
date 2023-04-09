package baguchan.funkyyoyo.util;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.yoyocore.YoyoCore;
import baguchan.funkyyoyo.yoyopower.YoyoPower;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
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

    public static ItemStack randomMakeYoyo(RandomSource random, ItemStack stack) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        FunkyYoyo.registryAccess().registryOrThrow(YoyoCore.REGISTRY_KEY).getRandom(random).ifPresent(yoyoCoreReference -> {
            compoundTag.putString(TAG_YOYO_CORE, yoyoCoreReference.key().location().toString());
        });
        FunkyYoyo.registryAccess().registryOrThrow(YoyoSide.REGISTRY_KEY).getRandom(random).ifPresent(yoyoSideReference -> {
            compoundTag.putString(TAG_YOYO_SIDE, yoyoSideReference.key().location().toString());
        });
        return stack;
    }

    @Nullable
    public static YoyoSide getYoyoSide(ResourceKey<YoyoSide> resourceLocation) {
        return FunkyYoyo.registryAccess().registryOrThrow(YoyoSide.REGISTRY_KEY).get(resourceLocation);
    }

    @Nullable
    public static YoyoCore getYoyoCore(ResourceKey<YoyoCore> resourceLocation) {
        return FunkyYoyo.registryAccess().registryOrThrow(YoyoCore.REGISTRY_KEY).get(resourceLocation);
    }

    @Nullable
    public static YoyoPower getPower(ResourceKey<YoyoPower> resourceLocation) {
        return FunkyYoyo.registryAccess().registryOrThrow(YoyoPower.REGISTRY_KEY).get(resourceLocation);
    }

    @Nullable
    public static YoyoSide getYoyoSide(ResourceLocation resourceLocation) {
        return FunkyYoyo.registryAccess().registryOrThrow(YoyoSide.REGISTRY_KEY).get(resourceLocation);
    }

    @Nullable
    public static YoyoCore getYoyoCore(ResourceLocation resourceLocation) {
        return FunkyYoyo.registryAccess().registryOrThrow(YoyoCore.REGISTRY_KEY).get(resourceLocation);
    }

    @Nullable
    public static YoyoPower getPower(ResourceLocation resourceLocation) {
        return FunkyYoyo.registryAccess().registryOrThrow(YoyoPower.REGISTRY_KEY).get(resourceLocation);
    }

    @Nullable
    public static ResourceKey<YoyoSide> getYoyoSideKey(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null) {
            YoyoSide side = YoyoUtils.getYoyoSide(ResourceLocation.tryParse(compoundTag.getString(TAG_YOYO_SIDE)));
            if (side != null) {
                return FunkyYoyo.registryAccess().registryOrThrow(YoyoSide.REGISTRY_KEY).getResourceKey(side).get();
            }
        }
        return null;
    }

    @Nullable
    public static ResourceKey<YoyoCore> getYoyoCoreKey(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null) {
            YoyoCore core = YoyoUtils.getYoyoCore(ResourceLocation.tryParse(compoundTag.getString(TAG_YOYO_CORE)));
            if (core != null) {
                return FunkyYoyo.registryAccess().registryOrThrow(YoyoCore.REGISTRY_KEY).getResourceKey(core).get();
            }
        }
        return null;
    }

    @Nullable
    public static YoyoSide getYoyoSide(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null) {
            return FunkyYoyo.registryAccess().registryOrThrow(YoyoSide.REGISTRY_KEY).get(ResourceLocation.tryParse(compoundTag.getString(TAG_YOYO_SIDE)));
        }

        return null;
    }

    @Nullable
    public static YoyoCore getYoyoCore(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null) {
            return FunkyYoyo.registryAccess().registryOrThrow(YoyoCore.REGISTRY_KEY).get(ResourceLocation.tryParse(compoundTag.getString(TAG_YOYO_CORE)));
        }

        return null;
    }


}