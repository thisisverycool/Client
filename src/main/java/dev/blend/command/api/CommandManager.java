package dev.blend.command.api;

import dev.blend.Client;
import dev.blend.command.Command;
import dev.blend.command.impl.*;
import dev.blend.event.impl.player.ChatSendEvent;
import dev.blend.util.MC;
import dev.blend.util.player.ChatUtil;
import lombok.Getter;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Getter
public class CommandManager implements MC {

    private final List<Command> commands = new ArrayList<>();

    public CommandManager() {
        commands.addAll(Arrays.asList(
                new Help(),
                new Bind(),
                new Config(),
                new Binds(),
                new ListCommand(),
                new Say(),
                new Toggle()
        ));
        commands.sort(Comparator.comparing(c -> c.getCommandInfo().names()[0]));
        Client.instance.getEventBus().register(this);
    }

    @Subscribe
    public void onChatSend(ChatSendEvent event) {
        if (event.getMessage() != null && !event.getMessage().isEmpty() && event.getMessage().startsWith(".") && event.getMessage().length() > 1 && !event.getMessage().startsWith("..")) {
            event.cancel();
            final String string = event.getMessage().substring(1);
            final String[] args = string.split(" ");
            for (Command c : commands) {
                for (String s : c.getCommandInfo().names()) {
                    if (s.equalsIgnoreCase(args[0])) {
                        c.execute(args);
                        return;
                    }
                }
            }
            ChatUtil.addChatMessageI("Command NOT found.");
        }
    }

}
