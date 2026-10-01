package dev.forgeeverything.everythingores.mixin.stellaris;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.forgeeverything.everythingores.compat.StellarisFluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Tank validators: the Diesel Generator and space suits accept Everything Ores diesel,
 * and the Fuel Refinery's input tank accepts Everything Ores crude oil.
 */
@Pseudo
@Mixin(targets = {
        "com.st0x0ef.stellaris.common.blocks.entities.machines.DieselGeneratorBlockEntity$1",
        "com.st0x0ef.stellaris.common.blocks.entities.machines.FuelRefineryBlockEntity$1",
        "com.st0x0ef.stellaris.common.items.armors.AbstractSpaceArmor$AbstractSpaceChestplate$1",
        "com.st0x0ef.stellaris.common.items.armors.AbstractSpaceArmor$Chestplate$1",
        "com.st0x0ef.stellaris.common.items.armors.SpaceSuit$1"
}, remap = false)
public abstract class StellarisFluidValidatorMixin {

    @ModifyExpressionValue(
            method = "isFluidValid",
            at = @At(value = "INVOKE", target = "Ldev/architectury/registry/registries/RegistrySupplier;get()Ljava/lang/Object;"))
    private Object everythingores$swap(Object original) {
        return StellarisFluids.swap(original);
    }
}
