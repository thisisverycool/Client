package dev.blend.mixin.misc;

import dev.blend.util.render.RenderUtil;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class MixinWindow {

    @Inject(
            method = "onWindowSizeChanged",
            at = @At("TAIL")
    )
    public void resize(long window, int width, int height, CallbackInfo ci) {
        RenderUtil.resize();
    }

}
