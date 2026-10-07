package com.smalldragonair.slabwise.block;

import net.minecraft.util.StringRepresentable;

public enum VerticalSlabType implements StringRepresentable {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    DOUBLE;

    @Override
    public String getSerializedName() {
        return name().toLowerCase(java.util.Locale.ROOT);
    }

    public VerticalSlabType opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            case DOUBLE -> DOUBLE;
        };
    }

    public boolean isComplementary(VerticalSlabType other) {
        return this != DOUBLE && other == opposite();
    }
}
