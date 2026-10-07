package com.smalldragonair.slabwise.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public final class WeatheringVerticalSlabBlock extends VerticalSlabBlock implements WeatheringCopper {
    public static final MapCodec<WeatheringVerticalSlabBlock> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    WeatherState.CODEC.fieldOf("weathering_state").forGetter(ChangeOverTimeBlock::getAge),
                    propertiesCodec()
            ).apply(instance, WeatheringVerticalSlabBlock::new));

    private final WeatherState age;

    public WeatheringVerticalSlabBlock(WeatherState age, Properties properties) {
        super(properties);
        this.age = age;
    }

    @Override
    protected MapCodec<? extends WeatheringVerticalSlabBlock> codec() {
        return CODEC;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        changeOverTime(state, level, pos, random);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    public WeatherState getAge() {
        return age;
    }
}
