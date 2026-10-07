package com.smalldragonair.slabwise.registry;

import com.smalldragonair.slabwise.Slabwise;
import com.smalldragonair.slabwise.block.VerticalSlabBlock;
import com.smalldragonair.slabwise.block.WeatheringVerticalSlabBlock;
import com.smalldragonair.slabwise.registry.VerticalSlabDefinition.TextureSet;
import com.smalldragonair.slabwise.registry.VerticalSlabDefinition.Tool;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Slabwise.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Slabwise.MOD_ID);

    private static final List<VerticalSlabDefinition> MUTABLE_DEFINITIONS = new ArrayList<>();

    // Stone and masonry slabs.
    public static final VerticalSlabDefinition STONE = stone("stone", Blocks.STONE_SLAB, TextureSet.all("stone"), "stone");
    public static final VerticalSlabDefinition SMOOTH_STONE = stone("smooth_stone", Blocks.SMOOTH_STONE_SLAB, TextureSet.slab("smooth_stone", "smooth_stone", "smooth_stone_slab_side"), "smooth_stone");
    public static final VerticalSlabDefinition SANDSTONE = stone("sandstone", Blocks.SANDSTONE_SLAB, TextureSet.slab("sandstone_bottom", "sandstone_top", "sandstone"), "sandstone");
    public static final VerticalSlabDefinition CUT_SANDSTONE = stone("cut_sandstone", Blocks.CUT_SANDSTONE_SLAB, TextureSet.slab("sandstone_top", "sandstone_top", "cut_sandstone"), "cut_sandstone");
    public static final VerticalSlabDefinition PETRIFIED_OAK = stone("petrified_oak", Blocks.PETRIFIED_OAK_SLAB, TextureSet.all("oak_planks"), "oak_planks");
    public static final VerticalSlabDefinition COBBLESTONE = stone("cobblestone", Blocks.COBBLESTONE_SLAB, TextureSet.all("cobblestone"), "cobblestone");
    public static final VerticalSlabDefinition BRICK = stone("brick", Blocks.BRICK_SLAB, TextureSet.all("bricks"), "bricks");
    public static final VerticalSlabDefinition STONE_BRICK = stone("stone_brick", Blocks.STONE_BRICK_SLAB, TextureSet.all("stone_bricks"), "stone_bricks");
    public static final VerticalSlabDefinition MUD_BRICK = stone("mud_brick", Blocks.MUD_BRICK_SLAB, TextureSet.all("mud_bricks"), "mud_bricks");
    public static final VerticalSlabDefinition NETHER_BRICK = stone("nether_brick", Blocks.NETHER_BRICK_SLAB, TextureSet.all("nether_bricks"), "nether_bricks");
    public static final VerticalSlabDefinition QUARTZ = stone("quartz", Blocks.QUARTZ_SLAB, TextureSet.slab("quartz_block_top", "quartz_block_top", "quartz_block_side"), "quartz_block");
    public static final VerticalSlabDefinition RED_SANDSTONE = stone("red_sandstone", Blocks.RED_SANDSTONE_SLAB, TextureSet.slab("red_sandstone_bottom", "red_sandstone_top", "red_sandstone"), "red_sandstone");
    public static final VerticalSlabDefinition CUT_RED_SANDSTONE = stone("cut_red_sandstone", Blocks.CUT_RED_SANDSTONE_SLAB, TextureSet.slab("red_sandstone_top", "red_sandstone_top", "cut_red_sandstone"), "cut_red_sandstone");
    public static final VerticalSlabDefinition PURPUR = stone("purpur", Blocks.PURPUR_SLAB, TextureSet.all("purpur_block"), "purpur_block");
    public static final VerticalSlabDefinition PRISMARINE = stone("prismarine", Blocks.PRISMARINE_SLAB, TextureSet.all("prismarine"), "prismarine");
    public static final VerticalSlabDefinition PRISMARINE_BRICK = stone("prismarine_brick", Blocks.PRISMARINE_BRICK_SLAB, TextureSet.all("prismarine_bricks"), "prismarine_bricks");
    public static final VerticalSlabDefinition DARK_PRISMARINE = stone("dark_prismarine", Blocks.DARK_PRISMARINE_SLAB, TextureSet.all("dark_prismarine"), "dark_prismarine");
    public static final VerticalSlabDefinition SMOOTH_QUARTZ = stone("smooth_quartz", Blocks.SMOOTH_QUARTZ_SLAB, TextureSet.all("quartz_block_bottom"), "smooth_quartz");
    public static final VerticalSlabDefinition SMOOTH_RED_SANDSTONE = stone("smooth_red_sandstone", Blocks.SMOOTH_RED_SANDSTONE_SLAB, TextureSet.all("red_sandstone_top"), "smooth_red_sandstone");
    public static final VerticalSlabDefinition SMOOTH_SANDSTONE = stone("smooth_sandstone", Blocks.SMOOTH_SANDSTONE_SLAB, TextureSet.all("sandstone_top"), "smooth_sandstone");
    public static final VerticalSlabDefinition MOSSY_COBBLESTONE = stone("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE_SLAB, TextureSet.all("mossy_cobblestone"), "mossy_cobblestone");
    public static final VerticalSlabDefinition MOSSY_STONE_BRICK = stone("mossy_stone_brick", Blocks.MOSSY_STONE_BRICK_SLAB, TextureSet.all("mossy_stone_bricks"), "mossy_stone_bricks");
    public static final VerticalSlabDefinition GRANITE = stone("granite", Blocks.GRANITE_SLAB, TextureSet.all("granite"), "granite");
    public static final VerticalSlabDefinition POLISHED_GRANITE = stone("polished_granite", Blocks.POLISHED_GRANITE_SLAB, TextureSet.all("polished_granite"), "polished_granite");
    public static final VerticalSlabDefinition DIORITE = stone("diorite", Blocks.DIORITE_SLAB, TextureSet.all("diorite"), "diorite");
    public static final VerticalSlabDefinition POLISHED_DIORITE = stone("polished_diorite", Blocks.POLISHED_DIORITE_SLAB, TextureSet.all("polished_diorite"), "polished_diorite");
    public static final VerticalSlabDefinition ANDESITE = stone("andesite", Blocks.ANDESITE_SLAB, TextureSet.all("andesite"), "andesite");
    public static final VerticalSlabDefinition POLISHED_ANDESITE = stone("polished_andesite", Blocks.POLISHED_ANDESITE_SLAB, TextureSet.all("polished_andesite"), "polished_andesite");
    public static final VerticalSlabDefinition END_STONE_BRICK = stone("end_stone_brick", Blocks.END_STONE_BRICK_SLAB, TextureSet.all("end_stone_bricks"), "end_stone_bricks");
    public static final VerticalSlabDefinition RED_NETHER_BRICK = stone("red_nether_brick", Blocks.RED_NETHER_BRICK_SLAB, TextureSet.all("red_nether_bricks"), "red_nether_bricks");
    public static final VerticalSlabDefinition BLACKSTONE = stone("blackstone", Blocks.BLACKSTONE_SLAB, TextureSet.all("blackstone"), "blackstone");
    public static final VerticalSlabDefinition POLISHED_BLACKSTONE = stone("polished_blackstone", Blocks.POLISHED_BLACKSTONE_SLAB, TextureSet.all("polished_blackstone"), "polished_blackstone");
    public static final VerticalSlabDefinition POLISHED_BLACKSTONE_BRICK = stone("polished_blackstone_brick", Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, TextureSet.all("polished_blackstone_bricks"), "polished_blackstone_bricks");
    public static final VerticalSlabDefinition COBBLED_DEEPSLATE = stone("cobbled_deepslate", Blocks.COBBLED_DEEPSLATE_SLAB, TextureSet.all("cobbled_deepslate"), "cobbled_deepslate");
    public static final VerticalSlabDefinition POLISHED_DEEPSLATE = stone("polished_deepslate", Blocks.POLISHED_DEEPSLATE_SLAB, TextureSet.all("polished_deepslate"), "polished_deepslate");
    public static final VerticalSlabDefinition DEEPSLATE_TILE = stone("deepslate_tile", Blocks.DEEPSLATE_TILE_SLAB, TextureSet.all("deepslate_tiles"), "deepslate_tiles");
    public static final VerticalSlabDefinition DEEPSLATE_BRICK = stone("deepslate_brick", Blocks.DEEPSLATE_BRICK_SLAB, TextureSet.all("deepslate_bricks"), "deepslate_bricks");
    public static final VerticalSlabDefinition TUFF = stone("tuff", Blocks.TUFF_SLAB, TextureSet.all("tuff"), "tuff");
    public static final VerticalSlabDefinition POLISHED_TUFF = stone("polished_tuff", Blocks.POLISHED_TUFF_SLAB, TextureSet.all("polished_tuff"), "polished_tuff");
    public static final VerticalSlabDefinition TUFF_BRICK = stone("tuff_brick", Blocks.TUFF_BRICK_SLAB, TextureSet.all("tuff_bricks"), "tuff_bricks");

    // Flammable Overworld wood slabs.
    public static final VerticalSlabDefinition OAK = wood("oak", Blocks.OAK_SLAB, TextureSet.all("oak_planks"), "oak_planks", true);
    public static final VerticalSlabDefinition SPRUCE = wood("spruce", Blocks.SPRUCE_SLAB, TextureSet.all("spruce_planks"), "spruce_planks", true);
    public static final VerticalSlabDefinition BIRCH = wood("birch", Blocks.BIRCH_SLAB, TextureSet.all("birch_planks"), "birch_planks", true);
    public static final VerticalSlabDefinition JUNGLE = wood("jungle", Blocks.JUNGLE_SLAB, TextureSet.all("jungle_planks"), "jungle_planks", true);
    public static final VerticalSlabDefinition ACACIA = wood("acacia", Blocks.ACACIA_SLAB, TextureSet.all("acacia_planks"), "acacia_planks", true);
    public static final VerticalSlabDefinition CHERRY = wood("cherry", Blocks.CHERRY_SLAB, TextureSet.all("cherry_planks"), "cherry_planks", true);
    public static final VerticalSlabDefinition DARK_OAK = wood("dark_oak", Blocks.DARK_OAK_SLAB, TextureSet.all("dark_oak_planks"), "dark_oak_planks", true);
    public static final VerticalSlabDefinition MANGROVE = wood("mangrove", Blocks.MANGROVE_SLAB, TextureSet.all("mangrove_planks"), "mangrove_planks", true);
    public static final VerticalSlabDefinition BAMBOO = wood("bamboo", Blocks.BAMBOO_SLAB, TextureSet.all("bamboo_planks"), "bamboo_planks", true);
    public static final VerticalSlabDefinition BAMBOO_MOSAIC = wood("bamboo_mosaic", Blocks.BAMBOO_MOSAIC_SLAB, TextureSet.all("bamboo_mosaic"), "bamboo_mosaic", true);
    public static final VerticalSlabDefinition CRIMSON = wood("crimson", Blocks.CRIMSON_SLAB, TextureSet.all("crimson_planks"), "crimson_planks", false);
    public static final VerticalSlabDefinition WARPED = wood("warped", Blocks.WARPED_SLAB, TextureSet.all("warped_planks"), "warped_planks", false);

    // Cut copper, including every oxidation and waxed variant.
    public static final VerticalSlabDefinition CUT_COPPER = weatheringCopper("cut_copper", Blocks.CUT_COPPER_SLAB, TextureSet.all("cut_copper"), "cut_copper", WeatheringCopper.WeatherState.UNAFFECTED);
    public static final VerticalSlabDefinition EXPOSED_CUT_COPPER = weatheringCopper("exposed_cut_copper", Blocks.EXPOSED_CUT_COPPER_SLAB, TextureSet.all("exposed_cut_copper"), "exposed_cut_copper", WeatheringCopper.WeatherState.EXPOSED);
    public static final VerticalSlabDefinition WEATHERED_CUT_COPPER = weatheringCopper("weathered_cut_copper", Blocks.WEATHERED_CUT_COPPER_SLAB, TextureSet.all("weathered_cut_copper"), "weathered_cut_copper", WeatheringCopper.WeatherState.WEATHERED);
    public static final VerticalSlabDefinition OXIDIZED_CUT_COPPER = weatheringCopper("oxidized_cut_copper", Blocks.OXIDIZED_CUT_COPPER_SLAB, TextureSet.all("oxidized_cut_copper"), "oxidized_cut_copper", WeatheringCopper.WeatherState.OXIDIZED);
    public static final VerticalSlabDefinition WAXED_CUT_COPPER = stone("waxed_cut_copper", Blocks.WAXED_CUT_COPPER_SLAB, TextureSet.all("cut_copper"), "cut_copper");
    public static final VerticalSlabDefinition WAXED_EXPOSED_CUT_COPPER = stone("waxed_exposed_cut_copper", Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, TextureSet.all("exposed_cut_copper"), "exposed_cut_copper");
    public static final VerticalSlabDefinition WAXED_WEATHERED_CUT_COPPER = stone("waxed_weathered_cut_copper", Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, TextureSet.all("weathered_cut_copper"), "weathered_cut_copper");
    public static final VerticalSlabDefinition WAXED_OXIDIZED_CUT_COPPER = stone("waxed_oxidized_cut_copper", Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB, TextureSet.all("oxidized_cut_copper"), "oxidized_cut_copper");

    public static final List<VerticalSlabDefinition> DEFINITIONS = Collections.unmodifiableList(MUTABLE_DEFINITIONS);

    private ModBlocks() {}

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
    }

    public static void registerFlammability() {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        DEFINITIONS.stream().filter(VerticalSlabDefinition::flammable)
                .forEach(definition -> fire.setFlammable(definition.block().get(), 5, 20));
    }

    private static VerticalSlabDefinition stone(String material, Block source, TextureSet textures, String fullModel) {
        return registerVerticalSlab(material, source, textures, fullModel, Tool.PICKAXE, false);
    }

    private static VerticalSlabDefinition wood(String material, Block source, TextureSet textures, String fullModel, boolean flammable) {
        return registerVerticalSlab(material, source, textures, fullModel, Tool.AXE, flammable);
    }

    private static VerticalSlabDefinition weatheringCopper(String material, Block source, TextureSet textures,
                                                            String fullModel, WeatheringCopper.WeatherState age) {
        String id = "vertical_" + material + "_slab";
        DeferredBlock<VerticalSlabBlock> block = BLOCKS.register(id,
                () -> new WeatheringVerticalSlabBlock(age, BlockBehaviour.Properties.ofFullCopy(source).noOcclusion()));
        return finishRegistration(material, source, textures, fullModel, Tool.PICKAXE, false, id, block);
    }

    private static VerticalSlabDefinition registerVerticalSlab(String material, Block source, TextureSet textures,
                                                                String fullModel, Tool tool, boolean flammable) {
        String id = "vertical_" + material + "_slab";
        DeferredBlock<VerticalSlabBlock> block = BLOCKS.register(id,
                () -> new VerticalSlabBlock(BlockBehaviour.Properties.ofFullCopy(source).noOcclusion()));
        return finishRegistration(material, source, textures, fullModel, tool, flammable, id, block);
    }

    private static VerticalSlabDefinition finishRegistration(String material, Block source, TextureSet textures,
                                                               String fullModel, Tool tool, boolean flammable,
                                                               String id, DeferredBlock<VerticalSlabBlock> block) {
        DeferredItem<BlockItem> item = ITEMS.registerSimpleBlockItem(id, block);
        VerticalSlabDefinition definition = new VerticalSlabDefinition(
                material,
                source,
                block,
                item,
                textures,
                ResourceLocation.fromNamespaceAndPath("minecraft", "block/" + fullModel),
                tool,
                flammable
        );
        MUTABLE_DEFINITIONS.add(definition);
        return definition;
    }
}
