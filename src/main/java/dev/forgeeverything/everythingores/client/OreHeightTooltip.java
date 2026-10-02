package dev.forgeeverything.everythingores.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.forgeeverything.everythingores.EverythingOres;
import dev.forgeeverything.everythingores.config.EOConfig;
import dev.forgeeverything.everythingores.config.EOMaterials;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * "Found between Y -32 and Y 80" on the tooltip of every ore block.
 *
 * The range is read from the ore's own placed feature in the mod jar, so it
 * always matches worldgen: an ore block maps to the placed feature of the same
 * name, with the deepslate_ prefix dropped (deepslate_tin_ore -> tin_ore).
 * Placed features are server data and never reach the client registries,
 * hence reading the JSON directly.
 *
 * The feature's config_gate is checked against the config too, so an ore
 * that's switched off for its dimension (or swapped out by ore_source or the
 * sulfur caves) shows no range instead of a wrong one.
 */
@EventBusSubscriber(modid = EverythingOres.MOD_ID, value = Dist.CLIENT)
public final class OreHeightTooltip {

    private OreHeightTooltip() {}

    private static final String PLACED_FEATURE_DIR =
            "/data/" + EverythingOres.MOD_ID + "/worldgen/placed_feature/";

    /** Parsed once per ore; empty when the block has no readable range. */
    private static final Map<Item, Optional<Range>> CACHE = new HashMap<>();

    /** A Y range plus the config gate that decides whether it generates at all. */
    private record Range(int minY, int maxY, EOMaterials.Dimension dimension,
                         String material, String family, Boolean sulfurCaves) {

        boolean generates() {
            if (sulfurCaves != null && sulfurCaves != EOConfig.isSulfurCavesEnabled()) return false;
            return material == null || EOConfig.isOreEnabled(material, dimension, family);
        }
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        if (!(event.getItemStack().getItem() instanceof BlockItem item)) return;
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(item);
        if (!id.getNamespace().equals(EverythingOres.MOD_ID) || !id.getPath().endsWith("_ore")) return;

        Range range = CACHE.computeIfAbsent(item, i -> load(id.getPath())).orElse(null);
        if (range == null || !range.generates()) return;

        String key = range.dimension == null || range.dimension == EOMaterials.Dimension.OVERWORLD
                ? "tooltip.everythingores.found_between"
                : "tooltip.everythingores.found_between." + range.dimension.key();
        // Index 1 keeps it under the name, above other mods' lines (mod name, EMC...).
        event.getToolTip().add(Math.min(1, event.getToolTip().size()),
                Component.translatable(key, range.minY, range.maxY).withStyle(ChatFormatting.GOLD));
    }

    private static Optional<Range> load(String blockPath) {
        String feature = blockPath.startsWith("deepslate_") ? blockPath.substring("deepslate_".length()) : blockPath;
        try (InputStream in = OreHeightTooltip.class.getResourceAsStream(PLACED_FEATURE_DIR + feature + ".json")) {
            if (in == null) return Optional.empty(); // no worldgen for this ore (yet)
            JsonObject root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();

            JsonObject height = null, gate = null;
            for (JsonElement e : root.getAsJsonArray("placement")) {
                JsonObject p = e.getAsJsonObject();
                String type = p.get("type").getAsString();
                if (type.equals("minecraft:height_range")) height = p.getAsJsonObject("height");
                else if (type.equals(EverythingOres.MOD_ID + ":config_gate")) gate = p;
            }
            if (height == null || !height.has("min_inclusive") || !height.has("max_inclusive")) {
                return Optional.empty();
            }

            EOMaterials.Dimension dim = null;
            String gateDim = string(gate, "dimension");
            if (gateDim != null) {
                try {
                    dim = EOMaterials.Dimension.valueOf(gateDim.toUpperCase(Locale.ROOT));
                } catch (IllegalArgumentException ignored) {
                    // Unknown dimension - show the range without a dimension name.
                }
            }
            int bottom = dim == null || dim == EOMaterials.Dimension.OVERWORLD ? -64 : 0;
            Integer min = anchor(height.getAsJsonObject("min_inclusive"), bottom);
            Integer max = anchor(height.getAsJsonObject("max_inclusive"), bottom);
            if (min == null || max == null) return Optional.empty();

            Boolean caves = gate != null && gate.has("sulfur_caves") ? gate.get("sulfur_caves").getAsBoolean() : null;
            return Optional.of(new Range(min, max, dim, string(gate, "material"), string(gate, "family"), caves));
        } catch (Exception e) {
            EverythingOres.LOGGER.warn("Couldn't read the Y range for {}", blockPath, e);
            return Optional.empty();
        }
    }

    /** A vertical anchor as an absolute Y; null for forms this doesn't resolve (below_top). */
    private static Integer anchor(JsonObject a, int bottom) {
        if (a.has("absolute")) return a.get("absolute").getAsInt();
        if (a.has("above_bottom")) return bottom + a.get("above_bottom").getAsInt();
        return null;
    }

    private static String string(JsonObject o, String key) {
        return o != null && o.has(key) ? o.get(key).getAsString() : null;
    }
}
