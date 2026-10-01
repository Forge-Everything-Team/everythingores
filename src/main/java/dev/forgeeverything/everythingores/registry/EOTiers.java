package dev.forgeeverything.everythingores.registry;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * Defines every custom Tier (tool material) owned by Everything Ores.
 *
 * NeoForge 1.21.1 removed TierSortingRegistry and SimpleTier — tiers are
 * now plain implementations of the vanilla Tier interface. The mining level
 * is determined entirely by getIncorrectBlocksForDrops():
 *
 *   NEEDS_IRON_TOOL              → stone-equivalent  (can't correctly mine iron ore)
 *   NEEDS_DIAMOND_TOOL           → iron-equivalent   (can't correctly mine obsidian)
 *   INCORRECT_FOR_DIAMOND_TOOL   → diamond-equivalent (can mine everything diamond can)
 *   INCORRECT_FOR_NETHERITE_TOOL → netherite-equivalent (can mine everything)
 *
 * Tier ordering (weakest → strongest):
 *   BRONZE → ALUMINUM → NICKEL → SILVER → INVAR
 *   → CONSTANTAN → BISMUTH → STEEL → STAINLESS_STEEL → OSMIUM
 *   → CHROMIUM → PLATINUM → TUNGSTEN → IRIDIUM
 */
public class EOTiers {

    /** An empty ingredient when the repair material was disabled and never registered. */
    private static Ingredient repair(DeferredItem<Item> item) {
        return item == null ? Ingredient.of() : Ingredient.of(item.get());
    }

    // ── Helper — creates a Tier using pure vanilla interface ──────────────────

    private static Tier of(
            int uses, float speed, float attackDamageBonus,
            TagKey<Block> incorrectBlocksForDrops,
            int enchantmentValue,
            Supplier<Ingredient> repairIngredient) {
        return new Tier() {
            @Override public int getUses()                          { return uses; }
            @Override public float getSpeed()                       { return speed; }
            @Override public float getAttackDamageBonus()           { return attackDamageBonus; }
            @Override public TagKey<Block> getIncorrectBlocksForDrops() { return incorrectBlocksForDrops; }
            @Override public int getEnchantmentValue()              { return enchantmentValue; }
            @Override public Ingredient getRepairIngredient()       { return repairIngredient.get(); }
        };
    }

    // ================================================================
    // EARLY GAME  (stone-equivalent mining level)
    // ================================================================

    /** Cu+Sn alloy. Between stone and iron. Classic early tool metal. */
    public static final Tier BRONZE = of(
            300, 5.5f, 1.5f, BlockTags.NEEDS_IRON_TOOL, 12,
            () -> repair(EOItems.BRONZE_INGOT));

    /** Light, fast. Same mining level as stone despite good speed. */
    public static final Tier ALUMINUM = of(
            250, 7.0f, 1.0f, BlockTags.NEEDS_IRON_TOOL, 14,
            () -> repair(EOItems.ALUMINUM_INGOT));

    // ================================================================
    // MID GAME  (iron-equivalent mining level)
    // ================================================================

    /** Iron-tier mining. Solid middle-ground. */
    public static final Tier NICKEL = of(
            350, 6.0f, 2.0f, BlockTags.NEEDS_DIAMOND_TOOL, 9,
            () -> repair(EOItems.NICKEL_INGOT));

    /** High enchantability at iron mining level. */
    public static final Tier SILVER = of(
            400, 6.5f, 2.0f, BlockTags.NEEDS_DIAMOND_TOOL, 25,
            () -> repair(EOItems.SILVER_INGOT));

    /** Fe+Ni alloy. Iron++ in all stats. */
    public static final Tier INVAR = of(
            500, 6.5f, 2.5f, BlockTags.NEEDS_DIAMOND_TOOL, 10,
            () -> repair(EOItems.INVAR_INGOT));

    /** Cu+Ni alloy. Slightly lower enchantability than invar. */
    public static final Tier CONSTANTAN = of(
            450, 6.5f, 2.5f, BlockTags.NEEDS_DIAMOND_TOOL, 8,
            () -> repair(EOItems.CONSTANTAN_INGOT));

    /** Decent enchantability, iron mining level. */
    public static final Tier BISMUTH = of(
            450, 6.5f, 2.5f, BlockTags.NEEDS_DIAMOND_TOOL, 15,
            () -> repair(EOItems.BISMUTH_INGOT));

    // ================================================================
    // LATE GAME  (diamond-equivalent mining level)
    // ================================================================

    /** Fe alloy. Diamond-tier mining, lower durability than diamond. */
    public static final Tier STEEL = of(
            750, 7.0f, 2.5f, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 9,
            () -> repair(EOItems.STEEL_INGOT));

    /** Fe+Cr alloy. Marginally better than steel. */
    public static final Tier STAINLESS_STEEL = of(
            800, 7.5f, 2.5f, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 10,
            () -> repair(EOItems.STAINLESS_STEEL_INGOT));

    /** Dense and hard-wearing. Hits harder than the steels, but barely takes an enchantment. */
    public static final Tier OSMIUM = of(
            1000, 8.0f, 3.0f, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 9,
            () -> repair(EOItems.OSMIUM_INGOT));

    /** Diamond-tier with high enchantability. */
    public static final Tier PLATINUM = of(
            1400, 8.5f, 3.0f, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 18,
            () -> repair(EOItems.PLATINUM_INGOT));

    // ================================================================
    // END GAME  (netherite-equivalent mining level)
    // ================================================================

    /** Very slow but hits harder than netherite. High toughness via armor. */
    public static final Tier TUNGSTEN = of(
            1800, 7.0f, 4.0f, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 7,
            () -> repair(EOItems.TUNGSTEN_INGOT));

    /** Netherite-tier speed and damage, significantly better enchantability. */
    public static final Tier IRIDIUM = of(
            2000, 9.0f, 4.0f, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 15,
            () -> repair(EOItems.IRIDIUM_INGOT));

		// ================================================================
		// Everything Ores exclusive tool material
		// ================================================================

		/** Nemonium tool material. */
		public static final Tier NEMONIUM = of(
				3000, 9.0f, 5.0f, BlockTags.INCORRECT_FOR_NETHERITE_TOOL, 20,
				() -> repair(EOItems.NEMONIUM_INGOT));
}
