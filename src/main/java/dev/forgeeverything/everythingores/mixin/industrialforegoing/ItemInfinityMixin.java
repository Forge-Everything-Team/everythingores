package dev.forgeeverything.everythingores.mixin.industrialforegoing;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.forgeeverything.everythingores.compat.IFBiofuel;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/** Infinity tools store, read and burn Everything Ores biofuel. */
@Pseudo
@Mixin(targets = "com.buuz135.industrial.item.infinity.ItemInfinity", remap = false)
public abstract class ItemInfinityMixin {

    private static final String GET_SOURCE_FLUID =
            "Lcom/hrznstudio/titanium/fluid/TitaniumFluidInstance;getSourceFluid()Lnet/neoforged/neoforge/registries/DeferredHolder;";

    @ModifyExpressionValue(
            method = {"getFuelFromStack", "consumeFuel"},
            at = @At(value = "INVOKE", target = GET_SOURCE_FLUID))
    private DeferredHolder<?, ?> everythingores$fuel(DeferredHolder<?, ?> original) {
        return IFBiofuel.swap(original);
    }

    // Tank definition lambdas: $14 is the fluid validator, $15 builds the tank
    @ModifyExpressionValue(
            method = {"lambda$getTankConstructor$14", "lambda$getTankConstructor$15"},
            at = @At(value = "INVOKE", target = GET_SOURCE_FLUID))
    private static DeferredHolder<?, ?> everythingores$tank(DeferredHolder<?, ?> original) {
        return IFBiofuel.swap(original);
    }
}
