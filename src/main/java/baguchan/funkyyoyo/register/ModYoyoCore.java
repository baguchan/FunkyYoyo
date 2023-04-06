package baguchan.funkyyoyo.register;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.yoyocore.YoyoCore;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class ModYoyoCore {
    public static final DeferredRegister<YoyoCore> YOYO_CORE = DeferredRegister.create(YoyoCore.REGISTRY_KEY,
            FunkyYoyo.MODID);
    public static final Supplier<IForgeRegistry<YoyoCore>> YOYO_CORE_REGISTRY = YOYO_CORE.makeRegistry(
            () -> new RegistryBuilder<YoyoCore>().disableSaving());
}