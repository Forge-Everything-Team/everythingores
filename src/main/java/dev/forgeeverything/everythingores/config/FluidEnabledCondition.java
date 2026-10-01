package dev.forgeeverything.everythingores.config;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;

/**
 * Condition that drops a datapack entry when its fluid is switched off.
 *
 * Used on recipes and, more importantly, on the worldgen entries that name a
 * fluid block directly:
 *   "neoforge:conditions": [
 *     { "type": "everythingores:fluid_enabled", "fluid": "crude_oil" }
 *   ]
 *
 * A disabled fluid is not registered at all, so anything referencing its block
 * or bucket has to be skipped rather than left to fail resolving.
 */
public record FluidEnabledCondition(String fluid) implements ICondition {

    public static final MapCodec<FluidEnabledCondition> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            com.mojang.serialization.Codec.STRING.fieldOf("fluid").forGetter(FluidEnabledCondition::fluid)
    ).apply(i, FluidEnabledCondition::new));

    @Override
    public boolean test(IContext context) {
        return EOConfig.isFluidEnabled(fluid);
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
