package com.donutsmp.gambler;

import com.donutsmp.gambler.hud.GamblerHudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class DonutGamblerMod implements ClientModInitializer {
    public static final String MOD_ID = "donutgambler";
    public static long sessionProfit = 0;

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new GamblerHudOverlay());
    }
}
