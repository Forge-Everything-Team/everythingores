package dev.forgeeverything.everythingores.compat;

import dev.forgeeverything.everythingores.registry.EOFluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.fluids.FluidStack;

/**
 * Shared swap for the Immersive Engineering mixins.
 *
 * IE's coke oven builds its output as new FluidStack(IEFluids.CREOSOTE.getStill(), amount).
 * The mixins wrap that construction and hand back the same amount of Everything Ores
 * creosote, so the handlers only touch NeoForge types — no compile dependency on IE.
 */
public final class IECreosote {

    private static final ResourceLocation IE_CREOSOTE =
            ResourceLocation.fromNamespaceAndPath("immersiveengineering", "creosote");

    private IECreosote() {}

    public static FluidStack swap(FluidStack original) {
        if (EOFluids.CREOSOTE == null) return original;
        if (IE_CREOSOTE.equals(BuiltInRegistries.FLUID.getKey(original.getFluid()))) {
            return new FluidStack(EOFluids.CREOSOTE.source().get(), original.getAmount());
        }
        return original;
    }
}
