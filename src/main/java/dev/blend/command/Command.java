package dev.blend.command;

import dev.blend.command.api.CommandInfo;
import dev.blend.util.MC;
import lombok.Getter;

@Getter
public abstract class Command implements MC {

    private final CommandInfo commandInfo;

    public Command() {
        if (this.getClass().isAnnotationPresent(CommandInfo.class)) {
            this.commandInfo = this.getClass().getAnnotation(CommandInfo.class);
        } else {
            throw new IllegalStateException("@CommandInfo not found on " + this.getClass().getSimpleName());
        }
    }

    public abstract void execute(final String[] args);

}
