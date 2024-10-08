package dev.blend.command.api;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface CommandInfo {
    String[] names();
    String description();
}
