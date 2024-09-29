package dev.blend.command.impl;

import dev.blend.command.Command;
import dev.blend.command.api.CommandInfo;
import dev.blend.util.player.ChatUtil;
import net.minecraft.text.Text;

@CommandInfo(
        names = {"say", "s"},
        description = "Say it."
)
public class Say extends Command {
    @Override
    public void execute(String[] args) {
        StringBuilder builder = new StringBuilder();
        boolean skip = true;
        for (String string : args) {
            if (!skip) {
                builder.append(string).append(" ");
            }
            skip = false;
        }
        if (mc.player != null) {
            mc.player.sendMessage(Text.of(builder.toString()));
        } else {
            ChatUtil.addChatMessageE("Couldn't execute \".say\"");
        }
    }
}

