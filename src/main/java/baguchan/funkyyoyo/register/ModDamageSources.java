package baguchan.funkyyoyo.register;

import baguchan.funkyyoyo.FunkyYoyo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public interface ModDamageSources {
    ResourceKey<DamageType> LIGHTNING_THROWN = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(FunkyYoyo.MODID, "lightning_thrown"));

}