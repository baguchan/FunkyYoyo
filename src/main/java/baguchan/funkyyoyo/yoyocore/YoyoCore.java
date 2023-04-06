package baguchan.funkyyoyo.yoyocore;

import baguchan.funkyyoyo.FunkyYoyo;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class YoyoCore {
    public static final Codec<YoyoCore> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    ResourceLocation.CODEC.fieldOf("material_id").forGetter(yoyoCore -> yoyoCore.materialID),
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(yoyoCore -> yoyoCore.texture),
                    Codec.INT.fieldOf("durability").forGetter(yoyoCore -> yoyoCore.durability))
            .apply(instance, YoyoCore::new)
    );

    public static final ResourceKey<Registry<YoyoCore>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(FunkyYoyo.MODID, "yoyo_core"));
    private final ResourceLocation materialID;
    private final ResourceLocation texture;
    private final int durability;

    public YoyoCore(ResourceLocation materialID, ResourceLocation texture, int durability) {
        this.materialID = materialID;
        this.texture = texture;
        this.durability = durability;
    }

    public ResourceLocation getMaterialId() {
        return materialID;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getDurability() {
        return durability;
    }
}