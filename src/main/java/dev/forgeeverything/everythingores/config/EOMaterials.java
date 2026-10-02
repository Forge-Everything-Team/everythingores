package dev.forgeeverything.everythingores.config;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * The material table the config is built from, plus the resolver that maps a
 * registry path back to the material it belongs to.
 *
 * A "material" is the item chain (aluminum, chromium, tin...). An "ore family"
 * is what the ore block is called, which is not always the material name:
 * bauxite yields aluminum, chromite yields chromium.
 *
 * Aluminum and chromium each have two families, and both generate in every
 * dimension. That gives those materials an ore_source switch: a straight
 * choice of flavour between the realistic mineral (bauxite, chromite) and the
 * invented block most mods ship (aluminum ore, chromium ore), rather than a
 * choice of where to mine. A material's invented family is the one whose name
 * matches the material itself.
 */
public final class EOMaterials {

    private EOMaterials() {}

    /**
     * The dimensions an ore can be gated on.
     *
     * There is deliberately no DEEPSLATE entry: an Overworld ore's stone and
     * deepslate variants share a single configured feature, whose target list
     * picks the variant by depth. Splitting them would mean splitting every
     * Overworld ore feature in two, which would change generation rates. So
     * OVERWORLD gates both.
     */
    public enum Dimension {
        OVERWORLD, NETHER, END, AETHER;

        public String key() {
            return name().toLowerCase(Locale.ROOT);
        }
    }

    /** ore_source values that mean "all of them" and "none of them". */
    public static final String SOURCE_BOTH = "both";
    public static final String SOURCE_NONE = "none";

    /** A material that has at least one ore block somewhere. */
    public record OreMaterial(String name, Set<Dimension> dimensions, List<String> families) {}

    private static OreMaterial m(String name, List<Dimension> dims, String... families) {
        return new OreMaterial(name, Set.copyOf(dims), List.of(families));
    }

    private static final List<Dimension> ALL = List.of(
            Dimension.OVERWORLD, Dimension.NETHER, Dimension.END, Dimension.AETHER);
    private static final List<Dimension> OW = List.of(Dimension.OVERWORLD);
    private static final List<Dimension> NEA = List.of(Dimension.NETHER, Dimension.END, Dimension.AETHER);

    /** Materials with ores. Generated from the blockstate set — keep in sync if ores are added. */
    public static final List<OreMaterial> ORE_MATERIALS = List.of(
            m("aluminum",  ALL, "bauxite", "aluminum"),
            m("bismuth",   ALL, "bismuth"),
            m("chromium",  ALL, "chromite", "chromium"),
            m("cinnabar",  List.of(Dimension.OVERWORLD, Dimension.NETHER), "cinnabar"),
            m("coal",      List.of(Dimension.NETHER, Dimension.END), "coal"),
            m("copper",    NEA, "copper"),
            m("diamond",   NEA, "diamond"),
            m("emerald",   NEA, "emerald"),
            m("fluorite",  ALL, "fluorite"),
            m("gold",      List.of(Dimension.END, Dimension.AETHER), "gold"),
            m("iridium",   ALL, "iridium"),
            m("iron",      NEA, "iron"),
            m("lapis",     NEA, "lapis"),
            m("lead",      ALL, "lead"),
            m("lithium",   ALL, "lithium"),
            m("monazite",  ALL, "monazite"),
            m("nickel",    ALL, "nickel"),
            m("osmium",    ALL, "osmium"),
            m("platinum",  ALL, "platinum"),
            m("redstone",  NEA, "redstone"),
            m("ruby",      NEA, "ruby"),
            m("salt",      ALL, "salt"),
            m("saltpeter", ALL, "saltpeter"),
            m("sapphire",  NEA, "sapphire"),
            m("silver",    ALL, "silver"),
            // No sulfur ore in the Nether - the nether gate is for 26.2 style blocks of sulfur.
            m("sulfur",    List.of(Dimension.OVERWORLD, Dimension.NETHER), "sulfur"),
            m("thorium",   ALL, "thorium"),
            m("tin",       ALL, "cassiterite", "tin"),
            m("titanium",  ALL, "titanium"),
            m("tungsten",  ALL, "tungsten"),
            m("uranium",   ALL, "uranium"),
            m("zinc",      ALL, "zinc"));

    /**
     * Ore materials that exist only in Everything Ores. Kept apart from
     * ORE_MATERIALS so the config can give them their own section — these are
     * the ones a pack builder is most likely to want to cut.
     */
    public static final List<OreMaterial> EXCLUSIVE_MATERIALS = List.of(
            m("nemonium",  OW,  "nemonium"));

    /**
     * The fluids, each of which owns a fluid type, still and flowing fluids,
     * a liquid block and a bucket. Only crude oil generates in the world.
     */
    public static final List<String> FLUIDS = List.of(
            "crude_oil", "biofuel", "gasoline", "diesel", "biodiesel", "kerosene",
            "ethanol", "creosote", "sulfuric_acid", "lpg", "heavy_oil", "lubricant",
            "mercury");


    /** Alloys and derived metals — no ore, so no dimension gates. */
    public static final List<String> ALLOY_MATERIALS = List.of(
            "bronze", "constantan", "electrum", "invar", "red_alloy", "stainless_steel", "steel");

    /** Ore family name -> material name, where the two differ. */
    private static final Map<String, String> FAMILY_TO_MATERIAL = Map.of(
            "bauxite", "aluminum",
            "chromite", "chromium",
            "cassiterite", "tin");

    /** Dimension prefix on a block id -> the layer it belongs to. */
    private static final Map<String, Dimension> PREFIXES = Map.of(
            "nether_", Dimension.NETHER,
            "end_", Dimension.END,
            "holystone_", Dimension.AETHER,
            "deepslate_", Dimension.OVERWORLD);

    /** Suffixes stripped to get from an item id back to its material. Longest first. */
    private static final List<String> SUFFIXES = List.of(
            "_chestplate", "_leggings", "_pickaxe", "_crystal", "_shovel", "_helmet",
            "_nugget", "_sword", "_plate", "_spike", "_block", "_boots", "_dust", "_gear", "_ingot",
            "_axe", "_hoe", "_ore");

    /** Prefixes stripped after the dimension prefix. */
    private static final List<String> ITEM_PREFIXES = List.of("raw_", "tiny_", "potent_");


    private static final Set<String> ALL_MATERIAL_NAMES;
    static {
        var names = new java.util.HashSet<String>();
        ORE_MATERIALS.forEach(om -> names.add(om.name()));
        EXCLUSIVE_MATERIALS.forEach(om -> names.add(om.name()));
        names.addAll(ALLOY_MATERIALS);
        names.addAll(FAMILY_TO_MATERIAL.keySet());
        ALL_MATERIAL_NAMES = Set.copyOf(names);
    }

    /** What a registry path resolved to: its material, and the layer if it is an ore block. */
    public record Resolved(String material, Dimension dimension, String family) {}

    /** The fluid a path belongs to — its liquid block or its bucket — or null. */
    public static String resolveFluid(String path) {
        if (FLUIDS.contains(path)) return path;
        if (path.endsWith("_bucket")) {
            String f = path.substring(0, path.length() - "_bucket".length());
            if (FLUIDS.contains(f)) return f;
        }
        return null;
    }

    /**
     * Resolves a registry path such as {@code deepslate_bauxite_ore} or
     * {@code tiny_chromium_dust} to its material. Returns null for anything
     * this mod does not gate by material — fluids are handled by
     * {@link #resolveFluid}, and callers treat a null as always enabled.
     */
    public static Resolved resolve(String path) {

        String rest = path;
        Dimension dim = null;

        for (var e : PREFIXES.entrySet()) {
            if (rest.startsWith(e.getKey())) {
                String candidate = rest.substring(e.getKey().length());
                // Only treat it as a dimension prefix if what follows is really ours.
                if (looksLikeMaterial(candidate)) {
                    dim = e.getValue();
                    rest = candidate;
                }
                break;
            }
        }

        boolean isOre = rest.endsWith("_ore");
        for (String p : ITEM_PREFIXES) {
            if (rest.startsWith(p)) { rest = rest.substring(p.length()); break; }
        }
        for (String s : SUFFIXES) {
            if (rest.endsWith(s) && rest.length() > s.length()) { rest = rest.substring(0, rest.length() - s.length()); break; }
        }

        if (!ALL_MATERIAL_NAMES.contains(rest)) return null;

        String family = rest;
        String material = FAMILY_TO_MATERIAL.getOrDefault(rest, rest);
        // An ore with no dimension prefix sits in plain Overworld stone.
        if (isOre && dim == null) dim = Dimension.OVERWORLD;
        return new Resolved(material, dim, isOre ? family : null);
    }

    private static boolean looksLikeMaterial(String candidate) {
        String rest = candidate;
        for (String p : ITEM_PREFIXES) {
            if (rest.startsWith(p)) { rest = rest.substring(p.length()); break; }
        }
        for (String s : SUFFIXES) {
            if (rest.endsWith(s) && rest.length() > s.length()) { rest = rest.substring(0, rest.length() - s.length()); break; }
        }
        return ALL_MATERIAL_NAMES.contains(rest);
    }
}
