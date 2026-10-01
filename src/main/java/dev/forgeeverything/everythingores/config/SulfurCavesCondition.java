package dev.forgeeverything.everythingores.config;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;

/**
 * Condition that keeps a datapack entry only when the sulfur_caves switch
 * matches.
 *
 * Switching to 26.2 style sulfur removes the sulfur and cinnabar ores
 * (Overworld and Nether) from the registry, so every entry that names those blocks directly —
 * their loot tables and their ore features — carries:
 *   "neoforge:conditions": [
 *     { "type": "everythingores:sulfur_caves", "enabled": false }
 *   ]
 */
public record SulfurCavesCondition(boolean enabled) implements ICondition {

    public static final MapCodec<SulfurCavesCondition> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            com.mojang.serialization.Codec.BOOL.fieldOf("enabled").forGetter(SulfurCavesCondition::enabled)
    ).apply(i, SulfurCavesCondition::new));

    @Override
    public boolean test(IContext context) {
        return EOConfig.isSulfurCavesEnabled() == enabled;
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
