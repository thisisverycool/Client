package dev.blend.util.player;

import dev.blend.Client;
import dev.blend.util.MC;
import lombok.experimental.UtilityClass;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;

@UtilityClass
public class ChatUtil implements MC {

    public void addChatMessageI(final String message) {
        addChatMessage(message, Formatting.AQUA);
    }
    public void addChatMessageE(final String message) {
        addChatMessage(message, Formatting.RED);
    }
    public void addChatMessageW(final String message) {
        addChatMessage(message, Formatting.GOLD);
    }

    public void addChatMessage(final String message, final Formatting formatting) {
        Text content = Text.empty().append(formatting + Client.name + Formatting.GREEN + " » " + Formatting.RESET + message);
        mc.inGameHud.getChatHud().addMessage(content);
    }

}
