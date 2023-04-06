package baguchan.funkyyoyo.menu;

import baguchan.funkyyoyo.register.ModBlocks;
import baguchan.funkyyoyo.register.ModItems;
import baguchan.funkyyoyo.register.ModMenus;
import baguchan.funkyyoyo.util.YoyoUtils;
import baguchan.funkyyoyo.yoyocore.YoyoCore;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.ItemCombinerMenuSlotDefinition;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;

public class YoyoTableMenu extends ItemCombinerMenu {
    private final Level level;
    public static final int INPUT_SLOT = 0;
    public static final int ADDITIONAL_SLOT = 1;
    public static final int RESULT_SLOT = 2;
    private static final int INPUT_SLOT_X_PLACEMENT = 27;
    private static final int ADDITIONAL_SLOT_X_PLACEMENT = 76;
    private static final int RESULT_SLOT_X_PLACEMENT = 134;
    private static final int SLOT_Y_PLACEMENT = 47;

    public YoyoTableMenu(int p_267173_, Inventory p_267175_) {
        this(p_267173_, p_267175_, ContainerLevelAccess.NULL);
    }

    public YoyoTableMenu(int p_266937_, Inventory p_267213_, ContainerLevelAccess p_266723_) {
        super(ModMenus.YOYO_TABLE.get(), p_266937_, p_267213_, p_266723_);
        this.level = p_267213_.player.level;
    }

    protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
        return ItemCombinerMenuSlotDefinition.create().withSlot(0, 8, 48, (p_266883_) -> {
            return true;
        }).withSlot(1, 26, 48, (p_267323_) -> {
            return true;
        }).withSlot(2, 44, 48, (p_267323_) -> {
            return true;
        }).withResultSlot(3, 98, 48).build();
    }

    protected boolean isValidBlock(BlockState p_266887_) {
        return p_266887_.is(ModBlocks.YOYO_TABLE.get());
    }

    @Override
    protected boolean mayPickup(Player p_39798_, boolean p_39799_) {
        return true;
    }

    protected void onTake(Player p_267006_, ItemStack p_266731_) {
        p_266731_.onCraftedBy(p_267006_.level, p_267006_, p_266731_.getCount());
        this.resultSlots.awardUsedRecipes(p_267006_);
        this.shrinkStackInSlot(0);
        this.shrinkStackInSlot(1);
        this.shrinkStackInSlot(2);
        this.access.execute((p_267191_, p_267098_) -> {
            p_267191_.levelEvent(1044, p_267098_, 0);
        });
    }

    private void shrinkStackInSlot(int p_267273_) {
        ItemStack itemstack = this.inputSlots.getItem(p_267273_);
        itemstack.shrink(1);
        this.inputSlots.setItem(p_267273_, itemstack);
    }

    public void createResult() {
        ItemStack itemstack = this.inputSlots.getItem(0);
        ItemStack itemstack2 = this.inputSlots.getItem(1);
        ItemStack itemstack3 = this.inputSlots.getItem(2);
        Optional<YoyoCore> core = this.level.registryAccess().registryOrThrow(YoyoCore.REGISTRY_KEY).stream().filter(coreItem ->{
            return coreItem.getMaterialId().toString().equals(ForgeRegistries.ITEMS.getKey(itemstack.getItem()).toString());
        }).findFirst();
        Optional<YoyoSide> side = this.level.registryAccess().registryOrThrow(YoyoSide.REGISTRY_KEY).stream().filter(sideItem ->{
            return sideItem.getMaterialId().toString().equals(ForgeRegistries.ITEMS.getKey(itemstack2.getItem()).toString());
        }).findFirst();

        if (core.isEmpty() || side.isEmpty() || !itemstack3.is(Tags.Items.STRING) || itemstack3.isEmpty()) {
            this.resultSlots.setItem(0, ItemStack.EMPTY);
        } else {
            ItemStack stack = YoyoUtils.makeYoyo(core.get(), side.get(), new ItemStack(ModItems.YOYO.get()));
            if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
                this.resultSlots.setItem(0, stack);
            }
        }

    }

    public int getSlotToQuickMoveTo(ItemStack p_267241_) {
        return this.shouldQuickMoveToAdditionalSlot(p_267241_) ? 1 : 0;
    }

    protected boolean shouldQuickMoveToAdditionalSlot(ItemStack p_267176_) {
        return false;
    }

    public boolean canTakeItemForPickAll(ItemStack p_266810_, Slot p_267252_) {
        return p_267252_.container != this.resultSlots && super.canTakeItemForPickAll(p_266810_, p_267252_);
    }
}