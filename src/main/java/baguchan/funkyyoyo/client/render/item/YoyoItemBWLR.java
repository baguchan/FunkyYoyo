package baguchan.funkyyoyo.client.render.item;

import baguchan.funkyyoyo.client.ModModelLayers;
import baguchan.funkyyoyo.client.model.YoyoModel;
import baguchan.funkyyoyo.register.ModItems;
import baguchan.funkyyoyo.util.YoyoUtils;
import baguchan.funkyyoyo.yoyocore.YoyoCore;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class YoyoItemBWLR extends BlockEntityWithoutLevelRenderer {
    private YoyoModel yoyoModel;

    public YoyoItemBWLR() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.yoyoModel = new YoyoModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModModelLayers.YOYO));
    }

    @Override
    public void renderByItem(ItemStack pStack, ItemDisplayContext pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pOverlay) {
        if (pStack.is(ModItems.YOYO.get())) {
            pPoseStack.pushPose();
            YoyoCore core = YoyoUtils.getYoyoCore(pStack);
            if(core != null) {
                VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(pBuffer, this.yoyoModel.renderType(core.getTexture()), true, pStack.hasFoil());

                this.yoyoModel.renderAttach(pPoseStack, vertexconsumer, pPackedLight, pOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            YoyoSide side = YoyoUtils.getYoyoSide(pStack);
            if(side != null) {
                VertexConsumer vertexconsumer = ItemRenderer.getFoilBufferDirect(pBuffer, this.yoyoModel.renderType(side.getTexture()), true, pStack.hasFoil());

                this.yoyoModel.renderSide(pPoseStack, vertexconsumer, pPackedLight, pOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }
            pPoseStack.popPose();
        }
    }
}