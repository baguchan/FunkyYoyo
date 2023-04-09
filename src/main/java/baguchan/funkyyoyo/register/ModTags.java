package baguchan.funkyyoyo.register;

import baguchan.funkyyoyo.FunkyYoyo;
import baguchan.funkyyoyo.yoyoside.YoyoSide;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class ModTags {
    public static class YoyoSideTag {
        public static final TagKey<YoyoSide> CAN_LIGHTNING = create(new ResourceLocation(FunkyYoyo.MODID, "can_lightning"));

        public static TagKey<YoyoSide> create(ResourceLocation name) {
            return TagKey.create(YoyoSide.REGISTRY_KEY, name);
        }
    }
}
