package dev.forgeeverything.everythingores.registry;

import dev.forgeeverything.everythingores.EverythingOres;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Consumer;

/**
 * The five registry entries every NeoForge fluid needs, registered together:
 *   1. FluidType        — render properties, physics constants (density/viscosity)
 *   2. Source fluid     — the "still" form placed by buckets
 *   3. Flowing fluid    — the spreading form
 *   4. LiquidBlock      — the in-world block form (registered in EOBlocks.BLOCKS)
 *   5. BucketItem       — filled bucket (registered in EOItems.ITEMS)
 *
 * Textures are read from textures/fluid/{name}_still, _flowing and _overlay,
 * and the bucket model from models/item/{name}_bucket.
 *
 * Source and Flowing need the BaseFlowingFluid.Properties, which in turn need
 * the Source and Flowing holders. The lambdas below read {@link #properties}
 * when RegisterEvent invokes them, by which point the constructor has set it.
 */
public final class EOFluidSet {

    private final DeferredHolder<FluidType, FluidType> type;
    private final DeferredHolder<Fluid, FlowingFluid> source;
    private final DeferredHolder<Fluid, FlowingFluid> flowing;
    private final DeferredBlock<LiquidBlock> block;
    private final DeferredItem<BucketItem> bucket;
    // Not final: the Source/Flowing lambdas capture it before the constructor assigns it.
    private BaseFlowingFluid.Properties properties;

    /**
     * @param tint          ARGB colour multiplied over the fluid textures
     * @param levelDecrease how quickly the fluid thins out as it flows (1 = like water, 2 = like lava)
     */
    EOFluidSet(String name, FluidType.Properties typeProperties, int tint, MapColor mapColor, int levelDecrease) {
        ResourceLocation still = ResourceLocation.fromNamespaceAndPath(EverythingOres.MOD_ID, "fluid/" + name + "_still");
        ResourceLocation flow = ResourceLocation.fromNamespaceAndPath(EverythingOres.MOD_ID, "fluid/" + name + "_flowing");
        ResourceLocation overlay = ResourceLocation.fromNamespaceAndPath(EverythingOres.MOD_ID, "fluid/" + name + "_overlay");

        this.type = EOFluids.FLUID_TYPES.register(name, () -> new FluidType(typeProperties) {
            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                consumer.accept(new IClientFluidTypeExtensions() {
                    @Override public ResourceLocation getStillTexture()   { return still; }
                    @Override public ResourceLocation getFlowingTexture() { return flow; }
                    @Override public ResourceLocation getOverlayTexture() { return overlay; }
                    @Override public int getTintColor() { return tint; }
                });
            }
        });

        this.source = EOFluids.FLUIDS.register(name,
                () -> new BaseFlowingFluid.Source(this.properties));
        this.flowing = EOFluids.FLUIDS.register(name + "_flowing",
                () -> new BaseFlowingFluid.Flowing(this.properties));

        this.block = EOBlocks.BLOCKS.register(name,
                () -> new LiquidBlock(this.source.get(),
                        BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
                                .mapColor(mapColor)
                                .noLootTable()));

        this.bucket = EOItems.ITEMS.register(name + "_bucket",
                () -> new BucketItem(this.source.get(),
                        new Item.Properties()
                                .stacksTo(1)
                                .craftRemainder(Items.BUCKET)));

        this.properties = new BaseFlowingFluid.Properties(type, source, flowing)
                .slopeFindDistance(4)
                .levelDecreasePerBlock(levelDecrease)
                .block(block)
                .bucket(bucket);
    }

    public DeferredHolder<FluidType, FluidType> type()    { return type; }
    public DeferredHolder<Fluid, FlowingFluid>  source()  { return source; }
    public DeferredHolder<Fluid, FlowingFluid>  flowing() { return flowing; }
    public DeferredBlock<LiquidBlock>           block()   { return block; }
    public DeferredItem<BucketItem>             bucket()  { return bucket; }
}
