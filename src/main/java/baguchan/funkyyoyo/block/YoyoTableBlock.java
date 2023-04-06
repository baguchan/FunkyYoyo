package baguchan.funkyyoyo.block;

import baguchan.funkyyoyo.menu.YoyoTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class YoyoTableBlock extends CraftingTableBlock {
    private static final Component CONTAINER_TITLE = Component.translatable("container.funkyyoyo.yoyo_table");

    public YoyoTableBlock(BlockBehaviour.Properties p_56420_) {
        super(p_56420_);
    }

    public MenuProvider getMenuProvider(BlockState p_56435_, Level p_56436_, BlockPos p_56437_) {
        return new SimpleMenuProvider((p_270047_, p_270048_, p_270049_) -> {
            return new YoyoTableMenu(p_270047_, p_270048_, ContainerLevelAccess.create(p_56436_, p_56437_));
        }, CONTAINER_TITLE);
    }

    public InteractionResult use(BlockState p_56428_, Level p_56429_, BlockPos p_56430_, Player p_56431_, InteractionHand p_56432_, BlockHitResult p_56433_) {
        if (p_56429_.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            p_56431_.openMenu(p_56428_.getMenuProvider(p_56429_, p_56430_));
            return InteractionResult.CONSUME;
        }
    }
}