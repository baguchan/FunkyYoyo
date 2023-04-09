package baguchan.funkyyoyo.data;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.register.ModDamageSources;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagGenerator extends TagsProvider<DamageType> {
    public DamageTypeTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> future, ExistingFileHelper helper) {
        super(output, Registries.DAMAGE_TYPE, future, FunkyYoyo.MODID, helper);
    }

    protected void addTags(HolderLookup.Provider provider) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(ModDamageSources.LIGHTNING_THROWN);
        this.tag(DamageTypeTags.IS_LIGHTNING).add(ModDamageSources.LIGHTNING_THROWN);
    }
}