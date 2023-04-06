package baguchan.funkyyoyo.entity;

import baguchan.funkyyoyo.register.ModEntities;
import baguchan.funkyyoyo.register.ModItems;
import baguchan.funkyyoyo.util.YoyoUtils;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Yoyo extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Boolean> RETURNING = SynchedEntityData.defineId(Yoyo.class, EntityDataSerializers.BOOLEAN);

    private int flyTick;


    public Yoyo(EntityType<? extends Yoyo> entityEntityType, Level world) {
        super(entityEntityType, world);
    }

    public Yoyo(EntityType<? extends Yoyo> type, Level world, LivingEntity shootingEntity, ItemStack boomerang) {
        super(type, shootingEntity, world);
        setOwner(shootingEntity);
    }

    public Yoyo(Level world, LivingEntity entity, ItemStack boomerang) {
        this(ModEntities.YOYO.get(), world, entity, boomerang);
    }


    private void onHitFluid(BlockHitResult result) {
        double velocity = getVelocity();
        double horizontal = getDeltaMovement().y * getDeltaMovement().y;
        if (result.getType() == HitResult.Type.BLOCK && result.isInside() &&
                velocity >= 0.6499999761581421D && horizontal < 0.17499999701976776D)
            if (!this.level.getBlockState(result.getBlockPos()).isAir() && this.level.getFluidState(result.getBlockPos()).is(FluidTags.WATER)) {
                setDeltaMovement(getDeltaMovement().x, Mth.clamp(getDeltaMovement().y + 0.10000000149011612D, -0.10000000149011612D, 0.30000001192092896D), getDeltaMovement().z);
                this.hasImpulse = true;
            }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        boolean returnToOwner = false;
        Entity shooter = getOwner();
        if (result.getEntity() != getOwner()) {
            YoyoSide side = YoyoUtils.getYoyoSide(getItem());
            int baseDamage = side == null ? 2 : side.getAttackDamage();
            int sharpness = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, getItem());
            int damage = (int) ((baseDamage * Math.sqrt(getDeltaMovement().x * getDeltaMovement().x + getDeltaMovement().y * getDeltaMovement().y * 0.5D + getDeltaMovement().z * getDeltaMovement().z) + Math.min(1, sharpness) + Math.max(0, sharpness - 1) * 0.5D));

            if (damage != 0) {
                result.getEntity().hurt(this.damageSources().thrown(this, shooter), damage);
            }
            if (shooter instanceof LivingEntity) {
                getItem().hurtAndBreak(1, (LivingEntity) shooter, p_222182_1_ -> {
                });
            }

            double speed = getSpeed();
            returnToOwner = true;
            Vec3 motion = getDeltaMovement();
            double motionX = motion.x;
            double motionY = motion.y;
            double motionZ = motion.z;
            motionX = -motionX;
            motionZ = -motionZ;
            setDeltaMovement(motionX, motionY, motionZ);
            if (returnToOwner && !isReturning()) {
                setReturning(true);
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
            BlockState blockstate = this.level.getBlockState(result.getBlockPos());
            Entity entity = getOwner();
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
            checkInsideBlocks();
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
            return (!(entity instanceof Player) || !entity.isSpectator());
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
        if ((getOwner() instanceof Player && !((Player) getOwner()).isCreative())) {
            this.level.addFreshEntity(new ItemEntity(this.level, x, y, z, getItem().split(1)));
            this.discard();
        } else {
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 vec3 = this.getDeltaMovement();

        this.flyTick++;
        Vec3 vec3d1 = this.position();
        Vec3 vec3d2 = new Vec3(getX() + getDeltaMovement().x, getY() + getDeltaMovement().y, getZ() + getDeltaMovement().z);
        BlockHitResult fluidHitResult = this.level.clip(new ClipContext(vec3d1, vec3d2, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, this));
        onHitFluid(fluidHitResult);

        Entity entity = getOwner();
        if (!isReturning()) {
            if (this.flyTick >= 20 && entity != null) {
               setReturning(true);
            }
            if (this.flyTick < 20) {
                //recover movement
                this.setDeltaMovement(vec3.scale(1.01F));
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
        this.checkInsideBlocks();
    }

    @Override
    protected float getGravity() {
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
    protected Item getDefaultItem() {
        return ModItems.YOYO.get();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RETURNING, Boolean.valueOf(false));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putBoolean("returning", isReturning());
    }


    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        setReturning(nbt.getBoolean("returning"));
    }

    public boolean isReturning() {
        return ((Boolean) this.entityData.get(RETURNING)).booleanValue();
    }
    public double getSpeed() {
        return Math.sqrt(getDeltaMovement().x * getDeltaMovement().x + getDeltaMovement().y * getDeltaMovement().y + getDeltaMovement().z * getDeltaMovement().z);
    }

    public double getVelocity() {
        return Math.sqrt(getDeltaMovement().x * getDeltaMovement().x + getDeltaMovement().z * getDeltaMovement().z);
    }

    public void setReturning(boolean returning) {
        this.entityData.set(RETURNING, Boolean.valueOf(returning));
    }

}