package com.example.suitcase;

import com.example.suitcase.block.SuitcaseBlock;
import com.example.suitcase.block.SuitcaseBlockEntity;
import com.example.suitcase.client.ClientModEvents;
import com.example.suitcase.item.StickyNoteItem;
import com.example.suitcase.screen.SuitcaseMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@Mod("suitcase")
public class SuitcaseMod {
    public static final String MOD_ID = "suitcase";

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, MOD_ID);

    public static final Supplier<SuitcaseBlock> SUITCASE_BLOCK = BLOCKS.register("suitcase", SuitcaseBlock::new);
    public static final Supplier<Item> SUITCASE_ITEM = ITEMS.register("suitcase", () -> new BlockItem(SUITCASE_BLOCK.get(), new Item.Properties()));
    public static final Supplier<StickyNoteItem> STICKY_NOTE_ITEM = ITEMS.register("sticky_note", StickyNoteItem::new);

    public static final Supplier<BlockEntityType<SuitcaseBlockEntity>> SUITCASE_BLOCK_ENTITY = BLOCK_ENTITIES.register(
            "suitcase_block_entity",
            () -> BlockEntityType.Builder.of(SuitcaseBlockEntity::new, SUITCASE_BLOCK.get()).build(null)
    );

    public static final Supplier<MenuType<SuitcaseMenu>> SUITCASE_MENU = MENUS.register(
            "suitcase_menu",
            () -> IMenuTypeExtension.create((windowId, inv, data) -> new SuitcaseMenu(windowId, inv))
    );

    public SuitcaseMod(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        MENUS.register(modEventBus);
    }
}
