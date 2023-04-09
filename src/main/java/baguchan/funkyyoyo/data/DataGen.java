package baguchan.funkyyoyo.data;

import baguchan.funkyyoyo.FunkyYoyo;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = FunkyYoyo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        BlockTagsProvider blocktags = new BlockTagGenerator(packOutput, lookupProvider, event.getExistingFileHelper());
        event.getGenerator().addProvider(event.includeServer(), blocktags);
        generator.addProvider(event.includeServer(), new DamageTypeTagGenerator(packOutput, lookupProvider, existingFileHelper));

        event.getGenerator().addProvider(event.includeServer(), new CraftingGenerator(packOutput));
    }
}