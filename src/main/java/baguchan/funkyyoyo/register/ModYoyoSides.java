package baguchan.funkyyoyo.register;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class ModYoyoSides {
    public static final DeferredRegister<YoyoSide> YOYO_SIDE = DeferredRegister.create(YoyoSide.REGISTRY_KEY,
            FunkyYoyo.MODID);
    public static final Supplier<IForgeRegistry<YoyoSide>> YOYO_SIDE_REGISTRY = YOYO_SIDE.makeRegistry(
            () -> new RegistryBuilder<YoyoSide>().disableSaving());


    public static final ResourceKey<YoyoSide> COPPER = key(new ResourceLocation(FunkyYoyo.MODID, "copper"));
    public static final ResourceKey<YoyoSide> IRON = key(new ResourceLocation(FunkyYoyo.MODID, "iron"));
    public static final ResourceKey<YoyoSide> WOOD = key(new ResourceLocation(FunkyYoyo.MODID, "wood"));

    public static ResourceKey<YoyoSide> key(ResourceLocation name) {
        return ResourceKey.create(YoyoSide.REGISTRY_KEY, name);
    }
}