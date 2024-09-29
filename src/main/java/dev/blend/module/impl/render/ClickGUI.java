package dev.blend.module.impl.render;

import dev.blend.module.Module;
import dev.blend.module.api.Category;
import dev.blend.module.api.ModuleInfo;
import dev.blend.ui.TestGUI;
import org.lwjgl.glfw.GLFW;

@ModuleInfo(
        names = {"ClickGUI", "GUI"},
        description = "Opens a GUI for the user to modify modules and their values.",
        category = Category.RENDER,
        defaultKey = GLFW.GLFW_KEY_RIGHT_SHIFT
)
public class ClickGUI extends Module {

    @Override
    public void onEnable() {
        mc.setScreen(new TestGUI());
    }

    @Override
    public void onDisable() {
        if (mc.currentScreen instanceof TestGUI)
            mc.setScreen(null);
    }
}
