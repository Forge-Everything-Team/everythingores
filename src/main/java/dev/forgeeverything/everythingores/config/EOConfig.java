package dev.forgeeverything.everythingores.config;

import dev.forgeeverything.everythingores.config.EOMaterials.Dimension;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Per-material switches for pack builders.
 *
 * Disabling a material stops its ores generating, hides every item and block
 * in its chain from the creative tab (and therefore from JEI, which builds its
 * list from creative tabs), and switches off its recipes. Entries stay in the
 * registry — hiding reads identically to a player while leaving other mods'
 * recipes and tags that reference our ids intact.
 *
 * This is a STARTUP spec because recipe conditions are evaluated during
 * datapack load, before a server config would be available.
 */
public final class EOConfig {

    private EOConfig() {}

    public static final ModConfigSpec SPEC;

    private static final Map<String, ModConfigSpec.BooleanValue> MATERIAL_ENABLED = new HashMap<>();
    private static final Map<String, ModConfigSpec.BooleanValue> DIMENSION_ENABLED = new HashMap<>();
    private static final Map<String, ModConfigSpec.BooleanValue> FLUID_ENABLED = new HashMap<>();
    private static final Map<String, ModConfigSpec.ConfigValue<String>> ORE_SOURCE = new HashMap<>();
    private static ModConfigSpec.BooleanValue SULFUR_CAVES;
    private static ModConfigSpec.IntValue SULFUR_CAVES_WEIGHT;

    /** Materials whose ores give way to blocks of themselves in 26.2 style. */
    private static final java.util.Set<String> CAVE_MATERIALS = java.util.Set.of("sulfur", "cinnabar");

    /**
     * Entries that belong to the 26.2 system alone. With sulfur_caves off they
     * are never registered, so ore mode is exactly the ores and raw items.
     */
    private static final java.util.Set<String> CAVES_ONLY = java.util.Set.of(
            "sulfur_block", "cinnabar_block", "potent_sulfur", "sulfur_spike", "cinnabar_dust");

    static {
        var b = new ModConfigSpec.Builder();

        b.comment(
                "==============================================================",
                "  STOP. READ THIS. YES, YOU.",
                "==============================================================",
                "",
                "  DO NOT CHANGE THESE UNLESS YOU UNDERSTAND THE POTENTIAL",
                "  DAMAGES THAT COULD ENSUE.",
                "",
                "Still here? ok bet. Here's the deal.",
                "",
                "These switches don't 'hide' anything. They delete it before the",
                "game finishes loading. Flip one off and that ore or fluid never",
                "gets registered. It does not exist. It's gone. Ghosted. Left on read.",
                "",
                "What that means for you, in ascending order of 'oh no':",
                "",
                "  1. Change these BEFORE you generate a world. Not after. Not",
                "     'just this once, it'll probably be fine.' If your world",
                "     already has the thing and you turn the thing off, those",
                "     blocks have nowhere to go, and Minecraft will inform you",
                "     of this. Loudly. In a crash report you'll be pasting into",
                "     a Discord at 2am.",
                "",
                "  2. Your client and your server have to agree. Same file, same",
                "     values, no freestyling. If they disagree the registries",
                "     don't match and you are simply not joining anything today.",
                "",
                "  3. Other mods may be quietly depending on what you just",
                "     deleted. We tagged everything so it degrades gracefully,",
                "     but 'gracefully' is doing some heavy lifting there.",
                "",
                "This is a pack-builder tool. It exists so you can unify materials",
                "and cut the fourteen kinds of aluminum down to one. It is not a",
                "difficulty slider. Use it once, at the start, on purpose.",
                "",
                "Break your world after reading all that and it's a you problem.",
                "Respectfully: skill issue. We still believe in you.",
                "",
                "==============================================================",
                "",
                "Per-material switches.",
                "",
                "Setting a material to false stops its ores generating, hides its items and",
                "blocks from the creative tab and JEI, and disables its recipes.",
                "",
                "The per-dimension switches under each material gate only where that ore",
                "generates; the material's items stay available as long as 'enabled' is true.",
                "That is how you keep a material obtainable from another mod while removing",
                "ours from worldgen.")
                .push("materials");

        for (var om : EOMaterials.ORE_MATERIALS) {
            defineOreMaterial(b, om);
        }

        b.comment("", "Alloys and derived metals. These have no ore, so they have no dimension gates.")
                .push("alloys");
        for (String alloy : EOMaterials.ALLOY_MATERIALS) {
            MATERIAL_ENABLED.put(alloy, b
                    .comment("Enable " + alloy + " - items, blocks and recipes.")
                    .define(alloy, true));
        }
        b.pop();

        b.pop();

        b.comment(
                "26.2 style sulfur - the Sulfur Caves.",
                "",
                "Swaps the sulfur and cinnabar ores for the Sulfur Caves biome: a cave",
                "biome deep in the Overworld (around Y 0 to -32) where the walls turn to",
                "sulfur, sulfur spikes hang from the ceiling and grow from the floor,",
                "sulfur and cinnabar pillars join the two, and pools sit on a bed of",
                "potent sulfur that vents nauseating gas. Nature's Compass and",
                "/locate biome can find it. In the Nether, masses of sulfur and cinnabar",
                "run through the netherrack instead. Sulfur never generates in the",
                "Aether, in either style.",
                "",
                "What you get: the Sulfur and Cinnabar blocks and their dusts - that is",
                "all. Mining a block drops 2-4 dust (Fortune helps, up to 4); Silk Touch",
                "or crafting 4 dust gets the block. A block breaks back down into 2 dust",
                "by hand, or 4 in a crusher (Create, Mekanism, Immersive Engineering).",
                "Cinnabar dust makes mercury.",
                "",
                "What goes: the sulfur and cinnabar ores (Overworld and Nether) and the",
                "raw sulfur and cinnabar items. They are never registered, same as",
                "switching a material off, so the warning at the top of this file",
                "applies: flip this on a world that already has them and they have",
                "nowhere to go.",
                "",
                "While this is on, sulfur and cinnabar are locked on - their 'enabled'",
                "switches under [materials] are ignored. Their overworld and nether",
                "dimension switches still gate where the blocks generate.",
                "",
                "Needs TerraBlender installed to place the biome.")
                .push("sulfur_caves");
        SULFUR_CAVES = b.comment("Use 26.2 style sulfur.")
                .define("enabled", false);
        SULFUR_CAVES_WEIGHT = b.comment(
                "How much of the Overworld the Sulfur Caves' TerraBlender region claims,",
                "relative to other regions (vanilla's is 10). Higher means sulfur caves",
                "turn up more often.")
                .defineInRange("region_weight", 4, 1, 100);
        b.pop();

        b.comment(
                "Everything Ores exclusives.",
                "",
                "Materials that only exist in this mod - no other mod ships them,",
                "and nothing outside Everything Ores expects them. If you want a",
                "pack that sticks to the shared, cross-mod materials, this is the",
                "section to turn off.",
                "",
                "Same rules as the materials above: 'enabled = false' removes the",
                "whole chain - ores, items, blocks, tools, armor and recipes - and",
                "the warning at the top of this file very much still applies.")
                .push("exclusive");
        for (var om : EOMaterials.EXCLUSIVE_MATERIALS) {
            defineOreMaterial(b, om);
        }
        b.pop();

        b.comment(
                "Fluids.",
                "",
                "Same energy as the warning at the top of this file, except more so,",
                "because fluids are where we actually pull the plug hardest.",
                "",
                "Setting one to false means its fluid type, its still and flowing",
                "forms, its block and its bucket are never registered. Five entries,",
                "all gone. Any tank in your world holding it will be very confused,",
                "and by 'confused' we mean 'a crash report'.",
                "",
                "Turn it off if no mod in your pack uses it. That's the whole point.",
                "Just do it before the world exists, not after you've built a refinery.",
                "",
                "Only crude oil generates in the world; the rest come from machines.")
                .push("fluids");
        for (String fluid : EOMaterials.FLUIDS) {
            FLUID_ENABLED.put(fluid, b
                    .comment("Enable " + fluid.replace('_', ' ') + ".")
                    .define(fluid, true));
        }
        b.pop();

        SPEC = b.build();
    }

    /** One material's block: its enabled flag, its ore_source choice if it has two families, and its dimension gates. */
    private static void defineOreMaterial(ModConfigSpec.Builder b, EOMaterials.OreMaterial om) {
        b.push(om.name());
        if (CAVE_MATERIALS.contains(om.name())) {
            b.comment("Enable " + om.name() + " entirely - ores, items, blocks and recipes.",
                    "Locked on while [sulfur_caves] is enabled - the 26.2 system needs it.",
                    "Only turn " + om.name() + " off with sulfur_caves off.");
        } else {
            b.comment("Enable " + om.name() + " entirely - ores, items, blocks and recipes.");
        }
        MATERIAL_ENABLED.put(om.name(), b.define("enabled", true));

        // A material with two ore families gets a choice of which one generates.
        if (om.families().size() > 1) {
            String invented = om.name();
            String realistic = om.families().stream()
                    .filter(f -> !f.equals(invented)).findFirst().orElse(invented);
            var allowed = new java.util.ArrayList<String>(om.families());
            allowed.add(EOMaterials.SOURCE_BOTH);
            allowed.add(EOMaterials.SOURCE_NONE);
            ORE_SOURCE.put(om.name(), b.comment(
                    "Which ore block yields " + om.name() + ". Both exist in every",
                    "dimension, so this is purely which one you want to mine.",
                    "  " + invented + " - the invented block most mods ship (default)",
                    "  " + realistic + " - what " + om.name() + " is actually refined from",
                    "  both - both generate, which doubles how much ore you find",
                    "  none - neither generates; " + om.name() + " stays craftable elsewhere",
                    "Applied on top of the per-dimension switches below.")
                    .defineInList("ore_source", invented, allowed));
        }

        b.comment("Gate this material's ore generation per dimension.",
                "overworld covers both its stone and deepslate variants - they share one feature.")
                .push("dimensions");
        for (Dimension d : Dimension.values()) {
            if (!om.dimensions().contains(d)) continue;
            DIMENSION_ENABLED.put(key(om.name(), d), b.define(d.key(), true));
        }
        b.pop();
        b.pop();
    }

    private static String key(String material, Dimension d) {
        return material + "." + d.key();
    }

    /**
     * True unless the config switches this material off. Every gate downstream —
     * worldgen, the creative tab, JEI and recipe conditions — runs through here.
     */
    public static boolean isMaterialEnabled(String material) {
        String key = material.toLowerCase(Locale.ROOT);
        // The 26.2 system can't run without its two materials, so it overrides their switches.
        if (CAVE_MATERIALS.contains(key) && isSulfurCavesEnabled()) return true;
        var v = MATERIAL_ENABLED.get(key);
        if (v == null) return true;
        return !SPEC.isLoaded() || v.get();
    }

    /**
     * Cave materials switched off in the config while sulfur caves are on -
     * those switches are being ignored. Used to warn the pack builder.
     */
    public static java.util.List<String> overriddenCaveMaterials() {
        if (!isSulfurCavesEnabled()) return java.util.List.of();
        return CAVE_MATERIALS.stream().sorted()
                .filter(m -> MATERIAL_ENABLED.containsKey(m) && !MATERIAL_ENABLED.get(m).get())
                .toList();
    }

    /**
     * Whether an ore block should generate: its material must be on, the host
     * stone must be on, and for aluminum the ore source must include this family.
     */
    public static boolean isOreEnabled(String material, Dimension dimension, String family) {
        if (!isMaterialEnabled(material)) return false;
        if (!SPEC.isLoaded()) return true;

        if (dimension != null) {
            var v = DIMENSION_ENABLED.get(key(material, dimension));
            if (v != null && !v.get()) return false;
        }
        if (isReplacedByCaves(material, dimension, family)) return false;

        var source = ORE_SOURCE.get(material);
        if (source != null && family != null) {
            String chosen = source.get();
            if (EOMaterials.SOURCE_BOTH.equals(chosen)) return true;
            if (EOMaterials.SOURCE_NONE.equals(chosen)) return false;
            return family.equals(chosen);
        }
        return true;
    }

    /**
     * Whether sulfur generates 26.2 style, as sulfur caves, in place of its
     * ores. Off until the config is loaded, so the default is the ores. Sulfur
     * and cinnabar are forced on while this is true - see isMaterialEnabled.
     */
    public static boolean isSulfurCavesEnabled() {
        return SPEC.isLoaded() && SULFUR_CAVES != null && SULFUR_CAVES.get();
    }

    /** The Sulfur Caves TerraBlender region weight. */
    public static int sulfurCavesWeight() {
        return SPEC.isLoaded() && SULFUR_CAVES_WEIGHT != null ? SULFUR_CAVES_WEIGHT.get() : 4;
    }

    /**
     * True for an Overworld or Nether sulfur or cinnabar ore while sulfur caves
     * are on. In 26.2 style those materials come from blocks of sulfur and
     * cinnabar instead, so their ores are neither registered nor generated.
     */
    private static boolean isReplacedByCaves(String material, Dimension dimension, String family) {
        return family != null && (dimension == Dimension.OVERWORLD || dimension == Dimension.NETHER)
                && CAVE_MATERIALS.contains(material) && isSulfurCavesEnabled();
    }

    /** True unless the config is loaded and this fluid is switched off. */
    public static boolean isFluidEnabled(String fluid) {
        var v = FLUID_ENABLED.get(fluid.toLowerCase(Locale.ROOT));
        if (v == null) return true;
        return !SPEC.isLoaded() || v.get();
    }

    /**
     * Whether a registry entry should be created at all.
     *
     * Deliberately ignores the per-dimension switches and ore_source: those
     * choose where an ore generates, not whether the block exists. Only the
     * material's or fluid's own enabled flag removes an entry from the registry.
     */
    public static boolean isRegistrationEnabled(String path) {
        String fluid = EOMaterials.resolveFluid(path);
        if (fluid != null) return isFluidEnabled(fluid);
        if (CAVES_ONLY.contains(path) && !isSulfurCavesEnabled()) return false;
        var r = EOMaterials.resolve(path);
        if (r == null) return true;
        if (isReplacedByCaves(r.material(), r.dimension(), r.family())) return false;
        // In 26.2 style the raw sulfur and cinnabar items go too - the blocks
        // and their dusts replace them. The path IS the material name only for
        // those two items (sulfur_block, cinnabar_dust etc. all have a suffix).
        if (path.equals(r.material()) && CAVE_MATERIALS.contains(path) && isSulfurCavesEnabled()) return false;
        return isMaterialEnabled(r.material());
    }

    /** Resolves a registry path and answers whether it should be shown and craftable. */
    public static boolean isPathEnabled(String path) {
        String fluid = EOMaterials.resolveFluid(path);
        if (fluid != null) return isFluidEnabled(fluid);

        var r = EOMaterials.resolve(path);
        if (r == null) return true;
        if (r.family() != null) return isOreEnabled(r.material(), r.dimension(), r.family());
        return isMaterialEnabled(r.material());
    }
}
