package baguchan.funkyyoyo.register;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class ModYoyoSide {
    public static final DeferredRegister<YoyoSide> YOYO_SIDE = DeferredRegister.create(YoyoSide.REGISTRY_KEY,
            FunkyYoyo.MODID);
    public static final Supplier<IForgeRegistry<YoyoSide>> YOYO_SIDE_REGISTRY = YOYO_SIDE.makeRegistry(
            () -> new RegistryBuilder<YoyoSide>().disableSaving());
}