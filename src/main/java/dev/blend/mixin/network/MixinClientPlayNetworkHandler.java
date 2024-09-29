package dev.blend.mixin.network;

import dev.blend.event.impl.player.ChatSendEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {

    @Inject(
            method = "sendChatMessage",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    public void sendMessage(String content, CallbackInfo ci) {
        final ChatSendEvent event = new ChatSendEvent(content);
        event.post();
        if (event.isCancelled())
            ci.cancel();
    }


}
