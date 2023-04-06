package baguchan.funkyyoyo.data;

import baguchan.funkyyoyo.register.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
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
    }
}
