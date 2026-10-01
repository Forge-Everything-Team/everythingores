package dev.forgeeverything.everythingores.worldgen;

import com.mojang.serialization.Codec;
import dev.forgeeverything.everythingores.registry.EOFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * A crude oil geyser: a buried reservoir with a single vent column rising to
 * the surface, where it opens into a small pool and a plume.
 *
 * Shape, from the bottom up:
 *   1. Reservoir — a flattened ellipsoid of oil source blocks, carved only into
 *      solid rock so existing caves are left intact.
 *   2. Vent      — a one-block column from the reservoir roof to the surface.
 *      Any air the column passes through (a cave, a ravine) is sheathed in
 *      stone on all four sides, so the oil cannot drain sideways on the way up.
 *   3. Basin     — the vent mouth and its four orthogonal neighbours, sunk
 *      flush into the terrain. These are the blocks the plume spills into on
 *      its first block update, pre-filled so the spill merges into standing
 *      oil rather than creeping across the ground.
 *   4. Plume     — a single column continuing up out of the basin into open sky.
 *
 * Sites are scored on how flat they are rather than gated on it: level ground
 * always passes, a cliff never does, and a slope passes on a falling chance.
 * Oceans are allowed — a seabed reads as flat, and seabed wells give the ocean
 * a reason to be mined.
 *
 * Every write is clamped to MAX_RADIUS of the origin column, which keeps the
 * feature inside the 3x3 chunk area worldgen allows it to touch.
 */
public class CrudeOilGeyserFeature extends Feature<NoneFeatureConfiguration> {

    /** Horizontal reach cap — must stay well inside one chunk of the origin. */
    private static final int MAX_RADIUS = 8;

    /** Give up rather than build a vent taller than this. */
    private static final int MAX_VENT_HEIGHT = 160;

    /** How far the plume rises above ground level. */
    private static final int MIN_PLUME = 6;
    private static final int MAX_PLUME = 18;

    /** How far out the ground is sampled when judging how flat a site is. */
    private static final int RELIEF_SAMPLE_RADIUS = 2;
    /** Height spread at or below which a site counts as flat and always passes. */
    private static final int FLAT_RELIEF = 1;
    /** Height spread beyond which a site is always rejected as a cliff. */
    private static final int MAX_RELIEF = 6;

    public CrudeOilGeyserFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        WorldGenLevel level = ctx.level();
        RandomSource random = ctx.random();
        BlockPos origin = ctx.origin();

        // Crude oil switched off in the config means no block to place. The
        // datapack entries carry a fluid_enabled condition so this feature
        // should never be reached, but the feature type itself is still
        // registered and a pack could point something else at it.
        if (EOFluids.CRUDE_OIL == null) {
            return false;
        }

        BlockState oil = EOFluids.CRUDE_OIL.block().get().defaultBlockState();
        BlockState stone = Blocks.STONE.defaultBlockState();

        int surfaceY = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, origin.getX(), origin.getZ());

        // Reservoir sits below the origin; bail out if there is no room for a vent.
        int reservoirY = origin.getY();
        if (surfaceY - reservoirY < 8 || surfaceY - reservoirY > MAX_VENT_HEIGHT) {
            return false;
        }

        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        // Prefer flat ground without insisting on it. Sites are scored by the
        // height spread across the footprint: flat always passes, cliffs never
        // do, and everything between passes on a falling chance. A hard flatness
        // test was tried first and rejected almost every candidate.
        int relief = surfaceRelief(level, origin, RELIEF_SAMPLE_RADIUS);
        if (relief > MAX_RELIEF) {
            return false;
        }
        if (relief > FLAT_RELIEF) {
            // p = (MAX_RELIEF - relief + 1) / (MAX_RELIEF - FLAT_RELIEF + 1)
            // relief 2 -> 5/6, 3 -> 4/6, 4 -> 3/6, 5 -> 2/6, 6 -> 1/6
            int span = MAX_RELIEF - FLAT_RELIEF + 1;
            if (random.nextInt(span) >= MAX_RELIEF - relief + 1) {
                return false;
            }
        }

        int radiusX = 4 + random.nextInt(4);   // 4..7
        int radiusZ = 4 + random.nextInt(4);   // 4..7
        int radiusY = 2 + random.nextInt(3);   // 2..4

        // ── 1. Reservoir ─────────────────────────────────────────────────────
        int placed = 0;
        for (int dx = -radiusX; dx <= radiusX; dx++) {
            for (int dz = -radiusZ; dz <= radiusZ; dz++) {
                for (int dy = -radiusY; dy <= radiusY; dy++) {
                    double d = (double) (dx * dx) / (radiusX * radiusX)
                             + (double) (dz * dz) / (radiusZ * radiusZ)
                             + (double) (dy * dy) / (radiusY * radiusY);
                    if (d > 1.0D) continue;

                    cursor.set(origin.getX() + dx, reservoirY + dy, origin.getZ() + dz);
                    if (level.isOutsideBuildHeight(cursor.getY())) continue;
                    if (!isCarvable(level.getBlockState(cursor))) continue;

                    level.setBlock(cursor, oil, 2);
                    placed++;
                }
            }
        }
        if (placed == 0) return false;

        // ── 2. Vent ──────────────────────────────────────────────────────────
        for (int y = reservoirY + radiusY; y < surfaceY; y++) {
            cursor.set(origin.getX(), y, origin.getZ());
            if (level.isOutsideBuildHeight(y)) break;
            if (isBedrock(level.getBlockState(cursor))) continue;

            // Sheath the column so it cannot bleed into open space on the way up.
            for (int[] side : new int[][] { {1, 0}, {-1, 0}, {0, 1}, {0, -1} }) {
                BlockPos wall = new BlockPos(origin.getX() + side[0], y, origin.getZ() + side[1]);
                BlockState at = level.getBlockState(wall);
                if (at.isAir() || !at.getFluidState().isEmpty()) {
                    level.setBlock(wall, stone, 2);
                }
            }
            level.setBlock(cursor, oil, 2);
        }

        // ── 3. Catch basin ───────────────────────────────────────────────────
        // The vent mouth plus its four orthogonal neighbours, sunk into the top
        // layer of terrain so they sit flush with the ground.
        //
        // This is not trying to dam the oil in. It is pre-filling the exact
        // blocks the plume would spill into on its first block update, so the
        // spill has somewhere to land and merges into standing oil instead of
        // creeping outward. Cheap, and it lets a geyser drop almost anywhere
        // rather than demanding a flat site — of which there were far too few.
        int poolY = surfaceY - 1;
        int[][] basin = { {0, 0}, {1, 0}, {-1, 0}, {0, 1}, {0, -1} };
        for (int[] offset : basin) {
            cursor.set(origin.getX() + offset[0], poolY, origin.getZ() + offset[1]);
            if (level.isOutsideBuildHeight(cursor.getY())) continue;
            if (!isCarvable(level.getBlockState(cursor))) continue;

            level.setBlock(cursor, oil, 2);
        }

        // ── 4. Plume ─────────────────────────────────────────────────────────
        // The visible part: a single column continuing up out of the pool into
        // open sky. Stops early if it runs into an overhang or a cliff face, so
        // it never punches through terrain.
        // Starts at surfaceY, the first air block: the pool now sits one lower,
        // flush with the ground, so starting higher would leave a gap.
        int plumeHeight = MIN_PLUME + random.nextInt(MAX_PLUME - MIN_PLUME + 1);
        for (int i = 0; i < plumeHeight; i++) {
            int y = surfaceY + i;
            if (level.isOutsideBuildHeight(y)) break;

            cursor.set(origin.getX(), y, origin.getZ());
            BlockState at = level.getBlockState(cursor);
            if (!at.isAir() && at.getFluidState().isEmpty()) break;

            level.setBlock(cursor, oil, 2);
        }

        return true;
    }

    /**
     * Height spread of the ground around a point — 0 on a flat plain, large on
     * a cliff face. Uses the ocean floor heightmap, so a seabed reads as flat
     * regardless of the water above it.
     */
    private static int surfaceRelief(WorldGenLevel level, BlockPos origin, int radius) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                int h = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG,
                        origin.getX() + dx, origin.getZ() + dz);
                min = Math.min(min, h);
                max = Math.max(max, h);
            }
        }
        return max - min;
    }

    /** Solid rock we are allowed to replace — leaves caves, water and bedrock alone. */
    private static boolean isCarvable(BlockState state) {
        return !state.isAir() && state.getFluidState().isEmpty() && !isBedrock(state);
    }

    private static boolean isBedrock(BlockState state) {
        return state.is(Blocks.BEDROCK);
    }
}
