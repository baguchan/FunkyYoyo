package baguchan.funkyyoyo.register;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.yoyocore.YoyoCore;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class ModYoyoCores {
    public static final DeferredRegister<YoyoCore> YOYO_CORE = DeferredRegister.create(YoyoCore.REGISTRY_KEY,
            FunkyYoyo.MODID);
    public static final Supplier<IForgeRegistry<YoyoCore>> YOYO_CORE_REGISTRY = YOYO_CORE.makeRegistry(
            () -> new RegistryBuilder<YoyoCore>().disableSaving());

    public static final ResourceKey<YoyoCore> COPPER = key(new ResourceLocation(FunkyYoyo.MODID, "copper"));
    public static final ResourceKey<YoyoCore> IRON = key(new ResourceLocation(FunkyYoyo.MODID, "iron"));
    public static final ResourceKey<YoyoCore> WOOD = key(new ResourceLocation(FunkyYoyo.MODID, "wood"));

    public static ResourceKey<YoyoCore> key(ResourceLocation name) {
        return ResourceKey.create(YoyoCore.REGISTRY_KEY, name);
    }

}