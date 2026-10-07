package com.smalldragonair.slabwise.registry;

import com.smalldragonair.slabwise.block.VerticalSlabBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public record VerticalSlabDefinition(
        String material,
        Block sourceSlab,
        DeferredBlock<VerticalSlabBlock> block,
        DeferredItem<BlockItem> item,
        TextureSet textures,
        ResourceLocation fullBlockModel,
        Tool tool,
        boolean flammable
) {
    public String id() {
        return "vertical_" + material + "_slab";
    }

    public enum Tool {
        AXE,
        PICKAXE
    }

    public record TextureSet(ResourceLocation bottom, ResourceLocation top, ResourceLocation side) {
        public static TextureSet all(String texture) {
            ResourceLocation location = minecraftBlock(texture);
            return new TextureSet(location, location, location);
        }

        public static TextureSet slab(String bottom, String top, String side) {
            return new TextureSet(minecraftBlock(bottom), minecraftBlock(top), minecraftBlock(side));
        }

        private static ResourceLocation minecraftBlock(String path) {
            return ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + path);
        }
    }
}
