package com.example.suitcase.entity;

import com.example.suitcase.SuitcaseMod;
import com.example.suitcase.screen.SuitcaseMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SuitcaseBlockEntity extends BlockEntity implements MenuProvider {
    private static final int INVENTORY_SIZE = 54;
    private final ItemStackHandler itemHandler = createHandler();
    private boolean hasStickyNote = false;
    private String customName = "";

    public SuitcaseBlockEntity(BlockPos pos, BlockState state) {
        super(SuitcaseMod.SUITCASE_BLOCK_ENTITY.get(), pos, state);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(INVENTORY_SIZE) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }
        };
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public boolean hasStickyNote() {
        return hasStickyNote;
    }

    public void setHasStickyNote(boolean hasStickyNote) {
        this.hasStickyNote = hasStickyNote;
        setChanged();
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
        setChanged();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.suitcase");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new SuitcaseMenu(containerId, playerInventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Items", itemHandler.serializeNBT(registries));
        tag.putBoolean("HasStickyNote", hasStickyNote);
        tag.putString("CustomName", customName);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("Items"));
        hasStickyNote = tag.getBoolean("HasStickyNote");
        customName = tag.getString("CustomName");
    }
}
