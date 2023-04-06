package baguchan.funkyyoyo.client.render;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.client.model.FunkerModel;
import baguchan.funkyyoyo.entity.Funker;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.resources.ResourceLocation;

public class FunkerRenderer<T extends Funker> extends IllagerRenderer<T> {
    private static final ResourceLocation ILLAGER = new ResourceLocation(FunkyYoyo.MODID, "textures/entity/funker.png");

    public FunkerRenderer(EntityRendererProvider.Context p_174182_) {
        super(p_174182_, new FunkerModel<>(p_174182_.bakeLayer(ModelLayers.VINDICATOR)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(T p_114482_) {
        return ILLAGER;
    }
}
