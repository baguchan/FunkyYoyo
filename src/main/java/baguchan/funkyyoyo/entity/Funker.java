package baguchan.funkyyoyo.entity;

import baguchan.funkyyoyo.entity.ai.YoyoAttackGoal;
import baguchan.funkyyoyo.register.ModItems;
import baguchan.funkyyoyo.util.YoyoUtils;
import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.Map;

public class Funker extends AbstractIllager {
    public Funker(EntityType<? extends AbstractIllager> p_32105_, Level p_32106_) {
        super(p_32105_, p_32106_);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.setCanPickUpLoot(true);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new OpenDoorGoal(this, true));
        this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(4, new YoyoAttackGoal(this, 50, 16.0F));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.15F, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers(AbstractIllager.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, (double) 0.3F).add(Attributes.FOLLOW_RANGE, 20.0D).add(Attributes.MAX_HEALTH, 26.0D).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_37856_, DifficultyInstance p_37857_, MobSpawnType p_37858_, @Nullable SpawnGroupData p_37859_, @Nullable CompoundTag p_37860_) {
        RandomSource randomsource = p_37856_.getRandom();
        SpawnGroupData ilivingentitydata = super.finalizeSpawn(p_37856_, p_37857_, p_37858_, p_37859_, p_37860_);
        ((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);
        this.setCanPickUpLoot(true);
        if (p_37858_ != MobSpawnType.STRUCTURE) {
            this.populateDefaultEquipmentSlots(randomsource, p_37857_);
        }
        this.populateDefaultEquipmentEnchantments(randomsource, p_37857_);
        return ilivingentitydata;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource p_217055_, DifficultyInstance p_217056_) {
        if (this.getCurrentRaid() == null) {
            this.setItemSlot(EquipmentSlot.MAINHAND, YoyoUtils.randomMakeYoyo(this.random, new ItemStack(ModItems.YOYO.get())));
        }
    }

    @Override
    public void applyRaidBuffs(int p_213660_1_, boolean p_213660_2_) {
        ItemStack mainhand = YoyoUtils.randomMakeYoyo(this.random, new ItemStack(ModItems.YOYO.get()));

        Raid raid = this.getCurrentRaid();

        int i = 1;
        if (p_213660_1_ > raid.getNumGroups(Difficulty.NORMAL)) {
            i = 2;
        }

        boolean flag = this.random.nextFloat() <= raid.getEnchantOdds();
        if (flag) {

            Map<Enchantment, Integer> map2 = Maps.newHashMap();
            map2.put(Enchantments.SHARPNESS, i);
            EnchantmentHelper.setEnchantments(map2, mainhand);
        }
        this.setItemInHand(InteractionHand.MAIN_HAND, mainhand);
    }

    @Override
    protected void pickUpItem(ItemEntity p_175445_1_) {
        ItemStack itemstack = p_175445_1_.getItem();
        if (itemstack.getItem() instanceof BannerItem) {
            super.pickUpItem(p_175445_1_);
        } else {
            Item item = itemstack.getItem();
            if (item == ModItems.YOYO.get()) {
                if (this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
                    this.onItemPickup(p_175445_1_);
                    this.take(p_175445_1_, itemstack.getCount());

                    this.setItemSlot(EquipmentSlot.MAINHAND, itemstack);
                    p_175445_1_.discard();
                }
            }
        }

    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33306_) {
        return SoundEvents.PILLAGER_HURT;
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.PILLAGER_CELEBRATE;
    }

    public void performAttack(LivingEntity p_82196_1_, float p_82196_2_) {
        Yoyo boomerang = new Yoyo(this.level, this, this.getOffhandItem().split(1));
        double d0 = p_82196_1_.getX() - this.getX();
        double d1 = p_82196_1_.getY(0.3333333333333333D) - boomerang.getY();
        double d2 = p_82196_1_.getZ() - this.getZ();
        double d3 = (double) Mth.sqrt((float) (d0 * d0 + d2 * d2));
        boomerang.shoot(d0, d1 + d3 * (double) 0.2F, d2, 1.2F, (float) (14 - this.level.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(boomerang);
    }
}
