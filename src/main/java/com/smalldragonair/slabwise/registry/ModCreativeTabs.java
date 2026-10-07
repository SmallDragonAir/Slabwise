package com.smalldragonair.slabwise.registry;

import com.smalldragonair.slabwise.Slabwise;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Slabwise.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SLABWISE = TABS.register("slabwise", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.slabwise"))
                    .withTabsBefore(CreativeModeTabs.BUILDING_BLOCKS)
                    .icon(() -> ModBlocks.STONE.item().get().getDefaultInstance())
                    .displayItems((parameters, output) -> ModBlocks.DEFINITIONS
                            .forEach(definition -> output.accept(definition.item().get())))
                    .build());

    private ModCreativeTabs() {}

    public static void register(IEventBus bus) {
        TABS.register(bus);
    }
}
