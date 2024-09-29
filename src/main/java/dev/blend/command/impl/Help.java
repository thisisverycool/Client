package dev.blend.command.impl;

import dev.blend.Client;
import dev.blend.command.Command;
import dev.blend.command.api.CommandInfo;
import dev.blend.util.player.ChatUtil;
import net.minecraft.util.Formatting;

@CommandInfo(
        names = {"help", "h", "?", "wtf"},
        description = "Helps users with \".commands\""
)
public class Help extends Command {
    @Override
    public void execute(String[] args) {
        ChatUtil.addChatMessageI("All commands available in the client: ");
        for (Command c : Client.instance.getCommandManager().getCommands()) {
            StringBuilder yay = new StringBuilder();
            for (String fr : c.getCommandInfo().names()) {
                yay.append(fr);
                if (!fr.equalsIgnoreCase(c.getCommandInfo().names()[c.getCommandInfo().names().length - 1])) {
                    yay.append(", ");
                }
            }
            ChatUtil.addChatMessageI(yay.toString() + Formatting.AQUA + " » " + Formatting.RESET + c.getCommandInfo().description());
        }
    }
}
