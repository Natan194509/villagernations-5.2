package com.example.villagernations.client;

import com.example.villagernations.NationsMod;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/** Abre o painel quando o jogador aperta a tecla. */
@EventBusSubscriber(modid = NationsMod.MODID, value = Dist.CLIENT)
public final class ClientGameEvents {
    private ClientGameEvents() {}

    @SubscribeEvent
    public static void tick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        while (ClientModEvents.ABRIR_PAINEL.consumeClick()) {
            if (mc.player != null && mc.screen == null && ClientNationState.ativo()) {
                mc.setScreen(new NationPanelScreen());
            }
        }
    }
}
