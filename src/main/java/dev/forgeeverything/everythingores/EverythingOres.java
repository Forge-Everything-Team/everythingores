package dev.forgeeverything.everythingores;

import dev.forgeeverything.everythingores.registry.EOArmor;
import dev.forgeeverything.everythingores.registry.EOArmorMaterials;
import dev.forgeeverything.everythingores.registry.EOBlocks;
import dev.forgeeverything.everythingores.registry.EOCreativeTab;
import dev.forgeeverything.everythingores.config.EOConfig;
import dev.forgeeverything.everythingores.registry.EOConditions;
import dev.forgeeverything.everythingores.registry.EOFeatures;
import dev.forgeeverything.everythingores.registry.EOPlacements;
import dev.forgeeverything.everythingores.registry.EOFluids;
import dev.forgeeverything.everythingores.registry.EOItems;
import dev.forgeeverything.everythingores.registry.EOTools;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import dev.forgeeverything.everythingores.compat.SulfurCavesRegion;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

@Mod(EverythingOres.MOD_ID)
public class EverythingOres {

    public static final String MOD_ID = "everythingores";
    public static final Logger LOGGER = LogUtils.getLogger();

    public EverythingOres(IEventBus modEventBus, ModContainer modContainer) {
        // STARTUP so the material switches are readable while recipe conditions
        // are evaluated during datapack load.
        modContainer.registerConfig(ModConfig.Type.STARTUP, EOConfig.SPEC);

        // FluidTypes must register before Fluids — the FluidType Holder is
        // referenced inside BaseFlowingFluid.Properties.
				// best practice is to register fluid types and fluids before any other content,
				// so that they are available for use in blocks and items.
        EOFluids.FLUID_TYPES.register(modEventBus);
        EOFluids.FLUIDS.register(modEventBus);

        // ArmorMaterials before EOArmor — EOArmor fields reference these Holders.
        EOArmorMaterials.ARMOR_MATERIALS.register(modEventBus);

        // Calling .register() forces class init, guaranteeing all registrations
        // happen inside the RegisterEvent window.
        EOBlocks.BLOCKS.register(modEventBus);
        EOItems.ITEMS.register(modEventBus);
        EOArmor.ITEMS.register(modEventBus);
        EOTools.ITEMS.register(modEventBus);

        // Worldgen feature types must exist before datapack features referencing
        // everythingores:crude_oil_geyser are parsed.
        EOFeatures.FEATURES.register(modEventBus);

        // config_gate placement drops veins whose material is switched off;
        // material_enabled guards the recipes for the same materials.
        EOPlacements.PLACEMENT_MODIFIERS.register(modEventBus);
        EOConditions.CONDITIONS.register(modEventBus);

        EOCreativeTab.TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        LOGGER.info("Everything Ores initialised — {} owns the veins now.", MOD_ID);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // The sulfur caves biome is placed through a TerraBlender region. The
        // region class is only touched when TerraBlender is present, so the
        // mod still loads without it.
        if (!EOConfig.isSulfurCavesEnabled()) return;
        for (String material : EOConfig.overriddenCaveMaterials()) {
            LOGGER.warn("[materials.{}] enabled = false is being ignored - [sulfur_caves] is on and needs {}."
                    + " Turn sulfur_caves off to disable {}.", material, material, material);
        }
        if (ModList.get().isLoaded("terrablender")) {
            event.enqueueWork(SulfurCavesRegion::register);
        } else {
            LOGGER.warn("[sulfur_caves] is on but TerraBlender is not installed - the sulfur caves biome"
                    + " will not generate. Install TerraBlender, or turn sulfur_caves off.");
        }
    }

}
