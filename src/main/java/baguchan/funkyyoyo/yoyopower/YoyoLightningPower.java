package baguchan.funkyyoyo.yoyopower;

import baguchan.funkyyoyo.entity.Yoyo;
import baguchan.funkyyoyo.register.ModDamageSources;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class YoyoLightningPower extends YoyoPower {
    public YoyoLightningPower() {
        super();
    }

    @Override
    public void applyDamageEffect(Yoyo entity, Entity throwedEntity, @Nullable Entity owner) {
        if (!entity.getYoyoData().contains("Lightning")) {
            throwedEntity.damageSources().source(ModDamageSources.LIGHTNING_THROWN, throwedEntity, entity);
            entity.getYoyoData().putBoolean("Lightninged", true);
        }
    }

    @Override
    public void applyTick(Yoyo entity) {
    }
}
