package dev.forgeeverything.everythingores.config;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;

/**
 * Recipe condition that skips a recipe when its material is switched off.
 *
 * Used as:
 *   "neoforge:conditions": [
 *     { "type": "everythingores:material_enabled", "material": "aluminum" }
 *   ]
 *
 * Conditions are evaluated during datapack load, which is why the config
 * backing this is a STARTUP spec — it is already loaded by then.
 */
public record MaterialEnabledCondition(String material) implements ICondition {

    public static final MapCodec<MaterialEnabledCondition> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            com.mojang.serialization.Codec.STRING.fieldOf("material").forGetter(MaterialEnabledCondition::material)
    ).apply(i, MaterialEnabledCondition::new));

    @Override
    public boolean test(IContext context) {
        return EOConfig.isMaterialEnabled(material);
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
