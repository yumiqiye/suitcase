package com.example.suitcase.screen;

import com.example.suitcase.SuitcaseMod;
import com.example.suitcase.entity.SuitcaseBlockEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SuitcaseScreen extends AbstractContainerScreen<SuitcaseMenu> {
    private static final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath(SuitcaseMod.MOD_ID, "textures/gui/suitcase.png");
    
    private final SuitcaseBlockEntity blockEntity;
    private EditBox nameEditBox;
    private Button editButton;

    public SuitcaseScreen(SuitcaseMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.blockEntity = menu.blockEntity != null ? menu.blockEntity : null;
        this.imageWidth = 176;
        this.imageHeight = 222;
    }

    @Override
    protected void init() {
        super.init();
        
        // Create edit button for sticky note name editing
        int buttonX = leftPos + imageWidth / 2 - 40;
        int buttonY = topPos - 25;
        
        editButton = Button.builder(Component.literal("✏️"), btn -> openNameEditor())
                .pos(buttonX, buttonY)
                .size(80, 20)
                .build();
        
        if (blockEntity != null && blockEntity.hasStickyNote()) {
            addRenderableWidget(editButton);
        }
    }

    private void openNameEditor() {
        if (nameEditBox == null) {
            int editBoxX = leftPos + imageWidth / 2 - 50;
            int editBoxY = topPos - 45;
            
            nameEditBox = new EditBox(font, editBoxX, editBoxY, 100, 20, Component.literal("Name"));
            nameEditBox.setValue(blockEntity != null ? blockEntity.getCustomName() : "");
            nameEditBox.setResponder(this::updateName);
            nameEditBox.setFocused(true);
            addRenderableWidget(nameEditBox);
            
            // Remove edit button while editing
            removeWidget(editButton);
        }
    }

    private void updateName(String newName) {
        if (blockEntity != null) {
            // Send packet to server to update name
            // For now, we'll just set it locally (in a real mod, you'd send a packet)
            blockEntity.setCustomName(newName);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        guiGraphics.blit(BACKGROUND_LOCATION, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int x = (imageWidth - font.width(title.getString())) / 2;
        guiGraphics.drawString(font, title.getString(), x, 6, 0x404040, false);
        
        // Render custom name on sticky note if present
        if (blockEntity != null && blockEntity.hasStickyNote() && !blockEntity.getCustomName().isEmpty()) {
            String displayName = blockEntity.getCustomName();
            guiGraphics.drawString(font, displayName, 8, 8, 0x333333, false);
        }
        
        guiGraphics.drawString(font, playerInventoryTitle.getString(), 8, imageHeight - 96 + 4, 0x404040, false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (nameEditBox != null && nameEditBox.isFocused()) {
            if (keyCode == 256) { // ESC key
                finishEditing();
                return true;
            }
            return nameEditBox.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (nameEditBox != null && nameEditBox.isFocused()) {
            if (!nameEditBox.isMouseOver(mouseX - leftPos, mouseY - topPos)) {
                finishEditing();
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void finishEditing() {
        if (nameEditBox != null) {
            removeWidget(nameEditBox);
            nameEditBox = null;
            
            // Re-add edit button
            int buttonX = leftPos + imageWidth / 2 - 40;
            int buttonY = topPos - 25;
            
            editButton = Button.builder(Component.literal("✏️"), btn -> openNameEditor())
                    .pos(buttonX, buttonY)
                    .size(80, 20)
                    .build();
            addRenderableWidget(editButton);
        }
    }
}
