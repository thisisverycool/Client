package dev.blend.module.api;

import org.lwjgl.glfw.GLFW;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
public @interface ModuleInfo {
    String[] names();
    String description();
    Category category();
    boolean canBeEnabled() default true;
    boolean defaultEnabled() default false;
    int defaultKey() default GLFW.GLFW_KEY_UNKNOWN;
}
