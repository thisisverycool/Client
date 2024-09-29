package dev.blend.command.impl;

import dev.blend.Client;
import dev.blend.command.Command;
import dev.blend.command.api.CommandInfo;
import dev.blend.util.player.ChatUtil;
import net.minecraft.util.Formatting;

@CommandInfo(
        names = {"c", "config"},
        description = "Saves client settings to a file to be shared/reused."
)
public class Config extends Command {
    @Override
    public void execute(String[] args) {
        if (args.length > 1) {
            if (
                    args[1].equalsIgnoreCase("save")
            ) {
                if (args.length > 2) {
                    Client.instance.getConfigManager().save(args[2]);
                    ChatUtil.addChatMessageI("Saved config as \"" + args[2] +"\".");
                } else {
                    Client.instance.getConfigManager().save("default");
                    ChatUtil.addChatMessageI("Saved config as \"default\".");
                }
                return;
            } else if (
                    args[1].equalsIgnoreCase("load")
            ) {
                if (args.length > 2) {
                    if (Client.instance.getConfigManager().load(args[2])) {
                        ChatUtil.addChatMessageI("Loaded config " + Formatting.GREEN + args[2] + Formatting.RESET + ".");
                    } else {
                        ChatUtil.addChatMessageI("Config " + Formatting.RED + args[2] + Formatting.RESET + " not found.");
                    }
                } else {
                    if (Client.instance.getConfigManager().load("default")) {
                        ChatUtil.addChatMessageI("Loaded config " + Formatting.GREEN + "default" + Formatting.RESET + ".");
                    } else {
                        ChatUtil.addChatMessageI("Config " + Formatting.RED + "default" + Formatting.RESET + " not found.");
                    }
                }
                return;
            } else if (
                    args[1].equalsIgnoreCase("folder") || args[1].equalsIgnoreCase("open")
            ) {
                if (Client.instance.getConfigManager().openFolder()) {
                    ChatUtil.addChatMessageI(Formatting.GREEN + "Done! " + Formatting.RESET + "Opened config folder");
                } else {
                    ChatUtil.addChatMessageE(Formatting.RED + "ERROR! " + Formatting.RESET + "Could not open config folder");
                }
                return;
            }
        }
        ChatUtil.addChatMessageW("Syntax: .config <save/load> <config name>");
    }
}

