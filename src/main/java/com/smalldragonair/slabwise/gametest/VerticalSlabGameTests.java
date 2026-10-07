package com.smalldragonair.slabwise.gametest;

import com.smalldragonair.slabwise.Slabwise;
import com.smalldragonair.slabwise.block.VerticalSlabBlock;
import com.smalldragonair.slabwise.block.VerticalSlabType;
import com.smalldragonair.slabwise.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@GameTestHolder(Slabwise.MOD_ID)
@PrefixGameTestTemplate(false)
public final class VerticalSlabGameTests {
    private static final BlockPos TEST_POS = new BlockPos(3, 1, 3);

    private VerticalSlabGameTests() {}

    @GameTest(template = "empty")
    public static void orientationShapes(GameTestHelper helper) {
        assertBounds(helper, VerticalSlabType.WEST, new AABB(0, 0, 0, 0.5, 1, 1));
        assertBounds(helper, VerticalSlabType.EAST, new AABB(0.5, 0, 0, 1, 1, 1));
        assertBounds(helper, VerticalSlabType.NORTH, new AABB(0, 0, 0, 1, 1, 0.5));
        assertBounds(helper, VerticalSlabType.SOUTH, new AABB(0, 0, 0.5, 1, 1, 1));
        assertBounds(helper, VerticalSlabType.DOUBLE, new AABB(0, 0, 0, 1, 1, 1));
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void horizontalSidePlacement(GameTestHelper helper) {
        helper.setBlock(TEST_POS, Blocks.STONE);
        BlockPlaceContext context = context(helper, TEST_POS, Direction.EAST,
                helper.absoluteVec(new Vec3(4, 1.5, 3.5)));
        BlockState placed = ModBlocks.STONE.block().get().getStateForPlacement(context);
        helper.assertValueEqual(placed.getValue(VerticalSlabBlock.TYPE), VerticalSlabType.WEST, "east-face placement type");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void topFaceCursorPlacement(GameTestHelper helper) {
        BlockPos support = new BlockPos(3, 0, 3);
        helper.setBlock(support, Blocks.STONE);
        BlockPlaceContext context = context(helper, support, Direction.UP,
                helper.absoluteVec(new Vec3(3.1, 1, 3.6)));
        BlockState placed = ModBlocks.STONE.block().get().getStateForPlacement(context);
        helper.assertValueEqual(placed.getValue(VerticalSlabBlock.TYPE), VerticalSlabType.WEST, "top-face nearest edge");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void bottomFaceCursorPlacement(GameTestHelper helper) {
        BlockPos support = new BlockPos(3, 2, 3);
        helper.setBlock(support, Blocks.STONE);
        BlockPlaceContext context = context(helper, support, Direction.DOWN,
                helper.absoluteVec(new Vec3(3.6, 2, 3.9)));
        BlockState placed = ModBlocks.STONE.block().get().getStateForPlacement(context);
        helper.assertValueEqual(placed.getValue(VerticalSlabBlock.TYPE), VerticalSlabType.SOUTH, "bottom-face nearest edge");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void westEastCombine(GameTestHelper helper) {
        assertCombines(helper, VerticalSlabType.WEST, Direction.EAST, new Vec3(3.5, 1.5, 3.5));
    }

    @GameTest(template = "empty")
    public static void northSouthCombine(GameTestHelper helper) {
        assertCombines(helper, VerticalSlabType.NORTH, Direction.SOUTH, new Vec3(3.5, 1.5, 3.5));
    }

    @GameTest(template = "empty")
    public static void perpendicularHalvesDoNotCombine(GameTestHelper helper) {
        VerticalSlabBlock block = ModBlocks.STONE.block().get();
        helper.setBlock(TEST_POS, block.defaultBlockState().setValue(VerticalSlabBlock.TYPE, VerticalSlabType.WEST));
        BlockPlaceContext context = context(helper, TEST_POS, Direction.UP,
                helper.absoluteVec(new Vec3(3.75, 2, 3.1)));
        helper.assertFalse(context.replacingClickedOnBlock(), "perpendicular placement must target the adjacent block");
        helper.assertValueEqual(helper.getBlockState(TEST_POS).getValue(VerticalSlabBlock.TYPE), VerticalSlabType.WEST,
                "existing perpendicular half");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void waterlogging(GameTestHelper helper) {
        BlockPos support = TEST_POS.west();
        helper.setBlock(support, Blocks.STONE);
        helper.setBlock(TEST_POS, Blocks.WATER);
        BlockPlaceContext context = context(helper, support, Direction.EAST,
                helper.absoluteVec(new Vec3(3, 1.5, 3.5)));
        BlockState placed = ModBlocks.STONE.block().get().getStateForPlacement(context);
        helper.assertTrue(placed.getValue(VerticalSlabBlock.WATERLOGGED), "placement in water must waterlog a half slab");
        BlockState doubled = placed.setValue(VerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE)
                .setValue(VerticalSlabBlock.WATERLOGGED, false);
        helper.assertFalse(doubled.getFluidState().isSource(), "double slab must not contain source water");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void doubleDropsTwoItems(GameTestHelper helper) {
        VerticalSlabBlock block = ModBlocks.STONE.block().get();
        helper.setBlock(TEST_POS, block.defaultBlockState().setValue(VerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE));
        helper.getLevel().destroyBlock(helper.absolutePos(TEST_POS), true);
        helper.runAfterDelay(1, () -> {
            helper.assertItemEntityCountIs(block.asItem(), TEST_POS, 1.5, 2);
            helper.succeed();
        });
    }

    @GameTest(template = "empty")
    public static void connectionBlocksRespectOccupiedFace(GameTestHelper helper) {
        VerticalSlabBlock block = ModBlocks.STONE.block().get();
        BlockState west = block.defaultBlockState().setValue(VerticalSlabBlock.TYPE, VerticalSlabType.WEST);

        BlockPos slab = new BlockPos(3, 1, 2);
        helper.setBlock(slab.west(), Blocks.OAK_FENCE);
        helper.setBlock(slab.east(), Blocks.OAK_FENCE);
        helper.setBlock(slab, west);
        helper.assertTrue(helper.getBlockState(slab.west()).getValue(FenceBlock.EAST), "fence should connect to occupied west face");
        helper.assertFalse(helper.getBlockState(slab.east()).getValue(FenceBlock.WEST), "fence must not connect through empty east face");

        slab = new BlockPos(3, 1, 4);
        helper.setBlock(slab.west(), Blocks.IRON_BARS);
        helper.setBlock(slab.east(), Blocks.IRON_BARS);
        helper.setBlock(slab, west);
        helper.assertTrue(helper.getBlockState(slab.west()).getValue(CrossCollisionBlock.EAST), "pane should connect to occupied west face");
        helper.assertFalse(helper.getBlockState(slab.east()).getValue(CrossCollisionBlock.WEST), "pane must not connect through empty east face");

        slab = new BlockPos(3, 1, 6);
        helper.setBlock(slab.west(), Blocks.COBBLESTONE_WALL);
        helper.setBlock(slab.east(), Blocks.COBBLESTONE_WALL);
        helper.setBlock(slab, west);
        helper.assertTrue(helper.getBlockState(slab.west()).getValue(WallBlock.EAST_WALL) != WallSide.NONE,
                "wall should connect to occupied west face");
        helper.assertValueEqual(helper.getBlockState(slab.east()).getValue(WallBlock.WEST_WALL), WallSide.NONE,
                "wall must not connect through empty east face");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void occupiedOuterFaceIsSturdy(GameTestHelper helper) {
        BlockState west = ModBlocks.STONE.block().get().defaultBlockState()
                .setValue(VerticalSlabBlock.TYPE, VerticalSlabType.WEST);
        helper.setBlock(TEST_POS, west);
        BlockPos absolute = helper.absolutePos(TEST_POS);
        helper.assertTrue(west.isFaceSturdy(helper.getLevel(), absolute, Direction.WEST, SupportType.FULL),
                "occupied outer face should provide full support");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void emptyOuterFaceIsNotSturdy(GameTestHelper helper) {
        BlockState west = ModBlocks.STONE.block().get().defaultBlockState()
                .setValue(VerticalSlabBlock.TYPE, VerticalSlabType.WEST);
        helper.setBlock(TEST_POS, west);
        BlockPos absolute = helper.absolutePos(TEST_POS);
        helper.assertFalse(west.isFaceSturdy(helper.getLevel(), absolute, Direction.EAST, SupportType.FULL),
                "empty outer face must not provide support");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void lanternUsesCenterSupport(GameTestHelper helper) {
        VerticalSlabBlock block = ModBlocks.STONE.block().get();
        BlockState half = block.defaultBlockState().setValue(VerticalSlabBlock.TYPE, VerticalSlabType.WEST);
        BlockState full = block.defaultBlockState().setValue(VerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE);
        BlockState lantern = Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, false);
        BlockPos lanternPos = TEST_POS.above();

        helper.setBlock(TEST_POS, half);
        helper.assertFalse(lantern.canSurvive(helper.getLevel(), helper.absolutePos(lanternPos)),
                "half-width top face must not fake center support");
        helper.setBlock(TEST_POS, full);
        helper.assertTrue(lantern.canSurvive(helper.getLevel(), helper.absolutePos(lanternPos)),
                "double slab should support a lantern");
        helper.succeed();
    }

    @GameTest(template = "empty")
    public static void neighborUpdateRemovesUnsupportedAttachment(GameTestHelper helper) {
        VerticalSlabBlock block = ModBlocks.STONE.block().get();
        BlockState west = block.defaultBlockState().setValue(VerticalSlabBlock.TYPE, VerticalSlabType.WEST);
        BlockPos torchPos = TEST_POS.west();
        BlockState torch = Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, Direction.WEST);

        helper.setBlock(TEST_POS, west);
        helper.setBlock(torchPos, torch);
        helper.assertTrue(torch.canSurvive(helper.getLevel(), helper.absolutePos(torchPos)),
                "wall torch should survive on occupied outer face");
        helper.setBlock(TEST_POS, Blocks.AIR);
        helper.succeedWhen(() -> helper.assertBlockPresent(Blocks.AIR, torchPos));
    }

    private static void assertCombines(GameTestHelper helper, VerticalSlabType initial, Direction clickedFace, Vec3 relativeHit) {
        VerticalSlabBlock block = ModBlocks.STONE.block().get();
        helper.setBlock(TEST_POS, block.defaultBlockState().setValue(VerticalSlabBlock.TYPE, initial));
        BlockPlaceContext context = context(helper, TEST_POS, clickedFace, helper.absoluteVec(relativeHit));
        helper.assertTrue(context.replacingClickedOnBlock(), "complementary half should replace the existing slab");
        BlockState placed = block.getStateForPlacement(context);
        helper.assertValueEqual(placed.getValue(VerticalSlabBlock.TYPE), VerticalSlabType.DOUBLE, "combined slab type");
        helper.assertFalse(placed.getValue(VerticalSlabBlock.WATERLOGGED), "combined slab must clear waterlogging");
        helper.succeed();
    }

    private static BlockPlaceContext context(GameTestHelper helper, BlockPos clickedPos, Direction face, Vec3 absoluteHit) {
        Block block = ModBlocks.STONE.block().get();
        return new BlockPlaceContext(helper.getLevel(), null, InteractionHand.MAIN_HAND, new ItemStack(block),
                new BlockHitResult(absoluteHit, face, helper.absolutePos(clickedPos), false));
    }

    private static void assertBounds(GameTestHelper helper, VerticalSlabType type, AABB expected) {
        helper.assertValueEqual(VerticalSlabBlock.shapeFor(type).bounds(), expected, type + " shape");
    }
}
