package baguchan.funkyyoyo.entity;

import baguchan.funkyyoyo.register.ModDamageSources;
import baguchan.funkyyoyo.register.ModEnchantments;
import baguchan.funkyyoyo.register.ModEntities;
import baguchan.funkyyoyo.register.ModSounds;
import baguchan.funkyyoyo.util.YoyoUtils;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Yoyo extends Projectile implements ItemSupplier {
    private static final EntityDataAccessor<Boolean> ATTACH = SynchedEntityData.defineId(Yoyo.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> RETURNING = SynchedEntityData.defineId(Yoyo.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> SPEED_DECREASE = SynchedEntityData.defineId(Yoyo.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<CompoundTag> YOYO_DATA = SynchedEntityData.defineId(Yoyo.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(Yoyo.class, EntityDataSerializers.ITEM_STACK);

    private int flyTick;
    protected boolean inGround;


    public Yoyo(EntityType<? extends Yoyo> entityEntityType, Level world) {
        super(entityEntityType, world);
    }

    public Yoyo(EntityType<? extends Yoyo> type, Level world, LivingEntity shootingEntity, ItemStack yoyo) {
        super(type, world);
        setOwner(shootingEntity);
        YoyoSide side = YoyoUtils.getYoyoSide(yoyo);
        if (side != null) {
            this.setSpeedDecrease(side.getSpeedDecrease());
        }
        if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ATTACH.get(), yoyo) > 0) {
            setAttach(true);
        }
        this.setItem(yoyo);
        this.setPos(shootingEntity.getX(), shootingEntity.getEyeY(), shootingEntity.getZ());
    }

    public Yoyo(Level world, LivingEntity entity, ItemStack yoyo) {
        this(ModEntities.YOYO.get(), world, entity, yoyo);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity shooter = getOwner();
        if (result.getEntity() != getOwner()) {
            YoyoSide side = YoyoUtils.getYoyoSide(getItem());
            int baseDamage = side == null ? 2 : side.getAttackDamage();
            int sharpness = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, getItem());
            int lightning = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.CHANNELING, getItem());
            int damage = (int) (((baseDamage + 1.0D) * Math.sqrt(getDeltaMovement().x * getDeltaMovement().x + getDeltaMovement().y * getDeltaMovement().y * 0.5D + getDeltaMovement().z * getDeltaMovement().z) + Math.min(1, sharpness) + Math.max(0, sharpness - 1) * 0.5D));

            if (damage != 0) {
                if (result.getEntity().hurt(this.damageSources().thrown(this, shooter), damage)) {
                    if (lightning > 0) {
                        result.getEntity().hurt(this.damageSources().source(ModDamageSources.LIGHTNING_THROWN, this, shooter), 4.0F);
                        this.playSound(ModSounds.ELECTRIC.get());
                    }
                }
            }
            setAttach(false);
            if (shooter instanceof LivingEntity) {
                getItem().hurtAndBreak(1, (LivingEntity) shooter, p_222182_1_ -> {
                });
            }

        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();
        BlockState state = this.level.getBlockState(pos);
        SoundType soundType = state.getSoundType(this.level, pos, this);
        if (!isReturning()) {
            this.level.playSound(null, getX(), getY(), getZ(), soundType.getHitSound(), SoundSource.BLOCKS, soundType.getVolume(), soundType.getPitch());
            if (!this.canAttach()) {
                BlockState blockstate = this.level.getBlockState(result.getBlockPos());
                if (!blockstate.getCollisionShape(this.level, result.getBlockPos()).isEmpty()) {
                    Vec3i direction = result.getDirection().getNormal();
                    switch (result.getDirection()) {
                        case UP, SOUTH, EAST -> direction = direction.multiply(-1);
                        default -> {
                        }
                    }
                    direction = new Vec3i(direction.getX() == 0 ? 1 : direction.getX(), direction.getY() == 0 ? 1 : direction.getY(), direction.getZ() == 0 ? 1 : direction.getZ());
                    this.setDeltaMovement(this.getDeltaMovement().multiply(new Vec3(direction.getX(), direction.getY(), direction.getZ())));
                }
            } else {
                Vec3 vec3 = result.getLocation().subtract(this.getX(), this.getY(), this.getZ());
                this.setDeltaMovement(vec3);
                this.inGround = true;
                Vec3 vec31 = vec3.normalize().scale((double) 0.05F);
                this.setPosRaw(this.getX() - vec31.x, this.getY() - vec31.y, this.getZ() - vec31.z);
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
    }


    public int getFlyTick() {
        return flyTick;
    }

    private boolean shouldReturnToThrower() {
        Entity entity = getOwner();
        if (entity != null && entity.isAlive())
            return (this.distanceToSqr(entity) > 3 && !entity.isSpectator());
        return false;
    }

    @Override
    public void playerTouch(Player entityIn) {
        super.playerTouch(entityIn);
        if (this.flyTick >= 10 && entityIn == getOwner()) {
            drop(getOwner().getX(), getOwner().getY(), getOwner().getZ());
        }
    }

    public void drop(double x, double y, double z) {
        if (!this.level.isClientSide) {
            if ((getOwner() instanceof Player && !((Player) getOwner()).isCreative()) || !(getOwner() instanceof Player)) {
                this.level.addFreshEntity(new ItemEntity(this.level, x, y, z, getItem().split(1)));
                this.discard();
            } else {
                this.discard();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.flyTick++;

        Entity entity = getOwner();

        if (!isReturning() && !this.canAttach()) {
            if (this.flyTick >= 20 && entity != null) {
                setReturning(true);
            }
        }

        if (this.shouldFall()) {
            this.inGround = false;
        }

        if (!this.inGround && this.canAttach() && !this.isReturning()) {
            BlockPos blockpos = this.blockPosition();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (!blockstate.isAir()) {
                VoxelShape voxelshape = blockstate.getCollisionShape(this.level, blockpos);
                if (!voxelshape.isEmpty()) {
                    Vec3 vec31 = this.position();

                    for (AABB aabb : voxelshape.toAabbs()) {
                        if (aabb.move(blockpos).contains(vec31)) {
                            this.inGround = true;
                            break;
                        }
                    }
                }
            }
        }

        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);

        if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        this.checkInsideBlocks();

        if (entity != null) {
            if (inGround && canAttach()) {
                Vec3 vec3d3 = new Vec3(getX() - entity.getX(), getY() - entity.getEyeY(), getZ() - entity.getZ());
                double d0 = 0.2;
                entity.setDeltaMovement(entity.getDeltaMovement().scale(0.95D).add(vec3d3.normalize().scale(d0)));

            }
            if (entity.isShiftKeyDown()) {
                setReturning(true);
                setAttach(false);
                this.inGround = false;
            }
        }

        if (entity != null && !shouldReturnToThrower() && isReturning()) {
            drop(getX(), getY(), getZ());
        } else if (entity != null && isReturning()) {
            this.noPhysics = true;
            Vec3 vec3d3 = new Vec3(entity.getX() - getX(), entity.getEyeY() - getY(), entity.getZ() - getZ());
            double d0 = 0.2;
            this.setDeltaMovement(getDeltaMovement().scale(0.95D).add(vec3d3.normalize().scale(d0)));
        }

        Vec3 vec3 = this.getDeltaMovement();
        double d5 = vec3.x;
        double d6 = vec3.y;
        double d1 = vec3.z;


        double d7 = this.getX() + d5;
        double d2 = this.getY() + d6;
        double d3 = this.getZ() + d1;
        this.updateRotation();
        float f = 0.99F;
        float f1 = 0.05F;
        if (this.isInWater()) {
            for (int j = 0; j < 4; ++j) {
                float f2 = 0.25F;
                this.level.addParticle(ParticleTypes.BUBBLE, d7 - d5 * 0.25D, d2 - d6 * 0.25D, d3 - d1 * 0.25D, d5, d6, d1);
            }

            f = this.getWaterInertia();
        }
        if (this.isReturning()) {
            this.setDeltaMovement(vec3.scale((double) f));
        }
        if (!this.isNoGravity() && !this.isReturning() && !this.inGround) {
            Vec3 vec34 = this.getDeltaMovement();
            this.setDeltaMovement(vec34.x, vec34.y - (double) this.getGravity(), vec34.z);
        }

        this.setPos(d7, d2, d3);

        this.checkInsideBlocks();
    }

    private boolean shouldFall() {
        return this.inGround && this.level.noCollision(this.getBoundingBox().inflate(0.001D));
    }

    private float getGravity() {
        YoyoSide side = YoyoUtils.getYoyoSide(this.getItem());
        if (side != null) {
            return side.getSpeedDecrease();
        }
        return 0.0F;
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double p_70112_1_) {
        double d0 = this.getBoundingBox().getSize() * 5.0F;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D;
        return p_70112_1_ < d0 * d0;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(ATTACH, false);
        this.entityData.define(RETURNING, false);
        this.entityData.define(SPEED_DECREASE, 0F);
        this.entityData.define(YOYO_DATA, new CompoundTag());
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("Attach", this.canAttach());
        nbt.putBoolean("Returning", this.isReturning());
        nbt.putFloat("SpeedDecrease", this.getSpeedDecrease());
        nbt.put("YoyoData", this.getYoyoData());
        ItemStack itemstack = this.getItem();
        if (!itemstack.isEmpty()) {
            nbt.put("Item", itemstack.save(new CompoundTag()));
        }
    }


    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setAttach(nbt.getBoolean("Attach"));
        this.setReturning(nbt.getBoolean("Returning"));
        this.setSpeedDecrease(nbt.getFloat("SpeedDecrease"));
        this.setYoyoData(nbt.getCompound("YoyoData"));
        ItemStack itemstack = ItemStack.of(nbt.getCompound("Item"));
        this.setItem(itemstack);
    }

    public void setItem(ItemStack p_37447_) {
        this.getEntityData().set(DATA_ITEM_STACK, Util.make(p_37447_.copy(), (p_37451_) -> {
            p_37451_.setCount(1);
        }));
    }

    public ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    public boolean isReturning() {
        return this.entityData.get(RETURNING);
    }

    public double getSpeed() {
        return Math.sqrt(getDeltaMovement().x * getDeltaMovement().x + getDeltaMovement().y * getDeltaMovement().y + getDeltaMovement().z * getDeltaMovement().z);
    }

    public double getVelocity() {
        return Math.sqrt(getDeltaMovement().x * getDeltaMovement().x + getDeltaMovement().z * getDeltaMovement().z);
    }

    public boolean canAttach() {
        return this.entityData.get(ATTACH);
    }

    protected void setAttach(boolean attach) {
        this.entityData.set(ATTACH, attach);
    }

    public void setReturning(boolean returning) {
        this.entityData.set(RETURNING, Boolean.valueOf(returning));
    }

    public void setSpeedDecrease(float speedDecrease) {
        this.entityData.set(SPEED_DECREASE, speedDecrease);
    }

    public float getSpeedDecrease() {
        return this.entityData.get(SPEED_DECREASE);
    }

    public void setYoyoData(CompoundTag yoyoData) {
        this.entityData.set(YOYO_DATA, yoyoData);
    }

    public CompoundTag getYoyoData() {
        return this.entityData.get(YOYO_DATA);
    }

    protected float getWaterInertia() {
        return 0.8F;
    }
}