package dev.forgeeverything.everythingores.registry;

import dev.forgeeverything.everythingores.EverythingOres;
import dev.forgeeverything.everythingores.config.EOConfig;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Registers every armor item owned by Everything Ores.
 *
 * 7 materials × 4 pieces = 28 items.
 *
 * Uses its own DeferredRegister.Items so that calling
 * EOArmor.ITEMS.register(modEventBus) in the mod constructor forces
 * static initialisation inside the RegisterEvent window — the same
 * pattern used by EOTools to avoid late-registration crashes.
 *
 * ArmorMaterial Holders live in EOArmorMaterials, which must be
 * registered before this class initialises (handled by registration
 * order in EverythingOres.java).
 */
public class EOArmor {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(EverythingOres.MOD_ID);

    // ── Helper ────────────────────────────────────────────────────────────────

    private static DeferredItem<ArmorItem> armor(String name,
            net.minecraft.core.Holder<net.minecraft.world.item.ArmorMaterial> mat,
            ArmorItem.Type type) {
        if (!EOConfig.isRegistrationEnabled(name)) return null;
        return ITEMS.register(name,
                () -> new ArmorItem(mat, type, new Item.Properties()));
    }

    // ================================================================
    // BRONZE
    // ================================================================
    public static final DeferredItem<ArmorItem> BRONZE_HELMET     = armor("bronze_helmet",     EOArmorMaterials.BRONZE, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> BRONZE_CHESTPLATE = armor("bronze_chestplate", EOArmorMaterials.BRONZE, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> BRONZE_LEGGINGS   = armor("bronze_leggings",   EOArmorMaterials.BRONZE, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> BRONZE_BOOTS      = armor("bronze_boots",      EOArmorMaterials.BRONZE, ArmorItem.Type.BOOTS);

    // ================================================================
    // SILVER
    // ================================================================
    public static final DeferredItem<ArmorItem> SILVER_HELMET     = armor("silver_helmet",     EOArmorMaterials.SILVER, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> SILVER_CHESTPLATE = armor("silver_chestplate", EOArmorMaterials.SILVER, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> SILVER_LEGGINGS   = armor("silver_leggings",   EOArmorMaterials.SILVER, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> SILVER_BOOTS      = armor("silver_boots",      EOArmorMaterials.SILVER, ArmorItem.Type.BOOTS);

    // ================================================================
    // INVAR
    // ================================================================
    public static final DeferredItem<ArmorItem> INVAR_HELMET     = armor("invar_helmet",     EOArmorMaterials.INVAR, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> INVAR_CHESTPLATE = armor("invar_chestplate", EOArmorMaterials.INVAR, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> INVAR_LEGGINGS   = armor("invar_leggings",   EOArmorMaterials.INVAR, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> INVAR_BOOTS      = armor("invar_boots",      EOArmorMaterials.INVAR, ArmorItem.Type.BOOTS);


    // ================================================================
    // STEEL
    // ================================================================
    public static final DeferredItem<ArmorItem> STEEL_HELMET     = armor("steel_helmet",     EOArmorMaterials.STEEL, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> STEEL_CHESTPLATE = armor("steel_chestplate", EOArmorMaterials.STEEL, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> STEEL_LEGGINGS   = armor("steel_leggings",   EOArmorMaterials.STEEL, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> STEEL_BOOTS      = armor("steel_boots",      EOArmorMaterials.STEEL, ArmorItem.Type.BOOTS);

    // ================================================================
    // OSMIUM
    // ================================================================
    public static final DeferredItem<ArmorItem> OSMIUM_HELMET     = armor("osmium_helmet",     EOArmorMaterials.OSMIUM, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> OSMIUM_CHESTPLATE = armor("osmium_chestplate", EOArmorMaterials.OSMIUM, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> OSMIUM_LEGGINGS   = armor("osmium_leggings",   EOArmorMaterials.OSMIUM, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> OSMIUM_BOOTS      = armor("osmium_boots",      EOArmorMaterials.OSMIUM, ArmorItem.Type.BOOTS);

    // ================================================================
    // PLATINUM
    // ================================================================
    public static final DeferredItem<ArmorItem> PLATINUM_HELMET     = armor("platinum_helmet",     EOArmorMaterials.PLATINUM, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> PLATINUM_CHESTPLATE = armor("platinum_chestplate", EOArmorMaterials.PLATINUM, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> PLATINUM_LEGGINGS   = armor("platinum_leggings",   EOArmorMaterials.PLATINUM, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> PLATINUM_BOOTS      = armor("platinum_boots",      EOArmorMaterials.PLATINUM, ArmorItem.Type.BOOTS);

    // ================================================================
    // IRIDIUM
    // ================================================================
    public static final DeferredItem<ArmorItem> IRIDIUM_HELMET     = armor("iridium_helmet",     EOArmorMaterials.IRIDIUM, ArmorItem.Type.HELMET);
    public static final DeferredItem<ArmorItem> IRIDIUM_CHESTPLATE = armor("iridium_chestplate", EOArmorMaterials.IRIDIUM, ArmorItem.Type.CHESTPLATE);
    public static final DeferredItem<ArmorItem> IRIDIUM_LEGGINGS   = armor("iridium_leggings",   EOArmorMaterials.IRIDIUM, ArmorItem.Type.LEGGINGS);
    public static final DeferredItem<ArmorItem> IRIDIUM_BOOTS      = armor("iridium_boots",      EOArmorMaterials.IRIDIUM, ArmorItem.Type.BOOTS);
}
