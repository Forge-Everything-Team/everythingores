package dev.forgeeverything.everythingores.mixin.industrialforegoing;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.forgeeverything.everythingores.compat.IFBiofuel;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/** Biofuel Generator's input tank accepts Everything Ores biofuel. */
@Pseudo
@Mixin(targets = "com.buuz135.industrial.block.generator.tile.BiofuelGeneratorTile", remap = false)
public abstract class BiofuelGeneratorTileMixin {

    // Input tank validator lambda
    @ModifyExpressionValue(
            method = "lambda$new$0",
            at = @At(value = "INVOKE",
                    target = "Lcom/hrznstudio/titanium/fluid/TitaniumFluidInstance;getSourceFluid()Lnet/neoforged/neoforge/registries/DeferredHolder;"))
    private static DeferredHolder<?, ?> everythingores$tankFilter(DeferredHolder<?, ?> original) {
        return IFBiofuel.swap(original);
    }
}
