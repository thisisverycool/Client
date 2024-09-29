package dev.blend.mixin.misc;

import dev.blend.Client;
import dev.blend.event.impl.input.KeyEvent;
import dev.blend.util.MC;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard implements MC {

    @Inject(
            method = "onKey",
            at = @At(
                    value = "HEAD"
            )
    )
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (window == mc.getWindow().getHandle() && mc.currentScreen == null)
            Client.instance.getEventBus().post(new KeyEvent(action, key));
    }

}
