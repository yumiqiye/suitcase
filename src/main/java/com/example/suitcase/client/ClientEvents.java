package com.example.suitcase.client;

import com.example.suitcase.SuitcaseMod;
import com.example.suitcase.screen.SuitcaseMenu;
import com.example.suitcase.screen.SuitcaseScreen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = SuitcaseMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ClientEvents {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(SuitcaseMod.SUITCASE_MENU.get(), SuitcaseScreen::new);
    }
}
