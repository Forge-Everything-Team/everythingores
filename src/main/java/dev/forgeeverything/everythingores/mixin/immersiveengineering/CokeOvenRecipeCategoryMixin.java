package dev.forgeeverything.everythingores.mixin.immersiveengineering;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.forgeeverything.everythingores.compat.IECreosote;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/** JEI's coke oven category shows Everything Ores creosote as the output. */
@Pseudo
@Mixin(targets = "blusunrize.immersiveengineering.common.util.compat.jei.cokeoven.CokeOvenRecipeCategory", remap = false)
public abstract class CokeOvenRecipeCategoryMixin {

    @ModifyExpressionValue(
            method = "setRecipe",
            at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/material/Fluid;I)Lnet/neoforged/neoforge/fluids/FluidStack;"))
    private FluidStack everythingores$creosote(FluidStack original) {
        return IECreosote.swap(original);
    }
}
