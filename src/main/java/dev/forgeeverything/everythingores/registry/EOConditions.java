package dev.forgeeverything.everythingores.registry;

import com.mojang.serialization.MapCodec;
import dev.forgeeverything.everythingores.EverythingOres;
import dev.forgeeverything.everythingores.config.FluidEnabledCondition;
import dev.forgeeverything.everythingores.config.MaterialEnabledCondition;
import dev.forgeeverything.everythingores.config.SulfurCavesCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * Registers the recipe conditions owned by Everything Ores.
 *
 * everythingores:material_enabled guards every recipe whose output belongs to
 * a material a pack can switch off.
 */
public class EOConditions {

    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, EverythingOres.MOD_ID);

    static {
        CONDITIONS.register("material_enabled", () -> MaterialEnabledCondition.CODEC);
        CONDITIONS.register("fluid_enabled", () -> FluidEnabledCondition.CODEC);
        CONDITIONS.register("sulfur_caves", () -> SulfurCavesCondition.CODEC);
    }
}
