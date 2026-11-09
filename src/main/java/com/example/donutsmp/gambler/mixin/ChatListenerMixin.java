package com.donutsmp.gambler.mixin;

import com.donutsmp.gambler.DonutGamblerMod;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(ChatHud.class)
public class ChatListenerMixin {
    private static final Pattern PAY_OUT_PATTERN = Pattern.compile("You paid (\\w+) \\$(\\d+[,\\d+]*)");
    private static final Pattern PAY_IN_PATTERN = Pattern.compile("You received \\$(\\d+[,\\d+]*) from (\\w+)");

    @Inject(method = "logChatMessage", at = @At("HEAD"))
    private void onIncomingChatMessage(Component message, CallbackInfo ci) {
        if (message == null) return;
        String rawText = message.getString();

        Matcher outMatcher = PAY_OUT_PATTERN.matcher(rawText);
        if (outMatcher.find()) {
            try {
                long amount = Long.parseLong(outMatcher.group(2).replace(",", ""));
                DonutGamblerMod.sessionProfit -= amount;
            } catch (NumberFormatException e) {}
            return;
        }

        Matcher inMatcher = PAY_IN_PATTERN.matcher(rawText);
        if (inMatcher.find()) {
            try {
                long amount = Long.parseLong(inMatcher.group(1).replace(",", ""));
                DonutGamblerMod.sessionProfit += amount;
            } catch (NumberFormatException e) {}
        }
    }
}
