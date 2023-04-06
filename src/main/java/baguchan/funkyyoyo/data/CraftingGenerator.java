package baguchan.funkyyoyo.data;

import baguchan.funkyyoyo.register.ModBlocks;
import baguchan.funkyyoyo.register.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class CraftingGenerator extends RecipeProvider {
    public CraftingGenerator(PackOutput generator) {
        super(generator);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModBlocks.YOYO_TABLE.get())
                .pattern("#")
                .pattern("C")
                .define('#', Tags.Items.INGOTS_COPPER)
                .define('C', Blocks.CRAFTING_TABLE)
                .unlockedBy("has_item", has(Tags.Items.INGOTS_COPPER))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOOD_PARTS.get())
                .pattern("# #")
                .define('#', ItemTags.WOODEN_PRESSURE_PLATES)
                .unlockedBy("has_item", has(ItemTags.WOODEN_PRESSURE_PLATES))
                .save(consumer);
    }
}
