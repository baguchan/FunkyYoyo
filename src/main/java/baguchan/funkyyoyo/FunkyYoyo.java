package baguchan.funkyyoyo;

import baguchan.funkyyoyo.client.ClientRegistry;
import baguchan.funkyyoyo.item.YoyoItem;
import baguchan.funkyyoyo.register.*;
import baguchan.funkyyoyo.yoyocore.YoyoCore;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FunkyYoyo.MODID)
public class FunkyYoyo
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "funkyyoyo";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public FunkyYoyo()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModYoyoCore.YOYO_CORE.register(modEventBus);
        ModYoyoSide.YOYO_SIDE.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModMenus.MENU_TYPES.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::dataSetup);
        modEventBus.addListener(this::onRegisterCreativeTabs);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientRegistry::setup));
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        Raid.RaiderType.create("funkyyoyo:funker", ModEntities.FUNKER.get(), new int[]{0, 1, 1, 2, 1, 2, 3, 4});
    }

    private void dataSetup(final DataPackRegistryEvent.NewRegistry event)
    {
        event.dataPackRegistry(YoyoCore.REGISTRY_KEY, YoyoCore.CODEC, YoyoCore.CODEC);
        event.dataPackRegistry(YoyoSide.REGISTRY_KEY, YoyoSide.CODEC, YoyoSide.CODEC);
    }

    public void onRegisterCreativeTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(FunkyYoyo.MODID, "tofu_foods")
                , (builder) -> {
                    builder.icon(() -> {
                        return new ItemStack(ModBlocks.YOYO_TABLE.get());
                    }).title(Component.translatable("itemGroup." + FunkyYoyo.MODID + ".main")).displayItems((features, output) -> {
                        output.acceptAll(YoyoItem.generateYoyo());
                        output.acceptAll(BuiltInRegistries.ITEM.stream().filter(i -> {
                           return BuiltInRegistries.ITEM.getKey(i).getNamespace().equals(FunkyYoyo.MODID) && i != ModItems.YOYO.get();
                        }).map(i -> {
                            return new ItemStack(i);
                        }).toList());
                    }).build();
                });
    }

    public static RegistryAccess registryAccess() {
        if (EffectiveSide.get().isServer()) {
            return ServerLifecycleHooks.getCurrentServer().registryAccess();
        }
        return Minecraft.getInstance().getConnection().registryAccess();
    }
}
