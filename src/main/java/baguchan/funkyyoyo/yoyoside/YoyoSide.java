package baguchan.funkyyoyo.yoyoside;

import baguchan.funkyyoyo.FunkyYoyo;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;

public class YoyoSide {
    public static final Codec<YoyoSide> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(
                    ResourceLocation.CODEC.fieldOf("material_id").forGetter(yoyoCore -> yoyoCore.materialID),
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(yoyoCore -> yoyoCore.texture),
                    Codec.list(ResourceLocation.CODEC).fieldOf("power_id_list").orElse(Collections.emptyList()).forGetter(materialModifier -> materialModifier.powerIdList),
                    Codec.INT.fieldOf("attack_damage").forGetter(yoyoCore -> yoyoCore.attackDamage),
                    Codec.FLOAT.fieldOf("speed_decrease").orElse(0.0F).forGetter(yoyoCore -> yoyoCore.speedDecrease))
            .apply(instance, YoyoSide::new)
    );

    public static final ResourceKey<Registry<YoyoSide>> REGISTRY_KEY = ResourceKey
            .createRegistryKey(new ResourceLocation(FunkyYoyo.MODID, "yoyo_side"));
    private final ResourceLocation materialID;
    private final ResourceLocation texture;
    private final List<ResourceLocation> powerIdList;
    private final int attackDamage;
    private final float speedDecrease;

    public YoyoSide(ResourceLocation materialID, ResourceLocation texture, List<ResourceLocation> powerIdList, int attackDamage, float speedDecrease) {
        this.materialID = materialID;
        this.texture = texture;
        this.powerIdList = powerIdList;
        this.attackDamage = attackDamage;
        this.speedDecrease = speedDecrease;
    }

    public ResourceLocation getMaterialId() {
        return materialID;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public List<ResourceLocation> getPowerIdList() {
        return powerIdList;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public float getSpeedDecrease() {
        return speedDecrease;
    }
}