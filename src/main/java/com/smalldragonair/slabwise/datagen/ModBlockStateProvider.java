package com.smalldragonair.slabwise.datagen;

import com.smalldragonair.slabwise.Slabwise;
import com.smalldragonair.slabwise.block.VerticalSlabBlock;
import com.smalldragonair.slabwise.block.VerticalSlabType;
import com.smalldragonair.slabwise.registry.ModBlocks;
import com.smalldragonair.slabwise.registry.VerticalSlabDefinition;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class ModBlockStateProvider extends net.neoforged.neoforge.client.model.generators.BlockStateProvider {
    private final ExistingFileHelper existingFiles;

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFiles) {
        super(output, Slabwise.MOD_ID, existingFiles);
        this.existingFiles = existingFiles;
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.DEFINITIONS.forEach(this::registerVerticalSlab);
    }

    private void registerVerticalSlab(VerticalSlabDefinition definition) {
        ModelFile west = halfModel(definition, VerticalSlabType.WEST);
        ModelFile east = halfModel(definition, VerticalSlabType.EAST);
        ModelFile north = halfModel(definition, VerticalSlabType.NORTH);
        ModelFile south = halfModel(definition, VerticalSlabType.SOUTH);
        ModelFile full = new ModelFile.ExistingModelFile(definition.fullBlockModel(), existingFiles);

        getVariantBuilder(definition.block().get()).forAllStatesExcept(state -> {
            ModelFile model = switch (state.getValue(VerticalSlabBlock.TYPE)) {
                case WEST -> west;
                case EAST -> east;
                case NORTH -> north;
                case SOUTH -> south;
                case DOUBLE -> full;
            };
            return ConfiguredModel.builder().modelFile(model).build();
        }, VerticalSlabBlock.WATERLOGGED);

        simpleBlockItem(definition.block().get(), west);
    }

    private ModelFile halfModel(VerticalSlabDefinition definition, VerticalSlabType type) {
        String orientation = type.getSerializedName();
        return models().withExistingParent(definition.id() + "_" + orientation,
                        modLoc("block/vertical_slab_" + orientation))
                .texture("bottom", definition.textures().bottom())
                .texture("top", definition.textures().top())
                .texture("side", definition.textures().side());
    }
}
