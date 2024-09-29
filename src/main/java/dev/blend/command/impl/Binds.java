package dev.blend.command.impl;

import dev.blend.Client;
import dev.blend.command.Command;
import dev.blend.command.api.CommandInfo;
import dev.blend.module.Module;
import dev.blend.util.misc.MiscUtil;
import dev.blend.util.player.ChatUtil;
import org.lwjgl.glfw.GLFW;

@CommandInfo(
        names = {"binds", "keys"},
        description = "List all modules that are bound."
)
public class Binds extends Command {
    @Override
    public void execute(String[] args) {
        ChatUtil.addChatMessageI("All modules with binds");
        for (Module m : Client.instance.getModuleManager().getModules()) {
            if (m.getKey() != GLFW.GLFW_KEY_UNKNOWN) {
                ChatUtil.addChatMessageI(m.getName() + " : " + MiscUtil.getNameFromKey(m.getKey()));
            }
        }
    }
}

