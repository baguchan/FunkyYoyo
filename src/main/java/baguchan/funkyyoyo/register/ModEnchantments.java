package baguchan.funkyyoyo.register;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.enchant.AttachEnchantment;
import baguchan.funkyyoyo.item.YoyoItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Registries.ENCHANTMENT, FunkyYoyo.MODID);


    public static final EnchantmentCategory YOYO = EnchantmentCategory.create("yoyo", (item) -> {
        return item instanceof YoyoItem;
    });

    public static final RegistryObject<Enchantment> ATTACH = ENCHANTMENTS.register("attach", () -> new AttachEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND));

}