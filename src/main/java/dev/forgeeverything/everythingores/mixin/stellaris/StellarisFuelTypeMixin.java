package dev.forgeeverything.everythingores.mixin.stellaris;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.forgeeverything.everythingores.compat.StellarisFluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/** Vehicles treat the Everything Ores diesel bucket as diesel when refuelling. */
@Pseudo
@Mixin(targets = "com.st0x0ef.stellaris.common.vehicle_upgrade.FuelType$Type", remap = false)
public abstract class StellarisFuelTypeMixin {

    @ModifyExpressionValue(
            method = "getTypeBasedOnItem",
            at = @At(value = "INVOKE", target = "Ldev/architectury/registry/registries/RegistrySupplier;get()Ljava/lang/Object;"))
    private static Object everythingores$swap(Object original) {
        return StellarisFluids.swap(original);
    }
}
