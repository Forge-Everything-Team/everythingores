package dev.forgeeverything.everythingores.registry;

import dev.forgeeverything.everythingores.EverythingOres;
import dev.forgeeverything.everythingores.config.EOConfig;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers every tool owned by Everything Ores.
 *
 * 14 materials × 5 tools = 70 items.
 *
 * This class uses its OWN DeferredRegister.Items so that calling
 * EOTools.ITEMS.register(modEventBus) in the mod constructor forces
 * static initialisation — and therefore all tool registrations — inside
 * the RegisterEvent window, preventing the "Cannot register after
 * RegisterEvent has been fired" crash that split classes can cause.
 *
 * Tool damage reference (base player = 1, sword modifier = +3):
 *   Sword attack = tier.attackDamageBonus + 3 + 1 base
 *
 * Materials excluded intentionally:
 *   Uranium — radioactive, no tool tradition in modded MC
 *   Zinc    — not a traditional tool metal
 *   Lead    — too soft/dense; no real-world or modded-MC tool use
 */
public class EOTools {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(EverythingOres.MOD_ID);

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static DeferredItem<SwordItem> sword(String mat, net.minecraft.world.item.Tier tier) {
        if (!EOConfig.isRegistrationEnabled(mat + "_sword")) return null;
        return ITEMS.register(mat + "_sword",
                () -> new SwordItem(tier,
                        new Item.Properties().attributes(SwordItem.createAttributes(tier, 3, -2.4f))));
    }

    private static DeferredItem<PickaxeItem> pickaxe(String mat, net.minecraft.world.item.Tier tier) {
        if (!EOConfig.isRegistrationEnabled(mat + "_pickaxe")) return null;
        return ITEMS.register(mat + "_pickaxe",
                () -> new PickaxeItem(tier,
                        new Item.Properties().attributes(PickaxeItem.createAttributes(tier, 1, -2.8f))));
    }

    private static DeferredItem<AxeItem> axe(String mat, net.minecraft.world.item.Tier tier) {
        if (!EOConfig.isRegistrationEnabled(mat + "_axe")) return null;
        return ITEMS.register(mat + "_axe",
                () -> new AxeItem(tier,
                        new Item.Properties().attributes(AxeItem.createAttributes(tier, 6.0f, -3.1f))));
    }

    private static DeferredItem<ShovelItem> shovel(String mat, net.minecraft.world.item.Tier tier) {
        if (!EOConfig.isRegistrationEnabled(mat + "_shovel")) return null;
        return ITEMS.register(mat + "_shovel",
                () -> new ShovelItem(tier,
                        new Item.Properties().attributes(ShovelItem.createAttributes(tier, 1.5f, -3.0f))));
    }

    private static DeferredItem<HoeItem> hoe(String mat, net.minecraft.world.item.Tier tier) {
        if (!EOConfig.isRegistrationEnabled(mat + "_hoe")) return null;
        return ITEMS.register(mat + "_hoe",
                () -> new HoeItem(tier,
                        new Item.Properties().attributes(HoeItem.createAttributes(tier, -2, 0.0f))));
    }

    // ================================================================
    // BRONZE  (stone-equivalent mining)
    // ================================================================
    public static final DeferredItem<SwordItem>   BRONZE_SWORD   = sword  ("bronze", EOTiers.BRONZE);
    public static final DeferredItem<PickaxeItem> BRONZE_PICKAXE = pickaxe("bronze", EOTiers.BRONZE);
    public static final DeferredItem<AxeItem>     BRONZE_AXE     = axe    ("bronze", EOTiers.BRONZE);
    public static final DeferredItem<ShovelItem>  BRONZE_SHOVEL  = shovel ("bronze", EOTiers.BRONZE);
    public static final DeferredItem<HoeItem>     BRONZE_HOE     = hoe    ("bronze", EOTiers.BRONZE);

    // ================================================================
    // ALUMINUM  (stone-equivalent mining, fast)
    // ================================================================
    public static final DeferredItem<SwordItem>   ALUMINUM_SWORD   = sword  ("aluminum", EOTiers.ALUMINUM);
    public static final DeferredItem<PickaxeItem> ALUMINUM_PICKAXE = pickaxe("aluminum", EOTiers.ALUMINUM);
    public static final DeferredItem<AxeItem>     ALUMINUM_AXE     = axe    ("aluminum", EOTiers.ALUMINUM);
    public static final DeferredItem<ShovelItem>  ALUMINUM_SHOVEL  = shovel ("aluminum", EOTiers.ALUMINUM);
    public static final DeferredItem<HoeItem>     ALUMINUM_HOE     = hoe    ("aluminum", EOTiers.ALUMINUM);

    // ================================================================
    // NICKEL  (iron-equivalent mining)
    // ================================================================
    public static final DeferredItem<SwordItem>   NICKEL_SWORD   = sword  ("nickel", EOTiers.NICKEL);
    public static final DeferredItem<PickaxeItem> NICKEL_PICKAXE = pickaxe("nickel", EOTiers.NICKEL);
    public static final DeferredItem<AxeItem>     NICKEL_AXE     = axe    ("nickel", EOTiers.NICKEL);
    public static final DeferredItem<ShovelItem>  NICKEL_SHOVEL  = shovel ("nickel", EOTiers.NICKEL);
    public static final DeferredItem<HoeItem>     NICKEL_HOE     = hoe    ("nickel", EOTiers.NICKEL);

    // ================================================================
    // SILVER  (iron-equivalent mining, high enchantability)
    // ================================================================
    public static final DeferredItem<SwordItem>   SILVER_SWORD   = sword  ("silver", EOTiers.SILVER);
    public static final DeferredItem<PickaxeItem> SILVER_PICKAXE = pickaxe("silver", EOTiers.SILVER);
    public static final DeferredItem<AxeItem>     SILVER_AXE     = axe    ("silver", EOTiers.SILVER);
    public static final DeferredItem<ShovelItem>  SILVER_SHOVEL  = shovel ("silver", EOTiers.SILVER);
    public static final DeferredItem<HoeItem>     SILVER_HOE     = hoe    ("silver", EOTiers.SILVER);

    // ================================================================
    // INVAR  (iron++ tier)
    // ================================================================
    public static final DeferredItem<SwordItem>   INVAR_SWORD   = sword  ("invar", EOTiers.INVAR);
    public static final DeferredItem<PickaxeItem> INVAR_PICKAXE = pickaxe("invar", EOTiers.INVAR);
    public static final DeferredItem<AxeItem>     INVAR_AXE     = axe    ("invar", EOTiers.INVAR);
    public static final DeferredItem<ShovelItem>  INVAR_SHOVEL  = shovel ("invar", EOTiers.INVAR);
    public static final DeferredItem<HoeItem>     INVAR_HOE     = hoe    ("invar", EOTiers.INVAR);

    // ================================================================
    // CONSTANTAN  (iron++ tier)
    // ================================================================
    public static final DeferredItem<SwordItem>   CONSTANTAN_SWORD   = sword  ("constantan", EOTiers.CONSTANTAN);
    public static final DeferredItem<PickaxeItem> CONSTANTAN_PICKAXE = pickaxe("constantan", EOTiers.CONSTANTAN);
    public static final DeferredItem<AxeItem>     CONSTANTAN_AXE     = axe    ("constantan", EOTiers.CONSTANTAN);
    public static final DeferredItem<ShovelItem>  CONSTANTAN_SHOVEL  = shovel ("constantan", EOTiers.CONSTANTAN);
    public static final DeferredItem<HoeItem>     CONSTANTAN_HOE     = hoe    ("constantan", EOTiers.CONSTANTAN);

    // ================================================================
    // BISMUTH  (mid-tier, good enchantability)
    // ================================================================
    public static final DeferredItem<SwordItem>   BISMUTH_SWORD   = sword  ("bismuth", EOTiers.BISMUTH);
    public static final DeferredItem<PickaxeItem> BISMUTH_PICKAXE = pickaxe("bismuth", EOTiers.BISMUTH);
    public static final DeferredItem<AxeItem>     BISMUTH_AXE     = axe    ("bismuth", EOTiers.BISMUTH);
    public static final DeferredItem<ShovelItem>  BISMUTH_SHOVEL  = shovel ("bismuth", EOTiers.BISMUTH);
    public static final DeferredItem<HoeItem>     BISMUTH_HOE     = hoe    ("bismuth", EOTiers.BISMUTH);

    // ================================================================
    // STEEL  (diamond-equivalent mining)
    // ================================================================
    public static final DeferredItem<SwordItem>   STEEL_SWORD   = sword  ("steel", EOTiers.STEEL);
    public static final DeferredItem<PickaxeItem> STEEL_PICKAXE = pickaxe("steel", EOTiers.STEEL);
    public static final DeferredItem<AxeItem>     STEEL_AXE     = axe    ("steel", EOTiers.STEEL);
    public static final DeferredItem<ShovelItem>  STEEL_SHOVEL  = shovel ("steel", EOTiers.STEEL);
    public static final DeferredItem<HoeItem>     STEEL_HOE     = hoe    ("steel", EOTiers.STEEL);

    // ================================================================
    // STAINLESS STEEL  (diamond-equivalent mining)
    // ================================================================
    public static final DeferredItem<SwordItem>   STAINLESS_STEEL_SWORD   = sword  ("stainless_steel", EOTiers.STAINLESS_STEEL);
    public static final DeferredItem<PickaxeItem> STAINLESS_STEEL_PICKAXE = pickaxe("stainless_steel", EOTiers.STAINLESS_STEEL);
    public static final DeferredItem<AxeItem>     STAINLESS_STEEL_AXE     = axe    ("stainless_steel", EOTiers.STAINLESS_STEEL);
    public static final DeferredItem<ShovelItem>  STAINLESS_STEEL_SHOVEL  = shovel ("stainless_steel", EOTiers.STAINLESS_STEEL);
    public static final DeferredItem<HoeItem>     STAINLESS_STEEL_HOE     = hoe    ("stainless_steel", EOTiers.STAINLESS_STEEL);

    // ================================================================
    // OSMIUM  (diamond-equivalent mining, hard hitting, poor enchantability)
    // ================================================================
    public static final DeferredItem<SwordItem>   OSMIUM_SWORD   = sword  ("osmium", EOTiers.OSMIUM);
    public static final DeferredItem<PickaxeItem> OSMIUM_PICKAXE = pickaxe("osmium", EOTiers.OSMIUM);
    public static final DeferredItem<AxeItem>     OSMIUM_AXE     = axe    ("osmium", EOTiers.OSMIUM);
    public static final DeferredItem<ShovelItem>  OSMIUM_SHOVEL  = shovel ("osmium", EOTiers.OSMIUM);
    public static final DeferredItem<HoeItem>     OSMIUM_HOE     = hoe    ("osmium", EOTiers.OSMIUM);

    // ================================================================
    // PLATINUM  (diamond-equivalent mining, high enchantability)
    // ================================================================
    public static final DeferredItem<SwordItem>   PLATINUM_SWORD   = sword  ("platinum", EOTiers.PLATINUM);
    public static final DeferredItem<PickaxeItem> PLATINUM_PICKAXE = pickaxe("platinum", EOTiers.PLATINUM);
    public static final DeferredItem<AxeItem>     PLATINUM_AXE     = axe    ("platinum", EOTiers.PLATINUM);
    public static final DeferredItem<ShovelItem>  PLATINUM_SHOVEL  = shovel ("platinum", EOTiers.PLATINUM);
    public static final DeferredItem<HoeItem>     PLATINUM_HOE     = hoe    ("platinum", EOTiers.PLATINUM);

    // ================================================================
    // TUNGSTEN  (netherite-equivalent mining, slow but hard)
    // ================================================================
    public static final DeferredItem<SwordItem>   TUNGSTEN_SWORD   = sword  ("tungsten", EOTiers.TUNGSTEN);
    public static final DeferredItem<PickaxeItem> TUNGSTEN_PICKAXE = pickaxe("tungsten", EOTiers.TUNGSTEN);
    public static final DeferredItem<AxeItem>     TUNGSTEN_AXE     = axe    ("tungsten", EOTiers.TUNGSTEN);
    public static final DeferredItem<ShovelItem>  TUNGSTEN_SHOVEL  = shovel ("tungsten", EOTiers.TUNGSTEN);
    public static final DeferredItem<HoeItem>     TUNGSTEN_HOE     = hoe    ("tungsten", EOTiers.TUNGSTEN);

    // ================================================================
    // IRIDIUM  (netherite-equivalent mining, high enchantability)
    // ================================================================
    public static final DeferredItem<SwordItem>   IRIDIUM_SWORD   = sword  ("iridium", EOTiers.IRIDIUM);
    public static final DeferredItem<PickaxeItem> IRIDIUM_PICKAXE = pickaxe("iridium", EOTiers.IRIDIUM);
    public static final DeferredItem<AxeItem>     IRIDIUM_AXE     = axe    ("iridium", EOTiers.IRIDIUM);
    public static final DeferredItem<ShovelItem>  IRIDIUM_SHOVEL  = shovel ("iridium", EOTiers.IRIDIUM);
    public static final DeferredItem<HoeItem>     IRIDIUM_HOE     = hoe    ("iridium", EOTiers.IRIDIUM);

		// ================================================================
		// NEMONIUM  (Everything Ores exclusive — post-boss end game tier)
		// ================================================================
		public static final DeferredItem<SwordItem>   NEMONIUM_SWORD   = sword  ("nemonium", EOTiers.NEMONIUM);
		public static final DeferredItem<PickaxeItem> NEMONIUM_PICKAXE = pickaxe("nemonium", EOTiers.NEMONIUM);
		public static final DeferredItem<AxeItem>     NEMONIUM_AXE     = axe    ("nemonium", EOTiers.NEMONIUM);
		public static final DeferredItem<ShovelItem>  NEMONIUM_SHOVEL  = shovel ("nemonium", EOTiers.NEMONIUM);
		public static final DeferredItem<HoeItem>     NEMONIUM_HOE     = hoe    ("nemonium", EOTiers.NEMONIUM);
}
