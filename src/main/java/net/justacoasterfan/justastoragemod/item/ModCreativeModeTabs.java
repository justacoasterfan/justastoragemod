package net.justacoasterfan.justastoragemod.item;

import net.justacoasterfan.justastoragemod.Justastoragemod;
import net.justacoasterfan.justastoragemod.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Justastoragemod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CREATIVE_MODE_TAB = CREATIVE_MODE_TABS.register("storage_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.STORAGE_REMOTE.get()))
                    .title(Component.translatable("creativetab.storage_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.STORAGE_REMOTE.get());

                        pOutput.accept(ModBlocks.STORAGE_CONTROLLER.get());
                        pOutput.accept(ModBlocks.STORAGE_CABLE.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
