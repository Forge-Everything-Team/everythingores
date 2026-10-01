package dev.forgeeverything.everythingores.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

/**
 * Sulfur spike — the sulfur caves' answer to pointed dripstone.
 *
 * Placed on a ceiling it hangs as a stalactite, on a floor it grows as a
 * stalagmite, and stacked spikes taper from base to tip exactly like
 * dripstone, sharing its blockstate properties so the models line up.
 *
 * Deliberately simpler than dripstone: it never drips, never grows, and an
 * unsupported stalactite breaks instead of falling. Landing on a stalagmite
 * tip still hurts.
 *
 * Vanilla's PointedDripstoneBlock hard-codes Blocks.POINTED_DRIPSTONE in its
 * neighbour checks, so this is a standalone block rather than a subclass.
 */
public class SulfurSpikeBlock extends Block implements SimpleWaterloggedBlock {

    public static final MapCodec<SulfurSpikeBlock> CODEC = simpleCodec(SulfurSpikeBlock::new);
    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape TIP_MERGE_SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
    private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
    private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
    private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape BASE_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    public SulfurSpikeBlock(BlockBehaviour.Properties props) {
        super(props);
        registerDefaultState(stateDefinition.any()
                .setValue(TIP_DIRECTION, Direction.UP)
                .setValue(THICKNESS, DripstoneThickness.TIP)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public MapCodec<SulfurSpikeBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
    }

    /** The state a spike at this position should have, pointing its tip in {@code tip}. */
    public BlockState stateFor(Direction tip, DripstoneThickness thickness) {
        return defaultBlockState().setValue(TIP_DIRECTION, tip).setValue(THICKNESS, thickness);
    }

    // ================================================================
    // SUPPORT AND SHAPE UPDATES
    // ================================================================

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return isValidPlacement(level, pos, state.getValue(TIP_DIRECTION));
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction dir, BlockState neighbor,
                                     LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        if (dir != Direction.UP && dir != Direction.DOWN) {
            return state;
        }

        Direction tip = state.getValue(TIP_DIRECTION);
        if (dir == tip.getOpposite() && !canSurvive(state, level, pos)) {
            // Break on the next tick rather than mid-update, so a whole column
            // unravels one spike at a time like dripstone does.
            level.scheduleTick(pos, this, 1);
            return state;
        }
        boolean merged = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
        return state.setValue(THICKNESS, calculateThickness(level, pos, tip, merged));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        LevelAccessor level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        Direction wanted = ctx.getNearestLookingVerticalDirection().getOpposite();

        Direction tip;
        if (isValidPlacement(level, pos, wanted)) {
            tip = wanted;
        } else if (isValidPlacement(level, pos, wanted.getOpposite())) {
            tip = wanted.getOpposite();
        } else {
            return null;
        }
        // Sneaking places an unmerged tip against an opposing tip, like dripstone.
        boolean merge = !ctx.isSecondaryUseActive();
        return stateFor(tip, calculateThickness(level, pos, tip, merge))
                .setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
    }

    private boolean isValidPlacement(LevelReader level, BlockPos pos, Direction tip) {
        BlockPos rootPos = pos.relative(tip.getOpposite());
        BlockState root = level.getBlockState(rootPos);
        return root.isFaceSturdy(level, rootPos, tip) || isSpikeWithDirection(root, tip);
    }

    /** Mirrors dripstone: tip, then frustum, then middle sections, with a base at the root. */
    private DripstoneThickness calculateThickness(LevelReader level, BlockPos pos, Direction tip, boolean merge) {
        Direction back = tip.getOpposite();
        BlockState ahead = level.getBlockState(pos.relative(tip));
        if (isSpikeWithDirection(ahead, back)) {
            return !merge && ahead.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE
                    ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        }
        if (!isSpikeWithDirection(ahead, tip)) {
            return DripstoneThickness.TIP;
        }
        DripstoneThickness aheadThickness = ahead.getValue(THICKNESS);
        if (aheadThickness == DripstoneThickness.TIP || aheadThickness == DripstoneThickness.TIP_MERGE) {
            return DripstoneThickness.FRUSTUM;
        }
        BlockState behind = level.getBlockState(pos.relative(back));
        return isSpikeWithDirection(behind, tip) ? DripstoneThickness.MIDDLE : DripstoneThickness.BASE;
    }

    private static boolean isSpikeWithDirection(BlockState state, Direction tip) {
        return state.getBlock() instanceof SulfurSpikeBlock && state.getValue(TIP_DIRECTION) == tip;
    }

    // ================================================================
    // INTERACTION
    // ================================================================

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.getValue(TIP_DIRECTION) == Direction.UP && state.getValue(THICKNESS) == DripstoneThickness.TIP) {
            entity.causeFallDamage(fallDistance + 2.0F, 2.0F, level.damageSources().stalagmite());
        } else {
            super.fallOn(level, state, pos, entity, fallDistance);
        }
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (level.isClientSide) return;
        BlockPos pos = hit.getBlockPos();
        if (projectile.mayInteract(level, pos) && projectile.mayBreak(level)
                && projectile instanceof ThrownTrident && projectile.getDeltaMovement().length() > 0.6) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    // ================================================================
    // SHAPE AND FLUID
    // ================================================================

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        VoxelShape shape = switch (state.getValue(THICKNESS)) {
            case TIP_MERGE -> TIP_MERGE_SHAPE;
            case TIP -> state.getValue(TIP_DIRECTION) == Direction.DOWN ? TIP_SHAPE_DOWN : TIP_SHAPE_UP;
            case FRUSTUM -> FRUSTUM_SHAPE;
            case MIDDLE -> MIDDLE_SHAPE;
            case BASE -> BASE_SHAPE;
        };
        Vec3 offset = state.getOffset(level, pos);
        return shape.move(offset.x, 0.0, offset.z);
    }

    @Override
    protected boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    protected float getMaxHorizontalOffset() {
        return 0.125F;
    }
}
