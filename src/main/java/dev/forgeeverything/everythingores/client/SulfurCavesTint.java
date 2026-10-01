package dev.forgeeverything.everythingores.client;

import dev.forgeeverything.everythingores.EverythingOres;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

/**
 * A faint yellow wash over the screen inside the sulfur caves - for shader
 * packs only.
 *
 * Shader packs (Iris) draw fog their own way and drop the fog color, so the
 * caves would otherwise lose their yellow. This layer is drawn under the HUD,
 * after the shader pass, so it always shows. Without a shader pack the real
 * fog already does the job and this stays off, so the two never stack.
 *
 * It fades with the same blend as the fog. Iris is reached by reflection, so
 * there is no dependency on it; if the lookup fails the tint simply stays off.
 */
@EventBusSubscriber(modid = EverythingOres.MOD_ID, value = Dist.CLIENT)
public final class SulfurCavesTint {

    private SulfurCavesTint() {}

    /** Tint color (RGB) - the same sulfur yellow as the fog. */
    private static final int TINT_RGB = 0x9C9A3C;
    /**
     * Opacity at full strength, 0-1. Kept light: Complementary still draws
     * distance fog from SulfurCavesFog's shader-mode distances, so this only
     * needs to add the yellow cast on top.
     */
    private static final float MAX_ALPHA = 0.10F;

    @SubscribeEvent
    public static void registerLayers(RegisterGuiLayersEvent event) {
        event.registerBelowAll(ResourceLocation.fromNamespaceAndPath(EverythingOres.MOD_ID, "sulfur_caves_tint"),
                SulfurCavesTint::render);
    }

    private static void render(GuiGraphics graphics, DeltaTracker delta) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || !IrisHook.shaderPackInUse()) return;

        Camera camera = mc.gameRenderer.getMainCamera();
        // Shader packs may skip the fog events that normally advance the fade.
        SulfurCavesFog.updateBlend(camera);
        float blend = SulfurCavesFog.blend();
        if (blend <= 0.0F || !SulfurCavesFog.affectsCamera(camera)) return;

        int alpha = Math.round(blend * MAX_ALPHA * 255.0F);
        // guiOverlay: no depth test or depth write. The default gui type would
        // stamp the whole screen's depth, hiding HUD drawn at a similar z
        // afterwards - Jade's tooltip vanished inside the caves because of it.
        graphics.fill(RenderType.guiOverlay(), 0, 0, graphics.guiWidth(), graphics.guiHeight(),
                (alpha << 24) | TINT_RGB);
    }
}
