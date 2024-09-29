package dev.blend.module.api;

import dev.blend.Client;
import dev.blend.event.impl.input.KeyEvent;
import dev.blend.module.Module;
import dev.blend.module.impl.movement.Sprint;
import dev.blend.module.impl.render.ClickGUI;
import lombok.Getter;
import org.greenrobot.eventbus.Subscribe;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Getter
public class ModuleManager {

    private final List<Module> modules = new ArrayList<>();
    private final ClickGUI clickGUI = new ClickGUI();

    public ModuleManager() {
        modules.addAll(Arrays.asList(
                new Sprint(),
                clickGUI
        ));
        modules.sort(Comparator.comparing(Module::getName));
        modules.forEach(m -> m.setKey(m.getModuleInfo().defaultKey()));
        modules.stream().filter(m -> m.getModuleInfo().defaultEnabled()).forEach(m -> m.setEnabled(true));
        Client.instance.getEventBus().register(this);
    }

    @SuppressWarnings("unchecked")
    public <M extends Module> M getModule(final Class<M> module) {
        return (M) modules
                .stream()
                .filter(m -> m.getClass().equals(module))
                .findFirst()
                .orElse(null);
    }

    @SuppressWarnings("unchecked")
    public <M extends Module> M getModule(final String module) {
        return (M) modules
                .stream()
                .filter(m -> m.getName().equalsIgnoreCase(module))
                .findFirst()
                .orElse(null);
    }

    @Subscribe
    public void onKey(KeyEvent event) {
        if (event.getAction() == GLFW.GLFW_PRESS) {
            modules.stream()
                    .filter(m -> m.getKey() == event.getKey())
                    .forEach(Module::toggle);
        }
    }

}
