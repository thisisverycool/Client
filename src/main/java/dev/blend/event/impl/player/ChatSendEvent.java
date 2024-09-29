package dev.blend.event.impl.player;

import dev.blend.event.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatSendEvent extends CancellableEvent {
    private String message;
}
