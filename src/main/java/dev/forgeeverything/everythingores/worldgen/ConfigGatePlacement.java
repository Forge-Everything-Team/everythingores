package dev.forgeeverything.everythingores.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.forgeeverything.everythingores.config.EOConfig;
import dev.forgeeverything.everythingores.config.EOMaterials;
import dev.forgeeverything.everythingores.registry.EOPlacements;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Placement modifier that drops a feature entirely when what it places is
 * switched off in the config.
 *
 * Gates on either a material or a fluid:
 *   { "type": "everythingores:config_gate", "material": "tin", "dimension": "nether" }
 *   { "type": "everythingores:config_gate", "fluid": "crude_oil" }
 *
 * An optional "sulfur_caves" flag further requires the sulfur_caves switch to
 * match it, so the sulfur ores and the sulfur caves swap places:
 *   { "type": "everythingores:config_gate", "material": "sulfur", "sulfur_caves": true }
 *
 * It sits first in the placement list, so a disabled feature short-circuits
 * before any of the expensive placement work runs.
 */
public class ConfigGatePlacement extends PlacementModifier {

    public static final MapCodec<ConfigGatePlacement> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.STRING.optionalFieldOf("material").forGetter(p -> p.material),
            Codec.STRING.optionalFieldOf("dimension").forGetter(p -> p.dimension),
            Codec.STRING.optionalFieldOf("family").forGetter(p -> p.family),
            Codec.STRING.optionalFieldOf("fluid").forGetter(p -> p.fluid),
            Codec.BOOL.optionalFieldOf("sulfur_caves").forGetter(p -> p.sulfurCaves)
    ).apply(i, ConfigGatePlacement::new));

    private final Optional<String> material;
    private final Optional<String> dimension;
    private final Optional<String> family;
    private final Optional<String> fluid;
    private final Optional<Boolean> sulfurCaves;

    public ConfigGatePlacement(Optional<String> material, Optional<String> dimension,
                               Optional<String> family, Optional<String> fluid,
                               Optional<Boolean> sulfurCaves) {
        this.material = material;
        this.dimension = dimension;
        this.family = family;
        this.fluid = fluid;
        this.sulfurCaves = sulfurCaves;
    }

    private boolean enabled() {
        if (sulfurCaves.isPresent() && sulfurCaves.get() != EOConfig.isSulfurCavesEnabled()) {
            return false;
        }
        if (fluid.isPresent()) {
            return EOConfig.isFluidEnabled(fluid.get());
        }
        if (material.isEmpty()) {
            return true;
        }
        EOMaterials.Dimension dim = null;
        if (dimension.isPresent()) {
            try {
                dim = EOMaterials.Dimension.valueOf(dimension.get().toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException ignored) {
                // An unknown dimension name gates on the material alone rather than failing worldgen.
            }
        }
        return EOConfig.isOreEnabled(material.get(), dim, family.orElse(null));
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
        return enabled() ? Stream.of(pos) : Stream.empty();
    }

    @Override
    public PlacementModifierType<?> type() {
        return EOPlacements.CONFIG_GATE.get();
    }
}
