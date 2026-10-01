package dev.forgeeverything.everythingores.registry;

import dev.forgeeverything.everythingores.EverythingOres;
import dev.forgeeverything.everythingores.config.EOConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * Fluids owned by Everything Ores. Each one replaces the duplicates other mods add;
 * the modpack datapack retargets their recipes, Almost Unified merges their buckets,
 * and mixins cover the few places mods hardcode their own fluid.
 *
 * Crude oil absorbs:
 *   - PneumaticCraft Repressurized : pneumaticcraft:oil
 *   - Oritech                      : oritech:still_oil
 *   - Modern Industrialization     : modern_industrialization:crude_oil
 *   - TFMG                         : tfmg:crude_oil
 *   - Stellaris                    : stellaris:oil (hardcoded — see mixin.stellaris)
 *
 * Biofuel absorbs:
 *   - Create Crafts & Additions    : createaddition:bioethanol
 *   - Industrial Foregoing         : industrialforegoing:biofuel (hardcoded — see mixin.industrialforegoing)
 *   - Oritech                      : oritech:still_biofuel
 *
 * Gasoline absorbs:
 *   - PneumaticCraft Repressurized : pneumaticcraft:gasoline
 *   - TFMG                         : tfmg:gasoline
 *
 * Diesel absorbs:
 *   - PneumaticCraft Repressurized : pneumaticcraft:diesel
 *   - TFMG                         : tfmg:diesel
 *   - Oritech                      : oritech:still_diesel
 *   - Modern Industrialization     : modern_industrialization:diesel
 *   - Stellaris                    : stellaris:diesel (hardcoded — see mixin.stellaris)
 *
 * Biodiesel absorbs:
 *   - PneumaticCraft Repressurized : pneumaticcraft:biodiesel
 *   - Immersive Engineering        : immersiveengineering:biodiesel
 *   - Modern Industrialization     : modern_industrialization:biodiesel
 *
 * Kerosene absorbs:
 *   - PneumaticCraft Repressurized : pneumaticcraft:kerosene
 *   - TFMG                         : tfmg:kerosene
 *
 * Ethanol absorbs:
 *   - PneumaticCraft Repressurized : pneumaticcraft:ethanol
 *   - Immersive Engineering        : immersiveengineering:ethanol
 *   - Modern Industrialization     : modern_industrialization:ethanol
 *   - Electrodynamics              : electrodynamics:fluidethanol
 *
 * Sulfuric acid absorbs:
 *   - Modern Industrialization     : modern_industrialization:sulfuric_acid
 *   - Oritech                      : oritech:still_sulfuric_acid
 *   - TFMG                         : tfmg:sulfuric_acid
 *   - Electrodynamics              : electrodynamics:fluidsulfuricacid
 *   - Mekanism                     : mekanism:sulfuric_acid (the FLUID only — Mekanism's
 *     chemical of the same id is a separate registry and is left untouched)
 *
 * LPG absorbs:
 *   - PneumaticCraft Repressurized : pneumaticcraft:lpg
 *   - TFMG                         : tfmg:lpg
 *
 * Heavy oil absorbs:
 *   - Oritech                      : oritech:still_heavy_oil
 *   - TFMG                         : tfmg:heavy_oil
 *
 * Lubricant absorbs:
 *   - Modern Industrialization     : modern_industrialization:lubricant
 *   - PneumaticCraft Repressurized : pneumaticcraft:lubricant
 *   - TFMG                         : tfmg:lubrication_oil
 *
 * Creosote absorbs:
 *   - Immersive Engineering        : immersiveengineering:creosote (coke oven is hardcoded — see mixin.immersiveengineering)
 *   - Modern Industrialization     : modern_industrialization:creosote
 *   - TFMG                         : tfmg:creosote
 */
public class EOFluids {

    // ── DeferredRegisters ──────────────────────────────────────────────────────
    // Declared before the fluid sets below, which register into them.

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, EverythingOres.MOD_ID);

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(Registries.FLUID, EverythingOres.MOD_ID);

    /**
     * Builds a fluid set, or returns null when the fluid is switched off.
     *
     * Returning null means none of its five registry entries are ever created —
     * this is a real removal, not a hide. The STARTUP config is loaded the moment
     * it is registered in the mod constructor, which happens before this class
     * initialises, so the switches are readable here.
     *
     * Callers must null-check. Anything in a datapack that names a disabled
     * fluid carries an everythingores:fluid_enabled condition so it is skipped
     * rather than left dangling.
     */
    private static EOFluidSet fluid(String name, FluidType.Properties typeProperties,
                                    int tint, MapColor mapColor, int levelDecrease) {
        if (!EOConfig.isFluidEnabled(name)) {
            EverythingOres.LOGGER.info("Fluid {} disabled in config - skipping registration.", name);
            return null;
        }
        return new EOFluidSet(name, typeProperties, tint, mapColor, levelDecrease);
    }

    // ── Fluids ─────────────────────────────────────────────────────────────────

    /** Very dark brown-black, mostly opaque. Textures carry the colour. */
    public static final EOFluidSet CRUDE_OIL = fluid("crude_oil",
            FluidType.Properties.create()
                    .density(1200)        // denser than water (1000)
                    .viscosity(2000)      // much thicker than water (1000)
                    .temperature(300)     // room temperature
                    .motionScale(0.014)   // sluggish surface movement
                    .canSwim(false)
                    .canDrown(true)
                    .supportsBoating(false),
            0xFF1A1208, MapColor.COLOR_BLACK, 2);

    // The fluids below use near-white textures, so the tint sets their colour.

    /** Yellow-green, slightly translucent. */
    public static final EOFluidSet BIOFUEL = fluid("biofuel",
            FluidType.Properties.create()
                    .density(900)         // lighter than water, like ethanol
                    .viscosity(1200)      // slightly thicker than water
                    .temperature(300)
                    .canSwim(true)
                    .canDrown(true)
                    .supportsBoating(false),
            0xE0B8C23A, MapColor.COLOR_YELLOW, 1);

    /** Pale yellow, fairly translucent. */
    public static final EOFluidSet GASOLINE = fluid("gasoline",
            FluidType.Properties.create()
                    .density(750)         // much lighter than water
                    .viscosity(600)       // thinner than water
                    .temperature(300)
                    .canSwim(true)
                    .canDrown(true)
                    .supportsBoating(false),
            0xC8F5D76B, MapColor.SAND, 1);

    /** Amber-brown. */
    public static final EOFluidSet DIESEL = fluid("diesel",
            FluidType.Properties.create()
                    .density(840)
                    .viscosity(1400)
                    .temperature(300)
                    .canSwim(true)
                    .canDrown(true)
                    .supportsBoating(false),
            0xE0B7862D, MapColor.COLOR_BROWN, 1);

    /** Golden orange. */
    public static final EOFluidSet BIODIESEL = fluid("biodiesel",
            FluidType.Properties.create()
                    .density(880)
                    .viscosity(1600)
                    .temperature(300)
                    .canSwim(true)
                    .canDrown(true)
                    .supportsBoating(false),
            0xE0D9A441, MapColor.COLOR_ORANGE, 1);

    /** Pale blue-white, nearly clear. */
    public static final EOFluidSet KEROSENE = fluid("kerosene",
            FluidType.Properties.create()
                    .density(810)
                    .viscosity(1000)
                    .temperature(300)
                    .canSwim(true)
                    .canDrown(true)
                    .supportsBoating(false),
            0xC8CFE0EA, MapColor.QUARTZ, 1);

    /** Clear off-white. */
    public static final EOFluidSet ETHANOL = fluid("ethanol",
            FluidType.Properties.create()
                    .density(790)
                    .viscosity(1100)
                    .temperature(300)
                    .canSwim(true)
                    .canDrown(true)
                    .supportsBoating(false),
            0xC0EDEDE0, MapColor.TERRACOTTA_WHITE, 1);

    /** Dark oily brown. */
    public static final EOFluidSet CREOSOTE = fluid("creosote",
            FluidType.Properties.create()
                    .density(1100)
                    .viscosity(1800)
                    .temperature(300)
                    .motionScale(0.014)
                    .canSwim(false)
                    .canDrown(true)
                    .supportsBoating(false),
            0xE05B3A1E, MapColor.COLOR_BROWN, 2);

    /** Acid yellow-green. */
    public static final EOFluidSet SULFURIC_ACID = fluid("sulfuric_acid",
            FluidType.Properties.create()
                    .density(1800)        // much denser than water
                    .viscosity(1400)
                    .temperature(300)
                    .canSwim(false)
                    .canDrown(true)
                    .supportsBoating(false),
            0xD0CBE05A, MapColor.COLOR_YELLOW, 1);

    /**
     * Pale cyan. A normal placeable liquid, matching PneumaticCraft's LPG (density 550).
     *
     * TFMG's LPG is a Create VirtualFluid — no block, so it never leaves a pipe or tank —
     * and its tfmg:gas tag is descriptive only; nothing in the pack reads it. Being
     * placeable is therefore a small loosening of TFMG's design that causes no harm, and
     * TFMG's machines still accept this fluid through the tags.
     */
    public static final EOFluidSet LPG = fluid("lpg",
            FluidType.Properties.create()
                    .density(550)         // very light
                    .viscosity(400)       // very thin
                    .temperature(300)
                    .canSwim(true)
                    .canDrown(true)
                    .supportsBoating(false),
            0xB0C6E8F0, MapColor.COLOR_LIGHT_BLUE, 1);

    /** Very dark brown, thick. */
    public static final EOFluidSet HEAVY_OIL = fluid("heavy_oil",
            FluidType.Properties.create()
                    .density(1000)
                    .viscosity(2400)      // thicker than crude oil
                    .temperature(300)
                    .motionScale(0.014)
                    .canSwim(false)
                    .canDrown(true)
                    .supportsBoating(false),
            0xF02A1A0E, MapColor.COLOR_BROWN, 2);

    /** Deep amber. */
    public static final EOFluidSet LUBRICANT = fluid("lubricant",
            FluidType.Properties.create()
                    .density(950)
                    .viscosity(2000)
                    .temperature(300)
                    .canSwim(false)
                    .canDrown(true)
                    .supportsBoating(false),
            0xF0996633, MapColor.TERRACOTTA_ORANGE, 2);

    /**
     * Liquid metal, roasted out of cinnabar. Absurdly dense - real mercury is
     * 13.5x water - so it barely spreads and you are not swimming in it.
     */
    public static final EOFluidSet MERCURY = fluid("mercury",
            FluidType.Properties.create()
                    .density(13500)
                    .viscosity(1500)
                    .temperature(300)
                    .motionScale(0.007)
                    .canSwim(false)
                    .canDrown(true)
                    .supportsBoating(false),
            0xFFB8B8C0, MapColor.COLOR_LIGHT_GRAY, 2);
}
