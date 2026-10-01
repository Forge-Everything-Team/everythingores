package dev.forgeeverything.everythingores.mixin.stellaris;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.forgeeverything.everythingores.compat.StellarisFluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/** Diesel Generator burns Everything Ores diesel; the Pumpjack extracts Everything Ores crude oil. */
@Pseudo
@Mixin(targets = {
        "com.st0x0ef.stellaris.common.blocks.entities.machines.DieselGeneratorBlockEntity",
        "com.st0x0ef.stellaris.common.blocks.entities.machines.PumpjackBlockEntity"
}, remap = false)
public abstract class StellarisMachineTickMixin {

    @ModifyExpressionValue(
            method = "tick",
            at = @At(value = "INVOKE", target = "Ldev/architectury/registry/registries/RegistrySupplier;get()Ljava/lang/Object;"))
    private Object everythingores$swap(Object original) {
        return StellarisFluids.swap(original);
    }
}
