package baguchan.funkyyoyo.client.screen;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.menu.YoyoTableMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaternionf;

import javax.annotation.Nullable;

public class YoyoTableScreen extends ItemCombinerScreen<YoyoTableMenu> {
    private static final ResourceLocation SMITHING_LOCATION = new ResourceLocation(FunkyYoyo.MODID, "textures/gui/container/yoyo_table.png");

    public static final Quaternionf ARMOR_STAND_ANGLE = (new Quaternionf()).rotationXYZ(0.43633232F, 0.0F, (float) Math.PI);

    @Nullable
    private ArmorStand armorStandPreview;

    public YoyoTableScreen(YoyoTableMenu p_266823_, Inventory p_266925_, Component p_266749_) {
        super(p_266823_, p_266925_, p_266749_, SMITHING_LOCATION);
        this.titleLabelX = 44;
        this.titleLabelY = 15;
    }

    protected void subInit() {
        this.armorStandPreview = new ArmorStand(this.minecraft.level, 0.0D, 0.0D, 0.0D);
        this.armorStandPreview.setNoBasePlate(true);
        this.armorStandPreview.setShowArms(true);
        this.armorStandPreview.yBodyRot = 210.0F;
        this.armorStandPreview.setXRot(25.0F);
        this.armorStandPreview.yHeadRot = this.armorStandPreview.getYRot();
        this.armorStandPreview.yHeadRotO = this.armorStandPreview.getYRot();
        this.updateArmorStandPreview(this.menu.getSlot(3).getItem());
    }

    protected void renderBg(PoseStack p_266704_, float p_267158_, int p_267266_, int p_266722_) {
        super.renderBg(p_266704_, p_267158_, p_267266_, p_266722_);
        InventoryScreen.renderEntityInInventory(p_266704_, this.leftPos + 141, this.topPos + 75, 25, ARMOR_STAND_ANGLE, (Quaternionf) null, this.armorStandPreview);
    }

    public void slotChanged(AbstractContainerMenu p_267217_, int p_266842_, ItemStack p_267208_) {
        if (p_266842_ == 3) {
            this.updateArmorStandPreview(p_267208_);
        }

    }

    private void updateArmorStandPreview(ItemStack p_268225_) {
        if (this.armorStandPreview != null) {
            for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
                this.armorStandPreview.setItemSlot(equipmentslot, ItemStack.EMPTY);
            }

            if (!p_268225_.isEmpty()) {
                ItemStack itemstack = p_268225_.copy();
                Item item = p_268225_.getItem();
                if (item instanceof ArmorItem) {
                    ArmorItem armoritem = (ArmorItem) item;
                    this.armorStandPreview.setItemSlot(armoritem.getEquipmentSlot(), itemstack);
                } else {
                    this.armorStandPreview.setItemSlot(EquipmentSlot.OFFHAND, itemstack);
                }
            }

        }
    }

    protected void renderErrorIcon(PoseStack p_267095_, int p_267270_, int p_266714_) {
        if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(this.menu.getResultSlot()).hasItem()) {
            blit(p_267095_, p_267270_ + 65, p_266714_ + 46, this.imageWidth, 0, 28, 21);
        }

    }
}