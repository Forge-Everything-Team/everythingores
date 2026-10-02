package dev.forgeeverything.everythingores.registry;

import dev.forgeeverything.everythingores.EverythingOres;
import dev.forgeeverything.everythingores.block.PotentSulfurBlock;
import dev.forgeeverything.everythingores.block.SulfurSpikeBlock;
import dev.forgeeverything.everythingores.config.EOConfig;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers every block owned by Everything Ores.
 *
 * Three block categories:
 *   1. Ore blocks     — stone + deepslate variants (18 materials × 2 = 36 blocks)
 *   2. Storage blocks — 9 ingots → 1 block, all metals + alloys (19 blocks)
 *   3. Raw blocks     — 9 raw ores → 1 block, metallic materials only (13 blocks)
 *
 * Alloys registered here (no ore — crafted from base metals):
 *   Steel, Electrum, Constantan, Invar, Bronze, Stainless Steel
 *
 * Copper is absent from ore/storage blocks — vanilla owns copper_ore,
 * deepslate_copper_ore, and copper_block.
 */
public class EOBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(EverythingOres.MOD_ID);

    // ================================================================
    // HELPERS
    // ================================================================

    private static DeferredBlock<DropExperienceBlock> stoneOre(String name, UniformInt xp,
                                                                float hardness, MapColor color) {
        if (!EOConfig.isRegistrationEnabled(name)) return null;
        return BLOCKS.registerBlock(name,
                props -> new DropExperienceBlock(xp, props),
                BlockBehaviour.Properties.of()
                        .mapColor(color).requiresCorrectToolForDrops()
                        .strength(hardness, 3.0F).sound(SoundType.STONE));
    }

    private static DeferredBlock<DropExperienceBlock> deepslateOre(String name, UniformInt xp,
                                                                     float hardness, MapColor color) {
        if (!EOConfig.isRegistrationEnabled(name)) return null;
        return BLOCKS.registerBlock(name,
                props -> new DropExperienceBlock(xp, props),
                BlockBehaviour.Properties.of()
                        .mapColor(color).requiresCorrectToolForDrops()
                        .strength(hardness, 3.0F).sound(SoundType.DEEPSLATE));
    }

    /** Netherrack-hosted ore variant — same yield as the stone form, nether ore sound. */
    private static DeferredBlock<DropExperienceBlock> netherOre(String name, UniformInt xp,
                                                                 float hardness, MapColor color) {
        if (!EOConfig.isRegistrationEnabled(name)) return null;
        return BLOCKS.registerBlock(name,
                props -> new DropExperienceBlock(xp, props),
                BlockBehaviour.Properties.of()
                        .mapColor(color).requiresCorrectToolForDrops()
                        .strength(hardness, 3.0F).sound(SoundType.NETHER_ORE));
    }

    /** End-stone-hosted ore variant — deepslate-grade hardness, stone sound. */
    private static DeferredBlock<DropExperienceBlock> endOre(String name, UniformInt xp,
                                                              float hardness, MapColor color) {
        if (!EOConfig.isRegistrationEnabled(name)) return null;
        return BLOCKS.registerBlock(name,
                props -> new DropExperienceBlock(xp, props),
                BlockBehaviour.Properties.of()
                        .mapColor(color).requiresCorrectToolForDrops()
                        .strength(hardness, 3.0F).sound(SoundType.STONE));
    }

    /** Holystone-hosted ore variant — Aether host rock, stone sound. */
    private static DeferredBlock<DropExperienceBlock> holystoneOre(String name, UniformInt xp,
                                                                 float hardness, MapColor color) {
        if (!EOConfig.isRegistrationEnabled(name)) return null;
        return BLOCKS.registerBlock(name,
                props -> new DropExperienceBlock(xp, props),
                BlockBehaviour.Properties.of()
                        .mapColor(color).requiresCorrectToolForDrops()
                        .strength(hardness, 3.0F).sound(SoundType.STONE));
    }

    /**
     * Redstone ore variant — needs vanilla's RedStoneOreBlock rather than
     * DropExperienceBlock so it still lights up when walked on or punched and
     * still drops its own 1-5 XP. Light level and XP are handled by the class.
     */
    private static DeferredBlock<RedStoneOreBlock> redstoneOre(String name, SoundType sound,
                                                                float hardness, MapColor color) {
        if (!EOConfig.isRegistrationEnabled(name)) return null;
        return BLOCKS.registerBlock(name,
                RedStoneOreBlock::new,
                BlockBehaviour.Properties.of()
                        .mapColor(color).requiresCorrectToolForDrops()
                        .randomTicks()
                        .lightLevel(state -> state.getValue(RedStoneOreBlock.LIT) ? 9 : 0)
                        .strength(hardness, 3.0F).sound(sound));
    }

    /** 9-ingot storage block — drops itself, metal sound. */
    private static DeferredBlock<Block> metalBlock(String name, float hardness, MapColor color) {
        if (!EOConfig.isRegistrationEnabled(name)) return null;
        return BLOCKS.registerSimpleBlock(name,
                BlockBehaviour.Properties.of()
                        .mapColor(color).requiresCorrectToolForDrops()
                        .strength(hardness, 6.0F).sound(SoundType.METAL));
    }

    /** 9-raw-ore storage block — drops itself, stone sound. */
    private static DeferredBlock<Block> rawBlock(String name, float hardness, MapColor color) {
        if (!EOConfig.isRegistrationEnabled(name)) return null;
        return BLOCKS.registerSimpleBlock(name,
                BlockBehaviour.Properties.of()
                        .mapColor(color).requiresCorrectToolForDrops()
                        .strength(hardness, 3.0F).sound(SoundType.STONE));
    }

    // ================================================================
    // ORE BLOCKS — Original set
    // ================================================================

    public static final DeferredBlock<DropExperienceBlock> TIN_ORE =
            stoneOre("tin_ore", UniformInt.of(0, 2), 3.0F, MapColor.STONE);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_TIN_ORE =
            deepslateOre("deepslate_tin_ore", UniformInt.of(0, 2), 4.5F, MapColor.DEEPSLATE);
    // Cassiterite is the real tin mineral, and what Electrodynamics calls its
    // tin ore. Second family for tin, picked with the config ore_source switch.
    public static final DeferredBlock<DropExperienceBlock> CASSITERITE_ORE =
            stoneOre("cassiterite_ore", UniformInt.of(0, 2), 3.0F, MapColor.STONE);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_CASSITERITE_ORE =
            deepslateOre("deepslate_cassiterite_ore", UniformInt.of(0, 2), 4.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> LEAD_ORE =
            stoneOre("lead_ore", UniformInt.of(0, 2), 3.0F, MapColor.STONE);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_LEAD_ORE =
            deepslateOre("deepslate_lead_ore", UniformInt.of(0, 2), 4.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> NICKEL_ORE =
            stoneOre("nickel_ore", UniformInt.of(0, 2), 3.0F, MapColor.STONE);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_NICKEL_ORE =
            deepslateOre("deepslate_nickel_ore", UniformInt.of(0, 2), 4.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> BAUXITE_ORE =
            stoneOre("bauxite_ore", UniformInt.of(0, 2), 3.0F, MapColor.COLOR_ORANGE);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_BAUXITE_ORE =
            deepslateOre("deepslate_bauxite_ore", UniformInt.of(0, 2), 4.5F, MapColor.DEEPSLATE);
    // Aluminum has two ore families so packs can pick one via the config
    // ore_source switch: bauxite, or aluminum proper, in every dimension.
    public static final DeferredBlock<DropExperienceBlock> ALUMINUM_ORE =
            stoneOre("aluminum_ore", UniformInt.of(0, 2), 3.0F, MapColor.COLOR_ORANGE);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_ALUMINUM_ORE =
            deepslateOre("deepslate_aluminum_ore", UniformInt.of(0, 2), 4.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> ZINC_ORE =
            stoneOre("zinc_ore", UniformInt.of(0, 2), 3.0F, MapColor.STONE);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_ZINC_ORE =
            deepslateOre("deepslate_zinc_ore", UniformInt.of(0, 2), 4.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> SILVER_ORE =
            stoneOre("silver_ore", UniformInt.of(0, 3), 3.0F, MapColor.STONE);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_SILVER_ORE =
            deepslateOre("deepslate_silver_ore", UniformInt.of(0, 3), 4.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> URANIUM_ORE =
            stoneOre("uranium_ore", UniformInt.of(1, 4), 3.0F, MapColor.COLOR_GREEN);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_URANIUM_ORE =
            deepslateOre("deepslate_uranium_ore", UniformInt.of(1, 4), 4.5F, MapColor.DEEPSLATE);

    // Thorium — Electrodynamics duplicate; Nuclear Science turns the raw ore
    // into thorianite dust for molten salt reactor fuel. No ingot or dust:
    // nothing in the pack uses a thorium ingot, and thorianite dust is
    // single-source (§5.8), so it stays with Nuclear Science.
    public static final DeferredBlock<DropExperienceBlock> THORIUM_ORE =
            stoneOre("thorium_ore", UniformInt.of(1, 4), 3.0F, MapColor.COLOR_GRAY);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_THORIUM_ORE =
            deepslateOre("deepslate_thorium_ore", UniformInt.of(1, 4), 4.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> PLATINUM_ORE =
            stoneOre("platinum_ore", UniformInt.of(2, 5), 3.0F, MapColor.METAL);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_PLATINUM_ORE =
            deepslateOre("deepslate_platinum_ore", UniformInt.of(2, 5), 4.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> SULFUR_ORE =
            stoneOre("sulfur_ore", UniformInt.of(2, 5), 2.0F, MapColor.COLOR_YELLOW);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_SULFUR_ORE =
            deepslateOre("deepslate_sulfur_ore", UniformInt.of(2, 5), 3.5F, MapColor.DEEPSLATE);

    // Sulfur caves (26.2 style, config: sulfur.sulfur_caves). These three
    // register with sulfur in either style and double as building blocks;
    // switching the caves on removes the two sulfur ores above instead.
    // The sulfur block drops sulfur dust when mined, or itself with Silk Touch.
    public static final DeferredBlock<Block> SULFUR_BLOCK = EOConfig.isRegistrationEnabled("sulfur_block")
            ? BLOCKS.registerSimpleBlock("sulfur_block",
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_YELLOW).instrument(NoteBlockInstrument.BASEDRUM)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 3.0F).sound(SoundType.DRIPSTONE_BLOCK))
            : null;
    public static final DeferredBlock<PotentSulfurBlock> POTENT_SULFUR = EOConfig.isRegistrationEnabled("potent_sulfur")
            ? BLOCKS.registerBlock("potent_sulfur", PotentSulfurBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_LIGHT_GREEN).instrument(NoteBlockInstrument.BASEDRUM)
                            .requiresCorrectToolForDrops().randomTicks()
                            .strength(1.5F, 3.0F).sound(SoundType.DRIPSTONE_BLOCK))
            : null;
    public static final DeferredBlock<SulfurSpikeBlock> SULFUR_SPIKE = EOConfig.isRegistrationEnabled("sulfur_spike")
            ? BLOCKS.registerBlock("sulfur_spike", SulfurSpikeBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_YELLOW).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM)
                            .noOcclusion().sound(SoundType.POINTED_DRIPSTONE)
                            .strength(1.5F, 3.0F).dynamicShape()
                            .offsetType(BlockBehaviour.OffsetType.XZ)
                            .pushReaction(PushReaction.DESTROY)
                            .isRedstoneConductor((state, level, pos) -> false))
            : null;

    public static final DeferredBlock<DropExperienceBlock> SALTPETER_ORE =
            stoneOre("saltpeter_ore", UniformInt.of(1, 3), 2.0F, MapColor.SNOW);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_SALTPETER_ORE =
            deepslateOre("deepslate_saltpeter_ore", UniformInt.of(1, 3), 3.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> SALT_ORE =
            stoneOre("salt_ore", UniformInt.of(0, 1), 2.0F, MapColor.SNOW);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_SALT_ORE =
            deepslateOre("deepslate_salt_ore", UniformInt.of(0, 1), 3.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> MONAZITE_ORE =
            stoneOre("monazite_ore", UniformInt.of(2, 5), 3.0F, MapColor.COLOR_LIGHT_GRAY);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_MONAZITE_ORE =
            deepslateOre("deepslate_monazite_ore", UniformInt.of(2, 5), 4.5F, MapColor.DEEPSLATE);

    // ================================================================
    // ORE BLOCKS — Unified set
    // ================================================================

    public static final DeferredBlock<DropExperienceBlock> OSMIUM_ORE =
            stoneOre("osmium_ore", UniformInt.of(0, 2), 3.0F, MapColor.COLOR_BLUE);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_OSMIUM_ORE =
            deepslateOre("deepslate_osmium_ore", UniformInt.of(0, 2), 4.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> FLUORITE_ORE =
            stoneOre("fluorite_ore", UniformInt.of(2, 4), 2.0F, MapColor.COLOR_CYAN);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_FLUORITE_ORE =
            deepslateOre("deepslate_fluorite_ore", UniformInt.of(2, 4), 3.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> BISMUTH_ORE =
            stoneOre("bismuth_ore", UniformInt.of(0, 2), 3.0F, MapColor.COLOR_PINK);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_BISMUTH_ORE =
            deepslateOre("deepslate_bismuth_ore", UniformInt.of(0, 2), 4.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> CHROMITE_ORE =
            stoneOre("chromite_ore", UniformInt.of(1, 3), 3.0F, MapColor.METAL);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_CHROMITE_ORE =
            deepslateOre("deepslate_chromite_ore", UniformInt.of(1, 3), 4.5F, MapColor.DEEPSLATE);
    // Cinnabar is mercury sulfide - a standalone mineral like fluorite and
    // monazite, roasted into mercury rather than smelted into an ingot.
    public static final DeferredBlock<DropExperienceBlock> CINNABAR_ORE =
            stoneOre("cinnabar_ore", UniformInt.of(2, 5), 3.0F, MapColor.COLOR_RED);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_CINNABAR_ORE =
            deepslateOre("deepslate_cinnabar_ore", UniformInt.of(2, 5), 4.5F, MapColor.DEEPSLATE);
    // Block of cinnabar - the red rock of the 26.2 sulfur caves. Registered with
    // cinnabar in either style; with sulfur caves on it replaces the cinnabar
    // ores (Overworld and Nether). Drops cinnabar dust when mined, or itself with Silk Touch.
    public static final DeferredBlock<Block> CINNABAR_BLOCK = EOConfig.isRegistrationEnabled("cinnabar_block")
            ? BLOCKS.registerSimpleBlock("cinnabar_block",
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_RED).instrument(NoteBlockInstrument.BASEDRUM)
                            .requiresCorrectToolForDrops()
                            .strength(1.5F, 6.0F).sound(SoundType.TUFF))
            : null;
    // Chromium, like aluminum, has two ore families so packs can pick the
    // realistic mineral or the invented block via the config ore_source switch.
    public static final DeferredBlock<DropExperienceBlock> CHROMIUM_ORE =
            stoneOre("chromium_ore", UniformInt.of(1, 3), 3.0F, MapColor.METAL);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_CHROMIUM_ORE =
            deepslateOre("deepslate_chromium_ore", UniformInt.of(1, 3), 4.5F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> TUNGSTEN_ORE =
            stoneOre("tungsten_ore", UniformInt.of(1, 4), 4.5F, MapColor.COLOR_GRAY);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_TUNGSTEN_ORE =
            deepslateOre("deepslate_tungsten_ore", UniformInt.of(1, 4), 6.0F, MapColor.DEEPSLATE);

    public static final DeferredBlock<DropExperienceBlock> IRIDIUM_ORE =
            stoneOre("iridium_ore", UniformInt.of(3, 7), 3.0F, MapColor.GOLD);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_IRIDIUM_ORE =
            deepslateOre("deepslate_iridium_ore", UniformInt.of(3, 7), 4.5F, MapColor.DEEPSLATE);

    // Lithium — three-mod duplicate: TFMG (full chain), Electrodynamics
    // (processing chain), Mekanism (dust only).
    public static final DeferredBlock<DropExperienceBlock> LITHIUM_ORE =
            stoneOre("lithium_ore", UniformInt.of(0, 2), 3.0F, MapColor.COLOR_LIGHT_GRAY);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_LITHIUM_ORE =
            deepslateOre("deepslate_lithium_ore", UniformInt.of(0, 2), 4.5F, MapColor.DEEPSLATE);

    // Titanium — two-mod duplicate: Electrodynamics + Modern Industrialization.
    public static final DeferredBlock<DropExperienceBlock> TITANIUM_ORE =
            stoneOre("titanium_ore", UniformInt.of(0, 2), 3.0F, MapColor.METAL);
    public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_TITANIUM_ORE =
            deepslateOre("deepslate_titanium_ore", UniformInt.of(0, 2), 4.5F, MapColor.DEEPSLATE);

    // ================================================================
    // ORE BLOCKS — Nether variants
    //
    // Netherrack-hosted duplicates of the Overworld ores. Same drop and XP
    // as the stone form; the Nether is an alternate source, not a richer
    // one. Sulfur is the one ore with no Nether form - the Nether gets 26.2
    // style blocks of sulfur instead.
    // ================================================================

    public static final DeferredBlock<DropExperienceBlock> NETHER_TIN_ORE =
            netherOre("nether_tin_ore", UniformInt.of(0, 2), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_CASSITERITE_ORE =
            netherOre("nether_cassiterite_ore", UniformInt.of(0, 2), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_LEAD_ORE =
            netherOre("nether_lead_ore", UniformInt.of(0, 2), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_NICKEL_ORE =
            netherOre("nether_nickel_ore", UniformInt.of(0, 2), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_ALUMINUM_ORE =
            netherOre("nether_aluminum_ore", UniformInt.of(0, 2), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_BAUXITE_ORE =
            netherOre("nether_bauxite_ore", UniformInt.of(0, 2), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_ZINC_ORE =
            netherOre("nether_zinc_ore", UniformInt.of(0, 2), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_SILVER_ORE =
            netherOre("nether_silver_ore", UniformInt.of(0, 3), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_URANIUM_ORE =
            netherOre("nether_uranium_ore", UniformInt.of(1, 4), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_PLATINUM_ORE =
            netherOre("nether_platinum_ore", UniformInt.of(2, 5), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_OSMIUM_ORE =
            netherOre("nether_osmium_ore", UniformInt.of(0, 2), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_BISMUTH_ORE =
            netherOre("nether_bismuth_ore", UniformInt.of(0, 2), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_CHROMITE_ORE =
            netherOre("nether_chromite_ore", UniformInt.of(1, 3), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_CHROMIUM_ORE =
            netherOre("nether_chromium_ore", UniformInt.of(1, 3), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_CINNABAR_ORE =
            netherOre("nether_cinnabar_ore", UniformInt.of(2, 5), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_TUNGSTEN_ORE =
            netherOre("nether_tungsten_ore", UniformInt.of(1, 4), 4.5F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_IRIDIUM_ORE =
            netherOre("nether_iridium_ore", UniformInt.of(3, 7), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_LITHIUM_ORE =
            netherOre("nether_lithium_ore", UniformInt.of(0, 2), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_TITANIUM_ORE =
            netherOre("nether_titanium_ore", UniformInt.of(0, 2), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_THORIUM_ORE =
            netherOre("nether_thorium_ore", UniformInt.of(1, 4), 3.0F, MapColor.NETHER);
    // Minerals - drop their crystal or salt directly, like the stone form.
    public static final DeferredBlock<DropExperienceBlock> NETHER_SALTPETER_ORE =
            netherOre("nether_saltpeter_ore", UniformInt.of(1, 3), 2.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_SALT_ORE =
            netherOre("nether_salt_ore", UniformInt.of(0, 1), 2.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_MONAZITE_ORE =
            netherOre("nether_monazite_ore", UniformInt.of(2, 5), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_FLUORITE_ORE =
            netherOre("nether_fluorite_ore", UniformInt.of(2, 4), 2.0F, MapColor.NETHER);

    // ================================================================
    // ORE BLOCKS — End variants
    //
    // End-stone-hosted duplicates of the same ores. Deepslate-grade
    // hardness — End stone is the late-game host rock.
    // ================================================================

    public static final DeferredBlock<DropExperienceBlock> END_TIN_ORE =
            endOre("end_tin_ore", UniformInt.of(0, 2), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_CASSITERITE_ORE =
            endOre("end_cassiterite_ore", UniformInt.of(0, 2), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_LEAD_ORE =
            endOre("end_lead_ore", UniformInt.of(0, 2), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_NICKEL_ORE =
            endOre("end_nickel_ore", UniformInt.of(0, 2), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_ALUMINUM_ORE =
            endOre("end_aluminum_ore", UniformInt.of(0, 2), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_BAUXITE_ORE =
            endOre("end_bauxite_ore", UniformInt.of(0, 2), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_ZINC_ORE =
            endOre("end_zinc_ore", UniformInt.of(0, 2), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_SILVER_ORE =
            endOre("end_silver_ore", UniformInt.of(0, 3), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_URANIUM_ORE =
            endOre("end_uranium_ore", UniformInt.of(1, 4), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_PLATINUM_ORE =
            endOre("end_platinum_ore", UniformInt.of(2, 5), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_OSMIUM_ORE =
            endOre("end_osmium_ore", UniformInt.of(0, 2), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_BISMUTH_ORE =
            endOre("end_bismuth_ore", UniformInt.of(0, 2), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_CHROMITE_ORE =
            endOre("end_chromite_ore", UniformInt.of(1, 3), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_CHROMIUM_ORE =
            endOre("end_chromium_ore", UniformInt.of(1, 3), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_TUNGSTEN_ORE =
            endOre("end_tungsten_ore", UniformInt.of(1, 4), 6.0F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_IRIDIUM_ORE =
            endOre("end_iridium_ore", UniformInt.of(3, 7), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_LITHIUM_ORE =
            endOre("end_lithium_ore", UniformInt.of(0, 2), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_TITANIUM_ORE =
            endOre("end_titanium_ore", UniformInt.of(0, 2), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_THORIUM_ORE =
            endOre("end_thorium_ore", UniformInt.of(1, 4), 4.5F, MapColor.SAND);
    // Minerals - drop their crystal or salt directly, like the stone form.
    public static final DeferredBlock<DropExperienceBlock> END_SALTPETER_ORE =
            endOre("end_saltpeter_ore", UniformInt.of(1, 3), 3.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_SALT_ORE =
            endOre("end_salt_ore", UniformInt.of(0, 1), 3.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_MONAZITE_ORE =
            endOre("end_monazite_ore", UniformInt.of(2, 5), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_FLUORITE_ORE =
            endOre("end_fluorite_ore", UniformInt.of(2, 4), 3.5F, MapColor.SAND);

    // ================================================================
    // ORE BLOCKS — Holystone variants
    //
    // Holystone-hosted duplicates of the same ores. The Aether is
    // an optional dimension: these blocks always register, but they only
    // generate when the Aether mod supplies the host rock and biomes behind
    // everythingores:holystone_ore_replaceables / everythingores:is_aether.
    // ================================================================

    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_TIN_ORE =
            holystoneOre("holystone_tin_ore", UniformInt.of(0, 2), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_CASSITERITE_ORE =
            holystoneOre("holystone_cassiterite_ore", UniformInt.of(0, 2), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_LEAD_ORE =
            holystoneOre("holystone_lead_ore", UniformInt.of(0, 2), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_NICKEL_ORE =
            holystoneOre("holystone_nickel_ore", UniformInt.of(0, 2), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_ALUMINUM_ORE =
            holystoneOre("holystone_aluminum_ore", UniformInt.of(0, 2), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_BAUXITE_ORE =
            holystoneOre("holystone_bauxite_ore", UniformInt.of(0, 2), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_ZINC_ORE =
            holystoneOre("holystone_zinc_ore", UniformInt.of(0, 2), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_SILVER_ORE =
            holystoneOre("holystone_silver_ore", UniformInt.of(0, 3), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_URANIUM_ORE =
            holystoneOre("holystone_uranium_ore", UniformInt.of(1, 4), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_PLATINUM_ORE =
            holystoneOre("holystone_platinum_ore", UniformInt.of(2, 5), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_OSMIUM_ORE =
            holystoneOre("holystone_osmium_ore", UniformInt.of(0, 2), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_BISMUTH_ORE =
            holystoneOre("holystone_bismuth_ore", UniformInt.of(0, 2), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_CHROMITE_ORE =
            holystoneOre("holystone_chromite_ore", UniformInt.of(1, 3), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_CHROMIUM_ORE =
            holystoneOre("holystone_chromium_ore", UniformInt.of(1, 3), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_TUNGSTEN_ORE =
            holystoneOre("holystone_tungsten_ore", UniformInt.of(1, 4), 4.5F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_IRIDIUM_ORE =
            holystoneOre("holystone_iridium_ore", UniformInt.of(3, 7), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_LITHIUM_ORE =
            holystoneOre("holystone_lithium_ore", UniformInt.of(0, 2), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_TITANIUM_ORE =
            holystoneOre("holystone_titanium_ore", UniformInt.of(0, 2), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_THORIUM_ORE =
            holystoneOre("holystone_thorium_ore", UniformInt.of(1, 4), 3.0F, MapColor.QUARTZ);
    // Minerals - drop their crystal or salt directly, like the stone form.
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_SALTPETER_ORE =
            holystoneOre("holystone_saltpeter_ore", UniformInt.of(1, 3), 2.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_SALT_ORE =
            holystoneOre("holystone_salt_ore", UniformInt.of(0, 1), 2.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_MONAZITE_ORE =
            holystoneOre("holystone_monazite_ore", UniformInt.of(2, 5), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_FLUORITE_ORE =
            holystoneOre("holystone_fluorite_ore", UniformInt.of(2, 4), 2.0F, MapColor.QUARTZ);

    // ================================================================
    // ORE BLOCKS — Vanilla ore dimension variants
    //
    // Coal, iron, copper, redstone, lapis, diamond and emerald hosted in
    // netherrack, End stone and holystone. Drops, XP and tool tier all match
    // the vanilla overworld ore exactly — these are alternate locations for
    // the same ore, not buffed versions of it.
    // ================================================================

    // Nether
    public static final DeferredBlock<DropExperienceBlock> NETHER_COAL_ORE =
            netherOre("nether_coal_ore", UniformInt.of(0, 2), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_IRON_ORE =
            netherOre("nether_iron_ore", UniformInt.of(0, 0), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_COPPER_ORE =
            netherOre("nether_copper_ore", UniformInt.of(0, 0), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<RedStoneOreBlock> NETHER_REDSTONE_ORE =
            redstoneOre("nether_redstone_ore", SoundType.NETHER_ORE, 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_LAPIS_ORE =
            netherOre("nether_lapis_ore", UniformInt.of(2, 5), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_DIAMOND_ORE =
            netherOre("nether_diamond_ore", UniformInt.of(3, 7), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_EMERALD_ORE =
            netherOre("nether_emerald_ore", UniformInt.of(3, 7), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_RUBY_ORE =
            netherOre("nether_ruby_ore", UniformInt.of(3, 7), 3.0F, MapColor.NETHER);
    public static final DeferredBlock<DropExperienceBlock> NETHER_SAPPHIRE_ORE =
            netherOre("nether_sapphire_ore", UniformInt.of(3, 7), 3.0F, MapColor.NETHER);

    // End
    public static final DeferredBlock<DropExperienceBlock> END_COAL_ORE =
            endOre("end_coal_ore", UniformInt.of(0, 2), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_IRON_ORE =
            endOre("end_iron_ore", UniformInt.of(0, 0), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_COPPER_ORE =
            endOre("end_copper_ore", UniformInt.of(0, 0), 4.5F, MapColor.SAND);
    public static final DeferredBlock<RedStoneOreBlock> END_REDSTONE_ORE =
            redstoneOre("end_redstone_ore", SoundType.STONE, 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_LAPIS_ORE =
            endOre("end_lapis_ore", UniformInt.of(2, 5), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_DIAMOND_ORE =
            endOre("end_diamond_ore", UniformInt.of(3, 7), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_EMERALD_ORE =
            endOre("end_emerald_ore", UniformInt.of(3, 7), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_GOLD_ORE =
            endOre("end_gold_ore", UniformInt.of(0, 0), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_RUBY_ORE =
            endOre("end_ruby_ore", UniformInt.of(3, 7), 4.5F, MapColor.SAND);
    public static final DeferredBlock<DropExperienceBlock> END_SAPPHIRE_ORE =
            endOre("end_sapphire_ore", UniformInt.of(3, 7), 4.5F, MapColor.SAND);

    // Aether
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_IRON_ORE =
            holystoneOre("holystone_iron_ore", UniformInt.of(0, 0), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_COPPER_ORE =
            holystoneOre("holystone_copper_ore", UniformInt.of(0, 0), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<RedStoneOreBlock> HOLYSTONE_REDSTONE_ORE =
            redstoneOre("holystone_redstone_ore", SoundType.STONE, 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_LAPIS_ORE =
            holystoneOre("holystone_lapis_ore", UniformInt.of(2, 5), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_DIAMOND_ORE =
            holystoneOre("holystone_diamond_ore", UniformInt.of(3, 7), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_EMERALD_ORE =
            holystoneOre("holystone_emerald_ore", UniformInt.of(3, 7), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_GOLD_ORE =
            holystoneOre("holystone_gold_ore", UniformInt.of(0, 0), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_RUBY_ORE =
            holystoneOre("holystone_ruby_ore", UniformInt.of(3, 7), 3.0F, MapColor.QUARTZ);
    public static final DeferredBlock<DropExperienceBlock> HOLYSTONE_SAPPHIRE_ORE =
            holystoneOre("holystone_sapphire_ore", UniformInt.of(3, 7), 3.0F, MapColor.QUARTZ);



		// ================================================================
		// ORE BLOCKS — Everything Ores exclusive set
		// ================================================================
		public static final DeferredBlock<DropExperienceBlock> NEMONIUM_ORE =
				stoneOre("nemonium_ore", UniformInt.of(2, 5), 3.0F, MapColor.COLOR_LIGHT_GRAY);
		public static final DeferredBlock<DropExperienceBlock> DEEPSLATE_NEMONIUM_ORE =
				deepslateOre("deepslate_nemonium_ore", UniformInt.of(2, 5), 4.5F, MapColor.DEEPSLATE);
		
    // ================================================================
    // RAW ORE STORAGE BLOCKS  (9 raw ores → 1 block)
    // ================================================================

    // Original metals
    public static final DeferredBlock<Block> RAW_TIN_BLOCK      = rawBlock("raw_tin_block",      4.5F, MapColor.STONE);
    public static final DeferredBlock<Block> RAW_LEAD_BLOCK     = rawBlock("raw_lead_block",     4.5F, MapColor.STONE);
    public static final DeferredBlock<Block> RAW_NICKEL_BLOCK   = rawBlock("raw_nickel_block",   4.5F, MapColor.STONE);
    public static final DeferredBlock<Block> RAW_ALUMINUM_BLOCK = rawBlock("raw_aluminum_block", 4.5F, MapColor.COLOR_ORANGE);
    public static final DeferredBlock<Block> RAW_ZINC_BLOCK     = rawBlock("raw_zinc_block",     4.5F, MapColor.STONE);
    public static final DeferredBlock<Block> RAW_SILVER_BLOCK   = rawBlock("raw_silver_block",   4.5F, MapColor.STONE);
    public static final DeferredBlock<Block> RAW_URANIUM_BLOCK  = rawBlock("raw_uranium_block",  4.5F, MapColor.COLOR_GREEN);
    public static final DeferredBlock<Block> RAW_PLATINUM_BLOCK = rawBlock("raw_platinum_block", 4.5F, MapColor.METAL);

    // Unified metals
    public static final DeferredBlock<Block> RAW_OSMIUM_BLOCK   = rawBlock("raw_osmium_block",   4.5F, MapColor.COLOR_BLUE);
    public static final DeferredBlock<Block> RAW_BISMUTH_BLOCK  = rawBlock("raw_bismuth_block",  4.5F, MapColor.COLOR_PINK);
    public static final DeferredBlock<Block> RAW_CHROMITE_BLOCK = rawBlock("raw_chromite_block", 4.5F, MapColor.METAL);
    public static final DeferredBlock<Block> RAW_TUNGSTEN_BLOCK = rawBlock("raw_tungsten_block", 5.5F, MapColor.COLOR_GRAY);
    public static final DeferredBlock<Block> RAW_IRIDIUM_BLOCK  = rawBlock("raw_iridium_block",  4.5F, MapColor.GOLD);
    public static final DeferredBlock<Block> RAW_LITHIUM_BLOCK  = rawBlock("raw_lithium_block",  4.5F, MapColor.COLOR_LIGHT_GRAY);
    public static final DeferredBlock<Block> RAW_TITANIUM_BLOCK = rawBlock("raw_titanium_block", 4.5F, MapColor.METAL);
    public static final DeferredBlock<Block> RAW_THORIUM_BLOCK  = rawBlock("raw_thorium_block",  4.5F, MapColor.COLOR_GRAY);

		// Everything Ores exclusive raw ore storage block
		public static final DeferredBlock<Block> RAW_NEMONIUM_BLOCK = rawBlock("raw_nemonium_block", 4.5F, MapColor.COLOR_LIGHT_GRAY);

    // ================================================================
    // METAL STORAGE BLOCKS  (9 ingots → 1 block)
    // ================================================================

    // Original metals
    public static final DeferredBlock<Block> TIN_BLOCK      = metalBlock("tin_block",      5.0F, MapColor.METAL);
    public static final DeferredBlock<Block> LEAD_BLOCK     = metalBlock("lead_block",     5.0F, MapColor.COLOR_GRAY);
    public static final DeferredBlock<Block> NICKEL_BLOCK   = metalBlock("nickel_block",   5.0F, MapColor.METAL);
    public static final DeferredBlock<Block> ALUMINUM_BLOCK = metalBlock("aluminum_block", 5.0F, MapColor.METAL);
    public static final DeferredBlock<Block> ZINC_BLOCK     = metalBlock("zinc_block",     5.0F, MapColor.METAL);
    public static final DeferredBlock<Block> SILVER_BLOCK   = metalBlock("silver_block",   5.0F, MapColor.METAL);
    public static final DeferredBlock<Block> URANIUM_BLOCK  = metalBlock("uranium_block",  6.0F, MapColor.COLOR_GREEN);
    public static final DeferredBlock<Block> PLATINUM_BLOCK = metalBlock("platinum_block", 5.0F, MapColor.GOLD);

    // Unified metals
    public static final DeferredBlock<Block> OSMIUM_BLOCK   = metalBlock("osmium_block",   5.0F, MapColor.COLOR_BLUE);
    public static final DeferredBlock<Block> BISMUTH_BLOCK  = metalBlock("bismuth_block",  5.0F, MapColor.COLOR_PINK);
    public static final DeferredBlock<Block> CHROMIUM_BLOCK = metalBlock("chromium_block", 5.0F, MapColor.METAL);
    public static final DeferredBlock<Block> TUNGSTEN_BLOCK = metalBlock("tungsten_block", 7.0F, MapColor.COLOR_GRAY);
    public static final DeferredBlock<Block> IRIDIUM_BLOCK  = metalBlock("iridium_block",  6.0F, MapColor.GOLD);
    // No lithium storage block — TFMG's lithium_block is the only one in the
    // pack (single-source form, §5.8), so it stays with TFMG.
    public static final DeferredBlock<Block> TITANIUM_BLOCK = metalBlock("titanium_block", 6.0F, MapColor.METAL);

    // ================================================================
    // ALLOY STORAGE BLOCKS  (9 ingots → 1 block, no ore counterpart)
    // ================================================================

    public static final DeferredBlock<Block> STEEL_BLOCK           = metalBlock("steel_block",           6.0F, MapColor.METAL);
    public static final DeferredBlock<Block> ELECTRUM_BLOCK        = metalBlock("electrum_block",        5.0F, MapColor.GOLD);
    public static final DeferredBlock<Block> CONSTANTAN_BLOCK      = metalBlock("constantan_block",      5.0F, MapColor.METAL);
    public static final DeferredBlock<Block> INVAR_BLOCK           = metalBlock("invar_block",           5.0F, MapColor.METAL);
    public static final DeferredBlock<Block> BRONZE_BLOCK          = metalBlock("bronze_block",          5.0F, MapColor.COLOR_ORANGE);
    public static final DeferredBlock<Block> STAINLESS_STEEL_BLOCK = metalBlock("stainless_steel_block", 6.0F, MapColor.METAL);

    // Red Alloy — Cu + Redstone. Absorbs MoreRed red_alloy and EnderIO redstone_alloy.
    public static final DeferredBlock<Block> RED_ALLOY_BLOCK = metalBlock("red_alloy_block", 5.0F, MapColor.COLOR_RED);

		// ================================================================
		// EVERYTHING ORES EXCLUSIVE STORAGE BLOCKS  (9 ingots → 1 block)
		// ================================================================
		public static final DeferredBlock<Block> NEMONIUM_BLOCK = metalBlock("nemonium_block", 5.0F, MapColor.COLOR_LIGHT_GRAY);

    // ================================================================
    // CAVE DECORATION
    // ================================================================

}
