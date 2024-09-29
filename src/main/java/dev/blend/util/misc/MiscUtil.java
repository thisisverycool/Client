package dev.blend.util.misc;

import lombok.experimental.UtilityClass;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;

@UtilityClass
public class MiscUtil {

    public int getKeyFromName(String name) {
        switch (name.toLowerCase()) {
            case "rshift":
                return GLFW.GLFW_KEY_RIGHT_SHIFT;
            case "lshift":
                return GLFW.GLFW_KEY_LEFT_SHIFT;
            default:
                try {
                    return InputUtil.fromTranslationKey("key.keyboard." + name.toLowerCase()).getCode();
                } catch (Exception e) {
                    return GLFW.GLFW_KEY_UNKNOWN;
                }
        }
    }
    public String getNameFromKey(int keyCode) {
        if (keyCode == GLFW.GLFW_KEY_UNKNOWN) {
            return "NONE";
        }
        return InputUtil.fromKeyCode(keyCode, -1).getTranslationKey()
                .split("\\.")
                .length > 2 ? String.join("_", Arrays.copyOfRange(InputUtil.fromKeyCode(keyCode, -1).getTranslationKey().split("\\."), 2, InputUtil.fromKeyCode(keyCode, -1).getTranslationKey().split("\\.").length)).toUpperCase() : "";
    }

}
