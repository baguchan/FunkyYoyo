package baguchan.funkyyoyo.client.screen;

import baguchan.funkyyoyo.menu.YoyoTableMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nullable;

public class YoyoTableScreen extends ItemCombinerScreen<YoyoTableMenu> {
    private static final ResourceLocation SMITHING_LOCATION = new ResourceLocation("textures/gui/container/legacy_smithing.png");

    @Nullable
    private ArmorStand armorStandPreview;

    public YoyoTableScreen(YoyoTableMenu p_266823_, Inventory p_266925_, Component p_266749_) {
        super(p_266823_, p_266925_, p_266749_, SMITHING_LOCATION);
        this.titleLabelX = 44;
        this.titleLabelY = 15;
    }

    protected void renderErrorIcon(PoseStack p_267095_, int p_267270_, int p_266714_) {
        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(this.menu.getResultSlot()).hasItem()) {
            blit(p_267095_, p_267270_ + 65, p_266714_ + 46, this.imageWidth, 0, 28, 21);
        }

    }
}