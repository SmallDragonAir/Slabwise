package com.smalldragonair.slabwise.datagen;

import com.smalldragonair.slabwise.block.VerticalSlabBlock;
import com.smalldragonair.slabwise.block.VerticalSlabType;
import com.smalldragonair.slabwise.registry.ModBlocks;
import java.util.Set;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public final class ModBlockLoot extends BlockLootSubProvider {
    public ModBlockLoot(HolderLookup.Provider lookupProvider) {
        super(Set.<Item>of(), FeatureFlags.DEFAULT_FLAGS, lookupProvider);
    }

    @Override
    protected void generate() {
        ModBlocks.DEFINITIONS.forEach(definition -> add(definition.block().get(), createVerticalSlabTable(definition.block().get())));
    }

    private LootTable.Builder createVerticalSlabTable(Block block) {
        return LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(applyExplosionDecay(block, LootItem.lootTableItem(block)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                .hasProperty(VerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE)))))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.DEFINITIONS.stream().map(definition -> (Block) definition.block().get()).toList();
    }
}
