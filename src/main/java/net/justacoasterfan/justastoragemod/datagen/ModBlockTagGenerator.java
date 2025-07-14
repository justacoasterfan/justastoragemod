package net.justacoasterfan.justastoragemod.datagen;

import net.justacoasterfan.justastoragemod.Justastoragemod;
import net.justacoasterfan.justastoragemod.block.ModBlocks;
import net.justacoasterfan.justastoragemod.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Justastoragemod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.STORAGE_CONTROLLER.get(),
                (ModBlocks.STORAGE_CABLE.get()));
    }
}