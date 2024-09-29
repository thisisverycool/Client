package dev.blend.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancellableEvent extends Event {
    private boolean cancelled;
    public void cancel() {
        setCancelled(true);
    }
}
