package dev.forgeeverything.everythingores.mixin.industrialforegoing;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.forgeeverything.everythingores.compat.IFBiofuel;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/** Bioreactor produces Everything Ores biofuel, and its output tank only accepts it. */
@Pseudo
@Mixin(targets = "com.buuz135.industrial.block.generator.tile.BioReactorTile", remap = false)
public abstract class BioReactorTileMixin {

    // work(): fills the output tank with new FluidStack(BIOFUEL, amount)
    @ModifyExpressionValue(
            method = "work",
            at = @At(value = "INVOKE",
                    target = "Lcom/hrznstudio/titanium/fluid/TitaniumFluidInstance;getSourceFluid()Lnet/neoforged/neoforge/registries/DeferredHolder;"))
    private DeferredHolder<?, ?> everythingores$outputBiofuel(DeferredHolder<?, ?> original) {
        return IFBiofuel.swap(original);
    }

    // Output tank validator lambda
    @ModifyExpressionValue(
            method = "lambda$new$3",
            at = @At(value = "INVOKE",
                    target = "Lcom/hrznstudio/titanium/fluid/TitaniumFluidInstance;getSourceFluid()Lnet/neoforged/neoforge/registries/DeferredHolder;"))
    private static DeferredHolder<?, ?> everythingores$tankFilter(DeferredHolder<?, ?> original) {
        return IFBiofuel.swap(original);
    }
}
