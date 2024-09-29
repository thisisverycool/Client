package dev.blend.event.impl.player;

import dev.blend.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PreMotionEvent extends Event {
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;
}
