package net.justacoasterfan.justastoragemod.item;

import net.justacoasterfan.justastoragemod.Justastoragemod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Justastoragemod.MOD_ID);

    public static final RegistryObject<Item> STORAGE_REMOTE = ITEMS.register("storage_remote",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
