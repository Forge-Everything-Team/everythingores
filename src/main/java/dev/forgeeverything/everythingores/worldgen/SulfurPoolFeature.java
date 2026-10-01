package dev.forgeeverything.everythingores.worldgen;

import com.mojang.serialization.Codec;
import dev.forgeeverything.everythingores.registry.EOBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * A sulfur pool: a small round basin sunk into a cave floor, filled with
 * water over a bed of potent sulfur and ringed with blocks of sulfur, flecked
 * with cinnabar when cinnabar is enabled.
 *
 * Cross-section, with the origin sitting in the air just above the floor:
 *
 *     air   air   air   air   air
 *    [S]   ~~~   ~~~   ~~~   [S]     S = block of sulfur (or cinnabar) rim
 *    [S]   ~~~   ~~~   ~~~   [S]     ~ = water, 1-2 deep
 *    [S]   [P]   [P]   [P]   [S]     P = potent sulfur
 *
 * The whole footprint, rim included, must be solid opaque rock down to the
 * potent sulfur layer, or the pool is skipped: a basin that could leak into
 * a cave below would drain into a waterfall. Every write stays within
 * MAX_RADIUS + 1 of the origin, well inside the area worldgen allows.
 */
public class SulfurPoolFeature extends Feature<NoneFeatureConfiguration> {

    private static final int MIN_RADIUS = 2;
    private static final int MAX_RADIUS = 3;
    /** Share of rim columns built from cinnabar instead of sulfur. */
    private static final float CINNABAR_RIM_CHANCE = 0.45F;

    public SulfurPoolFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        RandomSource random = ctx.random();
        BlockPos origin = ctx.origin();

        // Sulfur switched off in the config means no block to place.
        if (EOBlocks.POTENT_SULFUR == null || EOBlocks.SULFUR_BLOCK == null) {
            return false;
        }
        if (!level.isEmptyBlock(origin)) {
            return false;
        }

        int radius = MIN_RADIUS + random.nextInt(MAX_RADIUS - MIN_RADIUS + 1);
        int depth = 1 + random.nextInt(2);
        double inner = radius + 0.5;
        double outer = radius + 1.5;
        BlockPos floor = origin.below();

        // Validate first: nothing is written unless the whole basin holds.
        for (int dx = -radius - 1; dx <= radius + 1; dx++) {
            for (int dz = -radius - 1; dz <= radius + 1; dz++) {
                if (Math.sqrt(dx * dx + dz * dz) > outer) continue;
                for (int dy = 0; dy <= depth; dy++) {
                    BlockPos p = floor.offset(dx, -dy, dz);
                    if (!level.getBlockState(p).isSolidRender(level, p)) {
                        return false;
                    }
                }
            }
        }

        BlockState water = Blocks.WATER.defaultBlockState();
        Block potentBlock = EOBlocks.POTENT_SULFUR.get();
        BlockState potent = potentBlock.defaultBlockState();
        BlockState sulfurRim = EOBlocks.SULFUR_BLOCK.get().defaultBlockState();
        BlockState cinnabarRim = EOBlocks.CINNABAR_BLOCK != null
                ? EOBlocks.CINNABAR_BLOCK.get().defaultBlockState() : sulfurRim;

        for (int dx = -radius - 1; dx <= radius + 1; dx++) {
            for (int dz = -radius - 1; dz <= radius + 1; dz++) {
                double d = Math.sqrt(dx * dx + dz * dz);
                if (d > outer) continue;
                if (d <= inner) {
                    for (int dy = 0; dy < depth; dy++) {
                        level.setBlock(floor.offset(dx, -dy, dz), water, 2);
                    }
                    BlockPos bed = floor.offset(dx, -depth, dz);
                    level.setBlock(bed, potent, 2);
                    // onPlace is not called during worldgen, so start the gas vent here.
                    level.scheduleTick(bed, potentBlock, 1 + random.nextInt(20));
                } else {
                    BlockState rim = random.nextFloat() < CINNABAR_RIM_CHANCE ? cinnabarRim : sulfurRim;
                    for (int dy = 0; dy <= depth; dy++) {
                        level.setBlock(floor.offset(dx, -dy, dz), rim, 2);
                    }
                }
            }
        }
        return true;
    }
}
