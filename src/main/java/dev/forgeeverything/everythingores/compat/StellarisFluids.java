package dev.forgeeverything.everythingores.compat;

import dev.forgeeverything.everythingores.registry.EOFluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

/**
 * Shared swap for the Stellaris mixins.
 *
 * Stellaris reads its diesel, oil and diesel bucket through Architectury's
 * RegistrySupplier.get(), which returns Object. The mixins wrap that call and
 * hand back the Everything Ores equivalent, matched by registry id, so every
 * other registry lookup in the same method passes through untouched and the
 * handlers need no compile dependency on Stellaris or Architectury.
 */
public final class StellarisFluids {

    private static final ResourceLocation DIESEL = stellaris("diesel");
    private static final ResourceLocation OIL = stellaris("oil");
    private static final ResourceLocation DIESEL_BUCKET = stellaris("diesel_bucket");

    private StellarisFluids() {}

    public static Object swap(Object original) {
        if (original instanceof Fluid fluid) {
            ResourceLocation id = BuiltInRegistries.FLUID.getKey(fluid);
            if (DIESEL.equals(id) && EOFluids.DIESEL != null) return EOFluids.DIESEL.source().get();
            if (OIL.equals(id) && EOFluids.CRUDE_OIL != null) return EOFluids.CRUDE_OIL.source().get();
        } else if (original instanceof Item item) {
            if (DIESEL_BUCKET.equals(BuiltInRegistries.ITEM.getKey(item)) && EOFluids.DIESEL != null) return EOFluids.DIESEL.bucket().get();
        }
        return original;
    }

    private static ResourceLocation stellaris(String path) {
        return ResourceLocation.fromNamespaceAndPath("stellaris", path);
    }
}
