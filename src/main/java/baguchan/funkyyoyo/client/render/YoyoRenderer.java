package baguchan.funkyyoyo.client.render;

import baguchan.funkyyoyo.client.ModModelLayers;
import baguchan.funkyyoyo.client.model.YoyoModel;
import baguchan.funkyyoyo.entity.Yoyo;
import baguchan.funkyyoyo.util.YoyoUtils;
import baguchan.funkyyoyo.yoyocore.YoyoCore;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class YoyoRenderer extends EntityRenderer<Yoyo> {

    private final YoyoModel<Yoyo> model;

    public YoyoRenderer(EntityRendererProvider.Context p_174420_) {
        super(p_174420_);
        this.model = new YoyoModel<Yoyo>(p_174420_.bakeLayer(ModModelLayers.YOYO));
    }

    @Override
    public void render(Yoyo entity, float p_116112_, float partialTicks, PoseStack stackIn, MultiBufferSource p_116115_, int p_116116_) {
        stackIn.pushPose();

        stackIn.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot())));
        stackIn.mulPose(Axis.XP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        stackIn.mulPose(Axis.XP.rotationDegrees((float) ((entity.tickCount + partialTicks) * 40F + (entity.getSpeed() * 60F))));


        stackIn.translate(0.0F, -1.501F + 0.4F, 0);
        YoyoCore core = YoyoUtils.getYoyoCore(entity.getItem());
        if(core != null) {
            VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(p_116115_, this.model.renderType(core.getTexture()), false, entity.getItem().hasFoil());
            this.model.renderCore(stackIn, vertexconsumer, p_116116_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        YoyoSide side = YoyoUtils.getYoyoSide(entity.getItem());
        if(side != null) {
            VertexConsumer vertexconsumer2 = ItemRenderer.getFoilBufferDirect(p_116115_, this.model.renderType(side.getTexture()), false, entity.getItem().hasFoil());
            this.model.renderCore(stackIn, vertexconsumer2, p_116116_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        stackIn.popPose();

        Entity owner = entity.getOwner();
        if (owner != null)
        {
            this.renderLeash(entity, partialTicks, stackIn, p_116115_, owner);
        }
        super.render(entity, p_116112_, partialTicks, stackIn, p_116115_, p_116116_);
    }



    private <E extends Entity> void renderLeash(Yoyo p_115462_, float p_115463_, PoseStack p_115464_, MultiBufferSource p_115465_, E p_115466_) {
        p_115464_.pushPose();
        Vec3 vec3 = p_115466_.getRopeHoldPosition(p_115463_);
        double d0 = (double)(Mth.lerp(p_115463_, p_115462_.yRotO, p_115462_.getYRot()) * ((float)Math.PI / 180F)) + (Math.PI / 2D);
        Vec3 vec31 = p_115462_.getLeashOffset(p_115463_);
        double d1 = Math.cos(d0) * vec31.z + Math.sin(d0) * vec31.x;
        double d2 = Math.sin(d0) * vec31.z - Math.cos(d0) * vec31.x;
        double d3 = Mth.lerp((double)p_115463_, p_115462_.xo, p_115462_.getX()) + d1;
        double d4 = Mth.lerp((double)p_115463_, p_115462_.yo, p_115462_.getY()) + vec31.y;
        double d5 = Mth.lerp((double)p_115463_, p_115462_.zo, p_115462_.getZ()) + d2;
        p_115464_.translate(d1, vec31.y, d2);
        float f = (float)(vec3.x - d3);
        float f1 = (float)(vec3.y - d4);
        float f2 = (float)(vec3.z - d5);
        float f3 = 0.025F;
        VertexConsumer vertexconsumer = p_115465_.getBuffer(RenderType.leash());
        Matrix4f matrix4f = p_115464_.last().pose();
        float f4 = Mth.invSqrt(f * f + f2 * f2) * 0.025F / 2.0F;
        float f5 = f2 * f4;
        float f6 = f * f4;
        BlockPos blockpos = BlockPos.containing(p_115462_.getEyePosition(p_115463_));
        BlockPos blockpos1 = BlockPos.containing(p_115466_.getEyePosition(p_115463_));
        int i = this.getBlockLightLevel(p_115462_, blockpos);
        int j = p_115466_.isOnFire() ? 15 : p_115466_.level.getBrightness(LightLayer.BLOCK, blockpos1);
        int k = p_115462_.level.getBrightness(LightLayer.SKY, blockpos);
        int l = p_115462_.level.getBrightness(LightLayer.SKY, blockpos1);

        for(int i1 = 0; i1 <= 24; ++i1) {
            addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, i1, false);
        }

        for(int j1 = 24; j1 >= 0; --j1) {
            addVertexPair(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true);
        }

        p_115464_.popPose();
    }

    private static void addVertexPair(VertexConsumer p_174308_, Matrix4f p_254405_, float p_174310_, float p_174311_, float p_174312_, int p_174313_, int p_174314_, int p_174315_, int p_174316_, float p_174317_, float p_174318_, float p_174319_, float p_174320_, int p_174321_, boolean p_174322_) {
        float f = (float)p_174321_ / 24.0F;
        int i = (int)Mth.lerp(f, (float)p_174313_, (float)p_174314_);
        int j = (int)Mth.lerp(f, (float)p_174315_, (float)p_174316_);
        int k = LightTexture.pack(i, j);
        float f1 = p_174321_ % 2 == (p_174322_ ? 1 : 0) ? 0.7F : 1.0F;
        float f2 = 0.5F * f1;
        float f3 = 0.4F * f1;
        float f4 = 0.3F * f1;
        float f5 = p_174310_ * f;
        float f6 = p_174311_ > 0.0F ? p_174311_ * f * f : p_174311_ - p_174311_ * (1.0F - f) * (1.0F - f);
        float f7 = p_174312_ * f;
        p_174308_.vertex(p_254405_, f5 - p_174319_, f6 + p_174318_, f7 + p_174320_).color(f2, f3, f4, 1.0F).uv2(k).endVertex();
        p_174308_.vertex(p_254405_, f5 + p_174319_, f6 + p_174317_ - p_174318_, f7 - p_174320_).color(f2, f3, f4, 1.0F).uv2(k).endVertex();
    }

    public ResourceLocation getTextureLocation(Yoyo p_116109_) {
        return null;
    }
}