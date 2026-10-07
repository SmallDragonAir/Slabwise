package com.smalldragonair.slabwise;

import com.smalldragonair.slabwise.datagen.ModDataGenerators;
import com.smalldragonair.slabwise.registry.ModBlocks;
import com.smalldragonair.slabwise.registry.ModCreativeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Slabwise.MOD_ID)
public final class Slabwise {
    public static final String MOD_ID = "slabwise";

    public Slabwise(IEventBus modBus) {
        ModBlocks.register(modBus);
        ModCreativeTabs.register(modBus);
        modBus.addListener(ModDataGenerators::gatherData);
        modBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModBlocks::registerFlammability);
    }
}
