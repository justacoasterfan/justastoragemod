package net.justacoasterfan.justastoragemod.block;

import net.justacoasterfan.justastoragemod.Justastoragemod;
import net.justacoasterfan.justastoragemod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Justastoragemod.MOD_ID);

    public static final RegistryObject<Block> STORAGE_CABLE = registerBlock("storage_cable",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));

    public static final RegistryObject<Block> STORAGE_CONTROLLER = registerBlock("storage_controller",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> blockObj = BLOCKS.register(name, block);
        registerBlockItem(name, blockObj);
        return blockObj;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
