package baguchan.funkyyoyo.client.render.item;

import baguchan.funkyyoyo.client.ModModelLayers;
import baguchan.funkyyoyo.client.model.YoyoModel;
import baguchan.funkyyoyo.util.YoyoUtils;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class YoyoPartItemBWLR extends BlockEntityWithoutLevelRenderer {
    private YoyoModel yoyoModel;
    private final ResourceLocation partID;

    public YoyoPartItemBWLR(ResourceLocation partID) {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.yoyoModel = new YoyoModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.YOYO));
        this.partID = partID;
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pOverlay) {
        pPoseStack.pushPose();
        YoyoSide side = YoyoUtils.getYoyoSide(this.partID);
        if (side != null) {
            VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(pBuffer, this.yoyoModel.renderType(side.getTexture()), true, pStack.hasFoil());
            this.yoyoModel.renderSide(pPoseStack, vertexconsumer, pPackedLight, pOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        pPoseStack.popPose();
    }
}