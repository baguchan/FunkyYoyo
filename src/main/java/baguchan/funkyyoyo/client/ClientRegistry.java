package baguchan.funkyyoyo.client;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.client.model.YoyoModel;
import baguchan.funkyyoyo.client.render.YoyoRenderer;
import baguchan.funkyyoyo.client.screen.YoyoTableScreen;
import baguchan.funkyyoyo.register.ModEntities;
import baguchan.funkyyoyo.register.ModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = FunkyYoyo.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistry {
    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.YOYO.get(), YoyoRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.YOYO, YoyoModel::createBodyLayer);
    }


    public static void setup(FMLCommonSetupEvent event) {

        MenuScreens.register(ModMenus.YOYO_TABLE.get(), YoyoTableScreen::new);
    }
}
