package dev.forgeeverything.everythingores.mixin.stellaris;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.forgeeverything.everythingores.compat.StellarisFluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/** Rover fuel slot takes the Everything Ores diesel bucket instead of Stellaris' own. */
@Pseudo
@Mixin(targets = "com.st0x0ef.stellaris.common.menus.slot.VehicleFuelSlot", remap = false)
public abstract class StellarisVehicleFuelMixin {

    @ModifyExpressionValue(
            method = "mayPlace",
            at = @At(value = "INVOKE", target = "Ldev/architectury/registry/registries/RegistrySupplier;get()Ljava/lang/Object;"))
    private Object everythingores$swap(Object original) {
        return StellarisFluids.swap(original);
    }
}
