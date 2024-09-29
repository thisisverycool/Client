package dev.blend.event.impl.input;

import dev.blend.event.Event;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class KeyEvent extends Event {
    private final int action, key;
}
