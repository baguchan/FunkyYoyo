package baguchan.funkyyoyo.enchant;

import baguchan.funkyyoyo.register.ModEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class AttachEnchantment extends Enchantment {
    public AttachEnchantment(Enchantment.Rarity p_i50019_1_, EquipmentSlot... p_i50019_2_) {
        super(p_i50019_1_, ModEnchantments.YOYO, p_i50019_2_);
    }

    public int getMinCost(int p_77321_1_) {
        return 1 + (p_77321_1_ - 1) * 10;
    }

    public int getMaxCost(int p_223551_1_) {
        return 50;
    }

    public int getMaxLevel() {
        return 1;
    }
}
