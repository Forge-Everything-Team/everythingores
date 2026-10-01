package dev.forgeeverything.everythingores.registry;

import dev.forgeeverything.everythingores.EverythingOres;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Registers every ArmorMaterial owned by Everything Ores.
 *
 * Tier overview (defense sum / toughness / enchantability):
 *   Leather=7/0/15  Gold=12/0/25  Iron=15/0/9  Diamond=20/2/10  Netherite=20/3/15 *
 *   Bronze       13/0.0/12  — early game, just under iron
 *   Silver       15/0.0/25  — iron protection, gold-like enchantability
 *   Invar        15/1.0/10  — iron++ tier
 *   Steel        19/1.5/9   — pre-diamond workhorse
 *   Osmium       19/2.0/9   — heavy pre-diamond
 *   Platinum     20/2.0/18  — diamond-tier, high enchantability
 *   Iridium      20/3.0/15  — near-netherite, high enchant + KBR
 */
public class EOArmorMaterials {

    /** An empty ingredient when the repair material was disabled and never registered. */
    private static Ingredient repair(DeferredItem<Item> item) {
        return item == null ? Ingredient.of() : Ingredient.of(item.get());
    }

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, EverythingOres.MOD_ID);

    // ── Helper ────────────────────────────────────────────────────────────────

    private static Holder<ArmorMaterial> register(
            String name,
            int helmet, int chestplate, int leggings, int boots,
            int enchantability,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repair) {
        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
                Map.of(
                        ArmorItem.Type.HELMET,     helmet,
                        ArmorItem.Type.CHESTPLATE, chestplate,
                        ArmorItem.Type.LEGGINGS,   leggings,
                        ArmorItem.Type.BOOTS,      boots
                ),
                enchantability,
                SoundEvents.ARMOR_EQUIP_IRON,
                repair,
                List.of(new ArmorMaterial.Layer(
                        ResourceLocation.fromNamespaceAndPath(EverythingOres.MOD_ID, name))),
                toughness,
                knockbackResistance
        ));
    }

    // ================================================================
    // EARLY GAME
    // ================================================================

    public static final Holder<ArmorMaterial> BRONZE = register(
            "bronze", 2, 5, 4, 2, 12, 0.0f, 0.0f,
            () -> repair(EOItems.BRONZE_INGOT));

    // ================================================================
    // MID GAME — Iron tier and adjacent
    // ================================================================

    /** Iron-level protection with no toughness, traded for gold-like enchantability. */
    public static final Holder<ArmorMaterial> SILVER = register(
            "silver", 2, 6, 5, 2, 25, 0.0f, 0.0f,
            () -> repair(EOItems.SILVER_INGOT));

    public static final Holder<ArmorMaterial> INVAR = register(
            "invar", 2, 6, 5, 2, 10, 1.0f, 0.0f,
            () -> repair(EOItems.INVAR_INGOT));

    // ================================================================
    // LATE GAME — Diamond tier and approaching Netherite
    // ================================================================

    public static final Holder<ArmorMaterial> STEEL = register(
            "steel", 3, 7, 6, 3, 9, 1.5f, 0.0f,
            () -> repair(EOItems.STEEL_INGOT));

    /** Same plating as steel but noticeably tougher; still a poor enchanting metal. */
    public static final Holder<ArmorMaterial> OSMIUM = register(
            "osmium", 3, 7, 6, 3, 9, 2.0f, 0.0f,
            () -> repair(EOItems.OSMIUM_INGOT));

    public static final Holder<ArmorMaterial> PLATINUM = register(
            "platinum", 3, 8, 6, 3, 18, 2.0f, 0.0f,
            () -> repair(EOItems.PLATINUM_INGOT));

    public static final Holder<ArmorMaterial> IRIDIUM = register(
            "iridium", 3, 8, 6, 3, 15, 3.0f, 0.1f,
            () -> repair(EOItems.IRIDIUM_INGOT));
}
