package baguchan.funkyyoyo.register;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.item.YoyoItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FunkyYoyo.MODID);
    public static final RegistryObject<Item> YOYO = ITEMS.register("yoyo", () -> new YoyoItem((new Item.Properties().stacksTo(1).durability(120))));
    public static final RegistryObject<Item> FUNKER_SPAWN_EGG = ITEMS.register("funker_spawn_egg", () -> new ForgeSpawnEggItem(ModEntities.FUNKER, 0x959B9B, 0xC0532F, (new Item.Properties())));

}
