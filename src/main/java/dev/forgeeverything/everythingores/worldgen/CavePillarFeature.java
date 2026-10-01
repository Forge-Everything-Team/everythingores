package dev.forgeeverything.everythingores.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/**
 * A solid pillar joining a cave floor to its ceiling, built from one block —
 * the sulfur caves use it twice, once in sulfur and once in cinnabar.
 *
 *   { "type": "everythingores:cave_pillar",
 *     "config": { "state": { "Name": "everythingores:cinnabar_block" } } }
 *
 * The origin is the air just above a floor. The feature climbs through air
 * until it meets a sturdy ceiling; if there is none within MAX_HEIGHT, or the
 * gap is shorter than MIN_HEIGHT, nothing is placed. Half the pillars are
 * 2x2 thick instead of 1x1, and each gets a ragged footing and cap where it
 * meets the rock. Only air is ever replaced, so pillars never cut into walls.
 */
public class CavePillarFeature extends Feature<CavePillarFeature.Config> {

    public record Config(BlockState state) implements FeatureConfiguration {
        public static final Codec<Config> CODEC =
                BlockState.CODEC.fieldOf("state").xmap(Config::new, Config::state).codec();
    }

    private static final int MIN_HEIGHT = 3;
    private static final int MAX_HEIGHT = 20;
    /** Chance each side of the footing and cap gets a flare block. */
    private static final float FLARE_CHANCE = 0.5F;

    public CavePillarFeature(Codec<Config> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<Config> ctx) {
        WorldGenLevel level = ctx.level();
        RandomSource random = ctx.random();
        BlockPos origin = ctx.origin();
        BlockState state = ctx.config().state();

        if (!level.isEmptyBlock(origin)
                || !level.getBlockState(origin.below()).isFaceSturdy(level, origin.below(), Direction.UP)) {
            return false;
        }

        // Find the ceiling.
        int height = 0;
        while (height < MAX_HEIGHT && level.isEmptyBlock(origin.above(height))) {
            height++;
        }
        BlockPos ceiling = origin.above(height);
        if (height < MIN_HEIGHT || height >= MAX_HEIGHT
                || !level.getBlockState(ceiling).isFaceSturdy(level, ceiling, Direction.DOWN)) {
            return false;
        }

        boolean thick = random.nextBoolean();
        int size = thick ? 2 : 1;
        for (int dx = 0; dx < size; dx++) {
            for (int dz = 0; dz < size; dz++) {
                fillColumn(level, origin.offset(dx, 0, dz), height, state);
            }
        }

        // Ragged footing and cap: single blocks against the floor and ceiling
        // around the pillar's edge.
        for (int dx = -1; dx <= size; dx++) {
            for (int dz = -1; dz <= size; dz++) {
                boolean inside = dx >= 0 && dx < size && dz >= 0 && dz < size;
                boolean corner = (dx == -1 || dx == size) && (dz == -1 || dz == size);
                if (inside || corner) continue;
                if (random.nextFloat() < FLARE_CHANCE) {
                    flare(level, origin.offset(dx, 0, dz), Direction.DOWN, state);
                }
                if (random.nextFloat() < FLARE_CHANCE) {
                    flare(level, origin.offset(dx, height - 1, dz), Direction.UP, state);
                }
            }
        }
        return true;
    }

    /** Fills air upward from the floor, stopping at the first solid block. */
    private static void fillColumn(WorldGenLevel level, BlockPos base, int height, BlockState state) {
        for (int dy = 0; dy < height; dy++) {
            BlockPos p = base.above(dy);
            if (!level.isEmptyBlock(p)) break;
            level.setBlock(p, state, 2);
        }
    }

    /** One flare block, only in air and only against the surface it grows from. */
    private static void flare(WorldGenLevel level, BlockPos pos, Direction toward, BlockState state) {
        BlockPos support = pos.relative(toward);
        if (level.isEmptyBlock(pos)
                && level.getBlockState(support).isFaceSturdy(level, support, toward.getOpposite())) {
            level.setBlock(pos, state, 2);
        }
    }
}
