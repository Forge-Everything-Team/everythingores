package dev.forgeeverything.everythingores.mixin.industrialforegoing;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.forgeeverything.everythingores.compat.IFBiofuel;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/** JEI's Bioreactor category shows Everything Ores biofuel as the output. */
@Pseudo
@Mixin(targets = "com.buuz135.industrial.plugin.RecipeViewerHelper", remap = false)
public abstract class RecipeViewerHelperMixin {

    @ModifyExpressionValue(
            method = "generateBioreactorRecipes",
            at = @At(value = "INVOKE",
                    target = "Lcom/hrznstudio/titanium/fluid/TitaniumFluidInstance;getSourceFluid()Lnet/neoforged/neoforge/registries/DeferredHolder;"))
    private static DeferredHolder<?, ?> everythingores$output(DeferredHolder<?, ?> original) {
        return IFBiofuel.swap(original);
    }
}
