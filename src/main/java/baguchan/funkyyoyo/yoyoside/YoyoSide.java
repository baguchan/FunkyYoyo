package baguchan.funkyyoyo.yoyoside;

import baguchan.funkyyoyo.FunkyYoyo;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class YoyoSide {
    public static final Codec<YoyoSide> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    ResourceLocation.CODEC.fieldOf("material_id").forGetter(yoyoCore -> yoyoCore.materialID),
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(yoyoCore -> yoyoCore.texture),
                    Codec.INT.fieldOf("attack_damage").forGetter(yoyoCore -> yoyoCore.attackDamage))
            .apply(instance, YoyoSide::new)
    );

    public static final ResourceKey<Registry<YoyoSide>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(FunkyYoyo.MODID, "yoyo_side"));
    private final ResourceLocation materialID;
    private final ResourceLocation texture;
    private final int attackDamage;

    public YoyoSide(ResourceLocation materialID, ResourceLocation texture, int attackDamage) {
        this.materialID = materialID;
        this.texture = texture;
        this.attackDamage = attackDamage;
    }

    public ResourceLocation getMaterialId() {
        return materialID;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getAttackDamage() {
        return attackDamage;
    }
}