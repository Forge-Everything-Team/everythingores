package dev.forgeeverything.everythingores.registry;

import dev.forgeeverything.everythingores.EverythingOres;
import dev.forgeeverything.everythingores.worldgen.CavePillarFeature;
import dev.forgeeverything.everythingores.worldgen.CrudeOilGeyserFeature;
import dev.forgeeverything.everythingores.worldgen.SulfurPoolFeature;
import dev.forgeeverything.everythingores.worldgen.SulfurSpikeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers the custom worldgen feature types owned by Everything Ores.
 *
 * Crude oil generates two ways:
 *   - Lakes   — plain minecraft:lake features, defined entirely in JSON under
 *               worldgen/configured_feature. No code needed for those.
 *   - Geysers — this feature type, since a reservoir plus a vent column is not
 *               something any vanilla feature can express.
 *
 * Sulfur caves use three more: spike columns (vanilla's dripstone features are
 * hard-wired to dripstone), pools over a bed of potent sulfur, and floor-to-
 * ceiling pillars in sulfur or cinnabar. The sulfur walls and floor/ceiling
 * patches themselves are plain JSON.
 */
public class EOFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, EverythingOres.MOD_ID);

    public static final DeferredHolder<Feature<?>, CrudeOilGeyserFeature> CRUDE_OIL_GEYSER =
            FEATURES.register("crude_oil_geyser",
                    () -> new CrudeOilGeyserFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, SulfurSpikeFeature> SULFUR_SPIKE =
            FEATURES.register("sulfur_spike",
                    () -> new SulfurSpikeFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, SulfurPoolFeature> SULFUR_POOL =
            FEATURES.register("sulfur_pool",
                    () -> new SulfurPoolFeature(NoneFeatureConfiguration.CODEC));

    public static final DeferredHolder<Feature<?>, CavePillarFeature> CAVE_PILLAR =
            FEATURES.register("cave_pillar",
                    () -> new CavePillarFeature(CavePillarFeature.Config.CODEC));
}
