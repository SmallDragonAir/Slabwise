package com.smalldragonair.slabwise.datagen;

import com.smalldragonair.slabwise.Slabwise;
import com.smalldragonair.slabwise.registry.ModBlocks;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, Slabwise.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.slabwise", "Slabwise");
        ModBlocks.DEFINITIONS.forEach(definition -> add(definition.block().get(),
                "Vertical " + titleCase(definition.material()) + " Slab"));
    }

    private static String titleCase(String value) {
        return Arrays.stream(value.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}
