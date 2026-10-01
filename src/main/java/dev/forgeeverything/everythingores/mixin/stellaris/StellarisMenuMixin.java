package dev.forgeeverything.everythingores.mixin.stellaris;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.forgeeverything.everythingores.compat.StellarisFluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/** Diesel Generator and Fuel Refinery screens accept Everything Ores diesel / crude oil containers. */
@Pseudo
@Mixin(targets = {
        "com.st0x0ef.stellaris.common.menus.DieselGeneratorMenu",
        "com.st0x0ef.stellaris.common.menus.FuelRefineryMenu"
}, remap = false)
public abstract class StellarisMenuMixin {

    // Static: the get() call builds an argument to super(...), where `this` is still
    // uninitialised. An instance handler there fails class verification (VerifyError).
    @ModifyExpressionValue(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Ldev/architectury/registry/registries/RegistrySupplier;get()Ljava/lang/Object;"))
    private static Object everythingores$swap(Object original) {
        return StellarisFluids.swap(original);
    }
}
