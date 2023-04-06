package baguchan.funkyyoyo.register;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.menu.YoyoTableMenu;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, FunkyYoyo.MODID);


    public static final RegistryObject<MenuType<YoyoTableMenu>> YOYO_TABLE = MENU_TYPES.register("yoyo_table", () -> new MenuType<>(YoyoTableMenu::new, FeatureFlags.DEFAULT_FLAGS));
}