package dev.forgeeverything.everythingores.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * Potent sulfur — the bed of a sulfur pool. It vents noxious gas.
 *
 * Every second it gives Nausea to any living entity in the gas column: the
 * block's own footprint plus one block around it, up to GAS_HEIGHT blocks
 * above. Standing on it counts too. A full solid block on top caps the vent.
 *
 * The vent runs on a self-rescheduling block tick, like magma's bubble
 * column. onPlace starts it for placed blocks; worldgen schedules it for the
 * blocks it writes, and a random tick restarts it if it was ever lost.
 */
public class PotentSulfurBlock extends Block {

    public static final MapCodec<PotentSulfurBlock> CODEC = simpleCodec(PotentSulfurBlock::new);

    /** Ticks between gas checks. */
    public static final int GAS_INTERVAL = 20;
    /** How long the Nausea lasts — long enough to outlast the next check. */
    private static final int NAUSEA_TICKS = 160;
    /** How far above the block the gas reaches. */
    private static final int GAS_HEIGHT = 4;
    /** Sickly yellow-green. */
    private static final int GAS_COLOR = 0xFFC8D24A;

    public PotentSulfurBlock(BlockBehaviour.Properties props) {
        super(props);
    }

    @Override
    public MapCodec<PotentSulfurBlock> codec() {
        return CODEC;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState old, boolean moved) {
        level.scheduleTick(pos, this, GAS_INTERVAL);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.getBlockTicks().hasScheduledTick(pos, this)) {
            level.scheduleTick(pos, this, GAS_INTERVAL);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isVenting(level, pos)) {
            AABB gas = new AABB(pos).inflate(1.0, 0.0, 1.0).expandTowards(0.0, GAS_HEIGHT, 0.0);
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, gas, e -> !e.isSpectator())) {
                poison(entity);
            }
        }
        level.scheduleTick(pos, this, GAS_INTERVAL);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!level.isClientSide && entity instanceof LivingEntity living) {
            poison(living);
        }
        super.stepOn(level, pos, state, entity);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!isVenting(level, pos)) return;

        // Bubbles rise through any water above, and the gas pools where the water ends.
        BlockPos top = pos.above();
        int depth = 0;
        while (depth < GAS_HEIGHT && level.getFluidState(top).is(FluidTags.WATER)) {
            if (random.nextInt(3) == 0) {
                level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_COLUMN_UP,
                        top.getX() + random.nextDouble(), top.getY() + random.nextDouble(),
                        top.getZ() + random.nextDouble(), 0.0, 0.04, 0.0);
            }
            top = top.above();
            depth++;
        }
        if (random.nextInt(2) == 0 && level.getBlockState(top).isAir()) {
            level.addParticle(ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, GAS_COLOR),
                    top.getX() + random.nextDouble(), top.getY() + random.nextDouble() * 0.5,
                    top.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
        }
    }

    private static boolean isVenting(Level level, BlockPos pos) {
        BlockPos above = pos.above();
        return !level.getBlockState(above).isCollisionShapeFullBlock(level, above);
    }

    private static void poison(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, NAUSEA_TICKS, 0, true, true));
    }
}
