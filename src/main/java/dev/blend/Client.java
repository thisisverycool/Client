package dev.blend;

import dev.blend.command.api.CommandManager;
import dev.blend.config.ConfigManager;
import dev.blend.module.api.ModuleManager;
import lombok.Getter;
import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class Client {

    public static final Client instance = new Client();
    public static final String name = "Blend", version = "5.0";
    public static final Logger logger = LoggerFactory.getLogger(name);
    private EventBus eventBus;
    private ModuleManager moduleManager;
    private CommandManager commandManager;
    private ConfigManager configManager;

    public void init() {
        logger.info("Initializing " + name + " v" + version);
        eventBus = EventBus.builder()
                .sendNoSubscriberEvent(false)
                .logNoSubscriberMessages(false)
                .sendSubscriberExceptionEvent(true)
                .build();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        configManager = new ConfigManager();
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown, "shutdown"));
    }

    private void shutdown() {

    }

}
