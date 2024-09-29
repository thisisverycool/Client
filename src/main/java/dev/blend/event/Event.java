package dev.blend.event;

import dev.blend.Client;

public class Event {
    public void post() {
        Client.instance.getEventBus().post(this);
    }
    public void call() {
        post();
    }
}
