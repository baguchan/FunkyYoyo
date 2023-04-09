package baguchan.funkyyoyo.yoyopower;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.entity.Yoyo;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public abstract class YoyoPower {
    public static final ResourceKey<Registry<YoyoPower>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(FunkyYoyo.MODID, "yoyo_power"));

    /*
     * this method make extra damage.
     */
    public abstract void applyDamageEffect(Yoyo entity, Entity throwedEntity, @Nullable Entity owner);

    /*
     * this method call every tick.
     */
    public abstract void applyTick(Yoyo entity);
}
