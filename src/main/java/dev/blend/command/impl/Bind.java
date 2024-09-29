package dev.blend.command.impl;

import dev.blend.Client;
import dev.blend.command.Command;
import dev.blend.command.api.CommandInfo;
import dev.blend.module.Module;
import dev.blend.util.misc.MiscUtil;
import dev.blend.util.player.ChatUtil;
import org.lwjgl.glfw.GLFW;

@CommandInfo(
        names = {"bind", "b", "setkey"},
        description = "Bind a module to a key."
)
public class Bind extends Command {

    @Override
    public void execute(String[] args) {
        if (args.length > 1) {
            if (args[1].equalsIgnoreCase("list")) {
                ChatUtil.addChatMessageI("All modules with binds");
                for (Module m : Client.instance.getModuleManager().getModules()) {
                    if (m.getKey() != GLFW.GLFW_KEY_UNKNOWN) {
                        ChatUtil.addChatMessageI(m.getName() + " : " + MiscUtil.getNameFromKey(m.getKey()));
                    }
                }
                return;
            }
            for (Module m : Client.instance.getModuleManager().getModules()) {
                for (String s : m.getModuleInfo().names()) {
                    if (args[1].equalsIgnoreCase(s)) {
                        if (args.length > 2) {
                            m.setKey(MiscUtil.getKeyFromName(args[2]));
                            ChatUtil.addChatMessageI("Bound " + s + " to " + MiscUtil.getNameFromKey(m.getKey()));
                        } else {
                            ChatUtil.addChatMessageI("Syntax: .bind <Module> <key>");
                        }
                        return;
                    }
                }
            }
            ChatUtil.addChatMessageI("Module " + args[1] + " not found. Try doing .list to list all the modules.");
        } else {
            ChatUtil.addChatMessageI("Syntax: .bind <Module> <key>");
        }
    }

}