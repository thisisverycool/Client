package dev.blend.command.impl;

import dev.blend.Client;
import dev.blend.command.Command;
import dev.blend.command.api.CommandInfo;
import dev.blend.module.Module;
import dev.blend.util.player.ChatUtil;

@CommandInfo(
        names = {"l", "list"},
        description = "List all registered modules."
)
public class ListCommand extends Command {
    @Override
    public void execute(String[] args) {
        ChatUtil.addChatMessageI("All modules: ");
        for (Module m : Client.instance.getModuleManager().getModules()) {
            ChatUtil.addChatMessageI(m.getName());
        }
    }
}
