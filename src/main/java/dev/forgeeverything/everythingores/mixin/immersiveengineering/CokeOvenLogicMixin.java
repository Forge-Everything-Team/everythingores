package dev.forgeeverything.everythingores.mixin.immersiveengineering;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.forgeeverything.everythingores.compat.IECreosote;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

/** The coke oven fills its tank with Everything Ores creosote. */
@Pseudo
@Mixin(targets = "blusunrize.immersiveengineering.common.blocks.multiblocks.logic.CokeOvenLogic", remap = false)
public abstract class CokeOvenLogicMixin {

    @ModifyExpressionValue(
            method = "tickServer",
            at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/material/Fluid;I)Lnet/neoforged/neoforge/fluids/FluidStack;"))
    private FluidStack everythingores$creosote(FluidStack original) {
        return IECreosote.swap(original);
    }
}
