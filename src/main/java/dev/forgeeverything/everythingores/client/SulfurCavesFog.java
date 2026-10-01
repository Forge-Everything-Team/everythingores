package dev.forgeeverything.everythingores.client;

import com.mojang.blaze3d.shaders.FogShape;
import dev.forgeeverything.everythingores.EverythingOres;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.material.FogType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;

/**
 * The thick yellow haze of the sulfur caves.
 *
 * The biome's own fog_color already tints vanilla fog, but vanilla keeps cave
 * fog out at the edge of render distance and darkens it at night. While the
 * camera is inside the sulfur caves this pulls the fog in close and holds it
 * at a sulfurous yellow, day or night.
 *
 * The effect fades in and out over about a second (BLEND_SECONDS) rather
 * than snapping at the biome border. It stands aside under water or lava,
 * and for Blindness and Darkness, whose fog is left exactly as vanilla has it.
 */
@EventBusSubscriber(modid = EverythingOres.MOD_ID, value = Dist.CLIENT)
public final class SulfurCavesFog {

    private SulfurCavesFog() {}

    private static final ResourceKey<Biome> SULFUR_CAVES = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(EverythingOres.MOD_ID, "sulfur_caves"));

    /** Fog color inside the caves - a murky sulfur yellow. */
    private static final float FOG_R = 0.61F, FOG_G = 0.60F, FOG_B = 0.24F;
    /**
     * Fog distances inside the caves, in blocks. Never pushed further than
     * vanilla's. Haze starts at the camera and swallows everything by FOG_FAR:
     * roughly a fifth faded at 10 blocks, half at 22, near-opaque by 40 -
     * tuned against the reference shot of the pools.
     */
    private static final float FOG_NEAR = 0.0F, FOG_FAR = 44.0F;
    /**
     * Distances while a shader pack is active. Complementary builds its own
     * denser haze from these and SulfurCavesTint adds a yellow cast on top, so
     * the vanilla numbers above bury the scene - these are pulled well back.
     */
    private static final float SHADER_FOG_NEAR = 8.0F, SHADER_FOG_FAR = 80.0F;
    /** Roughly how long the fade takes on entering or leaving. */
    private static final float BLEND_SECONDS = 1.0F;

    /** 0 = vanilla fog, 1 = full sulfur fog. */
    private static float blend;
    private static long lastNanos;

    /** Fires every frame before RenderFog, so the fade is advanced here. */
    @SubscribeEvent
    public static void onFogColor(ViewportEvent.ComputeFogColor event) {
        updateBlend(event.getCamera());
        if (blend <= 0.0F || !affectsCamera(event.getCamera())) return;
        event.setRed(Mth.lerp(blend, event.getRed(), FOG_R));
        event.setGreen(Mth.lerp(blend, event.getGreen(), FOG_G));
        event.setBlue(Mth.lerp(blend, event.getBlue(), FOG_B));
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        if (blend <= 0.0F || event.getMode() != FogRenderer.FogMode.FOG_TERRAIN
                || event.getType() != FogType.NONE || !affectsCamera(event.getCamera())) {
            return;
        }
        boolean shaders = IrisHook.shaderPackInUse();
        float targetFar = shaders ? SHADER_FOG_FAR : FOG_FAR;
        float targetNear = shaders ? SHADER_FOG_NEAR : FOG_NEAR;
        float far = event.getFarPlaneDistance();
        float near = event.getNearPlaneDistance();
        event.setFarPlaneDistance(Mth.lerp(blend, far, Math.min(far, targetFar)));
        event.setNearPlaneDistance(Mth.lerp(blend, near, Math.min(near, targetNear)));
        // Vanilla terrain fog is a cylinder, which leaves the ceiling and floor
        // clear. A sphere hazes every direction, which reads right in a cave.
        if (blend > 0.5F) event.setFogShape(FogShape.SPHERE);
        event.setCanceled(true); // required for the new distances to apply
    }

    /** The current fade, 0 (vanilla) to 1 (full sulfur haze). Shared with SulfurCavesTint. */
    static float blend() {
        return blend;
    }

    /**
     * Advances the fade toward whether the camera is in the sulfur caves.
     * Safe to call more than once a frame - the step is based on elapsed
     * time - so both the fog events and the tint layer can drive it, and it
     * keeps moving even when a shader pack skips the vanilla fog.
     */
    static void updateBlend(Camera camera) {
        long now = System.nanoTime();
        float dt = lastNanos == 0 ? 0.0F : Math.min((now - lastNanos) / 1.0e9F, 0.25F);
        lastNanos = now;

        Minecraft mc = Minecraft.getInstance();
        boolean inCaves = mc.level != null
                && mc.level.getBiome(camera.getBlockPosition()).is(SULFUR_CAVES);
        float target = inCaves ? 1.0F : 0.0F;
        // Exponential ease toward the target: frame-rate independent.
        blend += (target - blend) * (1.0F - (float) Math.exp(-dt * 3.0F / BLEND_SECONDS));
        if (Math.abs(target - blend) < 0.001F) blend = target;
    }

    /** False under water or lava, or while Blindness or Darkness owns the fog. */
    static boolean affectsCamera(Camera camera) {
        if (camera.getFluidInCamera() != FogType.NONE) return false;
        var player = Minecraft.getInstance().player;
        return player == null
                || !(player.hasEffect(MobEffects.BLINDNESS) || player.hasEffect(MobEffects.DARKNESS));
    }
}
