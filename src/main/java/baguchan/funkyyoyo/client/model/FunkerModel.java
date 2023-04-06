package baguchan.funkyyoyo.client.model;// Made with Blockbench 4.6.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import baguchan.funkyyoyo.entity.Funker;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;

public class FunkerModel<T extends Funker> extends IllagerModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor

    public FunkerModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(T p_102928_, float p_102929_, float p_102930_, float p_102931_, float p_102932_, float p_102933_) {
        super.setupAnim(p_102928_, p_102929_, p_102930_, p_102931_, p_102932_, p_102933_);
        this.getHat().visible = true;
    }
}