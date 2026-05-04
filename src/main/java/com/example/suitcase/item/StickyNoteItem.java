package com.example.suitcase.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class StickyNoteItem extends Item {
    public StickyNoteItem() {
        super(new Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        // The actual sticky note application logic is handled in the block
        return InteractionResult.CONSUME;
    }
}
