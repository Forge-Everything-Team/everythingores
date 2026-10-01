package dev.forgeeverything.everythingores.registry;

import dev.forgeeverything.everythingores.EverythingOres;
import dev.forgeeverything.everythingores.registry.EOFluids;
import dev.forgeeverything.everythingores.registry.EOArmor;
import dev.forgeeverything.everythingores.registry.EOTools;

import dev.forgeeverything.everythingores.config.EOConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.ItemLike;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EOCreativeTab {

    /**
     * Adds an entry unless its material is switched off in the config.
     * Skipping it here also removes it from JEI, which builds its ingredient
     * list from creative tab contents rather than from the item registry.
     */
    /** Adds a fluid's bucket, unless the fluid was switched off and never registered. */
    private static void addFluid(CreativeModeTab.Output output, EOFluidSet set) {
        if (set != null) {
            output.accept(set.bucket().get());
        }
    }

    private static void add(CreativeModeTab.Output output, ItemLike item) {
        // Null means a locked proprietary material that was never registered,
        // so there is nothing to show. DeferredItem is itself an ItemLike, which
        // is why the call sites pass the holder rather than resolving it first.
        if (item == null) {
            return;
        }
        var id = BuiltInRegistries.ITEM.getKey(item.asItem());
        if (id == null || EOConfig.isPathEnabled(id.getPath())) {
            output.accept(item);
        }
    }


    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EverythingOres.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EVERYTHING_ORES_TAB =
            TABS.register("everything_ores_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.everythingores.everything_ores_tab"))
                    .icon(() -> EOItems.TIN_INGOT.get().getDefaultInstance())
                    .displayItems((params, output) -> {

                        // ── Ore blocks ──────────────────────────────────────────
                        add(output, EOItems.TIN_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_TIN_ORE_ITEM);
                        add(output, EOItems.CASSITERITE_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_CASSITERITE_ORE_ITEM);
                        add(output, EOItems.LEAD_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_LEAD_ORE_ITEM);
                        add(output, EOItems.NICKEL_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_NICKEL_ORE_ITEM);
                        add(output, EOItems.BAUXITE_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_BAUXITE_ORE_ITEM);
                        add(output, EOItems.ALUMINUM_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_ALUMINUM_ORE_ITEM);
                        add(output, EOItems.ZINC_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_ZINC_ORE_ITEM);
                        add(output, EOItems.SILVER_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_SILVER_ORE_ITEM);
                        add(output, EOItems.URANIUM_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_URANIUM_ORE_ITEM);
                        add(output, EOItems.PLATINUM_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_PLATINUM_ORE_ITEM);
                        add(output, EOItems.SULFUR_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_SULFUR_ORE_ITEM);
                        add(output, EOItems.SULFUR_BLOCK_ITEM);
                        add(output, EOItems.POTENT_SULFUR_ITEM);
                        add(output, EOItems.SULFUR_SPIKE_ITEM);
                        add(output, EOItems.SALTPETER_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_SALTPETER_ORE_ITEM);
                        add(output, EOItems.SALT_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_SALT_ORE_ITEM);
                        add(output, EOItems.MONAZITE_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_MONAZITE_ORE_ITEM);
                        add(output, EOItems.OSMIUM_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_OSMIUM_ORE_ITEM);
                        add(output, EOItems.FLUORITE_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_FLUORITE_ORE_ITEM);
                        add(output, EOItems.BISMUTH_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_BISMUTH_ORE_ITEM);
                        add(output, EOItems.CHROMITE_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_CHROMITE_ORE_ITEM);
                        add(output, EOItems.CHROMIUM_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_CHROMIUM_ORE_ITEM);
                        add(output, EOItems.CINNABAR_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_CINNABAR_ORE_ITEM);
                        add(output, EOItems.CINNABAR_BLOCK_ITEM);
                        add(output, EOItems.TUNGSTEN_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_TUNGSTEN_ORE_ITEM);
                        add(output, EOItems.IRIDIUM_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_IRIDIUM_ORE_ITEM);
                        add(output, EOItems.LITHIUM_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_LITHIUM_ORE_ITEM);
                        add(output, EOItems.TITANIUM_ORE_ITEM);
                        add(output, EOItems.DEEPSLATE_TITANIUM_ORE_ITEM);

                        // ── Nether ore variants ─────────────────────────────────
                        add(output, EOItems.NETHER_TIN_ORE_ITEM);
                        add(output, EOItems.NETHER_CASSITERITE_ORE_ITEM);
                        add(output, EOItems.NETHER_LEAD_ORE_ITEM);
                        add(output, EOItems.NETHER_NICKEL_ORE_ITEM);
                        add(output, EOItems.NETHER_ALUMINUM_ORE_ITEM);
                        add(output, EOItems.NETHER_BAUXITE_ORE_ITEM);
                        add(output, EOItems.NETHER_ZINC_ORE_ITEM);
                        add(output, EOItems.NETHER_SILVER_ORE_ITEM);
                        add(output, EOItems.NETHER_URANIUM_ORE_ITEM);
                        add(output, EOItems.NETHER_PLATINUM_ORE_ITEM);
                        add(output, EOItems.NETHER_OSMIUM_ORE_ITEM);
                        add(output, EOItems.NETHER_BISMUTH_ORE_ITEM);
                        add(output, EOItems.NETHER_CHROMITE_ORE_ITEM);
                        add(output, EOItems.NETHER_CHROMIUM_ORE_ITEM);
                        add(output, EOItems.NETHER_CINNABAR_ORE_ITEM);
                        add(output, EOItems.NETHER_TUNGSTEN_ORE_ITEM);
                        add(output, EOItems.NETHER_IRIDIUM_ORE_ITEM);
                        add(output, EOItems.NETHER_LITHIUM_ORE_ITEM);
                        add(output, EOItems.NETHER_TITANIUM_ORE_ITEM);

                        // ── End ore variants ────────────────────────────────────
                        add(output, EOItems.END_TIN_ORE_ITEM);
                        add(output, EOItems.END_CASSITERITE_ORE_ITEM);
                        add(output, EOItems.END_LEAD_ORE_ITEM);
                        add(output, EOItems.END_NICKEL_ORE_ITEM);
                        add(output, EOItems.END_ALUMINUM_ORE_ITEM);
                        add(output, EOItems.END_BAUXITE_ORE_ITEM);
                        add(output, EOItems.END_ZINC_ORE_ITEM);
                        add(output, EOItems.END_SILVER_ORE_ITEM);
                        add(output, EOItems.END_URANIUM_ORE_ITEM);
                        add(output, EOItems.END_PLATINUM_ORE_ITEM);
                        add(output, EOItems.END_OSMIUM_ORE_ITEM);
                        add(output, EOItems.END_BISMUTH_ORE_ITEM);
                        add(output, EOItems.END_CHROMITE_ORE_ITEM);
                        add(output, EOItems.END_CHROMIUM_ORE_ITEM);
                        add(output, EOItems.END_TUNGSTEN_ORE_ITEM);
                        add(output, EOItems.END_IRIDIUM_ORE_ITEM);
                        add(output, EOItems.END_LITHIUM_ORE_ITEM);
                        add(output, EOItems.END_TITANIUM_ORE_ITEM);

                        // ── Holystone ore variants ─────────────────────────────────
                        add(output, EOItems.HOLYSTONE_TIN_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_CASSITERITE_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_LEAD_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_NICKEL_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_ALUMINUM_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_BAUXITE_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_ZINC_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_SILVER_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_URANIUM_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_PLATINUM_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_OSMIUM_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_BISMUTH_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_CHROMITE_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_CHROMIUM_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_TUNGSTEN_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_IRIDIUM_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_LITHIUM_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_TITANIUM_ORE_ITEM);

                        // ── Vanilla ore variants — Nether ───────────────────────
                        add(output, EOItems.NETHER_COAL_ORE_ITEM);
                        add(output, EOItems.NETHER_IRON_ORE_ITEM);
                        add(output, EOItems.NETHER_COPPER_ORE_ITEM);
                        add(output, EOItems.NETHER_REDSTONE_ORE_ITEM);
                        add(output, EOItems.NETHER_LAPIS_ORE_ITEM);
                        add(output, EOItems.NETHER_DIAMOND_ORE_ITEM);
                        add(output, EOItems.NETHER_EMERALD_ORE_ITEM);
                        add(output, EOItems.NETHER_RUBY_ORE_ITEM);
                        add(output, EOItems.NETHER_SAPPHIRE_ORE_ITEM);

                        // ── Vanilla ore variants — End ──────────────────────────
                        add(output, EOItems.END_COAL_ORE_ITEM);
                        add(output, EOItems.END_IRON_ORE_ITEM);
                        add(output, EOItems.END_COPPER_ORE_ITEM);
                        add(output, EOItems.END_REDSTONE_ORE_ITEM);
                        add(output, EOItems.END_LAPIS_ORE_ITEM);
                        add(output, EOItems.END_DIAMOND_ORE_ITEM);
                        add(output, EOItems.END_EMERALD_ORE_ITEM);
                        add(output, EOItems.END_GOLD_ORE_ITEM);
                        add(output, EOItems.END_RUBY_ORE_ITEM);
                        add(output, EOItems.END_SAPPHIRE_ORE_ITEM);

                        // ── Vanilla ore variants — Holystone ───────────────────────
                        add(output, EOItems.HOLYSTONE_IRON_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_COPPER_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_REDSTONE_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_LAPIS_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_DIAMOND_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_EMERALD_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_GOLD_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_RUBY_ORE_ITEM);
                        add(output, EOItems.HOLYSTONE_SAPPHIRE_ORE_ITEM);

												// ── Everything Ores exclusive ores ─────────────────────────────
												add(output, EOItems.NEMONIUM_ORE_ITEM);
												add(output, EOItems.DEEPSLATE_NEMONIUM_ORE_ITEM);

                        // ── Raw ores ────────────────────────────────────────────
                        add(output, EOItems.RAW_TIN);
                        add(output, EOItems.RAW_LEAD);
                        add(output, EOItems.RAW_NICKEL);
                        add(output, EOItems.RAW_ALUMINUM);
                        add(output, EOItems.RAW_ZINC);
                        add(output, EOItems.RAW_SILVER);
                        add(output, EOItems.RAW_URANIUM);
                        add(output, EOItems.RAW_PLATINUM);
                        add(output, EOItems.RAW_OSMIUM);
                        add(output, EOItems.RAW_BISMUTH);
                        add(output, EOItems.RAW_CHROMITE);
                        add(output, EOItems.RAW_TUNGSTEN);
                        add(output, EOItems.RAW_IRIDIUM);
                        add(output, EOItems.RAW_LITHIUM);
                        add(output, EOItems.RAW_TITANIUM);

												// ── Everything Ores exclusive raw ores ─────────────────────────────
												add(output, EOItems.RAW_NEMONIUM);

                        // ── Raw ore storage blocks ──────────────────────────────
                        add(output, EOItems.RAW_TIN_BLOCK_ITEM);
                        add(output, EOItems.RAW_LEAD_BLOCK_ITEM);
                        add(output, EOItems.RAW_NICKEL_BLOCK_ITEM);
                        add(output, EOItems.RAW_ALUMINUM_BLOCK_ITEM);
                        add(output, EOItems.RAW_ZINC_BLOCK_ITEM);
                        add(output, EOItems.RAW_SILVER_BLOCK_ITEM);
                        add(output, EOItems.RAW_URANIUM_BLOCK_ITEM);
                        add(output, EOItems.RAW_PLATINUM_BLOCK_ITEM);
                        add(output, EOItems.RAW_OSMIUM_BLOCK_ITEM);
                        add(output, EOItems.RAW_BISMUTH_BLOCK_ITEM);
                        add(output, EOItems.RAW_CHROMITE_BLOCK_ITEM);
                        add(output, EOItems.RAW_TUNGSTEN_BLOCK_ITEM);
                        add(output, EOItems.RAW_IRIDIUM_BLOCK_ITEM);
                        add(output, EOItems.RAW_LITHIUM_BLOCK_ITEM);
                        add(output, EOItems.RAW_TITANIUM_BLOCK_ITEM);

												// ── Everything Ores exclusive raw ore storage blocks ─────────────────────────────
												add(output, EOItems.RAW_NEMONIUM_BLOCK_ITEM);

                        // ── Ingots ──────────────────────────────────────────────
                        add(output, EOItems.TIN_INGOT);
                        add(output, EOItems.LEAD_INGOT);
                        add(output, EOItems.NICKEL_INGOT);
                        add(output, EOItems.ALUMINUM_INGOT);
                        add(output, EOItems.ZINC_INGOT);
                        add(output, EOItems.SILVER_INGOT);
                        add(output, EOItems.URANIUM_INGOT);
                        add(output, EOItems.PLATINUM_INGOT);
                        add(output, EOItems.OSMIUM_INGOT);
                        add(output, EOItems.BISMUTH_INGOT);
                        add(output, EOItems.TUNGSTEN_INGOT);
                        add(output, EOItems.IRIDIUM_INGOT);
                        add(output, EOItems.LITHIUM_INGOT);
                        add(output, EOItems.TITANIUM_INGOT);
                        add(output, EOItems.CHROMIUM_INGOT);

                        // ── Alloy ingots ────────────────────────────────────────
                        add(output, EOItems.STEEL_INGOT);
                        add(output, EOItems.ELECTRUM_INGOT);
                        add(output, EOItems.CONSTANTAN_INGOT);
                        add(output, EOItems.INVAR_INGOT);
                        add(output, EOItems.BRONZE_INGOT);
                        add(output, EOItems.STAINLESS_STEEL_INGOT);
                        add(output, EOItems.RED_ALLOY_INGOT);

												// ── Everything Ores exclusive ingots ─────────────────────────────
												add(output, EOItems.NEMONIUM_INGOT);

                        // ── Metal storage blocks ────────────────────────────────
                        add(output, EOItems.TIN_BLOCK_ITEM);
                        add(output, EOItems.LEAD_BLOCK_ITEM);
                        add(output, EOItems.NICKEL_BLOCK_ITEM);
                        add(output, EOItems.ALUMINUM_BLOCK_ITEM);
                        add(output, EOItems.ZINC_BLOCK_ITEM);
                        add(output, EOItems.SILVER_BLOCK_ITEM);
                        add(output, EOItems.URANIUM_BLOCK_ITEM);
                        add(output, EOItems.PLATINUM_BLOCK_ITEM);
                        add(output, EOItems.OSMIUM_BLOCK_ITEM);
                        add(output, EOItems.BISMUTH_BLOCK_ITEM);
                        add(output, EOItems.CHROMIUM_BLOCK_ITEM);
                        add(output, EOItems.TUNGSTEN_BLOCK_ITEM);
                        add(output, EOItems.IRIDIUM_BLOCK_ITEM);
                        add(output, EOItems.TITANIUM_BLOCK_ITEM);

                        // ── Alloy storage blocks ────────────────────────────────
                        add(output, EOItems.STEEL_BLOCK_ITEM);
                        add(output, EOItems.ELECTRUM_BLOCK_ITEM);
                        add(output, EOItems.CONSTANTAN_BLOCK_ITEM);
                        add(output, EOItems.INVAR_BLOCK_ITEM);
                        add(output, EOItems.BRONZE_BLOCK_ITEM);
                        add(output, EOItems.STAINLESS_STEEL_BLOCK_ITEM);
                        add(output, EOItems.RED_ALLOY_BLOCK_ITEM);

												// ── Everything Ores exclusive storage blocks ─────────────────────────────
												add(output, EOItems.NEMONIUM_BLOCK_ITEM);

                        // ── Mineral drops ───────────────────────────────────────
                        add(output, EOItems.SULFUR);
                        add(output, EOItems.SALTPETER);
                        add(output, EOItems.SALT);
                        add(output, EOItems.MONAZITE_CRYSTAL);
                        add(output, EOItems.FLUORITE_CRYSTAL);
                        add(output, EOItems.CINNABAR);
                        add(output, EOItems.RUBY);
                        add(output, EOItems.SAPPHIRE);

                        // ── Fluid buckets ────────────────────────────────────────────────
                        addFluid(output, EOFluids.CRUDE_OIL);
                        addFluid(output, EOFluids.BIOFUEL);
                        addFluid(output, EOFluids.GASOLINE);
                        addFluid(output, EOFluids.DIESEL);
                        addFluid(output, EOFluids.BIODIESEL);
                        addFluid(output, EOFluids.KEROSENE);
                        addFluid(output, EOFluids.ETHANOL);
                        addFluid(output, EOFluids.CREOSOTE);
                        addFluid(output, EOFluids.SULFURIC_ACID);
                        addFluid(output, EOFluids.LPG);
                        addFluid(output, EOFluids.HEAVY_OIL);
                        addFluid(output, EOFluids.LUBRICANT);
                        addFluid(output, EOFluids.MERCURY);

                        // ── Dusts ───────────────────────────────────────────────
                        add(output, EOItems.IRON_DUST);
                        add(output, EOItems.GOLD_DUST);
                        add(output, EOItems.COPPER_DUST);
                        add(output, EOItems.TIN_DUST);
                        add(output, EOItems.LEAD_DUST);
                        add(output, EOItems.NICKEL_DUST);
                        add(output, EOItems.ALUMINUM_DUST);
                        add(output, EOItems.ZINC_DUST);
                        add(output, EOItems.SILVER_DUST);
                        add(output, EOItems.URANIUM_DUST);
                        add(output, EOItems.PLATINUM_DUST);
                        add(output, EOItems.OSMIUM_DUST);
                        add(output, EOItems.BISMUTH_DUST);
												add(output, EOItems.CHROMIUM_DUST);
												add(output, EOItems.CHROMITE_DUST);
                        add(output, EOItems.TUNGSTEN_DUST);
                        add(output, EOItems.IRIDIUM_DUST);
                        add(output, EOItems.LITHIUM_DUST);
                        add(output, EOItems.TITANIUM_DUST);
                        add(output, EOItems.SULFUR_DUST);
                        add(output, EOItems.CINNABAR_DUST);
                        add(output, EOItems.FLUORITE_DUST);
                        add(output, EOItems.STEEL_DUST);
                        add(output, EOItems.ELECTRUM_DUST);
                        add(output, EOItems.CONSTANTAN_DUST);
                        add(output, EOItems.INVAR_DUST);
                        add(output, EOItems.BRONZE_DUST);
                        add(output, EOItems.STAINLESS_STEEL_DUST);
                        add(output, EOItems.RED_ALLOY_DUST);

                        // ── Tiny dusts ──────────────────────────────────────────────────
                        add(output, EOItems.TINY_IRON_DUST);
                        add(output, EOItems.TINY_GOLD_DUST);
                        add(output, EOItems.TINY_COPPER_DUST);
                        add(output, EOItems.TINY_TIN_DUST);
                        add(output, EOItems.TINY_LEAD_DUST);
                        add(output, EOItems.TINY_NICKEL_DUST);
                        add(output, EOItems.TINY_ALUMINUM_DUST);
                        add(output, EOItems.TINY_ZINC_DUST);
                        add(output, EOItems.TINY_SILVER_DUST);
                        add(output, EOItems.TINY_URANIUM_DUST);
                        add(output, EOItems.TINY_PLATINUM_DUST);
                        add(output, EOItems.TINY_OSMIUM_DUST);
                        add(output, EOItems.TINY_BISMUTH_DUST);
												add(output, EOItems.TINY_CHROMIUM_DUST);
												add(output, EOItems.TINY_CHROMITE_DUST);
                        add(output, EOItems.TINY_TUNGSTEN_DUST);
                        add(output, EOItems.TINY_IRIDIUM_DUST);
                        add(output, EOItems.TINY_LITHIUM_DUST);
                        add(output, EOItems.TINY_TITANIUM_DUST);
                        add(output, EOItems.TINY_SULFUR_DUST);
                        add(output, EOItems.TINY_FLUORITE_DUST);
                        add(output, EOItems.TINY_STEEL_DUST);
                        add(output, EOItems.TINY_ELECTRUM_DUST);
                        add(output, EOItems.TINY_CONSTANTAN_DUST);
                        add(output, EOItems.TINY_INVAR_DUST);
                        add(output, EOItems.TINY_BRONZE_DUST);
                        add(output, EOItems.TINY_STAINLESS_STEEL_DUST);
                        add(output, EOItems.TINY_RED_ALLOY_DUST);

												// ── Everything Ores exclusive dust ─────────────────────────────
													add(output, EOItems.NEMONIUM_DUST);

                        // ── Plates ──────────────────────────────────────────────────────
                        add(output, EOItems.IRON_PLATE);
                        add(output, EOItems.GOLD_PLATE);
                        add(output, EOItems.COPPER_PLATE);
                        add(output, EOItems.TIN_PLATE);
                        add(output, EOItems.LEAD_PLATE);
                        add(output, EOItems.NICKEL_PLATE);
                        add(output, EOItems.ALUMINUM_PLATE);
                        add(output, EOItems.ZINC_PLATE);
                        add(output, EOItems.SILVER_PLATE);
                        add(output, EOItems.URANIUM_PLATE);
                        add(output, EOItems.PLATINUM_PLATE);
                        add(output, EOItems.OSMIUM_PLATE);
                        add(output, EOItems.BISMUTH_PLATE);
                        add(output, EOItems.TUNGSTEN_PLATE);
                        add(output, EOItems.IRIDIUM_PLATE);
                        add(output, EOItems.TITANIUM_PLATE);
                        add(output, EOItems.STEEL_PLATE);
                        add(output, EOItems.ELECTRUM_PLATE);
                        add(output, EOItems.CONSTANTAN_PLATE);
                        add(output, EOItems.INVAR_PLATE);
                        add(output, EOItems.BRONZE_PLATE);
                        add(output, EOItems.STAINLESS_STEEL_PLATE);
                        add(output, EOItems.RED_ALLOY_PLATE);

                        // ── Nuggets ─────────────────────────────────────────────────────
                        add(output, EOItems.TIN_NUGGET);
                        add(output, EOItems.LEAD_NUGGET);
                        add(output, EOItems.NICKEL_NUGGET);
                        add(output, EOItems.ALUMINUM_NUGGET);
                        add(output, EOItems.SILVER_NUGGET);
                        add(output, EOItems.PLATINUM_NUGGET);

                        // ── Gears ───────────────────────────────────────────────────────
                        add(output, EOItems.TIN_GEAR);

                        // ── Armor ─────────────────────────────────────────────────────
                        add(output, EOArmor.BRONZE_HELMET);
                        add(output, EOArmor.BRONZE_CHESTPLATE);
                        add(output, EOArmor.BRONZE_LEGGINGS);
                        add(output, EOArmor.BRONZE_BOOTS);
                        add(output, EOArmor.SILVER_HELMET);
                        add(output, EOArmor.SILVER_CHESTPLATE);
                        add(output, EOArmor.SILVER_LEGGINGS);
                        add(output, EOArmor.SILVER_BOOTS);
                        add(output, EOArmor.INVAR_HELMET);
                        add(output, EOArmor.INVAR_CHESTPLATE);
                        add(output, EOArmor.INVAR_LEGGINGS);
                        add(output, EOArmor.INVAR_BOOTS);
                        add(output, EOArmor.STEEL_HELMET);
                        add(output, EOArmor.STEEL_CHESTPLATE);
                        add(output, EOArmor.STEEL_LEGGINGS);
                        add(output, EOArmor.STEEL_BOOTS);
                        add(output, EOArmor.OSMIUM_HELMET);
                        add(output, EOArmor.OSMIUM_CHESTPLATE);
                        add(output, EOArmor.OSMIUM_LEGGINGS);
                        add(output, EOArmor.OSMIUM_BOOTS);
                        add(output, EOArmor.PLATINUM_HELMET);
                        add(output, EOArmor.PLATINUM_CHESTPLATE);
                        add(output, EOArmor.PLATINUM_LEGGINGS);
                        add(output, EOArmor.PLATINUM_BOOTS);
                        add(output, EOArmor.IRIDIUM_HELMET);
                        add(output, EOArmor.IRIDIUM_CHESTPLATE);
                        add(output, EOArmor.IRIDIUM_LEGGINGS);
                        add(output, EOArmor.IRIDIUM_BOOTS);

                        // ── Tools ──────────────────────────────────────────────────────────
                        add(output, EOTools.BRONZE_SWORD);
                        add(output, EOTools.BRONZE_PICKAXE);
                        add(output, EOTools.BRONZE_AXE);
                        add(output, EOTools.BRONZE_SHOVEL);
                        add(output, EOTools.BRONZE_HOE);
                        add(output, EOTools.ALUMINUM_SWORD);
                        add(output, EOTools.ALUMINUM_PICKAXE);
                        add(output, EOTools.ALUMINUM_AXE);
                        add(output, EOTools.ALUMINUM_SHOVEL);
                        add(output, EOTools.ALUMINUM_HOE);
                        add(output, EOTools.NICKEL_SWORD);
                        add(output, EOTools.NICKEL_PICKAXE);
                        add(output, EOTools.NICKEL_AXE);
                        add(output, EOTools.NICKEL_SHOVEL);
                        add(output, EOTools.NICKEL_HOE);
                        add(output, EOTools.SILVER_SWORD);
                        add(output, EOTools.SILVER_PICKAXE);
                        add(output, EOTools.SILVER_AXE);
                        add(output, EOTools.SILVER_SHOVEL);
                        add(output, EOTools.SILVER_HOE);
                        add(output, EOTools.INVAR_SWORD);
                        add(output, EOTools.INVAR_PICKAXE);
                        add(output, EOTools.INVAR_AXE);
                        add(output, EOTools.INVAR_SHOVEL);
                        add(output, EOTools.INVAR_HOE);
                        add(output, EOTools.CONSTANTAN_SWORD);
                        add(output, EOTools.CONSTANTAN_PICKAXE);
                        add(output, EOTools.CONSTANTAN_AXE);
                        add(output, EOTools.CONSTANTAN_SHOVEL);
                        add(output, EOTools.CONSTANTAN_HOE);
                        add(output, EOTools.BISMUTH_SWORD);
                        add(output, EOTools.BISMUTH_PICKAXE);
                        add(output, EOTools.BISMUTH_AXE);
                        add(output, EOTools.BISMUTH_SHOVEL);
                        add(output, EOTools.BISMUTH_HOE);
                        add(output, EOTools.STEEL_SWORD);
                        add(output, EOTools.STEEL_PICKAXE);
                        add(output, EOTools.STEEL_AXE);
                        add(output, EOTools.STEEL_SHOVEL);
                        add(output, EOTools.STEEL_HOE);
                        add(output, EOTools.STAINLESS_STEEL_SWORD);
                        add(output, EOTools.STAINLESS_STEEL_PICKAXE);
                        add(output, EOTools.STAINLESS_STEEL_AXE);
                        add(output, EOTools.STAINLESS_STEEL_SHOVEL);
                        add(output, EOTools.STAINLESS_STEEL_HOE);
                        add(output, EOTools.OSMIUM_SWORD);
                        add(output, EOTools.OSMIUM_PICKAXE);
                        add(output, EOTools.OSMIUM_AXE);
                        add(output, EOTools.OSMIUM_SHOVEL);
                        add(output, EOTools.OSMIUM_HOE);
                        add(output, EOTools.PLATINUM_SWORD);
                        add(output, EOTools.PLATINUM_PICKAXE);
                        add(output, EOTools.PLATINUM_AXE);
                        add(output, EOTools.PLATINUM_SHOVEL);
                        add(output, EOTools.PLATINUM_HOE);
                        add(output, EOTools.TUNGSTEN_SWORD);
                        add(output, EOTools.TUNGSTEN_PICKAXE);
                        add(output, EOTools.TUNGSTEN_AXE);
                        add(output, EOTools.TUNGSTEN_SHOVEL);
                        add(output, EOTools.TUNGSTEN_HOE);
                        add(output, EOTools.IRIDIUM_SWORD);
                        add(output, EOTools.IRIDIUM_PICKAXE);
                        add(output, EOTools.IRIDIUM_AXE);
                        add(output, EOTools.IRIDIUM_SHOVEL);
                        add(output, EOTools.IRIDIUM_HOE);

												// ── Everything Ores exclusive tools ────────────────────────────
													add(output, EOTools.NEMONIUM_SWORD);
													add(output, EOTools.NEMONIUM_PICKAXE);
													add(output, EOTools.NEMONIUM_AXE);
													add(output, EOTools.NEMONIUM_SHOVEL);
													add(output, EOTools.NEMONIUM_HOE);
                    })
                    .build());
}
