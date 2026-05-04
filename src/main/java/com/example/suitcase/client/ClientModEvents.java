package com.example.suitcase.client;

import com.example.suitcase.SuitcaseMod;
import com.example.suitcase.block.SuitcaseBlock;
import com.example.suitcase.entity.SuitcaseBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = SuitcaseMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(SuitcaseMod.SUITCASE_BLOCK_ENTITY.get(), context -> new SuitcaseBlockEntityRenderer());
    }
}
