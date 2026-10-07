package com.smalldragonair.slabwise.datagen;

import com.smalldragonair.slabwise.Slabwise;
import java.util.List;
import java.util.Set;
import java.nio.file.Path;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.structures.SnbtToNbt;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class ModDataGenerators {
    private ModDataGenerators() {}

    public static void gatherData(GatherDataEvent event) {
        if (event.includeDev()) {
            Path projectRoot = Path.of("").toAbsolutePath().getParent();
            event.addProvider(new SnbtToNbt(event.getGenerator().getPackOutput(),
                    List.of(projectRoot.resolve("src/main/snbt"))));
        }

        if (event.includeClient()) {
            event.addProvider(new ModBlockStateProvider(event.getGenerator().getPackOutput(), event.getExistingFileHelper()));
            event.addProvider(new ModLanguageProvider(event.getGenerator().getPackOutput()));
        }

        if (event.includeServer()) {
            ModBlockTagsProvider blockTags = event.addProvider(new ModBlockTagsProvider(
                    event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper()));
            event.addProvider(new ModItemTagsProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(),
                    blockTags.contentsGetter(), event.getExistingFileHelper()));
            event.addProvider(new ModRecipeProvider(event.getGenerator().getPackOutput(), event.getLookupProvider()));
            event.addProvider(new LootTableProvider(
                    event.getGenerator().getPackOutput(),
                    Set.of(),
                    List.of(new LootTableProvider.SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK)),
                    event.getLookupProvider()));
        }
    }
}
