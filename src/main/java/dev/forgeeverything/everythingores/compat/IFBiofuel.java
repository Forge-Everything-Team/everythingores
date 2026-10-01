package dev.forgeeverything.everythingores.compat;

import dev.forgeeverything.everythingores.registry.EOFluids;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

/**
 * Shared swap for the Industrial Foregoing mixins.
 *
 * IF reads its biofuel through ModuleCore.BIOFUEL.getSourceFluid() everywhere
 * (Bioreactor output, Biofuel Generator filter, Infinity tool fuel, JEI). The
 * mixins wrap that call and hand back Everything Ores biofuel instead, so the
 * handlers only ever touch NeoForge types — no compile dependency on IF or
 * Titanium.
 */
public final class IFBiofuel {

    private static final ResourceLocation IF_BIOFUEL =
            ResourceLocation.fromNamespaceAndPath("industrialforegoing", "biofuel");

    private IFBiofuel() {}

    public static DeferredHolder<?, ?> swap(DeferredHolder<?, ?> original) {
        if (EOFluids.BIOFUEL == null) return original;
        return IF_BIOFUEL.equals(original.getId()) ? EOFluids.BIOFUEL.source() : original;
    }
}
