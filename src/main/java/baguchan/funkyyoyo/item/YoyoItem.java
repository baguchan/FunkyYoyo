package baguchan.funkyyoyo.item;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.client.render.item.YoyoItemBWLR;
import baguchan.funkyyoyo.entity.Yoyo;
import baguchan.funkyyoyo.register.ModItems;
import baguchan.funkyyoyo.register.ModYoyoSides;
import baguchan.funkyyoyo.util.YoyoUtils;
import baguchan.funkyyoyo.yoyocore.YoyoCore;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class YoyoItem extends Item {
    public YoyoItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        ResourceKey<YoyoSide> yoyoSide = YoyoUtils.getYoyoSideKey(stack);
        boolean canLightning = false;
        if (yoyoSide != null) {
            canLightning = yoyoSide.equals(ModYoyoSides.COPPER);
        }
        return (super.canApplyAtEnchantingTable(stack, enchantment) || enchantment == Enchantments.SHARPNESS || canLightning && enchantment == Enchantments.CHANNELING);
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return 2;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        levelIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (playerIn.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!levelIn.isClientSide) {
            Yoyo yoyo = new Yoyo(levelIn, playerIn, itemstack.split(1));
            yoyo.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 1.2F, 0F);
            levelIn.addFreshEntity(yoyo);
        }
        playerIn.awardStat(Stats.ITEM_USED.get(this));
        playerIn.getCooldowns().addCooldown(itemstack.getItem(), 10);
        return InteractionResultHolder.sidedSuccess(itemstack, levelIn.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag p_41424_) {
        super.appendHoverText(stack, level, tooltip, p_41424_);
        YoyoCore yoyoCore = YoyoUtils.getYoyoCore(stack);
        YoyoSide yoyoSide = YoyoUtils.getYoyoSide(stack);
        //tooltip.add(StringTextComponent.EMPTY);
        tooltip.add((Component.translatable("item.funkyyoyo.yoyo.what_made_of")).withStyle(ChatFormatting.LIGHT_PURPLE));
        if (yoyoCore != null) {
            tooltip.add(ForgeRegistries.ITEMS.getValue(yoyoCore.getMaterialId()).getDescription().copy().withStyle(ChatFormatting.WHITE));
        }

        if (yoyoSide != null) {
            tooltip.add(ForgeRegistries.ITEMS.getValue(yoyoSide.getMaterialId()).getDescription().copy().withStyle(ChatFormatting.WHITE));
        }

    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        YoyoCore yoyoCore = YoyoUtils.getYoyoCore(stack);
        if (yoyoCore != null) {
            return yoyoCore.getDurability();
        }
        return super.getMaxDamage(stack);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new YoyoItemBWLR();
            }
        });
    }

    public static List<ItemStack> generateYoyo() {
        List<ItemStack> items = Lists.newArrayList();
        for (YoyoCore core : FunkyYoyo.registryAccess().registryOrThrow(YoyoCore.REGISTRY_KEY).stream().toList()) {
            for (YoyoSide side : FunkyYoyo.registryAccess().registryOrThrow(YoyoSide.REGISTRY_KEY).stream().toList()) {
                items.add(YoyoUtils.makeYoyo(core, side, new ItemStack(ModItems.YOYO.get())));
            }
        }
        return items;
    }
}
