package baguchan.funkyyoyo.register;

import baguchan.funkyyoyo.FunkyYoyo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FunkyYoyo.MODID);

    public static final RegistryObject<SoundEvent> ELECTRIC = createEvent("entity.yoyo.electric");

    private static RegistryObject<SoundEvent> createEvent(String sound) {
        ResourceLocation name = new ResourceLocation(FunkyYoyo.MODID, sound);
        return SOUND_EVENTS.register(sound, () -> SoundEvent.createVariableRangeEvent(name));
    }

}
