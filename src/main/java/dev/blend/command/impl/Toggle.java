package dev.blend.command.impl;

import dev.blend.Client;
import dev.blend.command.Command;
import dev.blend.command.api.CommandInfo;
import dev.blend.module.Module;
import dev.blend.util.player.ChatUtil;
import net.minecraft.util.Formatting;

@CommandInfo(
        names = {"t", "toggle"},
        description = "Toggle modules using chat."
)
public class Toggle extends Command {
    @Override
    public void execute(String[] args) {
        if (args.length > 1) {
            Module module = Client.instance.getModuleManager().getModule(args[1]);
            if (module != null) {
                module.toggle();
                ChatUtil.addChatMessageI("Module " + args[1] + (module.isEnabled() ? Formatting.GREEN + " Enabled" : Formatting.RED + " Disabled"));
            } else {
                ChatUtil.addChatMessageI("Module " + args[1] + " not found. Try doing .list to list all the modules.");
            }
        } else {
            ChatUtil.addChatMessageW("Syntax: .toggle <Module>");
        }
    }
}

