package com.smalldragonair.slabwise.datagen;

import com.smalldragonair.slabwise.Slabwise;
import com.smalldragonair.slabwise.registry.ModBlocks;
import com.smalldragonair.slabwise.registry.VerticalSlabDefinition.Tool;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                ExistingFileHelper existingFiles) {
        super(output, lookupProvider, Slabwise.MOD_ID, existingFiles);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        var slabs = tag(BlockTags.SLABS);
        var woodenSlabs = tag(BlockTags.WOODEN_SLABS);
        var axe = tag(BlockTags.MINEABLE_WITH_AXE);
        var pickaxe = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        var stoneTool = tag(BlockTags.NEEDS_STONE_TOOL);

        ModBlocks.DEFINITIONS.forEach(definition -> {
            slabs.add(definition.block().get());
            if (definition.tool() == Tool.AXE) {
                woodenSlabs.add(definition.block().get());
                axe.add(definition.block().get());
            } else {
                pickaxe.add(definition.block().get());
            }
            if (definition.material().contains("copper")) {
                stoneTool.add(definition.block().get());
            }
        });
    }
}
