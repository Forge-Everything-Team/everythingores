package dev.forgeeverything.everythingores.registry;

import dev.forgeeverything.everythingores.EverythingOres;
import dev.forgeeverything.everythingores.config.EOConfig;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers every item owned by Everything Ores.
 *
 * Six sections:
 *   1. Block items      — one per ore/storage/raw block
 *   2. Raw ores         — raw_<metal>, dropped by metallic ore blocks
 *   3. Ingots           — base metal ingots (original + unified)
 *   4. Alloy ingots     — steel, electrum, constantan, invar, bronze, stainless steel
 *   5. Mineral drops    — direct drops from non-metallic ore blocks (no raw form)
 *   6. Dusts            — ground form of every metal + alloy + vanilla metals
 *
 * Copper is vanilla — no copper items registered here.
 * Bauxite drops raw_aluminum → aluminum_ingot.
 */
public class EOItems {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(EverythingOres.MOD_ID);

    /** Registers a plain item. */
    private static DeferredItem<Item> item(String name) {
        return EOConfig.isRegistrationEnabled(name) ? ITEMS.registerSimpleItem(name) : null;
    }

    /** Registers a block item, or nothing when its block was never registered. */
    private static DeferredItem<BlockItem> blockItem(DeferredBlock<? extends Block> block) {
        return block == null ? null : ITEMS.registerSimpleBlockItem(block);
    }

    // ================================================================
    // BLOCK ITEMS — Ore blocks
    // ================================================================

    // Original ores
    public static final DeferredItem<BlockItem> TIN_ORE_ITEM                 = blockItem(EOBlocks.TIN_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_TIN_ORE_ITEM       = blockItem(EOBlocks.DEEPSLATE_TIN_ORE);
    public static final DeferredItem<BlockItem> CASSITERITE_ORE_ITEM = blockItem(EOBlocks.CASSITERITE_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_CASSITERITE_ORE_ITEM = blockItem(EOBlocks.DEEPSLATE_CASSITERITE_ORE);
    public static final DeferredItem<BlockItem> LEAD_ORE_ITEM                = blockItem(EOBlocks.LEAD_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_LEAD_ORE_ITEM      = blockItem(EOBlocks.DEEPSLATE_LEAD_ORE);
    public static final DeferredItem<BlockItem> NICKEL_ORE_ITEM              = blockItem(EOBlocks.NICKEL_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_NICKEL_ORE_ITEM    = blockItem(EOBlocks.DEEPSLATE_NICKEL_ORE);
    public static final DeferredItem<BlockItem> BAUXITE_ORE_ITEM             = blockItem(EOBlocks.BAUXITE_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_BAUXITE_ORE_ITEM   = blockItem(EOBlocks.DEEPSLATE_BAUXITE_ORE);
    public static final DeferredItem<BlockItem> ALUMINUM_ORE_ITEM = blockItem(EOBlocks.ALUMINUM_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_ALUMINUM_ORE_ITEM = blockItem(EOBlocks.DEEPSLATE_ALUMINUM_ORE);
    public static final DeferredItem<BlockItem> ZINC_ORE_ITEM                = blockItem(EOBlocks.ZINC_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_ZINC_ORE_ITEM      = blockItem(EOBlocks.DEEPSLATE_ZINC_ORE);
    public static final DeferredItem<BlockItem> SILVER_ORE_ITEM              = blockItem(EOBlocks.SILVER_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_SILVER_ORE_ITEM    = blockItem(EOBlocks.DEEPSLATE_SILVER_ORE);
    public static final DeferredItem<BlockItem> URANIUM_ORE_ITEM             = blockItem(EOBlocks.URANIUM_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_URANIUM_ORE_ITEM   = blockItem(EOBlocks.DEEPSLATE_URANIUM_ORE);
    public static final DeferredItem<BlockItem> PLATINUM_ORE_ITEM            = blockItem(EOBlocks.PLATINUM_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_PLATINUM_ORE_ITEM  = blockItem(EOBlocks.DEEPSLATE_PLATINUM_ORE);
    public static final DeferredItem<BlockItem> SULFUR_ORE_ITEM              = blockItem(EOBlocks.SULFUR_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_SULFUR_ORE_ITEM    = blockItem(EOBlocks.DEEPSLATE_SULFUR_ORE);
    public static final DeferredItem<BlockItem> SULFUR_BLOCK_ITEM            = blockItem(EOBlocks.SULFUR_BLOCK);
    public static final DeferredItem<BlockItem> POTENT_SULFUR_ITEM           = blockItem(EOBlocks.POTENT_SULFUR);
    public static final DeferredItem<BlockItem> SULFUR_SPIKE_ITEM            = blockItem(EOBlocks.SULFUR_SPIKE);
    public static final DeferredItem<BlockItem> SALTPETER_ORE_ITEM           = blockItem(EOBlocks.SALTPETER_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_SALTPETER_ORE_ITEM = blockItem(EOBlocks.DEEPSLATE_SALTPETER_ORE);
    public static final DeferredItem<BlockItem> SALT_ORE_ITEM                = blockItem(EOBlocks.SALT_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_SALT_ORE_ITEM      = blockItem(EOBlocks.DEEPSLATE_SALT_ORE);
    public static final DeferredItem<BlockItem> MONAZITE_ORE_ITEM            = blockItem(EOBlocks.MONAZITE_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_MONAZITE_ORE_ITEM  = blockItem(EOBlocks.DEEPSLATE_MONAZITE_ORE);

    // Unified ores
    public static final DeferredItem<BlockItem> OSMIUM_ORE_ITEM              = blockItem(EOBlocks.OSMIUM_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_OSMIUM_ORE_ITEM    = blockItem(EOBlocks.DEEPSLATE_OSMIUM_ORE);
    public static final DeferredItem<BlockItem> FLUORITE_ORE_ITEM            = blockItem(EOBlocks.FLUORITE_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_FLUORITE_ORE_ITEM  = blockItem(EOBlocks.DEEPSLATE_FLUORITE_ORE);
    public static final DeferredItem<BlockItem> BISMUTH_ORE_ITEM             = blockItem(EOBlocks.BISMUTH_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_BISMUTH_ORE_ITEM   = blockItem(EOBlocks.DEEPSLATE_BISMUTH_ORE);
    public static final DeferredItem<BlockItem> CHROMITE_ORE_ITEM            = blockItem(EOBlocks.CHROMITE_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_CHROMITE_ORE_ITEM  = blockItem(EOBlocks.DEEPSLATE_CHROMITE_ORE);
    public static final DeferredItem<BlockItem> CHROMIUM_ORE_ITEM = blockItem(EOBlocks.CHROMIUM_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_CHROMIUM_ORE_ITEM = blockItem(EOBlocks.DEEPSLATE_CHROMIUM_ORE);
    public static final DeferredItem<BlockItem> CINNABAR_ORE_ITEM = blockItem(EOBlocks.CINNABAR_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_CINNABAR_ORE_ITEM = blockItem(EOBlocks.DEEPSLATE_CINNABAR_ORE);
    public static final DeferredItem<BlockItem> CINNABAR_BLOCK_ITEM          = blockItem(EOBlocks.CINNABAR_BLOCK);
    public static final DeferredItem<BlockItem> TUNGSTEN_ORE_ITEM            = blockItem(EOBlocks.TUNGSTEN_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_TUNGSTEN_ORE_ITEM  = blockItem(EOBlocks.DEEPSLATE_TUNGSTEN_ORE);
    public static final DeferredItem<BlockItem> IRIDIUM_ORE_ITEM             = blockItem(EOBlocks.IRIDIUM_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_IRIDIUM_ORE_ITEM   = blockItem(EOBlocks.DEEPSLATE_IRIDIUM_ORE);
    public static final DeferredItem<BlockItem> LITHIUM_ORE_ITEM             = blockItem(EOBlocks.LITHIUM_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_LITHIUM_ORE_ITEM   = blockItem(EOBlocks.DEEPSLATE_LITHIUM_ORE);
    public static final DeferredItem<BlockItem> TITANIUM_ORE_ITEM            = blockItem(EOBlocks.TITANIUM_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_TITANIUM_ORE_ITEM  = blockItem(EOBlocks.DEEPSLATE_TITANIUM_ORE);

		// Nether Ore Variants
		public static final DeferredItem<BlockItem> NETHER_TIN_ORE_ITEM          = blockItem(EOBlocks.NETHER_TIN_ORE);
		public static final DeferredItem<BlockItem> NETHER_CASSITERITE_ORE_ITEM = blockItem(EOBlocks.NETHER_CASSITERITE_ORE);
		public static final DeferredItem<BlockItem> NETHER_LEAD_ORE_ITEM         = blockItem(EOBlocks.NETHER_LEAD_ORE);
		public static final DeferredItem<BlockItem> NETHER_NICKEL_ORE_ITEM       = blockItem(EOBlocks.NETHER_NICKEL_ORE);
		public static final DeferredItem<BlockItem> NETHER_ALUMINUM_ORE_ITEM     = blockItem(EOBlocks.NETHER_ALUMINUM_ORE);
		public static final DeferredItem<BlockItem> NETHER_BAUXITE_ORE_ITEM = blockItem(EOBlocks.NETHER_BAUXITE_ORE);
		public static final DeferredItem<BlockItem> NETHER_ZINC_ORE_ITEM         = blockItem(EOBlocks.NETHER_ZINC_ORE);
		public static final DeferredItem<BlockItem> NETHER_SILVER_ORE_ITEM       = blockItem(EOBlocks.NETHER_SILVER_ORE);
		public static final DeferredItem<BlockItem> NETHER_URANIUM_ORE_ITEM      = blockItem(EOBlocks.NETHER_URANIUM_ORE);
		public static final DeferredItem<BlockItem> NETHER_PLATINUM_ORE_ITEM     = blockItem(EOBlocks.NETHER_PLATINUM_ORE);
		public static final DeferredItem<BlockItem> NETHER_OSMIUM_ORE_ITEM       = blockItem(EOBlocks.NETHER_OSMIUM_ORE);
		public static final DeferredItem<BlockItem> NETHER_BISMUTH_ORE_ITEM      = blockItem(EOBlocks.NETHER_BISMUTH_ORE);
		public static final DeferredItem<BlockItem> NETHER_CHROMITE_ORE_ITEM     = blockItem(EOBlocks.NETHER_CHROMITE_ORE);
		public static final DeferredItem<BlockItem> NETHER_CHROMIUM_ORE_ITEM = blockItem(EOBlocks.NETHER_CHROMIUM_ORE);
		public static final DeferredItem<BlockItem> NETHER_CINNABAR_ORE_ITEM = blockItem(EOBlocks.NETHER_CINNABAR_ORE);
		public static final DeferredItem<BlockItem> NETHER_TUNGSTEN_ORE_ITEM     = blockItem(EOBlocks.NETHER_TUNGSTEN_ORE);
		public static final DeferredItem<BlockItem> NETHER_IRIDIUM_ORE_ITEM      = blockItem(EOBlocks.NETHER_IRIDIUM_ORE);
		public static final DeferredItem<BlockItem> NETHER_LITHIUM_ORE_ITEM      = blockItem(EOBlocks.NETHER_LITHIUM_ORE);
		public static final DeferredItem<BlockItem> NETHER_TITANIUM_ORE_ITEM     = blockItem(EOBlocks.NETHER_TITANIUM_ORE);

		// End Ore Variants
		public static final DeferredItem<BlockItem> END_TIN_ORE_ITEM             = blockItem(EOBlocks.END_TIN_ORE);
		public static final DeferredItem<BlockItem> END_CASSITERITE_ORE_ITEM = blockItem(EOBlocks.END_CASSITERITE_ORE);
		public static final DeferredItem<BlockItem> END_LEAD_ORE_ITEM            = blockItem(EOBlocks.END_LEAD_ORE);
		public static final DeferredItem<BlockItem> END_NICKEL_ORE_ITEM          = blockItem(EOBlocks.END_NICKEL_ORE);
		public static final DeferredItem<BlockItem> END_ALUMINUM_ORE_ITEM        = blockItem(EOBlocks.END_ALUMINUM_ORE);
		public static final DeferredItem<BlockItem> END_BAUXITE_ORE_ITEM = blockItem(EOBlocks.END_BAUXITE_ORE);
		public static final DeferredItem<BlockItem> END_ZINC_ORE_ITEM            = blockItem(EOBlocks.END_ZINC_ORE);
		public static final DeferredItem<BlockItem> END_SILVER_ORE_ITEM          = blockItem(EOBlocks.END_SILVER_ORE);
		public static final DeferredItem<BlockItem> END_URANIUM_ORE_ITEM         = blockItem(EOBlocks.END_URANIUM_ORE);
		public static final DeferredItem<BlockItem> END_PLATINUM_ORE_ITEM        = blockItem(EOBlocks.END_PLATINUM_ORE);
		public static final DeferredItem<BlockItem> END_OSMIUM_ORE_ITEM          = blockItem(EOBlocks.END_OSMIUM_ORE);
		public static final DeferredItem<BlockItem> END_BISMUTH_ORE_ITEM         = blockItem(EOBlocks.END_BISMUTH_ORE);
		public static final DeferredItem<BlockItem> END_CHROMITE_ORE_ITEM        = blockItem(EOBlocks.END_CHROMITE_ORE);
		public static final DeferredItem<BlockItem> END_CHROMIUM_ORE_ITEM = blockItem(EOBlocks.END_CHROMIUM_ORE);
		public static final DeferredItem<BlockItem> END_TUNGSTEN_ORE_ITEM        = blockItem(EOBlocks.END_TUNGSTEN_ORE);
		public static final DeferredItem<BlockItem> END_IRIDIUM_ORE_ITEM         = blockItem(EOBlocks.END_IRIDIUM_ORE);
		public static final DeferredItem<BlockItem> END_LITHIUM_ORE_ITEM         = blockItem(EOBlocks.END_LITHIUM_ORE);
		public static final DeferredItem<BlockItem> END_TITANIUM_ORE_ITEM        = blockItem(EOBlocks.END_TITANIUM_ORE);

		// Holystone Ore Variants
		public static final DeferredItem<BlockItem> HOLYSTONE_TIN_ORE_ITEM              = blockItem(EOBlocks.HOLYSTONE_TIN_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_CASSITERITE_ORE_ITEM = blockItem(EOBlocks.HOLYSTONE_CASSITERITE_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_LEAD_ORE_ITEM             = blockItem(EOBlocks.HOLYSTONE_LEAD_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_NICKEL_ORE_ITEM           = blockItem(EOBlocks.HOLYSTONE_NICKEL_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_ALUMINUM_ORE_ITEM         = blockItem(EOBlocks.HOLYSTONE_ALUMINUM_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_BAUXITE_ORE_ITEM = blockItem(EOBlocks.HOLYSTONE_BAUXITE_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_ZINC_ORE_ITEM             = blockItem(EOBlocks.HOLYSTONE_ZINC_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_SILVER_ORE_ITEM           = blockItem(EOBlocks.HOLYSTONE_SILVER_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_URANIUM_ORE_ITEM          = blockItem(EOBlocks.HOLYSTONE_URANIUM_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_PLATINUM_ORE_ITEM         = blockItem(EOBlocks.HOLYSTONE_PLATINUM_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_OSMIUM_ORE_ITEM           = blockItem(EOBlocks.HOLYSTONE_OSMIUM_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_BISMUTH_ORE_ITEM          = blockItem(EOBlocks.HOLYSTONE_BISMUTH_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_CHROMITE_ORE_ITEM         = blockItem(EOBlocks.HOLYSTONE_CHROMITE_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_CHROMIUM_ORE_ITEM = blockItem(EOBlocks.HOLYSTONE_CHROMIUM_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_TUNGSTEN_ORE_ITEM         = blockItem(EOBlocks.HOLYSTONE_TUNGSTEN_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_IRIDIUM_ORE_ITEM          = blockItem(EOBlocks.HOLYSTONE_IRIDIUM_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_LITHIUM_ORE_ITEM          = blockItem(EOBlocks.HOLYSTONE_LITHIUM_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_TITANIUM_ORE_ITEM         = blockItem(EOBlocks.HOLYSTONE_TITANIUM_ORE);

		// Vanilla Ore Variants — Nether
		public static final DeferredItem<BlockItem> NETHER_COAL_ORE_ITEM             = blockItem(EOBlocks.NETHER_COAL_ORE);
		public static final DeferredItem<BlockItem> NETHER_IRON_ORE_ITEM             = blockItem(EOBlocks.NETHER_IRON_ORE);
		public static final DeferredItem<BlockItem> NETHER_COPPER_ORE_ITEM           = blockItem(EOBlocks.NETHER_COPPER_ORE);
		public static final DeferredItem<BlockItem> NETHER_REDSTONE_ORE_ITEM         = blockItem(EOBlocks.NETHER_REDSTONE_ORE);
		public static final DeferredItem<BlockItem> NETHER_LAPIS_ORE_ITEM            = blockItem(EOBlocks.NETHER_LAPIS_ORE);
		public static final DeferredItem<BlockItem> NETHER_DIAMOND_ORE_ITEM          = blockItem(EOBlocks.NETHER_DIAMOND_ORE);
		public static final DeferredItem<BlockItem> NETHER_EMERALD_ORE_ITEM          = blockItem(EOBlocks.NETHER_EMERALD_ORE);
		public static final DeferredItem<BlockItem> NETHER_RUBY_ORE_ITEM = blockItem(EOBlocks.NETHER_RUBY_ORE);
		public static final DeferredItem<BlockItem> NETHER_SAPPHIRE_ORE_ITEM = blockItem(EOBlocks.NETHER_SAPPHIRE_ORE);

		// Vanilla Ore Variants — End
		public static final DeferredItem<BlockItem> END_COAL_ORE_ITEM                = blockItem(EOBlocks.END_COAL_ORE);
		public static final DeferredItem<BlockItem> END_IRON_ORE_ITEM                = blockItem(EOBlocks.END_IRON_ORE);
		public static final DeferredItem<BlockItem> END_COPPER_ORE_ITEM              = blockItem(EOBlocks.END_COPPER_ORE);
		public static final DeferredItem<BlockItem> END_REDSTONE_ORE_ITEM            = blockItem(EOBlocks.END_REDSTONE_ORE);
		public static final DeferredItem<BlockItem> END_LAPIS_ORE_ITEM               = blockItem(EOBlocks.END_LAPIS_ORE);
		public static final DeferredItem<BlockItem> END_DIAMOND_ORE_ITEM             = blockItem(EOBlocks.END_DIAMOND_ORE);
		public static final DeferredItem<BlockItem> END_EMERALD_ORE_ITEM             = blockItem(EOBlocks.END_EMERALD_ORE);
		public static final DeferredItem<BlockItem> END_GOLD_ORE_ITEM = blockItem(EOBlocks.END_GOLD_ORE);
		public static final DeferredItem<BlockItem> END_RUBY_ORE_ITEM = blockItem(EOBlocks.END_RUBY_ORE);
		public static final DeferredItem<BlockItem> END_SAPPHIRE_ORE_ITEM = blockItem(EOBlocks.END_SAPPHIRE_ORE);

		// Vanilla Ore Variants — Holystone
		public static final DeferredItem<BlockItem> HOLYSTONE_IRON_ORE_ITEM             = blockItem(EOBlocks.HOLYSTONE_IRON_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_COPPER_ORE_ITEM           = blockItem(EOBlocks.HOLYSTONE_COPPER_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_REDSTONE_ORE_ITEM         = blockItem(EOBlocks.HOLYSTONE_REDSTONE_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_LAPIS_ORE_ITEM            = blockItem(EOBlocks.HOLYSTONE_LAPIS_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_DIAMOND_ORE_ITEM          = blockItem(EOBlocks.HOLYSTONE_DIAMOND_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_EMERALD_ORE_ITEM          = blockItem(EOBlocks.HOLYSTONE_EMERALD_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_GOLD_ORE_ITEM = blockItem(EOBlocks.HOLYSTONE_GOLD_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_RUBY_ORE_ITEM = blockItem(EOBlocks.HOLYSTONE_RUBY_ORE);
		public static final DeferredItem<BlockItem> HOLYSTONE_SAPPHIRE_ORE_ITEM = blockItem(EOBlocks.HOLYSTONE_SAPPHIRE_ORE);

		// Everything Ores exclusive ores
		public static final DeferredItem<BlockItem> NEMONIUM_ORE_ITEM            = blockItem(EOBlocks.NEMONIUM_ORE);
		public static final DeferredItem<BlockItem> DEEPSLATE_NEMONIUM_ORE_ITEM  = blockItem(EOBlocks.DEEPSLATE_NEMONIUM_ORE);
    // ================================================================
    // BLOCK ITEMS — Raw ore storage blocks
    // ================================================================

    public static final DeferredItem<BlockItem> RAW_TIN_BLOCK_ITEM      = blockItem(EOBlocks.RAW_TIN_BLOCK);
    public static final DeferredItem<BlockItem> RAW_LEAD_BLOCK_ITEM     = blockItem(EOBlocks.RAW_LEAD_BLOCK);
    public static final DeferredItem<BlockItem> RAW_NICKEL_BLOCK_ITEM   = blockItem(EOBlocks.RAW_NICKEL_BLOCK);
    public static final DeferredItem<BlockItem> RAW_ALUMINUM_BLOCK_ITEM = blockItem(EOBlocks.RAW_ALUMINUM_BLOCK);
    public static final DeferredItem<BlockItem> RAW_ZINC_BLOCK_ITEM     = blockItem(EOBlocks.RAW_ZINC_BLOCK);
    public static final DeferredItem<BlockItem> RAW_SILVER_BLOCK_ITEM   = blockItem(EOBlocks.RAW_SILVER_BLOCK);
    public static final DeferredItem<BlockItem> RAW_URANIUM_BLOCK_ITEM  = blockItem(EOBlocks.RAW_URANIUM_BLOCK);
    public static final DeferredItem<BlockItem> RAW_PLATINUM_BLOCK_ITEM = blockItem(EOBlocks.RAW_PLATINUM_BLOCK);
    public static final DeferredItem<BlockItem> RAW_OSMIUM_BLOCK_ITEM   = blockItem(EOBlocks.RAW_OSMIUM_BLOCK);
    public static final DeferredItem<BlockItem> RAW_BISMUTH_BLOCK_ITEM  = blockItem(EOBlocks.RAW_BISMUTH_BLOCK);
    public static final DeferredItem<BlockItem> RAW_CHROMITE_BLOCK_ITEM = blockItem(EOBlocks.RAW_CHROMITE_BLOCK);
    public static final DeferredItem<BlockItem> RAW_TUNGSTEN_BLOCK_ITEM = blockItem(EOBlocks.RAW_TUNGSTEN_BLOCK);
    public static final DeferredItem<BlockItem> RAW_IRIDIUM_BLOCK_ITEM  = blockItem(EOBlocks.RAW_IRIDIUM_BLOCK);
    public static final DeferredItem<BlockItem> RAW_LITHIUM_BLOCK_ITEM  = blockItem(EOBlocks.RAW_LITHIUM_BLOCK);
    public static final DeferredItem<BlockItem> RAW_TITANIUM_BLOCK_ITEM = blockItem(EOBlocks.RAW_TITANIUM_BLOCK);

		// Everything Ores exclusive raw ore storage block
		public static final DeferredItem<BlockItem> RAW_NEMONIUM_BLOCK_ITEM = blockItem(EOBlocks.RAW_NEMONIUM_BLOCK);

    // ================================================================
    // BLOCK ITEMS — Metal storage blocks
    // ================================================================

    // Original metals
    public static final DeferredItem<BlockItem> TIN_BLOCK_ITEM      = blockItem(EOBlocks.TIN_BLOCK);
    public static final DeferredItem<BlockItem> LEAD_BLOCK_ITEM     = blockItem(EOBlocks.LEAD_BLOCK);
    public static final DeferredItem<BlockItem> NICKEL_BLOCK_ITEM   = blockItem(EOBlocks.NICKEL_BLOCK);
    public static final DeferredItem<BlockItem> ALUMINUM_BLOCK_ITEM = blockItem(EOBlocks.ALUMINUM_BLOCK);
    public static final DeferredItem<BlockItem> ZINC_BLOCK_ITEM     = blockItem(EOBlocks.ZINC_BLOCK);
    public static final DeferredItem<BlockItem> SILVER_BLOCK_ITEM   = blockItem(EOBlocks.SILVER_BLOCK);
    public static final DeferredItem<BlockItem> URANIUM_BLOCK_ITEM  = blockItem(EOBlocks.URANIUM_BLOCK);
    public static final DeferredItem<BlockItem> PLATINUM_BLOCK_ITEM = blockItem(EOBlocks.PLATINUM_BLOCK);

    // Unified metals
    public static final DeferredItem<BlockItem> OSMIUM_BLOCK_ITEM   = blockItem(EOBlocks.OSMIUM_BLOCK);
    public static final DeferredItem<BlockItem> BISMUTH_BLOCK_ITEM  = blockItem(EOBlocks.BISMUTH_BLOCK);
    public static final DeferredItem<BlockItem> CHROMIUM_BLOCK_ITEM = blockItem(EOBlocks.CHROMIUM_BLOCK);
    public static final DeferredItem<BlockItem> TUNGSTEN_BLOCK_ITEM = blockItem(EOBlocks.TUNGSTEN_BLOCK);
    public static final DeferredItem<BlockItem> IRIDIUM_BLOCK_ITEM  = blockItem(EOBlocks.IRIDIUM_BLOCK);
    public static final DeferredItem<BlockItem> TITANIUM_BLOCK_ITEM = blockItem(EOBlocks.TITANIUM_BLOCK);

    // ================================================================
    // BLOCK ITEMS — Alloy storage blocks
    // ================================================================

    public static final DeferredItem<BlockItem> STEEL_BLOCK_ITEM           = blockItem(EOBlocks.STEEL_BLOCK);
    public static final DeferredItem<BlockItem> ELECTRUM_BLOCK_ITEM        = blockItem(EOBlocks.ELECTRUM_BLOCK);
    public static final DeferredItem<BlockItem> CONSTANTAN_BLOCK_ITEM      = blockItem(EOBlocks.CONSTANTAN_BLOCK);
    public static final DeferredItem<BlockItem> INVAR_BLOCK_ITEM           = blockItem(EOBlocks.INVAR_BLOCK);
    public static final DeferredItem<BlockItem> BRONZE_BLOCK_ITEM          = blockItem(EOBlocks.BRONZE_BLOCK);
    public static final DeferredItem<BlockItem> STAINLESS_STEEL_BLOCK_ITEM = blockItem(EOBlocks.STAINLESS_STEEL_BLOCK);
    public static final DeferredItem<BlockItem> RED_ALLOY_BLOCK_ITEM       = blockItem(EOBlocks.RED_ALLOY_BLOCK);

		// ===============================================================
		// EVERYTHING ORES EXCLUSIVE BLOCKS
		// ===============================================================

		public static final DeferredItem<BlockItem> NEMONIUM_BLOCK_ITEM = blockItem(EOBlocks.NEMONIUM_BLOCK);

		// Cave decoration

    // ================================================================
    // RAW ORES  (dropped by metallic ore blocks)
    // ================================================================

    // Original metals
    public static final DeferredItem<Item> RAW_TIN      = item("raw_tin");
    public static final DeferredItem<Item> RAW_LEAD     = item("raw_lead");
    public static final DeferredItem<Item> RAW_NICKEL   = item("raw_nickel");
    public static final DeferredItem<Item> RAW_ALUMINUM = item("raw_aluminum");
    public static final DeferredItem<Item> RAW_ZINC     = item("raw_zinc");
    public static final DeferredItem<Item> RAW_SILVER   = item("raw_silver");
    public static final DeferredItem<Item> RAW_URANIUM  = item("raw_uranium");
    public static final DeferredItem<Item> RAW_PLATINUM = item("raw_platinum");

    // Unified metals
    public static final DeferredItem<Item> RAW_OSMIUM   = item("raw_osmium");
    public static final DeferredItem<Item> RAW_BISMUTH  = item("raw_bismuth");
    public static final DeferredItem<Item> RAW_CHROMITE = item("raw_chromite");
    public static final DeferredItem<Item> RAW_TUNGSTEN = item("raw_tungsten");
    public static final DeferredItem<Item> RAW_IRIDIUM  = item("raw_iridium");
    public static final DeferredItem<Item> RAW_LITHIUM  = item("raw_lithium");
    public static final DeferredItem<Item> RAW_TITANIUM = item("raw_titanium");

		// Everything Ores exclusive raw ores
		public static final DeferredItem<Item> RAW_NEMONIUM = item("raw_nemonium");

    // ================================================================
    // INGOTS — Base metals
    // ================================================================

    // Original metals
    public static final DeferredItem<Item> TIN_INGOT      = item("tin_ingot");
    public static final DeferredItem<Item> LEAD_INGOT     = item("lead_ingot");
    public static final DeferredItem<Item> NICKEL_INGOT   = item("nickel_ingot");
    public static final DeferredItem<Item> ALUMINUM_INGOT = item("aluminum_ingot");
    public static final DeferredItem<Item> ZINC_INGOT     = item("zinc_ingot");
    public static final DeferredItem<Item> SILVER_INGOT   = item("silver_ingot");
    public static final DeferredItem<Item> URANIUM_INGOT  = item("uranium_ingot");
    public static final DeferredItem<Item> PLATINUM_INGOT = item("platinum_ingot");

    // Unified metals
    public static final DeferredItem<Item> OSMIUM_INGOT   = item("osmium_ingot");
    public static final DeferredItem<Item> BISMUTH_INGOT  = item("bismuth_ingot");
    public static final DeferredItem<Item> CHROMIUM_INGOT = item("chromium_ingot");
    public static final DeferredItem<Item> TUNGSTEN_INGOT = item("tungsten_ingot");
    public static final DeferredItem<Item> IRIDIUM_INGOT  = item("iridium_ingot");
    public static final DeferredItem<Item> LITHIUM_INGOT  = item("lithium_ingot");
    public static final DeferredItem<Item> TITANIUM_INGOT = item("titanium_ingot");

    // ================================================================
    // INGOTS — Alloys  (no ore block; crafted from base metals)
    // ================================================================

    public static final DeferredItem<Item> STEEL_INGOT          = item("steel_ingot");
    public static final DeferredItem<Item> ELECTRUM_INGOT       = item("electrum_ingot");
    public static final DeferredItem<Item> CONSTANTAN_INGOT     = item("constantan_ingot");
    public static final DeferredItem<Item> INVAR_INGOT          = item("invar_ingot");
    public static final DeferredItem<Item> BRONZE_INGOT         = item("bronze_ingot");
    public static final DeferredItem<Item> STAINLESS_STEEL_INGOT = item("stainless_steel_ingot");
    // Red Alloy — Cu + Redstone. Absorbs MoreRed:red_alloy_ingot and EnderIO:redstone_alloy_ingot.
    public static final DeferredItem<Item> RED_ALLOY_INGOT      = item("red_alloy_ingot");

		// ================================================================
		// INGOTS — Everything Ores exclusive ingots
		// ================================================================

		public static final DeferredItem<Item> NEMONIUM_INGOT = item("nemonium_ingot");

    // ================================================================
    // MINERAL DROPS  (dropped directly from non-metallic ore blocks)
    // ================================================================

    public static final DeferredItem<Item> SULFUR           = item("sulfur");
    public static final DeferredItem<Item> SALTPETER        = item("saltpeter");
    public static final DeferredItem<Item> SALT             = item("salt");
    public static final DeferredItem<Item> MONAZITE_CRYSTAL = item("monazite_crystal");
    public static final DeferredItem<Item> FLUORITE_CRYSTAL = item("fluorite_crystal");
    public static final DeferredItem<Item> CINNABAR          = item("cinnabar");
    public static final DeferredItem<Item> RUBY             = item("ruby");
    public static final DeferredItem<Item> SAPPHIRE         = item("sapphire");

    // ================================================================
    // DUSTS
    //
    // Registered here so EO is the canonical source across the pack.
    // Almost Unified redirects competing mods' recipes to these items.
    // Tags (neoforge:dusts/<metal>) wire them into Mekanism / IE machines.
    // ================================================================

    // Vanilla metals — Mekanism and IE both produce these; EO owns the item
    public static final DeferredItem<Item> IRON_DUST   = item("iron_dust");
    public static final DeferredItem<Item> GOLD_DUST   = item("gold_dust");
    public static final DeferredItem<Item> COPPER_DUST = item("copper_dust");

    // Original metals
    public static final DeferredItem<Item> TIN_DUST      = item("tin_dust");
    public static final DeferredItem<Item> LEAD_DUST     = item("lead_dust");
    public static final DeferredItem<Item> NICKEL_DUST   = item("nickel_dust");
    public static final DeferredItem<Item> ALUMINUM_DUST = item("aluminum_dust");
    public static final DeferredItem<Item> ZINC_DUST     = item("zinc_dust");
    public static final DeferredItem<Item> SILVER_DUST   = item("silver_dust");
    public static final DeferredItem<Item> URANIUM_DUST  = item("uranium_dust");
    public static final DeferredItem<Item> PLATINUM_DUST = item("platinum_dust");

    // Unified metals
    public static final DeferredItem<Item> OSMIUM_DUST   = item("osmium_dust");
    public static final DeferredItem<Item> BISMUTH_DUST  = item("bismuth_dust");
    public static final DeferredItem<Item> CHROMIUM_DUST = item("chromium_dust");
		public static final DeferredItem<Item> CHROMITE_DUST = item("chromite_dust");
    public static final DeferredItem<Item> TUNGSTEN_DUST = item("tungsten_dust");
    public static final DeferredItem<Item> IRIDIUM_DUST  = item("iridium_dust");
    public static final DeferredItem<Item> LITHIUM_DUST  = item("lithium_dust");
    public static final DeferredItem<Item> TITANIUM_DUST = item("titanium_dust");

    // Minerals with a dust form used by other mods
    public static final DeferredItem<Item> SULFUR_DUST   = item("sulfur_dust");
    // Crafted or crushed from the cinnabar block; with sulfur caves on it
    // replaces the raw cinnabar item as the route to mercury.
    public static final DeferredItem<Item> CINNABAR_DUST = item("cinnabar_dust");
    public static final DeferredItem<Item> FLUORITE_DUST = item("fluorite_dust");

    // Alloy dusts
    public static final DeferredItem<Item> STEEL_DUST           = item("steel_dust");
    public static final DeferredItem<Item> ELECTRUM_DUST        = item("electrum_dust");
    public static final DeferredItem<Item> CONSTANTAN_DUST      = item("constantan_dust");
    public static final DeferredItem<Item> INVAR_DUST           = item("invar_dust");
    public static final DeferredItem<Item> BRONZE_DUST          = item("bronze_dust");
    public static final DeferredItem<Item> STAINLESS_STEEL_DUST = item("stainless_steel_dust");
    public static final DeferredItem<Item> RED_ALLOY_DUST       = item("red_alloy_dust");

		// Everything Ores exclusive dust
		public static final DeferredItem<Item> NEMONIUM_DUST = item("nemonium_dust");

    // ================================================================
    // TINY DUSTS  (1/9 of a full dust — used in Mekanism ore processing
    //             and several other tech mod processing chains)
    // ================================================================

    // Vanilla metals
    public static final DeferredItem<Item> TINY_IRON_DUST   = item("tiny_iron_dust");
    public static final DeferredItem<Item> TINY_GOLD_DUST   = item("tiny_gold_dust");
    public static final DeferredItem<Item> TINY_COPPER_DUST = item("tiny_copper_dust");

    // Original metals
    public static final DeferredItem<Item> TINY_TIN_DUST      = item("tiny_tin_dust");
    public static final DeferredItem<Item> TINY_LEAD_DUST     = item("tiny_lead_dust");
    public static final DeferredItem<Item> TINY_NICKEL_DUST   = item("tiny_nickel_dust");
    public static final DeferredItem<Item> TINY_ALUMINUM_DUST = item("tiny_aluminum_dust");
    public static final DeferredItem<Item> TINY_ZINC_DUST     = item("tiny_zinc_dust");
    public static final DeferredItem<Item> TINY_SILVER_DUST   = item("tiny_silver_dust");
    public static final DeferredItem<Item> TINY_URANIUM_DUST  = item("tiny_uranium_dust");
    public static final DeferredItem<Item> TINY_PLATINUM_DUST = item("tiny_platinum_dust");

    // Unified metals
    public static final DeferredItem<Item> TINY_OSMIUM_DUST   = item("tiny_osmium_dust");
    public static final DeferredItem<Item> TINY_BISMUTH_DUST  = item("tiny_bismuth_dust");
    public static final DeferredItem<Item> TINY_TUNGSTEN_DUST = item("tiny_tungsten_dust");
    public static final DeferredItem<Item> TINY_IRIDIUM_DUST  = item("tiny_iridium_dust");
    public static final DeferredItem<Item> TINY_LITHIUM_DUST  = item("tiny_lithium_dust");
    public static final DeferredItem<Item> TINY_TITANIUM_DUST = item("tiny_titanium_dust");
		public static final DeferredItem<Item> TINY_CHROMIUM_DUST = item("tiny_chromium_dust");
		public static final DeferredItem<Item> TINY_CHROMITE_DUST = item("tiny_chromite_dust");

    // Minerals
    public static final DeferredItem<Item> TINY_SULFUR_DUST   = item("tiny_sulfur_dust");
    public static final DeferredItem<Item> TINY_FLUORITE_DUST = item("tiny_fluorite_dust");

    // Alloys
    public static final DeferredItem<Item> TINY_STEEL_DUST           = item("tiny_steel_dust");
    public static final DeferredItem<Item> TINY_ELECTRUM_DUST        = item("tiny_electrum_dust");
    public static final DeferredItem<Item> TINY_CONSTANTAN_DUST      = item("tiny_constantan_dust");
    public static final DeferredItem<Item> TINY_INVAR_DUST           = item("tiny_invar_dust");
    public static final DeferredItem<Item> TINY_BRONZE_DUST          = item("tiny_bronze_dust");
    public static final DeferredItem<Item> TINY_STAINLESS_STEEL_DUST = item("tiny_stainless_steel_dust");
    public static final DeferredItem<Item> TINY_RED_ALLOY_DUST       = item("tiny_red_alloy_dust");

    // ================================================================
    // PLATES  (pressed ingot form — used by IE, Mekanism, MI, and
    //          many other tech mods in their crafting recipes)
    // ================================================================

    // Vanilla metals
    public static final DeferredItem<Item> IRON_PLATE   = item("iron_plate");
    public static final DeferredItem<Item> GOLD_PLATE   = item("gold_plate");
    public static final DeferredItem<Item> COPPER_PLATE = item("copper_plate");

    // Original metals
    public static final DeferredItem<Item> TIN_PLATE      = item("tin_plate");
    public static final DeferredItem<Item> LEAD_PLATE     = item("lead_plate");
    public static final DeferredItem<Item> NICKEL_PLATE   = item("nickel_plate");
    public static final DeferredItem<Item> ALUMINUM_PLATE = item("aluminum_plate");
    public static final DeferredItem<Item> ZINC_PLATE     = item("zinc_plate");
    public static final DeferredItem<Item> SILVER_PLATE   = item("silver_plate");
    public static final DeferredItem<Item> URANIUM_PLATE  = item("uranium_plate");
    public static final DeferredItem<Item> PLATINUM_PLATE = item("platinum_plate");

    // Unified metals
    public static final DeferredItem<Item> OSMIUM_PLATE   = item("osmium_plate");
    public static final DeferredItem<Item> BISMUTH_PLATE  = item("bismuth_plate");
    public static final DeferredItem<Item> TUNGSTEN_PLATE = item("tungsten_plate");
    public static final DeferredItem<Item> IRIDIUM_PLATE  = item("iridium_plate");
    // Titanium plate — Electrodynamics + MI both carry it. Lithium plate is
    // Electrodynamics-only (single-source form, §5.8) and stays there.
    public static final DeferredItem<Item> TITANIUM_PLATE = item("titanium_plate");

    // Alloys
    public static final DeferredItem<Item> STEEL_PLATE           = item("steel_plate");
    public static final DeferredItem<Item> ELECTRUM_PLATE        = item("electrum_plate");
    public static final DeferredItem<Item> CONSTANTAN_PLATE      = item("constantan_plate");
    public static final DeferredItem<Item> INVAR_PLATE           = item("invar_plate");
    public static final DeferredItem<Item> BRONZE_PLATE          = item("bronze_plate");
    public static final DeferredItem<Item> STAINLESS_STEEL_PLATE = item("stainless_steel_plate");
    public static final DeferredItem<Item> RED_ALLOY_PLATE       = item("red_alloy_plate");

    // ================================================================
    // NUGGETS  (1/9 ingot — only for materials where 2+ satellite mods
    //          register a nugget; single-source nuggets stay put, §5.8)
    // ================================================================

    public static final DeferredItem<Item> TIN_NUGGET      = item("tin_nugget");      // Electrodynamics + MI
    public static final DeferredItem<Item> LEAD_NUGGET     = item("lead_nugget");     // Create: Nuclear + MI + TFMG
    public static final DeferredItem<Item> NICKEL_NUGGET   = item("nickel_nugget");   // MI + Oritech + TFMG
    public static final DeferredItem<Item> ALUMINUM_NUGGET = item("aluminum_nugget"); // MI + TFMG + XyCraft World
    public static final DeferredItem<Item> SILVER_NUGGET   = item("silver_nugget");   // Electrodynamics + MI + Occultism + I&F + Silent Gems + Werewolves
    public static final DeferredItem<Item> PLATINUM_NUGGET = item("platinum_nugget"); // MI + Oritech

    // ================================================================
    // GEARS  (only tin is duplicated: Electrodynamics + MI)
    // ================================================================

    public static final DeferredItem<Item> TIN_GEAR = item("tin_gear");
}