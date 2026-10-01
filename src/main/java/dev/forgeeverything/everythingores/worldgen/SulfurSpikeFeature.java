package dev.forgeeverything.everythingores.worldgen;

import com.mojang.serialization.Codec;
import dev.forgeeverything.everythingores.block.SulfurSpikeBlock;
import dev.forgeeverything.everythingores.registry.EOBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * A single sulfur spike column, grown into the air at the origin.
 *
 * The direction comes from what the origin is touching: under a ceiling it
 * hangs as a stalactite, otherwise on a floor it grows as a stalagmite. That
 * lets one feature serve both the floor and ceiling sulfur patches, which
 * place it as their vegetation.
 *
 * Columns are 1-3 blocks, occasionally up to 5, and stop short at the first
 * non-air block, so they never cut through the opposite surface.
 */
public class SulfurSpikeFeature extends Feature<NoneFeatureConfiguration> {

    private static final int MAX_LENGTH = 5;

    public SulfurSpikeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        RandomSource random = ctx.random();
        BlockPos origin = ctx.origin();

        // Sulfur switched off in the config means no block to place.
        if (EOBlocks.SULFUR_SPIKE == null || !level.isEmptyBlock(origin)) {
            return false;
        }
        SulfurSpikeBlock spike = EOBlocks.SULFUR_SPIKE.get();

        Direction tip;
        if (level.getBlockState(origin.above()).isFaceSturdy(level, origin.above(), Direction.DOWN)) {
            tip = Direction.DOWN;
        } else if (level.getBlockState(origin.below()).isFaceSturdy(level, origin.below(), Direction.UP)) {
            tip = Direction.UP;
        } else {
            return false;
        }

        int wanted = 1 + random.nextInt(3) + (random.nextInt(4) == 0 ? 2 : 0);
        wanted = Math.min(wanted, MAX_LENGTH);
        int length = 0;
        while (length < wanted && level.isEmptyBlock(origin.relative(tip, length))) {
            length++;
        }

        for (int i = 0; i < length; i++) {
            level.setBlock(origin.relative(tip, i), spike.stateFor(tip, thickness(i, length)), 2);
        }
        return length > 0;
    }

    /** Dripstone's taper: base at the root, middles, then frustum and tip. */
    private static DripstoneThickness thickness(int fromRoot, int length) {
        int fromTip = length - 1 - fromRoot;
        if (fromTip == 0) return DripstoneThickness.TIP;
        if (fromTip == 1) return DripstoneThickness.FRUSTUM;
        return fromRoot == 0 ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
    }
}
