package com.example.suitcase.screen;

import com.example.suitcase.entity.SuitcaseBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public class SuitcaseMenu extends AbstractContainerMenu {
    private final SuitcaseBlockEntity blockEntity;
    private final int containerRows = 6;
    private final int containerCols = 9;

    public SuitcaseMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, null);
    }

    public SuitcaseMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, null);
    }

    public SuitcaseMenu(int containerId, Inventory playerInventory, SuitcaseBlockEntity blockEntity) {
        super(null, containerId);
        this.blockEntity = blockEntity;

        if (blockEntity != null) {
            // Add suitcase inventory slots (6 rows x 9 cols = 54 slots)
            for (int row = 0; row < containerRows; row++) {
                for (int col = 0; col < containerCols; col++) {
                    addSlot(new SlotItemHandler(blockEntity.getItemHandler(), col + row * containerCols, 8 + col * 18, 18 + row * 18));
                }
            }
        }

        // Add player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 140 + row * 18));
            }
        }

        // Add player hotbar
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 198));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot.hasItem()) {
            ItemStack stackInSlot = slot.getItem();
            itemstack = stackInSlot.copy();

            if (index < containerRows * containerCols) {
                // From suitcase to player inventory
                if (!moveItemStackTo(stackInSlot, containerRows * containerCols, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // From player inventory to suitcase
                if (!moveItemStackTo(stackInSlot, 0, containerRows * containerCols, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity == null || !blockEntity.isRemoved();
    }
}
