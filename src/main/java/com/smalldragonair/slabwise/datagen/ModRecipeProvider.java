package com.smalldragonair.slabwise.datagen;

import com.smalldragonair.slabwise.Slabwise;
import com.smalldragonair.slabwise.registry.ModBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;

public final class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ModBlocks.DEFINITIONS.forEach(definition -> {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, definition.block().get())
                    .requires(definition.sourceSlab())
                    .unlockedBy(getHasName(definition.sourceSlab()), has(definition.sourceSlab()))
                    .save(output, ResourceLocation.fromNamespaceAndPath(Slabwise.MOD_ID, definition.id()));

            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, definition.sourceSlab())
                    .requires(definition.block().get())
                    .unlockedBy(getHasName(definition.block().get()), has(definition.block().get()))
                    .save(output, ResourceLocation.fromNamespaceAndPath(Slabwise.MOD_ID,
                            definition.material() + "_slab_from_vertical"));
        });
    }
}
