package dev.forgeeverything.everythingores.compat;

import com.mojang.datafixers.util.Pair;
import dev.forgeeverything.everythingores.EverythingOres;
import dev.forgeeverything.everythingores.config.EOConfig;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.Regions;

import java.util.function.Consumer;

/**
 * TerraBlender region that places the everythingores:sulfur_caves biome.
 *
 * The region is vanilla's Overworld layout unchanged, plus one cave biome
 * point. Like lush and dripstone caves, sulfur caves only exist underground:
 * the depth span below puts them roughly 60-110 blocks under the surface,
 * which over typical terrain is Y 0 to -32, matching 26.2. Warm, dry and
 * inland climates keep them clear of lush caves (humid) and dripstone caves
 * (far inland).
 *
 * Only registered while sulfur_caves is on. This class references
 * TerraBlender directly, so nothing may touch it unless TerraBlender is
 * loaded — see EverythingOres#commonSetup.
 */
public class SulfurCavesRegion extends Region {

    public static final ResourceKey<Biome> SULFUR_CAVES = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(EverythingOres.MOD_ID, "sulfur_caves"));

    public SulfurCavesRegion(int weight) {
        super(ResourceLocation.fromNamespaceAndPath(EverythingOres.MOD_ID, "sulfur_caves"),
                RegionType.OVERWORLD, weight);
    }

    /** Registers the region. Call from enqueued common setup work. */
    public static void register() {
        Regions.register(new SulfurCavesRegion(EOConfig.sulfurCavesWeight()));
    }

    @Override
    public void addBiomes(Registry<Biome> registry,
                          Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        addModifiedVanillaOverworldBiomes(mapper, builder -> {});
        addBiome(mapper, Climate.parameters(
                Climate.Parameter.span(0.2F, 1.0F),    // temperature: warm
                Climate.Parameter.span(-1.0F, 0.1F),   // humidity: dry
                Climate.Parameter.span(0.03F, 0.8F),   // continentalness: inland, short of dripstone
                Climate.Parameter.span(-1.0F, 1.0F),   // erosion: any
                Climate.Parameter.span(0.45F, 0.85F),  // depth: ~60-110 blocks down
                Climate.Parameter.span(-1.0F, 1.0F),   // weirdness: any
                0.0F), SULFUR_CAVES);
    }
}
