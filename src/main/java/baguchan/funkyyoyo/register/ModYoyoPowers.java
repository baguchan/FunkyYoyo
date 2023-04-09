package baguchan.funkyyoyo.register;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.yoyopower.YoyoLightningPower;
import baguchan.funkyyoyo.yoyopower.YoyoPower;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModYoyoPowers {
    public static final DeferredRegister<YoyoPower> YOYO_POWER = DeferredRegister.create(YoyoPower.REGISTRY_KEY,
            FunkyYoyo.MODID);
    public static final Supplier<IForgeRegistry<YoyoPower>> YOYO_POWER_REGISTRY = YOYO_POWER.makeRegistry(
            () -> new RegistryBuilder<YoyoPower>());

    public static final RegistryObject<YoyoLightningPower> LIGHTNING = YOYO_POWER.register("lightning", YoyoLightningPower::new);
}