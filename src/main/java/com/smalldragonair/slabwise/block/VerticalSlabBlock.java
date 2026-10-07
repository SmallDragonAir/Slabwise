package com.smalldragonair.slabwise.block;

import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VerticalSlabBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<VerticalSlabBlock> CODEC = simpleCodec(VerticalSlabBlock::new);
    public static final EnumProperty<VerticalSlabType> TYPE = EnumProperty.create("type", VerticalSlabType.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape WEST_SHAPE = Block.box(0, 0, 0, 8, 16, 16);
    public static final VoxelShape EAST_SHAPE = Block.box(8, 0, 0, 16, 16, 16);
    public static final VoxelShape NORTH_SHAPE = Block.box(0, 0, 0, 16, 16, 8);
    public static final VoxelShape SOUTH_SHAPE = Block.box(0, 0, 8, 16, 16, 16);

    public VerticalSlabBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(TYPE, VerticalSlabType.WEST)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends VerticalSlabBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeFor(state.getValue(TYPE));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeFor(state.getValue(TYPE));
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return shapeFor(state.getValue(TYPE));
    }

    @Override
    protected VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeFor(state.getValue(TYPE));
    }

    public static VoxelShape shapeFor(VerticalSlabType type) {
        return switch (type) {
            case WEST -> WEST_SHAPE;
            case EAST -> EAST_SHAPE;
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case DOUBLE -> Block.box(0, 0, 0, 16, 16, 16);
        };
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return state.getValue(TYPE) != VerticalSlabType.DOUBLE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState existing = context.getLevel().getBlockState(context.getClickedPos());
        if (existing.is(this)) {
            return existing.setValue(TYPE, VerticalSlabType.DOUBLE).setValue(WATERLOGGED, false);
        }

        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState()
                .setValue(TYPE, typeForNewPlacement(context))
                .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
    }

    public static VerticalSlabType typeForNewPlacement(BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        if (face.getAxis().isHorizontal()) {
            return fromDirection(face.getOpposite());
        }
        return nearestEdge(context.getClickLocation(), context.getClickedPos(), context.getHorizontalDirection());
    }

    public static VerticalSlabType nearestEdge(Vec3 hit, BlockPos pos, Direction playerFacing) {
        double x = hit.x - pos.getX();
        double z = hit.z - pos.getZ();
        double min = Math.min(Math.min(x, 1.0 - x), Math.min(z, 1.0 - z));

        // On exact ties, prefer the edge facing the player; fixed WEST/EAST/NORTH/SOUTH
        // ordering provides a deterministic fallback for corner and synthetic contexts.
        VerticalSlabType facing = fromDirection(playerFacing);
        if (distanceTo(facing, x, z) == min) {
            return facing;
        }
        if (x == min) return VerticalSlabType.WEST;
        if (1.0 - x == min) return VerticalSlabType.EAST;
        if (z == min) return VerticalSlabType.NORTH;
        return VerticalSlabType.SOUTH;
    }

    private static double distanceTo(VerticalSlabType type, double x, double z) {
        return switch (type) {
            case WEST -> x;
            case EAST -> 1.0 - x;
            case NORTH -> z;
            case SOUTH -> 1.0 - z;
            case DOUBLE -> Double.POSITIVE_INFINITY;
        };
    }

    public static VerticalSlabType fromDirection(Direction direction) {
        return switch (direction) {
            case NORTH -> VerticalSlabType.NORTH;
            case SOUTH -> VerticalSlabType.SOUTH;
            case EAST -> VerticalSlabType.EAST;
            case WEST -> VerticalSlabType.WEST;
            default -> throw new IllegalArgumentException("Vertical direction has no vertical slab half: " + direction);
        };
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (state.getValue(TYPE) == VerticalSlabType.DOUBLE || !context.getItemInHand().is(asItem())) {
            return false;
        }

        if (!context.replacingClickedOnBlock()) {
            return true;
        }

        VerticalSlabType occupied = state.getValue(TYPE);
        Direction face = context.getClickedFace();
        if (face.getAxis().isHorizontal()) {
            return fromDirection(face) == occupied.opposite();
        }

        VerticalSlabType intended = nearestEdge(context.getClickLocation(), context.getClickedPos(),
                context.getHorizontalDirection());
        return occupied.isComplementary(intended);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        return state.getValue(TYPE) != VerticalSlabType.DOUBLE
                && SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluidState);
    }

    @Override
    public boolean canPlaceLiquid(@Nullable Player player, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return state.getValue(TYPE) != VerticalSlabType.DOUBLE
                && SimpleWaterloggedBlock.super.canPlaceLiquid(player, level, pos, state, fluid);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                     LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return type == PathComputationType.WATER && state.getFluidState().is(net.minecraft.tags.FluidTags.WATER);
    }
}
