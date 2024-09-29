package dev.blend.module.impl.movement;

import dev.blend.event.impl.player.PreMotionEvent;
import dev.blend.module.Module;
import dev.blend.module.api.Category;
import dev.blend.module.api.ModuleInfo;
import net.minecraft.client.util.InputUtil;
import org.greenrobot.eventbus.Subscribe;

@ModuleInfo(
        names = "Sprint",
        description = "Makes the player sprint.",
        category = Category.MOVEMENT,
        defaultEnabled = true
)
public class Sprint extends Module {

    @Override
    public void onDisable() {
        mc.options.sprintKey.setPressed(
                InputUtil.isKeyPressed(
                        mc.getWindow().getHandle(),
                        mc.options.sprintKey.getDefaultKey().getCode()
                )
        );
    }

    @Subscribe
    public void onPreMotion(PreMotionEvent event) {
        mc.options.sprintKey.setPressed(true);
    }

}
