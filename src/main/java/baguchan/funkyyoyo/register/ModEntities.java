package baguchan.funkyyoyo.register;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.entity.Funker;
import baguchan.funkyyoyo.entity.Yoyo;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = FunkyYoyo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            FunkyYoyo.MODID);

    public static final RegistryObject<EntityType<Funker>> FUNKER = ENTITIES.register("funker", () -> EntityType.Builder.of(Funker::new, MobCategory.MONSTER).sized(0.6F, 1.95F).build(prefix("funker")));


    public static final RegistryObject<EntityType<Yoyo>> YOYO = ENTITIES.register("yoyo", () -> EntityType.Builder.<Yoyo>of(Yoyo::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build(prefix("yoyo")));


    private static String prefix(String path) {
        return FunkyYoyo.MODID + "." + path;
    }

    @SubscribeEvent
    public static void registerEntityAttribute(EntityAttributeCreationEvent event) {
        event.put(FUNKER.get(), Funker.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacement(SpawnPlacementRegisterEvent event) {
        event.register(FUNKER.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

}