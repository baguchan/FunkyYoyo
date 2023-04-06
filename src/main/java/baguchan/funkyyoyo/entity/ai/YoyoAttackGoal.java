package baguchan.funkyyoyo.entity.ai;

import baguchan.funkyyoyo.entity.Funker;
import baguchan.funkyyoyo.item.YoyoItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class YoyoAttackGoal extends Goal {
    private final Funker mob;
    private int attackIntervalMin;
    private final float attackRadiusSqr;
    private int attackTime = -1;
    private int seeTime;

    public YoyoAttackGoal(Funker hunterIllagerEntity, int attackIntervalMin, float attackRadiusSqr) {
        this.mob = hunterIllagerEntity;
        this.attackIntervalMin = attackIntervalMin;
        this.attackRadiusSqr = attackRadiusSqr * attackRadiusSqr;
    }

    @Override
    public boolean canUse() {
        LivingEntity entity = mob.getTarget();
        return entity != null && entity.isAlive() && entity.distanceToSqr(mob) > 8D;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        this.seeTime = 0;
        this.attackTime = -1;
        this.mob.stopUsingItem();
    }

    public void tick() {
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity != null) {
            double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
            boolean flag = this.mob.getSensing().hasLineOfSight(livingentity);
            boolean flag1 = this.seeTime > 0;
            if (flag != flag1) {
                this.seeTime = 0;
            }

            if (flag) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }

            if (mob.isHolding((item) -> item.getItem() instanceof YoyoItem)) {
                if (!flag && this.seeTime < -60) {
                    this.mob.stopUsingItem();
                } else if (flag) {

                    if (--this.attackTime < 0) {
                        this.mob.performAttack(livingentity);
                        this.attackTime = this.attackIntervalMin;
                    }
                }
            }

        }
    }
}