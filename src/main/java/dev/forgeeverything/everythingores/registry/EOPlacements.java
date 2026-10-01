package dev.forgeeverything.everythingores.registry;

import dev.forgeeverything.everythingores.EverythingOres;
import dev.forgeeverything.everythingores.worldgen.ConfigGatePlacement;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers the placement modifier types owned by Everything Ores.
 *
 * everythingores:config_gate is attached to every ore placed_feature so a
 * material switched off in the config simply stops generating.
 */
public class EOPlacements {

    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIERS =
            DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, EverythingOres.MOD_ID);

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<ConfigGatePlacement>> CONFIG_GATE =
            PLACEMENT_MODIFIERS.register("config_gate", () -> () -> ConfigGatePlacement.CODEC);
}
